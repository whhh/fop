/**
 * 文件名		：ESBCommProcessorImpl.java
 * 创建日期	：2013-11-5
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dc.esb.zjc.protocol.security.CheckedResult;
import com.dc.esb.zjc.protocol.security.ESBMacResultBean;
import com.dc.esb.zjc.protocol.security.ESBMacTool;
import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;
import com.fop.framework.exception.FOPRuntimeException;
import com.fop.framework.tcpip.format.FormatConfigContainer;
import com.fop.framework.tcpip.format.IFormat;

/**
 * 描述:TODO.....
 * 
 * @version 1.00
 * @author user
 * 
 */
public class ESBCommProcessorImpl implements CommProcessor{
  
  private static final Log logger = LogFactory.getLog(ESBCommProcessorImpl.class);// 日志记录对象
  
  private final static  String CHECK_SUCCESS = "MAC校验成功";
  
  public void checkPackage(byte[] message) throws Exception {
    //System.out.println(new String(message));
    int messLength = message.length;
    if(messLength < 6){
      //logger.error("报文头长度不等于6位!");
      throw new Exception("报文头长度错误!");
    }
    byte[] head = new byte[6];
    System.arraycopy(message, 0, head, 0, 6);
    Integer size = new Integer(new String(head));
    if(messLength != size.intValue()+6){
      //logger.error("报文头长度与报文体的长度不一致!");
      throw new Exception("读取报文数据失败!");
    }
   }
  
  
  public Object encode(Object originalMessage,String formatId)  {
    logger.info("encode" + formatId);
    logger.info("originalMessage" + originalMessage);
    FOPContext  sendData = null;
    if(!FOPContext.class.isAssignableFrom(originalMessage.getClass())){
      //throws
      logger.info("not FOPContext" + originalMessage.getClass());
    }
    sendData = (FOPContext)originalMessage;
    IFormat i = FormatConfigContainer.getFormat("send" + formatId);
    logger.info("format" + i);
    Document document = DocumentHelper.createDocument();
    Element e = document.addElement("SDOROOT");
    logger.debug(e);
    i.format(e,sendData);
    String mess = e.asXML();
    String _esbKey = (String)sendData.getFieldDataValue("_esbKey");
    //mod by 2013-12-12 修复esbKey日期问题
    String _esbKeyDate = (String)sendData.getFieldDataValue("_esbKeyDate");
    String _esbConsumerId = (String)sendData.getFieldDataValue("_esbConsumerId");
    if(null == _esbKey){
      //throws 
    }
//    System.out.println(mess);
//    int len = String.valueOf(mess.length()).length();

    return ESBMacTool.tcpPackWithMACForConsumer(mess.getBytes(), _esbKeyDate, _esbConsumerId, _esbKey);
    //String encodeMess = addLength(mess);
    // String size = String.valueOf(s.length());

   // byte[] b = encodeMess.getBytes();
//    ChannelBuffer sendMess = ChannelBuffers.buffer(ss.length());
//
//    sendMess.writeBytes(b);
    // e.getChannel().write(sendMess);
    // ctx.sendUpstream(e);
    //return encodeMess.getBytes();
  }
  
  public Object decode(FOPContext context,byte[] message,String formatId) throws FOPException{
    //checkPackage(message);
    //byte[] b = new byte[message.length - 6];
    //System.arraycopy(message, 6, b, 0, b.length);
//    int messlen = message.length;
//    byte[] len = new byte[4];
//    System.arraycopy(message, 6, len, 0, 4);
//    int size = Integer.valueOf(new String(len))+10;
//    int mesize = message.length - size;
//    byte[] b = new byte[mesize];
//    System.arraycopy(message, size, b, 0, b.length);
    
    String _esbKey = (String)context.getFieldDataValue("_esbKey");
    if(null == _esbKey){
      throw new FOPRuntimeException("FOP000350");
    }
    
    ESBMacResultBean mbean = ESBMacTool.tcpCheckDataWithMacForConsumer(message, _esbKey);
    CheckedResult checkedResult = mbean.getResult();
    String checkedMsg = checkedResult.getMsg();
    
    if(CHECK_SUCCESS != checkedMsg){
      logger.error(checkedMsg);
      throw new FOPRuntimeException("FOP000351");
    }

    byte[] b = ESBMacTool.tcpUnpackWithMACForCommon(message); 
    Element ele = null;
    try{
      Document doc = DocumentHelper.parseText(new String(b));
      ele = doc.getRootElement();
      if(null == formatId ){
        formatId = ele.element("SYS_HEAD").elementText("SERVICE_CODE");
      }
    }catch(DocumentException e){
      //LOG 解析失败
      throw new FOPException("FOP000352",e); 
    }
    
    if(formatId == null){
      //log交易ID为空
      return null;
    }
//    Map<String,Object> m = new HashMap<String,Object>(2);
//    m.put("tranCode", formatId);
//    m.put(formatId, ele);
    
    
    //GroupData data = new GroupData(formatId);
    IFormat format = FormatConfigContainer.getFormat("receive" + formatId);
    if(format == null){
      //异常
    }
    format.unFormat(ele, context);
    return formatId;
    }
  
  
  public String addLength(String mess) {
    int size = mess.length();
    int len = String.valueOf(size).length();
    StringBuffer buff = new StringBuffer(size + 6);
    switch (len) {
    case 1:
      buff.append("00000").append(size).append(mess);
      break;
    case 2:
      buff.append("0000").append(size).append(mess);
      break;
    case 3:
      buff.append("000").append(size).append(mess);
      break;
    case 4:
      buff.append("00").append(size).append(mess);
      break;
    case 5:
      buff.append("0").append(size).append(mess);
      break;
    case 6:
      buff.append(size).append(mess);
      break;
    default:// 异常;
    }
    return buff.toString();
  }


  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.CommProcessor#wrapMessagePackage(byte[])
   */
  @Override
  public byte[] wrapMessagePackage(byte[] send) {
    // TODO Auto-generated method stub
    return null;
  }
  

  public void esbMac(){
    //对ESB的响应报文进行MAC校验并返回校验结果和xml报文(服务消费者TCP方式适用)
    
//  对ESB标准XML报文做MAC打包（服务消费者TCP方式适用）
//  参数：
//  xmldata - 原始xml标准报文
//  workday - 工作密钥对应日期，如"20130418"
//  uid - ESB分配给系统的ID编号，如"010101"
//  secuMac_key - 与ESB同步密钥所获得的工作密钥的密文
    //ESBMacTool.tcpPackWithMACForConsumer(arg0, arg1, arg2, arg3);
    //获取对应系统初始密钥,其他正常交易需用同步密钥服务获得的密钥进行加解密
   // ScrtUtil.getDefaultMACKey(String uid);
  }

}

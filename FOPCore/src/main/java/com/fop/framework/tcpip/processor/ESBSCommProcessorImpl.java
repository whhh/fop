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

import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;
import com.fop.framework.tcpip.format.FormatConfigContainer;
import com.fop.framework.tcpip.format.IFormat;

/**
 * 描述:TODO.....
 * 
 * @version 1.00
 * @author user
 * 
 */
public class ESBSCommProcessorImpl implements CommProcessor{
  
  private static final Log logger = LogFactory.getLog(ESBSCommProcessorImpl.class);// 日志记录对象
  
  //private final static  String CHECK_SUCCESS = "MAC校验成功";
  
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
    logger.info("encode beign");
    FOPContext  sendData = null;
    if(!FOPContext.class.isAssignableFrom(originalMessage.getClass())){
      //throws
    }
    sendData = (FOPContext)originalMessage;
    IFormat i = FormatConfigContainer.getFormat("send" + formatId);
    Document document = DocumentHelper.createDocument();
    Element e = document.addElement("SDOROOT");
    i.format(e,sendData);
    
    
    String mess = e.asXML();
    String encodeMess = addLength(mess);
//    System.out.println(mess);
//    int len = String.valueOf(mess.length()).length();

    logger.info("encode end");
    return encodeMess.getBytes();
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
    logger.info("decode beign");
    if(message.length <= 6 ){
      throw new FOPException("FOP000353","报文长度需要大于6位");
    }
    byte[] b = new byte[message.length - 6];
    System.arraycopy(message, 6, b, 0, b.length);

    Element ele = null;
    try{
      context.addFieldData("receiveData", new String(b));
      Document doc = DocumentHelper.parseText(new String(b));
      ele = doc.getRootElement();
      if(null == formatId ){
        formatId = ele.element("SYS_HEAD").elementText("SERVICE_CODE");
      }
    }catch(DocumentException e){
      //LOG 解析失败
      throw new FOPException("FOP000352",e); 
    }
    

    IFormat format = FormatConfigContainer.getFormat("receive" + formatId);
    if(format == null){
      throw new FOPException("FOP000354","找不到相应的format类，id是"+formatId);
    }
    format.unFormat(ele, context);
    logger.info("decode end");
    return formatId;
    }
  
  
  public String addLength(String mess) {
    int size = mess.getBytes().length;
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

}

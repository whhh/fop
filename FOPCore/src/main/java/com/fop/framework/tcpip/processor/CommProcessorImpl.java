/**
 * 文件名		：CommProcessorImpl.java
 * 创建日期	：2011-5-5
 * Copyright (c) 2003-2011 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.processor;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 描述:默认通讯协议处理器实现类<br/>
 * 
 * @version 1.00
 * @author 
 * 
 */
public class CommProcessorImpl  {
	//private Logger logger = TraceLoggerFactory.getLogger(getClass());		//日志记录对象
	
	
	public void checkPackage(byte[] message) throws Exception {
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
	
	public Map<String,Object> readPackage(byte[] message) throws Exception{
	  checkPackage(message);
	  byte[] b = new byte[message.length - 6];
	  System.arraycopy(message, 6, b, 0, b.length);
	  Element ele = null;
	  String tranCode = null;
	  try{
	    Document doc = DocumentHelper.parseText(new String(b));
	    ele = doc.getRootElement();
	    tranCode = ele.element("SYS_HEAD").elementText("SERVICE_CODE");
	  }catch(Exception e){
	    //LOG 解析失败
	    throw new Exception(); 
	  }
	  
	  if(tranCode == null){
	    return null;
	  }
	  Map<String,Object> m = new HashMap<String,Object>(2);
	  m.put("tranCode", tranCode);
	  m.put(tranCode, ele);
	  
	  return m;
	  }

	/**
	 * @see com.fop.framework.tcpip.processor.chinamworld.mbp.comm.access.tcpip.CommProcessor#wrapMessagePackage(byte[])
	 */
	public byte[] wrapMessagePackage(byte[] send) {
		return send;
	}

}

/**
 * 文件名		：CommProcessor.java
 * 创建日期	：2011-4-22
 * Copyright (c) 2003-2011 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.processor;

import com.fop.framework.context.FOPContext;

/**
 * 描述:通讯协议处理器接口<br/>
 * 根据不同的通讯协议对报文数据进行处理
 * 
 * @version 1.00
 * @author lihui
 * 
 */
public interface CommProcessor {
	
	/**
	 * 组装报文数据
	 * 
	 * @param send 组装前的数据包
	 * @return 组装后的报文数据
	 */
	public  byte[] wrapMessagePackage(byte[] send);
	
	/**
	 * 读取报文数据
	 * 
	 * @param inputstream 报文数据流
	 * @return 报文数据
	 * @throws Exception 读取失败异常
	 */
	
	public Object decode(FOPContext context,byte[] message,String formatId) throws Exception;
	
	
	public Object encode(Object originalMessage,String formatId);

}

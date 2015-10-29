/**
 * 文件名		：DeCode.java
 * 创建日期	：2013-10-15
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.handler;


/**
 * 描述:TODO.....
 * 
 * @version 1.00
 * @author user
 * 
 */
public interface MessageHandler extends TCPIPHandler{
  
  Object getMessage();
  
  void setMessage(Object message);
  
  void setFormat(String id);
  
  int getState();
}

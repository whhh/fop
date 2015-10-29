/**
 * 文件名		：TCPIPHandler.java
 * 创建日期	：2013-11-11
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.handler;

import org.jboss.netty.channel.ChannelHandler;

import com.fop.framework.tcpip.processor.CommProcessor;

/**
 * 描述:TODO.....
 * 
 * @version 1.00
 * @author user
 * 
 */
public interface TCPIPHandler extends ChannelHandler {
  
  public CommProcessor getCommProcessor();
  
  public void setCommProcessor(CommProcessor commProcessor);

}

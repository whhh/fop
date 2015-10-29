/**
 * 文件名		：SocketConstant.java
 * 创建日期	：2013-10-14
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.constant;

/**
 * 描述:TODO.....
 * 
 * @version 1.00
 * @author user
 * 
 */
public class SocketConstant {
  
  public final static String REMOTEADDRESS = "remoteAddress";
  
  public final static String TCPNODELAY = "tcpNoDelay";
  
  public final static String KEEPALIVE = "keepAlive";
  
  public final static String REUSEADDRESS = "reuseAddress";
  
  public final static String SOLINGER = "soLinger";
  
  public final static String TRAFFICCLASS = "trafficClass";
  
  public final static String CONNECTTIMEOUTMILLIS = "connectTimeoutMillis";
  
  public final static String RECEIVEBUFFERSIZE = "receiveBufferSize";
  
  public final static String SENDBUFFERSIZE = "sendBufferSize";
  
  public final static String TCPIPHANDLER = "TCPIPHandler";
  
  public final static String CLIENTHANDLER ="com.fop.framework.tcpip.handler.ClientHandler";

  public final static String SERVERHANDLER ="com.fop.framework.tcpip.handler.ServerHandler";

  public final static int HANDLER_FINISH = 1;
  
  public final static int READ_FINISH = 2;
  
  public final static int READ_TIMEOUT = 3;
  
  public final static int FORMAT_EXCEPTION = 4;
  
//  public final int  IPTOS_LOWCOST = 0x02;
//  
//  public final int  IPTOS_RELIABILITY  = 0x04;
//  
//  public final int  IPTOS_THROUGHPUT = 0x08;
//  
//  public final int  IPTOS_LOWDELAY = 0x010;

}

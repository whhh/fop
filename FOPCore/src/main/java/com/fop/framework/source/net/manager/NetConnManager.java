/**
 * 文件名		：NetConnManager.java
 * 创建日期	：2013-9-27
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.net.manager;

import com.fop.framework.source.BaseManager;
import com.fop.framework.tcpip.ClientSocket;

/**
 * 其他系统访问的连接资源管理类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public interface NetConnManager extends BaseManager{
  /**
   * 通过配置的服务器实例得到ClientSock
   * @param configServerBeanId 服务器实例配置Id
   * @return
   */
  public ClientSocket getClientSocket(String configServerBeanId);
  
//  /**
//   * 根据url获取url连接信息
//   * @param url
//   * @return
//   */
//  public HttpURLConnection getByUrl(String url);
//  
//  /**
//   * 根据Ip和端口号获取服务Socket
//   * @param ip
//   * @param port
//   * @return
//   */
//  public Socket getSocket(String ip,String port);
//
//  /**
//   * 从当前的上下文中获取可用的Service
//   * @param serviceName
//   * @return
//   */
//  public Socket getServiceFromContext(String serviceName);
  
}

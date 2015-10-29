/**
 * 文件名		：NetExchangeManager.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.net.manager;

import com.fop.framework.source.BaseManager;
import com.fop.framework.tcpip.ClientSocket;

/**
 * 与其他系统交换的处理
 * 
 * @version 1.00
 * @author syl
 * 
 */
public interface NetExchangeManager extends BaseManager{
  
  /**
   * 与服务器交互，得到结果信息
   * @param clientSocket socket连接
   * @param messageConfigId 报文配置Id
   * @param message 消息内容
   * @param timeOut 超时时间
   * @return 返回结果对象
   */
  public Object getResponseInfo(ClientSocket clientSocket,String messageConfigId,Object message,long timeOut);
  
}

/**
 * 文件名		：NetConnManagerImpl.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.net.manager;

import com.fop.framework.source.BaseContext;
import com.fop.framework.source.net.context.NetConstant;
import com.fop.framework.source.net.context.NetContext;
import com.fop.framework.source.pub.SourceConstant;
import com.fop.framework.tcpip.ClientSocket;

/**
 * 描述:其他系统访问连接信息管理实现类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class NetConnManagerImpl implements NetConnManager{

  @Override
  public void operate(BaseContext netContext) {
    String serviceContainer=(String)netContext.get(NetConstant.CONST_ServiceContainer);
    ClientSocket connInfo=getClientSocket(serviceContainer);
    netContext.put(NetConstant.CONST_connInfo, connInfo);
  }
  
  @Override
  public ClientSocket getClientSocket(String serviceContainer) {
    ClientSocket clientSocket=null;
    clientSocket=(ClientSocket)NetContext.getBean(serviceContainer);
    return clientSocket;
  }
  @Override
  public String getDescription() {
    return SourceConstant.Net_Conn;
  }
}

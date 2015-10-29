/**
 * 文件名		：NetExchangeManagerImpl.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.net.manager;

import com.fop.framework.source.BaseContext;
import com.fop.framework.source.net.context.NetConstant;
import com.fop.framework.source.net.exception.NetRuntimeException;
import com.fop.framework.source.pub.SourceConstant;
import com.fop.framework.tcpip.ClientSocket;

/**
 * 描述:其他系统访问交互执行实现类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class NetExchangeManagerImpl implements NetExchangeManager{
  
  @Override
  public void operate(BaseContext netContext) {
    //获取连接信息
    ClientSocket connInfo=(ClientSocket)netContext.get(NetConstant.CONST_connInfo);
    //报文配置Id
    String messageConfigId=(String)netContext.get(NetConstant.CONST_MessageConfigId);
    // 报文内容
    Object message=netContext.get(NetConstant.CONST_Message); 
    //超时时间
    long timeOut=(Long)netContext.get(NetConstant.CONST_TimeOut);
    Object exchangeRes=getResponseInfo(connInfo, messageConfigId, message, timeOut);
    netContext.put(NetConstant.CONST_exchangeRes, exchangeRes);
  }

  @Override
  public Object getResponseInfo(ClientSocket clientSocket,
      String messageConfigId, Object message, long timeOut) {
    Object resData=null;
    try {
      //发送通讯，得到返回结果信息
      resData=clientSocket.sendAndWait(messageConfigId, message, timeOut);
    } catch (Exception e) {
      throw new NetRuntimeException(e);
    }
    return resData;
  }
  @Override
  public String getDescription() {
    return SourceConstant.Net_Exchange;
  }
}

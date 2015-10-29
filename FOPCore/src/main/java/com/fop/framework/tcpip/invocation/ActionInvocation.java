/**
 * 文件名		：ActionInvocation.java
 * 创建日期	：2013-10-21
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.invocation;

import java.lang.reflect.InvocationHandler;

import com.fop.framework.tcpip.proxy.ActionProxy;

/**
 * 描述:TODO.....
 * 
 * @version 1.00
 * @author user
 * 
 */
public interface ActionInvocation extends InvocationHandler{
  public Object bind(ActionProxy actionProxy);
}

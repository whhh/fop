/**
 * 文件名		：ControlHandlerChain.java
 * 创建日期	：2013-10-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.control.handler;

import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;

/**
 * 描述:策略处理链
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface ControlHandlerChain {

  /**
   * 执行策略处理链
   * @param requestContext
   * @throws FOPException
   */
  public void handlerChain(FOPContext requestContext) throws FOPException;
  
}

/**
 * 文件名		：ControlHandler.java
 * 创建日期	：2013-10-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.control.handler;

import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;

/**
 * 描述:执行策略处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface ControlHandler {

  /**
   * 执行策略处理
   * @param requestContext
   * @throws FOPException
   */
   public void handle(FOPContext requestContext) throws FOPException;
}

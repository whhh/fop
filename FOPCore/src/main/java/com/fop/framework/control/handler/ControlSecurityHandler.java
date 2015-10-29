/**
 * 文件名		：ControlSecurityHandler.java
 * 创建日期	：2013-10-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.control.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;

/**
 * 描述:安全策略处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ControlSecurityHandler implements ControlHandler{

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());
  
  /* (non-Javadoc)
   * @see com.fop.framework.control.handler.ControlHandler#handle(com.fop.framework.context.FOPContext)
   */
  @Override
  public void handle(FOPContext context) throws FOPException {
    logger.debug("----framework: ControlSecurityHandler execute");
  }
}
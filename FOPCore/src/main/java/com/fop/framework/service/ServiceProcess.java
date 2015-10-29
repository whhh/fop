/**
 * 文件名		：ServiceProcess.java
 * 创建日期	：2013-9-16
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service;

import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;

/**
 * 描述:服务装配层入口
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface ServiceProcess {

  /**
   * 服务装配层处理
   * @param serviceContext
   * @throws FOPException
   */
  public void serviceProcess(FOPContext serviceContext) throws FOPException; 
  
}

/**
 * 文件名		：ServiceInterceptor.java
 * 创建日期	：2013-10-17
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service;

import com.fop.framework.context.FOPContext;

/**
 * 描述:在每个Service执行前与执行后做处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface ServiceInterceptor {

  /**
   * Service执行前的处理
   */
  public void beforeService(FOPContext serviceContext);
  
  /**
   * Service执行后的处理
   */
  public void afterService(FOPContext serviceContext);
}

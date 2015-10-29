/**
 * 文件名		：ITradeDotInterceptor.java
 * 创建日期	：2013-9-24
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.trade;

import com.fop.framework.context.FOPContext;

/**
 * 描述: 在每个Dot执行前与执行后做相应处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface ITradeDotInterceptor {
  
  /**
   * 设置Dot标识
   */
  public void setDotId(String dotId);

  /**
   * Dot执行前的处理
   */
  public void beforeDot(FOPContext dotContext);
  
  /**
   * Dot执行后的处理
   */
  public void afterDot(FOPContext dotContext);
}

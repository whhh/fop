/**
 * 文件名		：ControlViewResolver.java
 * 创建日期	：2013-10-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.control;

import com.fop.framework.exception.FOPException;

/**
 * 描述:处理响应视图
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface ControlViewResolver {

  /**
   * 处理响应视图
   * @param viewtype 视图类型
   * @param viewobj  视图对象（或值）
   * @return
   * @throws FOPException
   */
  public Object viewResolve(String viewtype, Object viewobj) throws FOPException;
}

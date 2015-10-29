/**
 * 文件名		：ControlViewResolverImpl.java
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
public class ControlViewResolverImpl implements ControlViewResolver{

  /* (non-Javadoc)
   * @see com.fop.framework.control.ControlViewResolver#viewResolve(java.lang.String, java.lang.Object)
   */
  @Override
  public Object viewResolve(String viewtype, Object viewobj)
      throws FOPException {
    //视图处理
    return null;
  }

}

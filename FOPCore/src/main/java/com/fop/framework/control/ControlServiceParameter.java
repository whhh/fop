/**
 * 文件名		：ControlServiceParameter.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.control;

import java.util.List;

import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;

/**
 * 描述:处理Service的输入、输出参数
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface ControlServiceParameter {

  /**
   * 处理Service的输入参数
   * @param requestContext
   * @param listinput
   * @return
   * @throws FOPException
   */
  @SuppressWarnings("rawtypes")
  public FOPContext inputProcess(FOPContext requestContext, List listinput) throws FOPException;
  
  /**
   * 处理Service的输出参数
   * @param requestContext
   * @param serviceContext
   * @param listinput
   * @param listoutput
   * @throws FOPException
   */
  @SuppressWarnings("rawtypes")
  public void outputProcess(FOPContext requestContext, FOPContext serviceContext, List listinput, List listoutput) throws FOPException;
}

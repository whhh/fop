/**
 * 文件名		：ServiceDotParameter.java
 * 创建日期	：2013-10-12
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service;

import java.util.List;

import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;

/**
 * 描述: Dot出、入参处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface ServiceDotParameter {

  /**
   * 处理Dot的输入参数，将context的已知定义入参值及listinput各项值赋给dotContext对象
   * 对于session数据，使用"s:变量名"形式将数据存入
   * @param serviceContext
   * @return dotContext(FOPContext)
   */
  @SuppressWarnings("rawtypes")
  public FOPContext inputProcess(FOPContext serviceContext, List listinput) throws FOPException;
  
  
  /**
   * 处理Dot的输出参数，在serviceContext中加入listoutput各项值
   * @param context
   * @return
   */
  @SuppressWarnings("rawtypes")
  public void outputProcess(FOPContext serviceContext, FOPContext dotContext, List listinput, List listoutput) throws FOPException;
  
}

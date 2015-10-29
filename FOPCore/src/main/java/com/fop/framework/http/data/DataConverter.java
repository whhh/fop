/**
 * 文件名		：DataConverter.java
 * 创建日期	：2013-9-10
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.http.data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPConvertException;

/**
 * 描述:数据上下文转换
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface DataConverter {
  
  /**
   * 将输入的请求参数，转换成FOPContext形式数据
   * @param request
   * @param context
   * @return
   */
  public Object inContext(HttpServletRequest request, FOPContext context) throws FOPConvertException;
  
  /**
   * 将FOPContext形式数据，转换成响应所需格式参数
   * @param request
   * @param response
   * @param context
   * @return
   */
  public Object outContext(HttpServletRequest request, HttpServletResponse response, FOPContext context) throws FOPConvertException;
}

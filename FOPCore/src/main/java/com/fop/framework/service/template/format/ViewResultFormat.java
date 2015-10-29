/**
 * 文件名		：ViewResultFormat.java
 * 创建日期	：2013-10-9
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.template.format;

import java.util.List;
import java.util.Map;

import com.fop.framework.exception.FOPException;
import com.fop.framework.service.trade.ServiceView;

/**
 * 描述:视图定义解析
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface ViewResultFormat {
  
  /**
   * 视图定义解析
   * @param configViewResult
   * @return
   * @throws FOPException
   */
  @SuppressWarnings("rawtypes")
  public Map<String, ServiceView> format(List configViewResult) throws FOPException;
}

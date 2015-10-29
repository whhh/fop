/**
 * 文件名		：ServiceTemplate.java
 * 创建日期	：2013-9-16
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.template;

import java.util.List;
import java.util.Map;

/**
 * 描述: 交易流程模板接口
 * @version 1.00
 * @author xieyg
 * 
 */
@SuppressWarnings("all")
public interface ServiceTemplate {
  
  /**
   * 获取基本信息
   * @return
   */
  public Map getBasicInfo();

  /**
   * 获取输入要素检查链
   * @return
   */
  public List getCheckChain();
 
  /**
   * 获取业务执行链
   * @return
   */
  public List getDotChain();
 
  /**
   * 获取视图定义
   * @return
   */
  public List getViewResult();
  
}

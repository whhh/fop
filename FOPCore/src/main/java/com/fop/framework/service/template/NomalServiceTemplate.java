/**
 * 文件名		：NomalServiceTemplate.java
 * 创建日期	：2013-9-16
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.template;

import java.util.List;
import java.util.Map;

/**
 * 描述: 正常流程线模板
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
@SuppressWarnings("all")
public class NomalServiceTemplate implements ServiceTemplate{
  /**基本信息*/
  private Map basicInfo;
  /**输入要素检查链*/
  private List<Map> checkChain;
  /**业务执行链*/
  private List<Map> dotChain;
  /**视图定义*/
  private List<Map> viewResult;
  
  public void setBasicInfo(Map basicInfo) {
    this.basicInfo = basicInfo;
  }
  
  public void setCheckChain(List<Map> checkChain) {
    this.checkChain = checkChain;
  }

  public void setDotChain(List<Map> dotChain) {
    this.dotChain = dotChain;
  }

  public void setViewResult(List<Map> viewResult) {
    this.viewResult = viewResult;
  }

  /* (non-Javadoc)
   * @see com.fop.framework.service.ServiceTemplate#getCheckChain()
   */
  @Override
  public List getCheckChain() {
    return checkChain;
  }

  /* (non-Javadoc)
   * @see com.fop.framework.service.ServiceTemplate#getDotChain()
   */
  @Override
  public List getDotChain() {
    return dotChain;
  }

  /* (non-Javadoc)
   * @see com.fop.framework.service.ServiceTemplate#getViewResult()
   */
  @Override
  public List getViewResult() {
    return viewResult;
  }

  /* (non-Javadoc)
   * @see com.fop.framework.service.template.ServiceTemplate#getBasicInfo()
   */
  @Override
  public Map getBasicInfo() {
    return basicInfo;
  }
}

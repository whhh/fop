/**
 * 文件名		：ServiceConfig.java
 * 创建日期	：2013-9-24
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.trade;

import java.util.List;
import java.util.Map;

/**
 * 描述:交易流程对象，
 * 模板解析后的信息赋值给对应的属性，
 * 统一流程执行的数据结构。
 * 
 * @version 1.00
 * @author xieyg
 * 
 */

public class ServiceConfig {
  /**
   * 交易码
   */
  private String tradeId;

  /**基本信息*/
  private ServiceBasicInfo basicInfo;

  /**输入要素检查链*/
  private List<Map<String, ServiceCheck>> checkChain;

  /**业务流程链*/
  private List<Map<String, ServiceDot>> dotChain;

  /**视图定义*/
  private Map<String, ServiceView> viewResult;
  
  /**
   * @return the tradeId
   */
  public String getTradeId() {
    return this.tradeId;
  }
  
  /**
   * @param tradeId the tradeId to set
   */
  public void setTradeId(String tradeId) {
    this.tradeId = tradeId;
  }

  /**
   * @return the checkChain
   */
  public List<Map<String, ServiceCheck>> getCheckChain() {
    return checkChain;
  }

  /**
   * @param checkChain the checkChain to set
   */
  public void setCheckChain(List<Map<String, ServiceCheck>> checkChain) {
    this.checkChain = checkChain;
  }

  /**
   * @return the dotChain
   */
  public List<Map<String, ServiceDot>> getDotChain() {
    return dotChain;
  }

  /**
   * @param dotChain the dotChain to set
   */
  public void setDotChain(List<Map<String, ServiceDot>> dotChain) {
    this.dotChain = dotChain;
  }

  /**
   * @return the viewResult
   */
  public Map<String, ServiceView> getViewResult() {
    return viewResult;
  }

  /**
   * @param viewResult the viewResult to set
   */
  public void setViewResult(Map<String, ServiceView> viewResult) {
    this.viewResult = viewResult;
  }

  /**
   * @return the basicInfo
   */
  public ServiceBasicInfo getBasicInfo() {
    return basicInfo;
  }

  /**
   * @param basicInfo the basicInfo to set
   */
  public void setBasicInfo(ServiceBasicInfo basicInfo) {
    this.basicInfo = basicInfo;
  }
}

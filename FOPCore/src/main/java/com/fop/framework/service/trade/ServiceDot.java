/**
 * 文件名		：ServiceDot.java
 * 创建日期	：2013-9-22
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.trade;

import java.util.List;
import java.util.Map;

/**
 * 描述:流程点配置对象
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ServiceDot {
  /**编号标识*/
  private String id;

  /**描述*/
  private String description;

  /**流程走向标识*/
  private Map<String, String> ret;

  /**所处位置*/
  private int index;

  /**流程走向标识为end情况下的视图名称 */
  private String viewName;

  /**输入参数*/
  private List<TradeInparameter> inparameter;

  /**输出参数*/
  private List<TradeOutparameter> outparameter;

  /**
   * @return the viewName
   */
  public String getViewName() {
    return viewName;
  }

  /**
   * @param viewName the viewName to set
   */
  public void setViewName(String viewName) {
    this.viewName = viewName;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the ret
   */
  public Map<String, String> getRet() {
    return ret;
  }

  /**
   * @param ret the ret to set
   */
  public void setRet(Map<String, String> ret) {
    this.ret = ret;
  }

  /**
   * @return the index
   */
  public int getIndex() {
    return index;
  }

  /**
   * @param index the index to set
   */
  public void setIndex(int index) {
    this.index = index;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the inparameter
   */
  public List<TradeInparameter> getInparameter() {
    return inparameter;
  }

  /**
   * @param inparameter the inparameter to set
   */
  public void setInparameter(List<TradeInparameter> inparameter) {
    this.inparameter = inparameter;
  }

  /**
   * @return the outparameter
   */
  public List<TradeOutparameter> getOutparameter() {
    return outparameter;
  }

  /**
   * @param outparameter the outparameter to set
   */
  public void setOutparameter(List<TradeOutparameter> outparameter) {
    this.outparameter = outparameter;
  }
}

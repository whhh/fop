/**
 * 文件名		：ServiceBasicInfo.java
 * 创建日期	：2013-10-8
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.trade;

import java.util.List;
import java.util.Map;

/**
 * 描述:基本信息配置对象
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
@SuppressWarnings("rawtypes")
public class ServiceBasicInfo {
  /**
   * 交易码
   */
  private String tradeId;
  /**描述*/
  private String description;

  /**输入参数*/
  private List<TradeInparameter> inparameter;

  /**输出参数*/
  private List<TradeOutparameter> outparameter;

  /**其它扩展信息，预留*/
  private Map extInfo;

  /**
   * @return the extInfo
   */
  public Map getExtInfo() {
    return extInfo;
  }

  /**
   * @param extInfo the extInfo to set
   */
  public void setExtInfo(Map extInfo) {
    this.extInfo = extInfo;
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
   * @return the description
   */
  public String getTradeId() {
    return tradeId;
  }

  /**
   * @param description the description to set
   */
  public void setTradeId(String tradeId) {
    this.tradeId = tradeId;
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

/**
 * 文件名		：TradeOutparameter.java
 * 创建日期	：2013-10-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.trade;

/**
 * 描述:输出参数对象
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class TradeOutparameter {

  /**参数名称*/
  private String name; 

  /**参数实际名称（映射参数名称）*/
  private String realname; 

  /**参数值*/
  private String value; 

  /**参数类型*/
  private String type;

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the realname
   */
  public String getRealname() {
    return realname;
  }

  /**
   * @param realname the realname to set
   */
  public void setRealname(String realname) {
    this.realname = realname;
  }

  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /**
   * @param value the value to set
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  } 

}

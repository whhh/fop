/**
 * 文件名		：BaseFormat.java
 * 创建日期	：2013-10-23
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.format;

/**
 * 描述:TODO.....
 * 
 * @version 1.00
 * @author user
 * 
 */
public class BaseFormat {
  
  private String dataName; // 数据名称
  
  private String dataValue; // 数据值

  private String fieldName;// 域名称

  private String fieldValue;// 域值
  
  private String desc;//描述信息

  


  /**
   * @return the dataName
   */
  public String getDataName() {
    return dataName;
  }

  /**
   * @param dataName the dataName to set
   */
  public void setDataName(String dataName) {
    this.dataName = dataName;
  }

  /**
   * @return the dataValue
   */
  public String getDataValue() {
    return dataValue;
  }

  /**
   * @param dataValue the dataValue to set
   */
  public void setDataValue(String dataValue) {
    this.dataValue = dataValue;
  }

  /**
   * @return the fieldName
   */
  public String getFieldName() {
    return fieldName;
  }

  /**
   * @param fieldName the fieldName to set
   */
  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  /**
   * @return the fieldValue
   */
  public String getFieldValue() {
    return fieldValue;
  }

  /**
   * @param fieldValue the fieldValue to set
   */
  public void setFieldValue(String fieldValue) {
    this.fieldValue = fieldValue;
  }

  
  /**
   * @return the desc
   */
  public String getDesc() {
    return desc;
  }

  /**
   * @param desc the desc to set
   */
  public void setDesc(String desc) {
    this.desc = desc;
  }
}

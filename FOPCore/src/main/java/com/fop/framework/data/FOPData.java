/**
 * 文件名		：MBSData.java
 * 创建日期	：2013-8-27
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.data;

/**
 * 描述:统一数据对象
 * 统一数据对象是为统一标准而定义的数据结构
 * 统一数据对象包括三种数据对象: 单个数据对象, 分组数据对象, 重复数据对象
 * 所有的数据信息都可以通过这三种数据对象的组合来进行保存
 * 
 * @version 1.00
 * @author Dustin
 * 
 */
public class FOPData {
  //名称, 唯一标识
  private String name;  
  
  //别名
  private String destName;
  
  //增加标识 true-可增加 false-不可增加 
  private boolean append = false;

  /**
   * 构造函数
   */
  public FOPData() {
  }

  /**
   * 构造函数
   * @param name 名称
   */
  public FOPData(String name) {
    this.name = name;
  }

  /**
   * 克隆对象
   */
  public FOPData clone() {
    FOPData element = new FOPData(this.name);
    element.setAppend(this.append);
    return element;
  }

  /**
   * 重置对象
   */
  public void reset() {
  }

  /**
   * 转换成字符串
   * @return 字符串
   */
  public String toString() {
    return toString(0);
  }

  /**
   * 转换成字符串
   * @param tabCount 数目
   * @return 字符串
   */
  public String toString(int tabCount) {
    StringBuffer buf = new StringBuffer();
    
    for (int i = 0; i < tabCount; i++)
      buf.append("\t");
      buf.append("<FOPData id=\"");
      buf.append(this.name);
      
      if (((this instanceof GroupData)) || ((this instanceof RepeatData))) {
        buf.append("\" append=\"" + this.append + "\"/>");
      } else {
        buf.append("\"/>");
      }
      
      return buf.toString();
    }

  /**
   * 设置名称
   * @param id 名称
   */
  public void setId(String id) {
    this.name = id;
  }
  
  /**
   * 设置名称
   * @param name 名称
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 获取名称
   * @return 名称
   */
  public String getName() {
    return this.name;
  }

  /**
   * 设置别名
   * @param destName 别名
   */
  public void setDestName(String destName) {
    this.destName = destName;
  }
  
  /**
   * 获取别名
   * @return 别名
   */
  public String getDestName() {
    return this.destName;
  }
  
  /**
   * 设置增加标识
   * @param value 增加标识
   */
  public void setAppend(boolean value) {
    this.append = value;
  }

  /**
   * 设置增加标识
   * @param value 增加标识
   */
  public void setAppend(String append) {
    setAppend("true".equals(append));
  }
  
  /**
   * 获取增加标识
   * @return 增加标识
   */
  public boolean isAppend() {
    return this.append;
  }

}

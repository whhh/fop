/**
 * 文件名		：FormatConfigContainer.java
 * 创建日期	：2011-4-25
 * Copyright (c) 2003-2011 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.format;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述:报文格式化配置容器<br/>
 * 保存所有的报文格式化配置
 * 配置在Spring容器中，请配置单例
 * 
 * @version 1.00
 * @author user
 * 
 */
public class FormatConfigContainer {

  /**
   * 格式化类容器
   */
  public static Map<String, IFormat> formats = new HashMap<String, IFormat>();

  private Map<String, String> defines; // 格式化元素容器

  private Map<String, String> configs; // 格式化配置文件地址容器

  /**
   * 获取格式化类实例
   * 
   * @param fmtName
   *          格式化类名称
   * @return 格式化类实例
   */
  public static IFormat getFormat(String fmtName) {
    return formats.get(fmtName);
  }

  /**
   * 移除配置对象
   * 
   * @param fmtName
   *          配置名称
   */
  public static void removeFormat(String fmtName) {
    formats.remove(fmtName);
  }

  /**
   * 添加配置对象
   * 
   * @param fmtName
   *          配置名称
   * @param element
   *          配置对象
   */
  public static void addFormat(String fmtName, IFormat element) {
    formats.put(fmtName, element);
  }

  /**
   * 获取格式化元素容器
   * 
   * @return
   */
  public Map<String, String> getDefines() {
    return defines;
  }

  /**
   * 设置格式化元素容器
   * 
   * @param defines
   * 
   */
  public void setDefines(Map<String, String> defines) {
    this.defines = defines;
  }

  /**
   * 获取格式化配置文件地址容器
   * 
   * @return
   */
  public Map<String, String> getConfigs() {
    return configs;
  }

  /**
   * 设置格式化配置文件地址容器
   * 
   * @param configs
   */
  public void setConfigs(Map<String, String> configs) {
    this.configs = configs;
  }
}

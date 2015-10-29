/**
 * 文件名		：ServiceSessionDataConfig.java
 * 创建日期	：2013-10-9
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service;

import java.util.Map;

/**
 * 描述:交易会话数据配置
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ServiceSessionDataConfig {
  /**交易会话数据配置*/
  private Map<String, String> configs;
  
  /**
   * 获取交易会话数据配置
   * @return the configs
   */
  public Map<String, String> getConfigs() {
    return configs;
  }

  /**
   * 设置交易会话数据配置
   * @param configs the configs to set
   */
  public void setConfigs(Map<String, String> configs) {
    this.configs = configs;
  }
}

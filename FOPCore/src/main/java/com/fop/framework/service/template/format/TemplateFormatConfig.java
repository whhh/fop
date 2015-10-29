/**
 * 文件名		：TemplateFormatConfig.java
 * 创建日期	：2013-9-22
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.template.format;

import java.util.Map;

/**
 * 描述:流程模板解析配置
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class TemplateFormatConfig {
  
  /**流程模板解析配置*/
  private Map<String, Object> configs;
  
  /**
   * 获取流程模板解析配置
   * @return the configs
   */
  public Map<String, Object> getConfigs() {
    return configs;
  }

  /**
   * 设置流程模板解析配置
   * @param configs the configs to set
   */
  public void setConfigs(Map<String, Object> configs) {
    this.configs = configs;
  }

}

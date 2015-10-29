/**
 * 文件名		：ClientSimulatorConfig.java
 * 创建日期	：2013-12-24
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.tcpip.simulator;

import java.util.Map;

/**
 * 描述: 挡板交易配置
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ClientSimulatorConfig {
  
  /**挡板交易配置*/
  private Map<String, String> configs;
  
  /**
   * 获取挡板交易配置
   * @return the configs
   */
  public Map<String, String> getConfigs() {
    return configs;
  }

  /**
   * 设置挡板交易配置
   * @param configs the configs to set
   */
  public void setConfigs(Map<String, String> configs) {
    this.configs = configs;
  }
}

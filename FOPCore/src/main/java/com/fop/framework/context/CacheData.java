/**
 * 文件名		：CacheData.java
 * 创建日期	：2013-11-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.context;

import java.util.List;

/**
 * 描述: 缓存数据接口
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
@SuppressWarnings("rawtypes")
public interface CacheData {

  /**
   * 获取配置
   * @return
   */
  public List getConfigs();
  
  /**
   * 加载模式： lazy.使用的时候加载  eager.启动时加载
   * @return
   */
  public String getCachemode();
  
  /**
   * 加载处理
   * @return
   */
  public void load();
  
  /**
   * 设置加载标识
   * @return
   */
  public void setIsload(boolean isload);
}

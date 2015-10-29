/**
 * 文件名		：FOPDataProcess.java
 * 创建日期	：2013-10-24
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.context;

/**
 * 描述:初始化、结束数据处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface FOPDataProcess {

  /**
   * 初始化信息处理
   */
  public void init();
  
  /**
   * 结束信息处理
   */
  public void destroy();
}

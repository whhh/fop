/**
 * 文件名		：AbstractRecordLog.java
 * 创建日期	：2013-9-17
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.log;

import java.util.Map;

/**
 * 描述: 操作日志记录父类
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public abstract class AbstractRecordLog implements RecordLog{
  
  /**
   * 需要记录日志的服务
   */
  protected Map<String, String> logTrades;

  /**
   * setter logTrades
   * @param logTrades
   */
  public void setLogTrades(Map<String, String> logTrades) {
    this.logTrades = logTrades;
  }

  /**
   * getter logTrades
   * @return
   */
  public Map<String, String> getLogTrades() {
    return logTrades;
  }
  
}

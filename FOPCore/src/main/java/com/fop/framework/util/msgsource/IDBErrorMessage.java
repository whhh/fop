/**
 * 文件名		：IDBErrorMessage.java
 * 创建日期	：2013-11-18
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.util.msgsource;

/**
 * 描述:数据库错误信息
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface IDBErrorMessage {
  
  /**转义标志：1:已转义*/
  public static final String MSG_FLAG_TRUE = "1";
  
  /**转义标志：2:未转义*/
  public static final String MSG_FLAG_FALSE = "2";
  
  /**
   * 获取错误码
   * @return
   */
  public String getErrcode();

  /**
   * 获取错误信息
   * @return
   */
  public String getErrmsg();

  /**
   * 获取转义标志 1:已转义 2:未转义
   * @return
   */
  public String getMsgflag();

  /**
   * 获取转义错误信息
   * @return
   */
  public String getOtherErrmsg();
  
  /**
   * 获取显示语言
   * @return
   */
  public String getSlanguage();
}

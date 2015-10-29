/**
 * 文件名		：InvalidArgumentException.java
 * 创建日期	：2013-8-27
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.data;

/**
 * 描述:参数错误异常
 * 
 * @version 1.00
 * @author Dustin
 * 
 */
@SuppressWarnings("serial")
public class InvalidArgumentException extends RuntimeException {

  /**
   * 构造函数
   * @param message 错误信息
   */
  public InvalidArgumentException(String message) {
    super(message);
  }
}

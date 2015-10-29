/**
 * 文件名		：ObjectNotFoundException.java
 * 创建日期	：2013-8-27
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.data;

/**
 * 描述:对象未找到异常
 * 
 * @version 1.00
 * @author Dustin
 * 
 */
@SuppressWarnings("serial")
public class ObjectNotFoundException extends RuntimeException {

  /**
   * 构造函数
   * @param message 异常信息
   */
  public ObjectNotFoundException(String message) {
    super(message);
  }
}

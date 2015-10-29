/**
 * 文件名		：DBRuntimeException.java
 * 创建日期	：2013-10-12
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.exception;

import com.fop.framework.exception.FOPRuntimeException;

/**
 * 描述:数据库运行时操作异常类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class DBRuntimeException extends FOPRuntimeException{
  private static final long serialVersionUID = -9134740816091221576L;
  
  /**
   * @param errorCode 异常代码
   */
  public DBRuntimeException(String errorCode) {
    super(errorCode);
  }

  /**
   * 
   * @param cause 异常对象
   */
  public DBRuntimeException(Throwable cause) {
    super(cause);
  }

  /**
   * @param errorCode 异常代码
   * @param cause 异常对象
   */
  public DBRuntimeException(String errorCode, Throwable cause) {
    super(errorCode,cause);
  }

//  /**
//   * @param errorCode 异常代码
//   */
//  public DBRuntimeException(String errorCode, String defaultErrMessage) {
//    super(errorCode,defaultErrMessage);
//  }
  
}

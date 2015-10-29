/**
 * 文件名		：DBException.java
 * 创建日期	：2013-10-12
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.exception;

import com.fop.framework.exception.FOPException;

/**
 * 描述:数据库资源异常处理类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class DBException extends FOPException{
  /**
   * 
   */
  private static final long serialVersionUID = -6698544367893289590L;

  public DBException() {
    super();
  }

  /**
   * @param errorCode 异常代码
   */
  public DBException(String errorCode) {
    super(errorCode);
  }

  /**
   * 
   * @param cause 异常对象
   */
  public DBException(Throwable cause) {
    super(cause);
  }

  /**
   * @param errorCode 异常代码
   * @param cause 异常对象
   */
  public DBException(String errorCode, Throwable cause) {
    super(errorCode, cause);
  }

}

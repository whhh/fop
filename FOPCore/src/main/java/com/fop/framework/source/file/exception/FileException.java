/**
 * 文件名		：FileException.java
 * 创建日期	：2013-10-12
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.exception;

import com.fop.framework.exception.FOPException;

/**
 * 描述:文件资源异常处理类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class FileException extends FOPException{

  /**
   * 
   */
  private static final long serialVersionUID = -4595585213780720759L;

  public FileException() {
    super();
  }

  /**
   * @param errorCode 异常代码
   */
  public FileException(String errorCode) {
    super(errorCode);
  }

  /**
   * 
   * @param cause 异常对象
   */
  public FileException(Throwable cause) {
    super(cause);
  }

  /**
   * @param errorCode 异常代码
   * @param cause 异常对象
   */
  public FileException(String errorCode, Throwable cause) {
    super(errorCode, cause);
  }

}

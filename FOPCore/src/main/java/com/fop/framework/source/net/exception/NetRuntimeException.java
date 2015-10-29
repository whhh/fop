/**
 * 文件名		：NetRuntimeException.java
 * 创建日期	：2013-10-12
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.net.exception;

import com.fop.framework.exception.FOPRuntimeException;

/**
 * 描述:其他系统资源运行时操作异常类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class NetRuntimeException extends FOPRuntimeException{
  

  /**
   * 
   */
  private static final long serialVersionUID = -8685472422482598655L;

  /**
   * @param errorCode 异常代码
   */
  public NetRuntimeException(String errorCode) {
    super(errorCode);
  }

  /**
   * 
   * @param cause 异常对象
   */
  public NetRuntimeException(Throwable cause) {
    super(cause);
  }

  /**
   * @param errorCode 异常代码
   * @param cause 异常对象
   */
  public NetRuntimeException(String errorCode, Throwable cause) {
    super(errorCode,cause);
  }

//  /**
//   * @param errorCode 异常代码
//   */
//  public NetRuntimeException(String errorCode, String defaultErrMessage) {
//    super(errorCode,defaultErrMessage);
//  }
  
}

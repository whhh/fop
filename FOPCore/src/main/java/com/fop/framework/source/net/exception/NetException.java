/**
 * 文件名		：NetException.java
 * 创建日期	：2013-10-12
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.net.exception;

import com.fop.framework.exception.FOPException;

/**
 * 描述:其他系统资源异常处理类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class NetException extends FOPException{

  /**
   * 
   */
  private static final long serialVersionUID = -4050242385935888221L;

  public NetException() {
    super();
  }

  /**
   * @param errorCode 异常代码
   */
  public NetException(String errorCode) {
    super(errorCode);
  }

  /**
   * 
   * @param cause 异常对象
   */
  public NetException(Throwable cause) {
    super(cause);
  }

  /**
   * @param errorCode 异常代码
   * @param cause 异常对象
   */
  public NetException(String errorCode, Throwable cause) {
    super(errorCode, cause);
  }

}

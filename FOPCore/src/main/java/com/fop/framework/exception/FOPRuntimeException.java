/**
 * 文件名		：FOPRuntimeException.java
 * 创建日期	：2013-9-10
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.exception;

import com.fop.framework.util.StringUtil;
import com.fop.framework.util.MsgSourceUtil;

/**
 * 描述:自定义异常,Type is Runtime.
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class FOPRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 4056460454281408170L;

  /** 默认系统错误代码 */
  private static final String DEFAULT_ERR_CODE = "FOP000004";

  /** 错误代码 */
  private String errCode;

  /** 错误消息 */
  private String errMessage;

  public FOPRuntimeException() {
    this(DEFAULT_ERR_CODE);
  }

  /**
   * @param errorCode 异常代码
   */
  public FOPRuntimeException(String errorCode) {
    super(errorCode);
    errCode = errorCode;
    errMessage = MsgSourceUtil.getMessage(errorCode);
    if (errMessage == null){
      errMessage = "";
    }
  }

  /**
   * 
   * @param cause 异常对象
   */
  public FOPRuntimeException(Throwable cause) {
    this(DEFAULT_ERR_CODE, cause);
  }

  /**
   * @param errorCode 异常代码
   * @param cause 异常对象
   */
  public FOPRuntimeException(String errorCode, Throwable cause) {
    super(errorCode, cause);
    errCode = errorCode;
    errMessage = MsgSourceUtil.getMessage(errorCode);
    if (errMessage == null){
      errMessage = "";
    }
  }

  /**
   * @param errorCode 异常代码
   */
  public FOPRuntimeException(String errorCode, String defaultErrMessage) {
    super(errorCode);
    errCode = errorCode;
    errMessage = MsgSourceUtil.getMessage(errorCode);
    if (defaultErrMessage == null){
      defaultErrMessage = "";
    }
    if (StringUtil.isStrEmpty(errMessage)){ //未取到，使用默认消息
      errMessage = defaultErrMessage; 
    }
  }

  /**
   * @return the errCode
   */
  public String getErrCode() {
    return errCode;
  }

  /**
   * @return the errMessage
   */
  public String getErrMessage() {
    return errMessage;
  }

}

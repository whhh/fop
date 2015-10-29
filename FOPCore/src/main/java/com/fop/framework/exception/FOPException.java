/**
 * 文件名		：FOPException.java
 * 创建日期	：2013-9-10
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.exception;

import com.fop.framework.util.StringUtil;
import com.fop.framework.util.MsgSourceUtil;

/**
 * 描述: 自定义异常
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class FOPException extends Exception{

  private static final long serialVersionUID = -2301442267256747088L;

  /** 默认系统错误代码 */
  private static final String DEFAULT_ERR_CODE = "FOP000003";

  /** 错误代码 */
  private String errCode;

  /** 错误消息 */
  private String errMessage;

  public FOPException() {
    this(DEFAULT_ERR_CODE);
  }

  /**
   * @param errorCode 异常代码
   */
  public FOPException(String errorCode) {
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
  public FOPException(Throwable cause) {
    this(DEFAULT_ERR_CODE, cause);
  }

  /**
   * @param errorCode 异常代码
   * @param cause 异常对象
   */
  public FOPException(String errorCode, Throwable cause) {
    super(errorCode, cause);
    errCode = errorCode;
    errMessage = MsgSourceUtil.getMessage(errorCode);
    if (errMessage == null){
      errMessage = "";
    }
  }

  /**
   * @param errorCode 异常代码
   * @param defaultErrMessage 默认异常信息
   */
  public FOPException(String errorCode, String defaultErrMessage) {
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

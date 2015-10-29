/**
 * 文件名		：ValidateBody.java
 * 创建日期	：2013-9-17
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.validate;

import com.fop.framework.util.MsgSourceUtil;
import com.fop.framework.util.StringUtil;

/**
 * 描述:验证异常信息
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ValidateBody {

  /** 错误代码 */
  private String errCode;

  /** 错误消息 */
  private String errMessage;

  public ValidateBody()
  {
  }

  public ValidateBody(String errorCode)
  {
    errCode = errorCode;
    errMessage = MsgSourceUtil.getMessage(errCode);
    if (errMessage == null){
      errMessage = "";
    }
  }

  /**
   * 
   * @param errCode 异常代码
   * @param defaultErrMessage 默认异常信息
   */
  public ValidateBody(String errorCode, String defaultErrMessage)
  {
    errCode = errorCode;
    errMessage = MsgSourceUtil.getMessage(errCode);
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
   * @param errCode the errCode to set
   */
  public void setErrCode(String errCode) {
    this.errCode = errCode;
  }

  /**
   * @return the errMessage
   */
  public String getErrMessage() {
    return errMessage;
  }

  /**
   * @param errMessage the errMessage to set
   */
  public void setErrMessage(String errMessage) {
    this.errMessage = errMessage;
  }

  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "{\"errorCode\":\"" + errCode + "\",\"errorMessage\":\"" + errMessage + "\"}";
  }
}

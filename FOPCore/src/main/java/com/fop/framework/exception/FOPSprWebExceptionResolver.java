/**
 * 文件名		：FOPSprWebExceptionResolver.java
 * 创建日期	：2013-9-10
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 描述:MVC流程异常处理解析
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class FOPSprWebExceptionResolver extends SimpleMappingExceptionResolver {

  /**日志对象*/
  private Log logger = LogFactory.getLog(FOPSprWebExceptionResolver.class);

  protected ModelAndView doResolveException(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      java.lang.Object o, java.lang.Exception e) {
    String errCode = "";
    String errMessage = "";
    if (e instanceof FOPException) { // 类型是FOPException
      FOPException eobj = (FOPException) e;
      errCode = eobj.getErrCode();
      errMessage = eobj.getErrMessage();
      logger.error("FOPException----errCode is: " + errCode);
      logger.error("FOPException----errMessage is: " + errMessage);
      httpServletRequest.setAttribute("exceptionobj", eobj);
    } else if (e instanceof FOPRuntimeException) { // 类型是FOPRuntimeException
      FOPRuntimeException eobj = (FOPRuntimeException) e;
      errCode = eobj.getErrCode();
      errMessage = eobj.getErrMessage();
      logger.error("FOPRuntimeException----errCode is: " + errCode);
      logger.error("FOPRuntimeException----errMessage is: " + errMessage);
      httpServletRequest.setAttribute("exceptionobj", eobj);
    } else {
      //转换为类型是FOPException
      FOPException eobj = new FOPException((Throwable) e);
      errCode = eobj.getErrCode();
      logger.error("FOPException----the " + errCode + " is defaultErrCode");
      httpServletRequest.setAttribute("exceptionobj", eobj);
    }
    logger.error("----error e:" + e.getMessage(), e);
    return super.doResolveException(httpServletRequest,
        httpServletResponse, o, e);
  }
}

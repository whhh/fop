/**
 * 文件名		：ServiceInterceptorImpl.java
 * 创建日期	：2013-10-17
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.context.FOPContext;

/**
 * 描述:在每个Service执行前与执行后做处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ServiceInterceptorImpl implements ServiceInterceptor{

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());
  
  /** 开始时间 */
  private long startTime;
  
  /** 结束时间 */
  private long endTime;
  
  /** Service标识  */
  private String serviceId;

  /* (non-Javadoc)
   * @see com.fop.framework.service.ServiceInterceptor#beforeService(com.fop.framework.context.FOPContext)
   */
  @Override
  public void beforeService(FOPContext serviceContext) {
    startTime = System.currentTimeMillis();
    serviceId = String.valueOf(serviceContext.getFieldDataValue(FOPConstants.TRADE_ID));
  }

  /* (non-Javadoc)
   * @see com.fop.framework.service.ServiceInterceptor#afterService(com.fop.framework.context.FOPContext)
   */
  @Override
  public void afterService(FOPContext serviceContext) {
    endTime = System.currentTimeMillis();
    //打印所使用的执行时间
    logger.info("----framework: " + serviceId + " process used time: " + (endTime - startTime) + "ms");
  }

 
}

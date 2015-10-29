/**
 * 文件名		：ITradeDotInterceptorImpl.java
 * 创建日期	：2013-9-24
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.trade;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.context.FOPContext;

/**
 * 描述:在每个Dot执行前与执行后做相应处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ITradeDotInterceptorImpl implements ITradeDotInterceptor{

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());

  /** 开始时间 */
  private long startTime;

  /** 结束时间 */
  private long endTime;

  /** Dot标识  */
  private String dotId;


  /* (non-Javadoc)
   * @see com.fop.framework.trade.ITradeDotInterceptor#setDotName(java.lang.String)
   */
  @Override
  public void setDotId(String dotId) {
    this.dotId = dotId;
  }


  /* (non-Javadoc)
   * @see com.fop.framework.service.trade.ITradeDotInterceptor#beforeDot(com.fop.framework.context.FOPContext)
   */
  @Override
  public void beforeDot(FOPContext dotContext) {
    startTime = System.currentTimeMillis();
  }

  /* (non-Javadoc)
   * @see com.fop.framework.service.trade.ITradeDotInterceptor#afterDot(com.fop.framework.context.FOPContext)
   */
  @Override
  public void afterDot(FOPContext dotContext) {
    endTime = System.currentTimeMillis();
    //打印所使用的单个点执行时间
    logger.info("----framework: " + dotId + " process used time: " + (endTime - startTime) + "ms");
  }
}

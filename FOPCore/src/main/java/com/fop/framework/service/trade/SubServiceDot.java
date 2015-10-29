/**
 * 文件名		：SubServiceDot.java
 * 创建日期	：2013-9-25
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.trade;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;

/**
 * 描述:Service子流程 处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class SubServiceDot implements ITradeDot {
  
  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());

  public void process(FOPContext dotContext) throws FOPException{
    logger.warn("------SubServiceDot.process 暂不支持");
  }
}

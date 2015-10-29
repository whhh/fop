/**
 * 文件名		：ServiceDotChain.java
 * 创建日期	：2013-9-22
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service;

import java.util.List;
import java.util.Map;

import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;
import com.fop.framework.service.trade.ServiceDot;
import com.fop.framework.service.trade.ServiceView;

/**
 * 描述:执行业务流程接口
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface ServiceDotChain {

  /**
   * 业务流程执行
   * @param listDotChain
   * @param mapViewResult
   * @param context
   * @throws FOPException
   */
  public void process(List<Map<String, ServiceDot>> listDotChain, 
      Map<String, ServiceView> mapViewResult, 
      FOPContext serviceContext) throws FOPException;
}

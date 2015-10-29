/**
 * 文件名		：ServiceCheckChain.java
 * 创建日期	：2013-9-22
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service;

import java.util.List;
import java.util.Map;

import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPValidateException;
import com.fop.framework.service.trade.ServiceCheck;

/**
 * 描述:执行输入要素检查接口
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface ServiceCheckChain {

  /**
   * 执行输入要素检查接口
   * @param listCheckChain
   * @param serviceContext
   * @throws FOPValidateException
   */
  public void process(List<Map<String, ServiceCheck>> listCheckChain, FOPContext serviceContext) throws FOPValidateException;
}

/**
 * 文件名		：DotChainFormat.java
 * 创建日期	：2013-10-9
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.template.format;

import java.util.List;
import java.util.Map;

import com.fop.framework.exception.FOPException;
import com.fop.framework.service.trade.ServiceDot;

/**
 * 描述:交易流程链解析
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface DotChainFormat {

  /**
   * 交易流程链解析
   * @param configDotChain
   * @return
   * @throws FOPException
   */
  @SuppressWarnings("rawtypes")
  public List<Map<String, ServiceDot>> format(List configDotChain) throws FOPException;
}

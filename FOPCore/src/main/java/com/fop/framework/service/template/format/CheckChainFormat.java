/**
 * 文件名		：CheckChainFormat.java
 * 创建日期	：2013-10-9
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.template.format;

import java.util.List;
import java.util.Map;

import com.fop.framework.exception.FOPException;
import com.fop.framework.service.trade.ServiceCheck;

/**
 * 描述:输入要素检查链解析
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface CheckChainFormat {

  /**
   * 输入要素检查链解析
   * @param configCheckChain
   * @return
   * @throws FOPException
   */
  @SuppressWarnings("rawtypes")
  public List<Map<String, ServiceCheck>> format(List configCheckChain) throws FOPException;
}

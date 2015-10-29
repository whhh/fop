/**
 * 文件名		：InparameterFormat.java
 * 创建日期	：2013-10-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.template.format;

import java.util.List;

import com.fop.framework.exception.FOPException;
import com.fop.framework.service.trade.TradeInparameter;

/**
 * 描述:输入参数解析
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface InparameterFormat {

  /**
   * 输入参数解析
   * @param input 入参
   * @return
   * @throws FOPException
   */
  public List<TradeInparameter> format(String input) throws FOPException;
  
}

/**
 * 文件名		：BasicInfoFormat.java
 * 创建日期	：2013-10-9
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.template.format;

import java.util.Map;

import com.fop.framework.exception.FOPException;
import com.fop.framework.service.trade.ServiceBasicInfo;

/**
 * 描述:基本信息解析
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface BasicInfoFormat {
  /**
   * 基本信息解析
   * @param mapBasicInfo
   * @return
   * @throws FOPException
   */
  @SuppressWarnings("rawtypes")
  public ServiceBasicInfo format(Map mapBasicInfo) throws FOPException;
}

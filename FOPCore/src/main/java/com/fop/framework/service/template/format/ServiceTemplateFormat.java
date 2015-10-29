/**
 * 文件名		：ServiceTemplateFormat.java
 * 创建日期	：2013-9-22
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.template.format;

import com.fop.framework.exception.FOPException;
import com.fop.framework.service.template.ServiceTemplate;
import com.fop.framework.service.trade.ServiceConfig;

/**
 * 描述:交易流程模板解析接口
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface ServiceTemplateFormat {

  /**
   * 交易流程模板解析
   * @param ServiceConfig
   * @return
   * @throws FOPException
   */
  public ServiceConfig format(ServiceTemplate templateConfig) throws FOPException;
}

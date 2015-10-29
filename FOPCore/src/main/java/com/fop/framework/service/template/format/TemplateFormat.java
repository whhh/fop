/**
 * 文件名		：TemplateFormat.java
 * 创建日期	：2013-9-23
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.template.format;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.exception.FOPException;
import com.fop.framework.service.template.ServiceTemplate;
import com.fop.framework.service.trade.ServiceConfig;

/**
 * 描述:流程模板解析
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class TemplateFormat {
  
  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());
  
  /**解析关系配置**/
  private TemplateFormatConfig templateFormatConfig;
  
  public ServiceConfig format(Object templateConfig) throws FOPException {
    //解析器名称
    String formatName = templateConfig.getClass().getSimpleName();
    logger.debug("----framework: formatName is: " + formatName);
    //取得解析关系配置
    Map<String, Object> configs = templateFormatConfig.getConfigs();
    //以解析器名称做为key值取出对应的实例
    ServiceTemplateFormat tlineFormat =  (ServiceTemplateFormat)configs.get(formatName);
    //解析配置模板
    ServiceConfig Service = tlineFormat.format((ServiceTemplate)templateConfig);
    return Service;
  }

  /**
   * @param templateFormatConfig the templateFormatConfig to set
   */
  public void setTemplateFormatConfig(TemplateFormatConfig templateFormatConfig) {
    this.templateFormatConfig = templateFormatConfig;
  }
}

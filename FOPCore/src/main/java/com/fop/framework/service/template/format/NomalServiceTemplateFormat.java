/**
 * 文件名		：NomalServiceTemplateFormat.java
 * 创建日期	：2013-9-22
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.template.format;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.exception.FOPException;
import com.fop.framework.service.template.ServiceTemplate;
import com.fop.framework.service.trade.ServiceBasicInfo;
import com.fop.framework.service.trade.ServiceCheck;
import com.fop.framework.service.trade.ServiceConfig;
import com.fop.framework.service.trade.ServiceDot;
import com.fop.framework.service.trade.ServiceView;


/**
 * 描述:标准交易流程模板解析
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
@SuppressWarnings("rawtypes")
public class NomalServiceTemplateFormat implements ServiceTemplateFormat{

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());

  /**流程模板解析配置*/
  private Map<String, Object> configs;

  @Override
  public ServiceConfig format(ServiceTemplate templateConfig) throws FOPException {
    //------------1.定义ServiceConfig------------
    //定义ServiceConfig对象
    ServiceConfig service = new ServiceConfig();

    try {
      //------------2.BasicInfo设置------------
      //取得mapBasicInfo
      Map mapBasicInfo = templateConfig.getBasicInfo();
      //取得basicInfo
      ServiceBasicInfo basicInfo = ((BasicInfoFormat)configs.get(FOPConstants.TEMPLATE_FORMAT_BASICINFO)).format(mapBasicInfo);
      //ServiceConfig对象设置basicInfo
      service.setBasicInfo(basicInfo);
      //交易码
      service.setTradeId(basicInfo.getTradeId());
      logger.debug("----framework: BasicInfo format finish");

      //------------3.CheckChain设置------------
      //取得configCheckChain
      List configCheckChain = templateConfig.getCheckChain();
      //取得checkChain
      List<Map<String, ServiceCheck>> checkChain = ((CheckChainFormat)configs.get(FOPConstants.TEMPLATE_FORMAT_CHECKCHAIN)).format(configCheckChain);
      //ServiceConfig对象设置checkChain
      service.setCheckChain(checkChain);
      logger.debug("----framework: CheckChain format finish");

      //------------4.DotChain设置------------
      //取得configDotChain
      List configDotChain = templateConfig.getDotChain();
      //取得dotChain
      List<Map<String, ServiceDot>> dotChain = ((DotChainFormat)configs.get(FOPConstants.TEMPLATE_FORMAT_DOTCHAIN)).format(configDotChain);
      //ServiceConfig对象设置dotChain
      service.setDotChain(dotChain);
      logger.debug("----framework: DotChain format finish");

      //------------5.ViewResult设置------------
      //取得configViewResult
      List configViewResult = templateConfig.getViewResult();
      //取得viewResult
      Map<String, ServiceView> viewResult = ((ViewResultFormat)configs.get(FOPConstants.TEMPLATE_FORMAT_VIEWRESULT)).format(configViewResult);
      //ServiceConfig对象设置viewResult
      service.setViewResult(viewResult);
      logger.debug("----framework: ViewResult format finish");
    } catch (FOPException ex) {
      throw ex;
    } catch (Exception e) {
      logger.error("----framework: error:" + e.getMessage(), e);
      throw new FOPException("FOP000514", "模板解析出错");
    }

    return service;
  }

  /**
   * 获取流程模板解析配置
   * @return the configs
   */
  public Map<String, Object> getConfigs() {
    return configs;
  }

  /**
   * 设置流程模板解析配置
   * @param configs the configs to set
   */
  public void setConfigs(Map<String, Object> configs) {
    this.configs = configs;
  }
}

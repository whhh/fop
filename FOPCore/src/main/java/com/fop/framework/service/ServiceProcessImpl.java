/**
 * 文件名		：ServiceProcessImpl.java
 * 创建日期	：2013-9-16
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;
import com.fop.framework.exception.FOPValidateException;
import com.fop.framework.service.trade.ServiceCheck;
import com.fop.framework.service.trade.ServiceConfig;
import com.fop.framework.service.trade.ServiceDot;
import com.fop.framework.service.trade.ServiceView;

/**
 * 描述:服务装配层处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ServiceProcessImpl implements ServiceProcess{

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());

  /**service拦截处理**/
  private ServiceInterceptor serviceInterceptor;

  /**CheckChain处理**/
  private ServiceCheckChain serviceCheckChain;

  /**DotChain处理**/
  private ServiceDotChain serviceDotChain;

  /* (non-Javadoc)
   * @see com.fop.framework.service.ServiceProcess#serviceProcess(com.fop.framework.context.FOPContext)
   */
  @Override
  public void serviceProcess(FOPContext serviceContext) throws FOPException {
    logger.debug("----framework: serviceProcess execute begin");
    ServiceConfig serviceConfig = (ServiceConfig)serviceContext.getFieldDataValue(FOPConstants.SERVICE_CONFIG_DATA);
    //取出来后做置空处理
    serviceContext.setFieldDataValue(FOPConstants.SERVICE_CONFIG_DATA, null);

    //----------1.执行前拦截---------------------
    try {
      serviceInterceptor.beforeService(serviceContext);
    } catch (Exception e1) {
      logger.error("----framework: error1:" + e1.getMessage(), e1);
      throw new FOPException("FOP000509", "流程前拦截处理出错");
    }
    logger.debug("----framework: beforeService finish.");

    //----------2.取得解析后的配置信息---------------------
    //取得输入要素检查链
    List<Map<String, ServiceCheck>> listCheckChain = serviceConfig.getCheckChain();
    //取得业务流程链
    List<Map<String, ServiceDot>> listDotChain = serviceConfig.getDotChain();
    //取得视图定义集合
    Map<String, ServiceView> mapViewResult = serviceConfig.getViewResult();

    //----------3.执行输入要素检查---------------------
    try {
      serviceCheckChain.process(listCheckChain, serviceContext);
    } catch (FOPValidateException e) {
      logger.error("----framework: error2:" + e.getMessage(), e);
      throw new FOPException(e.getErrCode());
    }
    logger.debug("----framework: ServiceCheckChain execute finish.");

    //----------4.执行业务流程---------------------
    try {
      serviceDotChain.process(listDotChain, mapViewResult, serviceContext);
    } catch (FOPException e) {
      logger.error("----framework: error2:" + e.getMessage(), e);
      throw e;
    }
    logger.debug("----framework: ServiceDotChain execute finish.");

    //----------5.执行后拦截---------------------
    try {
      serviceInterceptor.afterService(serviceContext);
    } catch (Exception e2) {
      logger.error("----framework: error3:" + e2.getMessage(), e2);
      throw new FOPException("FOP000510", "流程后拦截处理出错");
    }
    logger.debug("----framework: serviceProcess execute end");
  }

  /**
   * @param serviceInterceptor the serviceInterceptor to set
   */
  public void setServiceInterceptor(ServiceInterceptor serviceInterceptor) {
    this.serviceInterceptor = serviceInterceptor;
  }

  /**
   * @param serviceCheckChain the serviceCheckChain to set
   */
  public void setServiceCheckChain(ServiceCheckChain serviceCheckChain) {
    this.serviceCheckChain = serviceCheckChain;
  }

  /**
   * @param serviceDotChain the serviceDotChain to set
   */
  public void setServiceDotChain(ServiceDotChain serviceDotChain) {
    this.serviceDotChain = serviceDotChain;
  }

}

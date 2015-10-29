/**
 * 文件名		：ServiceBeanFactoryUtil.java
 * 创建日期	：2013-10-24
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;

import com.fop.framework.exception.FOPException;
import com.fop.framework.service.trade.ServiceConfig;
import com.fop.framework.util.StringUtil;

/**
 * 描述:模板解析后的交易（服务）bean操作
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ServiceBeanFactoryUtil {

  /**
   * 模板解析后的交易（服务）bean存储容器
   */
  // private static final Map<String, ServiceConfig> serviceBeanContext = new
  // ConcurrentHashMap<String, ServiceConfig>();
  private static final Map<String, List<ServiceConfig>> serviceBeanContext = new ConcurrentHashMap<String, List<ServiceConfig>>();

  /**
   * 添加bean对象
   */
  public static void addBean(String beanid, ServiceConfig beanobj) {
    // 为空则返回null
    if(beanobj.getTradeId() != null) {
      beanid = beanobj.getTradeId();
    }
    
    if (beanid == null || beanid.trim().length() < 1) {
      return;
    }
    // 第一个字母转小写
    beanid = StringUtil.firstLowerCase(beanid);
    // serviceBeanContext.put(beanid, beanobj);

    List<ServiceConfig> serviceBeans = serviceBeanContext.get(beanid);
    if (serviceBeans == null) {
      serviceBeans = new ArrayList<ServiceConfig>();
      serviceBeanContext.put(beanid, serviceBeans);
    }
    serviceBeans.add(beanobj);
  }
  
  /**
   * 获取bean实例
   * 
   * @param beanid
   *          bean标识
   * @return ServiceConfig bean实例
   * @throws BeansException
   */
  public static ServiceConfig getBean(String beanid) throws FOPException {
    // 为空则返回null
    if (beanid == null || beanid.trim().length() < 1) {
      return null;
    }
    // 第一个字母转小写
    beanid = StringUtil.firstLowerCase(beanid);
    // ServiceConfig beanobj = serviceBeanContext.get(beanid);
    // if (beanobj == null){
    // throw new FOPException("FOP000001", "找不到对应的ServiceBean");
    // }
    // return beanobj;

    List<ServiceConfig> serviceBeans = serviceBeanContext.get(beanid);
    if (serviceBeans == null) {
      throw new FOPException("FOP000001", "找不到对应的ServiceBean");
    }
    ServiceConfig beanobj = serviceBeans.get(0);
    
    if (beanobj == null) {
      throw new FOPException("FOP000001", "找不到对应的ServiceBean");
    }
    return beanobj;
  }

  /**
   * 获取bean实例
   * 
   * @param beanid
   *          bean标识
   * @param versionNo 版本号
   * @param deviceType 设备类型
   * @return ServiceConfig bean实例
   * @throws BeansException
   */
  @SuppressWarnings("rawtypes")
  public static ServiceConfig getBean(String beanid, String versionNo,
      String deviceType) throws FOPException {
    // 为空则返回null
    if (beanid == null || beanid.trim().length() < 1) {
      return null;
    }
    // 第一个字母转小写
    beanid = StringUtil.firstLowerCase(beanid);
    // ServiceConfig beanobj = serviceBeanContext.get(beanid);
    // if (beanobj == null){
    // throw new FOPException("FOP000001", "找不到对应的ServiceBean");
    // }
    // return beanobj;

    List<ServiceConfig> serviceBeans = serviceBeanContext.get(beanid);
    if (serviceBeans == null) {
      throw new FOPException("FOP000001", "找不到对应的ServiceBean");
    }
    ServiceConfig beanobj = null;
    for (ServiceConfig bean : serviceBeans) {
      Map extInfo = bean.getBasicInfo().getExtInfo();
      if (extInfo != null) {
        String v = (String) extInfo.get("versionNo");
        String d = (String) extInfo.get("deviceType");
        if (v != null || d != null) {
          if ((v != null && v.equals(versionNo) && d == null)
              || (v == null && d != null && d.equals(deviceType))
              || (v != null && v.equals(versionNo) && d != null && d
                  .equals(deviceType))) {
            beanobj = bean;
            break;
          }
        } else {
          beanobj = bean;
        }
      } else {
        beanobj = bean;
      }
    }
    if (beanobj == null) {
      throw new FOPException("FOP000001", "找不到对应的ServiceBean");
    }
    return beanobj;
  }

  /**
   * 取得容器所有bean集合
   * 
   * @return
   */
  public static Map<String, List<ServiceConfig>> getAllBeans() {
    return serviceBeanContext;
  }
 /* public static Map<String, ServiceConfig> getAllBeans() {
    return serviceBeanContext;
  }*/
}

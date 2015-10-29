/**
 * 文件名		：ApplicationContextUtil.java
 * 创建日期	：2013-9-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.fop.framework.util.StringUtil;

/**
 * 描述:交易Bean操作
 * 装配Spring容器的ApplicationContext对象
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ApplicationContextUtil implements ApplicationContextAware{

  /**容器对象*/
  private static ApplicationContext applicationContext;

  /**
   * 实现ApplicationContextAware接口的回调方法，设置上下文环境
   */
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException { 
    ApplicationContextUtil.applicationContext = applicationContext;
  } 

  /**
   * 获取bean实例
   * @param Class beanclass类型
   * @return Object bean实例 
   * @throws BeansException
   */
  public static Object getBean(Class<?> beanclass) throws BeansException{
    return applicationContext.getBean(beanclass);
  }
  
  /**
   * 获取bean实例
   * @param beanid bean标识
   * @return Object bean实例 
   * @throws BeansException
   */
  public static Object getBean(String beanid) throws BeansException{
    //为空则返回null
    if (beanid == null || beanid.trim().length() < 1){
      return null;
    }
    beanid = StringUtil.firstLowerCase(beanid);
    return applicationContext.getBean(beanid);
  }

}

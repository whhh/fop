/**
 * 文件名		：FOPLoaderListener.java
 * 创建日期	：2013-10-24
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 描述: 初始化管理，需要放值在
 * org.springframework.web.context.ContextLoaderListener
 * 之后，依赖org.springframework.context.ApplicationContext
 * 对象取值
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class FOPLoaderListener implements ServletContextListener {

  /**
   * 初始化数据处理
   */
  public void contextInitialized(ServletContextEvent event) {
    //获取DataChain，执行initChain
     Object obj = ApplicationContextUtil.getBean("loaderDataChain");
     if (obj != null){
       FOPLoaderDataChain dataChain = (FOPLoaderDataChain)obj;
       dataChain.initChain();
     }
  }

  /**
   * 结束数据处理
   */
  public void contextDestroyed(ServletContextEvent event) {
    //获取DataChain，执行destroyChain
    Object obj = ApplicationContextUtil.getBean("loaderDataChain");
    if (obj != null){
      FOPLoaderDataChain dataChain = (FOPLoaderDataChain)obj;
      dataChain.destroyChain();
    }
  }
}

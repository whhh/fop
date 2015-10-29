/**
 * 文件名		：BaseApplicationContext.java
 * 创建日期	：2013-10-8
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package test.com.fop.framework.base;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 描述:配置文件加载，获取ApplicationContext操作
 * 供其它测试类调用
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class BaseApplicationContext {

  /**
   * 获取ApplicationContext操作
   * @return
   */
  public static ApplicationContext getApplicationContext(){
    ApplicationContext accontext = new ClassPathXmlApplicationContext("test-application-context.xml");
    return accontext;
  }
}

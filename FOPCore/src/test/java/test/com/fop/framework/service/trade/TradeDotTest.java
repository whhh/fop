/**
 * 文件名		：TradeDotTest.java
 * 创建日期	：2013-10-8
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package test.com.fop.framework.service.trade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import test.com.fop.framework.base.BaseApplicationContext;

import com.fop.framework.context.ApplicationContextUtil;



/**
 * 描述:单元测试--TradeDot
 * 测试两种情况：
 * 1、@Scope("prototype")，即非单例
 * 2、@Scope("singleton")，即单例
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class TradeDotTest {

  /**
   * 测试情况1，非单例
   */
  @Test
  public void testPrototypeTradeDot(){
    //加载配置
    BaseApplicationContext.getApplicationContext();
    //获取bean
    TradeDotPrototype bean1 = (TradeDotPrototype)ApplicationContextUtil.getBean("TradeDotPrototype");
    //改变索引值
    bean1.index = 1;
    //第二次获取bean
    TradeDotPrototype bean2 = (TradeDotPrototype)ApplicationContextUtil.getBean("TradeDotPrototype");
    //断言
    assertNotNull(bean2);  
    String result = "" + bean2.index;
    //System.out.println("----testPrototypeTradeDot:result: " + result);
    //第二个bean的index应为初始值0，因为是一个新对象
    assertEquals(result, "0");
  }

  /**
   * 测试情况2，单例测试
   */
  @Test
  public void testSingletonTradeDot(){
    //加载配置
    BaseApplicationContext.getApplicationContext();
    //获取bean
    TradeDotSingleton bean1 = (TradeDotSingleton)ApplicationContextUtil.getBean("TradeDotSingleton");
    //改变索引值
    bean1.index = 1;
    //第二次获取bean
    TradeDotSingleton bean2 = (TradeDotSingleton)ApplicationContextUtil.getBean("TradeDotSingleton");
    //断言
    assertNotNull(bean2);  
    String result = "" + bean2.index;
    //System.out.println("----testSingletonTradeDot:result: " + result);
    //第二个bean的index应为初始值1，因为是原来的对象
    assertEquals(result, "1");
  }

}

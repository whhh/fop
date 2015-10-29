/**
 * 文件名    ：ApplicationContextUtilTest.java
 * 创建日期 ：2013-9-29
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package test.com.fop.framework.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import test.com.fop.framework.base.BaseApplicationContext;

import com.fop.framework.context.ApplicationContextUtil;



/**
 * 描述:单元测试--ApplicationContextUtil
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ApplicationContextUtilTest {

  /**
   * 读取bean--测试
   */
  @Test
  public void testgetBean(){
    //加载配置
    BaseApplicationContext.getApplicationContext();
    //定义beanid
    String beanid = "tradedottest";
    //获取beanid
    Object bean = ApplicationContextUtil.getBean(beanid);
    //断言
    assertNotNull(bean);  
    assertEquals(bean.getClass().getName(), "test.com.fop.framework.service.trade.TradeDotTest");
    assertEquals(bean.getClass().getSimpleName(), "TradeDotTest"); 
  }
}

/**
 * 文件名		：CoreControllerTest.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package test.com.fop.framework.http.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import test.com.fop.framework.base.BaseApplicationContext;

import com.fop.framework.context.FOPContext;
import com.fop.framework.data.GroupData;

/**
 * 描述:CoreController某些情况测试
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class CoreControllerTest {

  /**
   * 测试情况1，经一次流程跳转后，获取requestContext信息
   */
  @Test
  public void testRequestContext(){
    try {
      //加载配置
      BaseApplicationContext.getApplicationContext();
      //定义requestContext上下文对象
      FOPContext requestContext = new FOPContext();
      GroupData gdata = new GroupData();
      requestContext.setDataElement(gdata);
      //requestContext.addFOPData(gdata);
      CProcess css = new CProcess();
      css.ctrlProcess(requestContext);

      String result = null;
      result = (String)requestContext.getFieldDataValue("instr");
      //      Map map = new HashMap();
      //      css.ctrlProcess2(map);
      //      result = (String)map.get("instr");
      //断言
      assertNotNull(result);  
      System.out.println("----test result: " + result);
      assertEquals(result, "is abc");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}

/**
 * 文件名		：StringUtilTest.java
 * 创建日期	：2013-9-17
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package test.com.fop.framework.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.fop.framework.util.StringUtil;

/**
 * 描述:单元测试--StringUtil
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class StringUtilTest {

  /**
   * 去掉首字符--测试
   */
  @Test
  public void testfirstLowerCase(){
    String oldstr = "ABC";
    String newstr = StringUtil.firstLowerCase(oldstr);
    assertNotNull(newstr);  
    assertEquals(newstr, "aBC"); //验证返回结果
  }
  
  /**
   * 取出第一个冒号后面的紧挨的字符串--测试
   */
  @Test
  public void getFirstAfterColonStr(){
    String oldstr = "a:b";
    String newstr = StringUtil.getFirstAfterColonStr(oldstr);
    assertNotNull(newstr);  
    assertEquals(newstr, "b"); //验证返回结果
    
    oldstr = "ab:b,c";
    newstr = StringUtil.getFirstAfterColonStr(oldstr);
    assertNotNull(newstr);  
    assertEquals(newstr, "b,c"); //验证返回结果
  }
}

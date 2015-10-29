/**
 * 文件名		：StringUtil.java
 * 创建日期	：2013-9-10
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.util;

/**
 * 描述:String操作工具类
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class StringUtil {

  /**
   * 判断字符串是否为空
   * 
   * @param str
   * @return
   */
  public static boolean isStrEmpty(String str) {
    if (str == null) {
      return true;
    }
    if (str.trim().length() > 0) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * 把字符串的第一个字符转为小写
   * @param str
   * @return
   */
  public static String firstLowerCase(String str) {
    if (isStrEmpty(str)) {
      return "";
    }
    String firstChar = str.substring(0, 1);
    String otherChars = str.substring(1, str.length());
    firstChar = firstChar.toLowerCase();
    //组合为新的串
    String nstr = firstChar + otherChars;
    return nstr;
  }
  
  /**
   * 去掉首字符
   * @param str
   * @return
   */
  public static String firstCharDel(String str) {
    if (isStrEmpty(str)) {
      return "";
    }
    String otherChars = str.substring(1, str.length());
    return otherChars;
  }
  
  /**
   * 取出第一个冒号后面的紧挨的字符串，
   * 例如：
   * t:a，取a；
   * t:b:a，取b；
   * t:b,a，取b,a；
   * @param str
   * @return
   */
  public static String getFirstAfterColonStr(String str) {
    if (isStrEmpty(str)) {
      return "";
    }
    String[] otherChars = str.split(":");
    if (otherChars.length > 1){
      return otherChars[1];
    }
    return "";
  }
  /**
   * 将对象转为String输出，如果为空则返回null
   * @param obj
   * @return
   */
  public static String toStringData(Object obj){
    if(obj==null){
      return null;
    }else{
      return obj.toString();
    }
  }
  
}

/**
 * 文件名		：TCPTools.java
 * 创建日期	：2013-11-20
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.util;

import java.io.UnsupportedEncodingException;

/**
 * 描述:TODO.....
 * 
 * @version 1.00
 * @author user
 * 
 */
public class TCPTools {
  /**
   * 字符串补齐
   * 
   * @param oldStr 原字符串
   * @param padChar 补齐字符
   * @param leftAlign 左补齐标识 true-左补齐 false-右补齐
   * @param len 补齐后字符串的长度
   * @return 补齐后的字符串
   */
  public static String paddingStr(String oldStr, char padChar, boolean leftAlign, int len) {
    StringBuffer newStr = new StringBuffer(oldStr);
    
    if (newStr.length() < len) {
      for (int i = oldStr.length(); i < len; i++) {
        if (leftAlign) {
          //左补齐
          newStr.insert(0, padChar);
        } else {
          //右补齐
          newStr.append(padChar);
        }
      }
    }
    
    return newStr.toString();
  }
  
  public static String getMD5(String pwd,String encode) throws UnsupportedEncodingException {
    byte[] source = pwd.getBytes(encode);
    String s = null;
    char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
        'e', 'f' };
    try {
      java.security.MessageDigest md = java.security.MessageDigest
          .getInstance("MD5");
      md.update(source);
      byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
      // 用字节表示就是 16 个字节
      char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
      // 所以表示成 16 进制需要 32 个字符
      int k = 0; // 表示转换结果中对应的字符位置
      for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
        // 转换成 16 进制字符的转换
        byte byte0 = tmp[i]; // 取第 i 个字节
        str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
        // >>> 为逻辑右移，将符号位一起右移
        str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
      }
      s = new String(str); // 换后的结果转换为字符串

    } catch (Exception e) {
      e.printStackTrace();
    }
    return s;
  }


}

/**
 * 文件名		：Crypter.java
 * 创建日期	：2013-12-12
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.http.encrypt.impl;

import com.fop.framework.context.FOPContext;
import com.fop.framework.http.encrypt.ICrypter;

/**
 * 描述:TODO.....
 * 
 * @version 1.00
 * @author zhangfeng
 * 
 */
public class Crypter implements ICrypter {

  /* 
   * 
   */
  @Override
  public String encrypt(String plainText,FOPContext context) {
    return plainText;
  }

  /* 
   * 
   */
  @Override
  public String decrypt(String cipherText,FOPContext context) {
    return cipherText;
  }

}

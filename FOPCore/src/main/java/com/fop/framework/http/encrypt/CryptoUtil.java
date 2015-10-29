/**
 * 文件名		：CryptoUtil.java
 * 创建日期	：2013-12-12
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.http.encrypt;

import com.fop.framework.context.FOPContext;
import com.fop.framework.http.encrypt.impl.Crypter;

/**
 * 描述:加密解密工具类
 * 
 * @version 1.00
 * @author zhangfeng
 * 
 */
public class CryptoUtil {
  private static ICrypter crypter = new Crypter();
  
  /**
   * @return the crypter
   */
  public static ICrypter getCrypter() {
    return crypter;
  }

  /**
   * @param crypter the crypter to set
   */
  public static void setCrypter(ICrypter crypter) {
    CryptoUtil.crypter = crypter;
  }

  public static String encrypt(String plainText,FOPContext context) {
    return crypter.encrypt(plainText,context);
  }
  
  public static String decrypt(String cipherText,FOPContext context) {
    return crypter.decrypt(cipherText,context);
  }
}

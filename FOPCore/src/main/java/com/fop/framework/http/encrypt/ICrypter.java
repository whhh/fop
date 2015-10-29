/**
 * 文件名		：ICrypter.java
 * 创建日期	：2013-12-12
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.http.encrypt;

import com.fop.framework.context.FOPContext;

/**
 * 描述:TODO.....
 * 
 * @version 1.00
 * @author zhangfeng
 * 
 */
public interface ICrypter {
  String encrypt(String plainText,FOPContext context);
  
  String decrypt(String cipherText,FOPContext context);
}

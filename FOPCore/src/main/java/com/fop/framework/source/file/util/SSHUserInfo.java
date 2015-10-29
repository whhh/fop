/**
 * 文件名		：MyUserInfo.java
 * 创建日期	：2013-10-18
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.util;

import com.jcraft.jsch.UserInfo;

/**
 * 描述:SSH服务器资源访问配置的用户信息模型
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class SSHUserInfo implements UserInfo{
  /**密钥解密密码*/
  private String passphrase;
  /**登陆密码*/
  private String password;
  /**
   * 文件服务器用户信息构造方法
   * @param passphrase 密钥解密密码
   */
  public SSHUserInfo(String passphrase){
    this.passphrase=passphrase;
  }
  /**
   * 文件服务器用户信息构造方法
   * @param passphrase 密钥解密密码
   * @param password 登陆密码
   */
  public SSHUserInfo(String passphrase,String password){
    this.passphrase=passphrase;
    this.password=password;
  }
  /**
   * 设定密钥解密密码
   */
  @Override
  public String getPassphrase() {
    return passphrase;
  }
  /**
   * 设定登陆密码
   */
  @Override
  public String getPassword() {
    return password;
  }
  /**
   * 
   */
  @Override
  public boolean promptPassphrase(String arg0) {
    return true;
  }
  /**
   * 
   */
  @Override
  public boolean promptPassword(String arg0) {
    return true;
  }
  /**
   * 
   */
  @Override
  public boolean promptYesNo(String arg0) {
    return true;
  }
  /**
   * 
   */
  @Override
  public void showMessage(String s) {
//    System.out.println(s);
  }
  
}

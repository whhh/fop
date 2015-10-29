/**
 * 文件名		：IUser.java
 * 创建日期	：2015-1-12
 * Copyright (c) 2003-2015 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.data;

/**
 * 描述:TODO.....
 * 
 * @version 1.00
 * @author zhangfeng
 * 
 */
public interface IUser {
  /**
   * 客户密码
   * @return
   */
  public String getPassword();
  /**
   * 客户名称
   * @return
   */
  public String getUsername();
  /**
   * 客户是否过期
   * @return
   */
  public boolean isAccountNonExpired();
  /**
   * 客户是否被锁定
   * @return
   */
  public boolean isAccountNonLocked();
  /**
   * 客户验证是否过期
   * @return
   */
  public boolean isCredentialsNonExpired();
  /**
   * 客户是否启动
   * @return
   */
  public boolean isEnabled();

}

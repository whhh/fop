/**
 * 文件名		：Session.java
 * 创建日期	：2013-9-6
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.session;

import java.util.Set;

/**
 * 描述:会话接口定义
 * 
 * @version 1.00
 * @author Dustin
 * 
 */
public interface Session {
  
  /**
   * 获取会话ID
   * @return ID 会话ID
   */
  public abstract String getId();

  /**
   * 判断是否是一个新的会话
   * @return true-新会话; false-非新会话
   */
  public abstract boolean isNew();

  /**
   * 设置会话数据
   * @param name 数据名称
   * @param value 数据值
   */
  public abstract void setAttribute(String name, Object value);

  /**
   * 通过名称获取会话数据的值
   * @param name 数据名称
   * @return 数据值
   */
  public abstract Object getAttribute(String name);

  /**
   * 删除会话数据
   * @param name 数据名称
   */
  public abstract void removeAttribute(String name);

  /**
   * 获取所有会话数据的名称
   * @return 数据名称集合
   */
  public abstract Set<String> getAttributeNames();
}

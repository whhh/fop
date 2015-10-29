/**
 * 文件名		：FileConnEnumTypes.java
 * 创建日期	：2013-10-17
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.data;

/**
 * 描述:文件资源连接信息类型
 * 
 * @version 1.00
 * @author syl
 * 
 */
public enum FileConnEnumTypes {
  /**操作本地应用文件时使用此配置*/
  Conn_None,
//  /**通过传入的配置信息获取连接*/
//  Conn_ByConfig,
  /**通过传入的BeanId信息获取连接*/
  Conn_ByBeanId
}

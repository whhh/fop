/**
 * 文件名		：ParamTypes.java
 * 创建日期	：2013-10-11
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.data;

/**
 * 描述:数据库参数信息可选类型
 * 
 * @version 1.00
 * @author syl
 * 
 */
public enum DBParamEnumTypes {
  /**DAO接口*/
  DAO_INTERFACE,
  /**完整的sql语句*/
  SQL_PUR,
//  /**动态sql语句，需要参数填充*/
//  SQL_DYNAMIC,
//  /**SQL配置文件的Key值*/
  SQL_KEY_PUR,
  /**SQL配置文件的动态Sql，对应的key值*/
//  SQL_KEY_DYNAMIC,
  
}

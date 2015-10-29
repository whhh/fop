/**
 * 文件名		：SQLAdapter.java
 * 创建日期	：2013-10-23
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db;

/**
 * 描述:Mybatis执行sql的Adapter
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class SQLAdapter {
  /**要执行的sql语句*/
  String sql;

  /**
   * 构造器
   * @param sql 要执行的sql语句
   */
  public SQLAdapter(String sql) {
    this.sql = sql;
  }

  public String getSql() {
    return sql;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }
}

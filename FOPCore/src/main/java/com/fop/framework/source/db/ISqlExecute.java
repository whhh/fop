/**
 * 文件名		：ISqlExecute.java
 * 创建日期	：2013-10-29
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db;


/**
 * 描述:定义Mybatis执行sql方式的基类接口
 * 
 * @version 1.00
 * @author syl
 * 
 */
public interface ISqlExecute {
  public static final String select="select";//查询时调用的方法名
  public static final String insert="insert";//新增时调用的方法名
  public static final String delete="delete";//删除时调用的方法名
  public static final String update="update";//更新时调用的方法名
//  public ArrayList<HashMap<String,Object>> executeSql(SQLAdapter sqlAdapter);
  /**
   * 新增操作方法，方法名不可随意修改，修改时应保证与该接口的静态常量一致
   * @param sqlAdapter sql存储对象
   */
  public void insert(SQLAdapter sqlAdapter);
  /**
   * 删除操作方法，方法名不可随意修改，修改时应保证与该接口的静态常量一致
   * @param sqlAdapter sql存储对象
   */
  public void delete(SQLAdapter sqlAdapter);
  /**
   * 修改操作方法，方法名不可随意修改，修改时应保证与该接口的静态常量一致
   * @param sqlAdapter sql存储对象
   */
  public void update(SQLAdapter sqlAdapter);
  
}

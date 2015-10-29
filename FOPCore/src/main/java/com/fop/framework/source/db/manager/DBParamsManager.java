/**
 * 文件名		：DBParamsManager.java
 * 创建日期	：2013-9-27
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.manager;

import com.fop.framework.source.BaseManager;
import com.fop.framework.source.pub.InvokeModel;

/**
 * 用于管理对数据库操作的sql、参数处理
 * 对sql语句统一管理
 * 定义sql/nameSql等相关文件可存放的位置
 * 
 * @version 1.00
 * @author syl
 * 
 */
public interface DBParamsManager extends BaseManager{
  /**
   * 获取可执行的sql
   * @param obj
   * @return
   */
  public String getByPureSql(String sql);
//
//  /**
//   * 获取动态sql
//   * @param value
//   * @return
//   */
//  public String getByPureDymSql(String sql,GroupData sqlParams);
//
  /**
   * 根据key值得到sql
   * @param obj
   * @return
   */
  public String getByKeySql(String key);

//  /**
//   * 根据模型组装sql返回
//   * @param value
//   * @return
//   */
//  public String getByKeyDymSql(String sql,GroupData sqlParams);

  /**
   * 获取反射执行的信息
   * @param value 包含执行的方法及参数
   * @return 返回反射信息
   */
  public InvokeModel getInvokeInfo(InvokeModel value);
  
}

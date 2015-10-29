/**
 * 文件名		：DBExchangeManager.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.manager;

import com.fop.framework.source.BaseManager;
import com.fop.framework.source.db.ISqlExecute;
import com.fop.framework.source.pub.InvokeModel;

/**
 * 与数据库交换的处理
 * @version 1.00
 * @author syl
 * 
 */
public interface DBExchangeManager extends BaseManager{
  /**
   * 执行sql语句
   * @param sqlExecute sql语句执行的对象
   * @param sql 待执行的sql语句
   * @return sql语句执行结果信息
   */
  public Object executeSql(ISqlExecute sqlExecute,String sql);

  /**invoke执行Dao对象的某个方法
   * @param daoObj 用于执行的对象
   * @param invokeModel 保存执行的方法及参数信息
   * @return 返回invoke执行的结果信息
   */
  public Object invokeDao(Object daoObj,InvokeModel invokeModel);
  
}

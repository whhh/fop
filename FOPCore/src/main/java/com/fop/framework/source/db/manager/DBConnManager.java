/**
 * 文件名		：DBConnManager.java
 * 创建日期	：2013-9-27
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.manager;

import com.fop.framework.source.BaseManager;
import com.fop.framework.source.db.exception.DBException;

/**
 * 将数据源、数据库连接、JDBCTemplate、已经组装好可用的DAO都视为数据库的资源
 * 之所以提供JDBCTemplate之类的获取，是为了使用JDBCTemplate已经包含的工具方法
 * @version 1.00
 * @author syl
 * 
 */
public interface DBConnManager extends BaseManager{
  /**
   * 获取默认sql执行对象
   * @return
   */
  public Object getDefaultSqlExecute();

  /**根据Dao接口从Spring上下文中获取具体实现对象
   * @param clazz dao接口名
   * @return 返回可执行的Dao对象
   */
  public <T> T getDaoImpl(Class<T> clazz) throws DBException;
  
}

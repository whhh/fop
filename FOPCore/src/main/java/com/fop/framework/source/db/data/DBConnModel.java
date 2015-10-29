/**
 * 文件名		：DBConnModel.java
 * 创建日期	：2013-10-16
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.data;

/**
 * 描述:数据库连接参数模型
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class DBConnModel {
  private DBConnEnumTypes dbEnumConnType;
  /**Dao接口的class*/
  private Class<?> daoClass;
  
  /**默认为DBConnEnumTypes.Class_DefaultDbExec*/
  public DBConnModel(){
    this.dbEnumConnType=DBConnEnumTypes.Class_DefaultDbExec;
  }
  /**
   * 数据库连接参数模型构造方法
   * @param daoClass 要执行的dao接口类
   */
  public DBConnModel(Class<?> daoClass){
    this.dbEnumConnType=DBConnEnumTypes.Class_DBBaseDao;
    this.daoClass=daoClass;
  }
  
  /**
   * @return the dbEnumConnType
   */
  public DBConnEnumTypes getDbEnumConnType() {
    return dbEnumConnType;
  }
  /**
   * @return the daoClass
   */
  public Class<?> getDaoClass() {
    return daoClass;
  }
  
}

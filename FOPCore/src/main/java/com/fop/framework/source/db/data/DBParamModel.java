/**
 * 文件名		：DBParamModel.java
 * 创建日期	：2013-10-12
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.data;

import com.fop.framework.source.pub.InvokeModel;
/**
 * 描述:数据库操作参数模型
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class DBParamModel {
  private DBParamEnumTypes dbParamEnumType;
  /**sql取值，key或者value*/
  private String sql;
  
//  /**sql待插入的参数取值*/
//  private GroupData sqlParams;
  
  /**InvokeModel,使用Dao方式时使用此参数值*/
  private InvokeModel daoInvokeModel;
  
//  protected DBParamModel(DBParamEnumTypes dbParamEnumTypes){
//    this.dbParamEnumTypes=dbParamEnumTypes;
//  }
  
  public DBParamModel(String sql){
    this(sql, false);
  }
  
  public DBParamModel(String sql,boolean isKey){
    if(isKey){
      this.dbParamEnumType=DBParamEnumTypes.SQL_KEY_PUR;
    }else{
      this.dbParamEnumType=DBParamEnumTypes.SQL_PUR;
    }
    this.sql=sql;
  }
  
//  public DBParamModel(String sql,GroupData sqlParams){
//    this(sql,sqlParams,false);
//  }
  
//  public DBParamModel(String sql,GroupData sqlParams,boolean isKey){
//    if(isKey){
//      this.dbParamEnumType=DBParamEnumTypes.SQL_KEY_DYNAMIC;
//    }else{
//      this.dbParamEnumType=DBParamEnumTypes.SQL_DYNAMIC;
//    }
//    this.sql=sql;
//    this.sqlParams=sqlParams;
//  }
  public DBParamModel(InvokeModel daoInvokeModel){
    this.dbParamEnumType=DBParamEnumTypes.DAO_INTERFACE;
    this.daoInvokeModel=daoInvokeModel;
  }
  /**
   * @return the dbParamEnumType
   */
  public DBParamEnumTypes getDbParamEnumType() {
    return dbParamEnumType;
  }
  /**
   * @return the sql
   */
  public String getSql() {
    return sql;
  }
  
//  /**
//   * @return the sqlParams
//   */
//  public GroupData getSqlParams() {
//    return sqlParams;
//  }
  
  /**
   * @return the daoInvokeModel
   */
  public InvokeModel getDaoInvokeModel() {
    return daoInvokeModel;
  }
  
  
}

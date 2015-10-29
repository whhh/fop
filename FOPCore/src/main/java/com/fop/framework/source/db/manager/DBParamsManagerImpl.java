/**
 * 文件名		：DBParamsManagerImpl.java
 * 创建日期	：2013-10-9
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.source.BaseContext;
import com.fop.framework.source.db.context.DBConstant;
import com.fop.framework.source.db.data.DBInDataModel;
import com.fop.framework.source.db.data.DBParamEnumTypes;
import com.fop.framework.source.db.data.DBParamModel;
import com.fop.framework.source.db.exception.DBRuntimeException;
import com.fop.framework.source.pub.InvokeModel;
import com.fop.framework.source.pub.ProcessDataType;
import com.fop.framework.source.pub.SourceConstant;

/**
 * 描述:数据库参数管理实现类
 * @version 1.00
 * @author syl
 * 
 */
public class DBParamsManagerImpl implements DBParamsManager {
  
  private Log logger = LogFactory.getLog(DBParamsManagerImpl.class);
  
  @Override
  public void operate(BaseContext dbContext) {
    //从上下文中获取数据库资源访问总入参对象
    DBInDataModel dbInDataModel=(DBInDataModel)(dbContext.getInData());
    //从总入参对象中得到参数信息
    DBParamModel dbParamModel=(DBParamModel)dbInDataModel.getData(ProcessDataType.PARAM);
    
    DBParamEnumTypes currEnumType=dbParamModel.getDbParamEnumType();
    String sql=null;
    InvokeModel invokeModel=null;
    switch(currEnumType){
    case SQL_PUR:
      sql=this.getByPureSql(dbParamModel.getSql());
      break;
//    case SQL_DYNAMIC:
//      sql=this.getByPureDymSql(dbParamModel.getSql(),dbParamModel.getSqlParams());
//      break;
    case SQL_KEY_PUR:
      sql=this.getByKeySql(dbParamModel.getSql());
      break;
//    case SQL_KEY_DYNAMIC:
//      sql=this.getByKeyDymSql(dbParamModel.getSql(),dbParamModel.getSqlParams());
//      break;
    case DAO_INTERFACE:
      invokeModel=this.getInvokeInfo(dbParamModel.getDaoInvokeModel());
      break;
    default :
      logger.error(DBConstant.PARAM_TYPE_ERROR+":"+currEnumType);
      throw new DBRuntimeException(DBConstant.PARAM_TYPE_ERROR+":"+currEnumType);
    }
    dbContext.put(DBConstant.CONST_Sql, sql,false);
    dbContext.put(DBConstant.CONST_InvokeModel, invokeModel,false);
  }
  
  @Override
  public String getByPureSql(String sql) {
    return sql;
  }

//  @Override
//  public String getByPureDymSql(String sql, GroupData sqlParams) {
//    throw new DBRuntimeException(DBConstant.Method_Develop_UnFinished+":--getByPureDymSql(String sql, GroupData sqlParams)");
//  }
//
  @Override
  public String getByKeySql(String key) {
    logger.error(DBConstant.Method_Develop_UnFinished+"--getByKeySql(String key)");
    throw new DBRuntimeException(DBConstant.Method_Develop_UnFinished+"--getByKeySql(String key)");
  }

//  @Override
//  public String getByKeyDymSql(String sql, GroupData sqlParams) {
//    throw new DBRuntimeException(DBConstant.Method_Develop_UnFinished+"--getByKeySql(String key)");
//  }
  
  @Override
  public InvokeModel getInvokeInfo(InvokeModel value) {
    //是否需要校验invoke数据
    return value;//直接返回
  }
  @Override
  public String getDescription() {
    return SourceConstant.DB_Param;
  }
}

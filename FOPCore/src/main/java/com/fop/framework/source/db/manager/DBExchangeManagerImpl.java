/**
 * 文件名		：DBExchangeManagerImpl.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.manager;


import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.source.BaseContext;
import com.fop.framework.source.db.ISqlExecute;
import com.fop.framework.source.db.SQLAdapter;
import com.fop.framework.source.db.context.DBConstant;
import com.fop.framework.source.db.data.DBConnEnumTypes;
import com.fop.framework.source.db.data.DBConnModel;
import com.fop.framework.source.db.data.DBInDataModel;
import com.fop.framework.source.db.exception.DBRuntimeException;
import com.fop.framework.source.pub.InvokeModel;
import com.fop.framework.source.pub.ProcessDataType;
import com.fop.framework.source.pub.SourceConstant;
import com.fop.framework.util.StringUtil;

/**
 * 描述:数据库资源交互实现类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class DBExchangeManagerImpl implements DBExchangeManager{
//  /**日志打印对象**/
  private Log logger = LogFactory.getLog(DBExchangeManagerImpl.class);
  
  @Override
  public void operate(BaseContext dbContext) {
    //从上下文中获取数据库资源访问总入参对象
    DBInDataModel dbInDataModel=(DBInDataModel)(dbContext.getInData());
    //从总入参对象中得到连接信息
    DBConnModel dbConnModel=(DBConnModel)dbInDataModel.getData(ProcessDataType.CONN);
    
    DBConnEnumTypes dbConnEnumType=dbConnModel.getDbEnumConnType();
    Object connInfo=dbContext.get(DBConstant.CONST_ConnInfo);
    String sql=StringUtil.toStringData(dbContext.get(DBConstant.CONST_Sql));
    InvokeModel invokeModel=(InvokeModel)dbContext.get(DBConstant.CONST_InvokeModel);
    Object dbExchangeRes=null;
    switch(dbConnEnumType){
    case Class_DefaultDbExec:
      if(sql!=null){
        dbExchangeRes=this.executeSql((ISqlExecute)connInfo,sql);
      }else{
        logger.error(DBConstant.SQL_IS_NULL+":"+sql);
        throw new DBRuntimeException(DBConstant.SQL_IS_NULL+":"+sql);
      }
      break;
    case Class_DBBaseDao:
      dbExchangeRes=this.invokeDao(connInfo, invokeModel);
      break;
    default :
      logger.error(DBConstant.CONN_TYPE_ERROR+":"+dbConnEnumType);
      throw new DBRuntimeException(DBConstant.CONN_TYPE_ERROR+":"+dbConnEnumType);
    }
    dbContext.put(DBConstant.CONST_DBExchangeRes, dbExchangeRes,false);
  }
  
  @Override
  public Object executeSql(ISqlExecute sqlExecute,String sql) {
//    logger.debug(sql);
    Object resDataValue=null;
    InvokeModel invokeModel=new InvokeModel();
    SQLAdapter sqlAdapter=new SQLAdapter(sql);
    invokeModel.setParms(new Object[]{sqlAdapter});
    String operMethod=getResDataTypeBySql(sql);
    if(ISqlExecute.insert.equals(operMethod)||ISqlExecute.update.equals(operMethod)
        ||ISqlExecute.delete.equals(operMethod)){
      invokeModel.setMethodName(operMethod);
      try {
        invokeDao(sqlExecute,invokeModel);
        resDataValue=true;
      } catch (Exception e){
        resDataValue=false;
        logger.error("执行数据操作异常", e);
        throw new DBRuntimeException(DBConstant.SQL_EXE_ERROR+sql,e);
      }
    }else{
      logger.error(DBConstant.SQL_UNSupport_TYPE);
      throw new RuntimeException(DBConstant.SQL_UNSupport_TYPE+"("+operMethod+"):"+sql);
    }
    return resDataValue;
  }

  @Override
  public Object invokeDao(Object daoObj,InvokeModel invokeModel) {
    Object resDataValue=null;
    //搜集invoke参数
    String methodName=invokeModel.getMethodName();
    Object[] params=invokeModel.getParms();
    //获取要执行的方法
    Method currMethod=null;
    {
      Method[] allMethods=daoObj.getClass().getMethods();
      for (int i = 0; i < allMethods.length; i++) {
        if(allMethods[i].getName().equals(methodName)){
          currMethod=allMethods[i];
          break;
        }
      }
    }
    //提交要执行的方法
    if(currMethod==null){
      logger.error(DBConstant.DAO_METHOD_NOT_EXIST + ":" + currMethod);
      throw new DBRuntimeException(DBConstant.DAO_METHOD_NOT_EXIST+":"+currMethod);
    }else{
      try {
        if(params==null||params.length<=0){
          resDataValue=currMethod.invoke(daoObj);
        }else{
          resDataValue=currMethod.invoke(daoObj,params);
        }
      } catch (Exception e) {
        logger.error(DBConstant.DAO_METHOD_EXE_ERROR, e);
        throw new DBRuntimeException(DBConstant.DAO_METHOD_EXE_ERROR+":"+daoObj.getClass()+","+currMethod.getName(),e);
      }
    }
    //返回
    return resDataValue;
  }
  
  /**
   * 通过写好的sql得到要返回的数据类型
   * @param sql
   * @return
   */
  private String getResDataTypeBySql(String sql){
    String resType=null;
    if(sql==null||sql.trim().length()<=0){
      logger.error(DBConstant.SQL_UNKNOWN+":"+sql);
      throw new RuntimeException(DBConstant.SQL_UNKNOWN+":"+sql);
    }else{
      sql=sql.trim().toLowerCase();
      if(sql.startsWith("select")){
        resType=ISqlExecute.select;
      }else if(sql.startsWith("insert")){
        resType=ISqlExecute.insert;
      }else if(sql.startsWith("update")){
        resType=ISqlExecute.update;
      }else if(sql.startsWith("delete")){
        resType=ISqlExecute.delete;
      }else{
        logger.error(DBConstant.SQL_UNKNOWN+":" + sql);
        throw new RuntimeException(DBConstant.SQL_UNKNOWN+":"+sql);
      }
    }
    return resType;
  }
  
//  /**
//   * 
//   * @param operType 操作类型：增、删、改、查
//   * @param connInfo 数据库连接
//   * @param dbSql sql语句
//   * @param keepConn 是否保持连接，false表示关闭连接，true表示保持连接
//   * @return
//   */
//  private Object executeDB(int operType,Connection connInfo,String dbSql,boolean keepConn){
//    Object resDataValue=null;
//    if(select==operType){
//      resDataValue=DBUtil.executeQuery((Connection)connInfo, dbSql,keepConn);
//    }else if(insert==operType){
//      resDataValue=DBUtil.executeInsert((Connection)connInfo, dbSql,keepConn);
//    }else if(update==operType){
//      resDataValue=DBUtil.executeUpdate((Connection)connInfo, dbSql,keepConn);
//    }else if(delete==operType){
//      resDataValue=DBUtil.executeDelete((Connection)connInfo, dbSql,keepConn);
//    }else{
//      throw new RuntimeException(DBConstant.SQL_UNKNOWN+":"+dbSql);
//    }
//    return resDataValue; 
//  }
  @Override
  public String getDescription() {
    return SourceConstant.DB_Exchange;
  }
  
}

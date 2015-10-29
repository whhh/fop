/**
 * 文件名		：DBInDataModel.java
 * 创建日期	：2013-10-14
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.data;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.source.BaseInDataModel;
import com.fop.framework.source.db.context.DBConstant;
import com.fop.framework.source.db.exception.DBRuntimeException;
import com.fop.framework.source.pub.InvokeModel;
import com.fop.framework.source.pub.ProcessDataType;
import com.fop.framework.source.pub.SourceType;

/**
 * 描述:数据库操作传入参数模型，
 * 对所有属性的初始化只能在创建对象时完成，不提供set方法
 * @version 1.00
 * @author syl
 * 
 */
public class DBInDataModel implements BaseInDataModel{
  
  private Log logger = LogFactory.getLog(DBInDataModel.class);
  /**
   * 资源类型
   */
  private static final SourceType sourceType=SourceType.DataBase;
  /**
   * 存入参数模型信息
   */
  private HashMap<ProcessDataType,Object> dataModels=new HashMap<ProcessDataType, Object>();
  
  /**
   * 数据库操作入参基本构造方法
   * 该方法暂不开放
   * @param dbParamModel 数据库资源参数信息，必须输入
   * @param dbConnModel 数据库资源连接信息,可以为空
   * @param dbResModel 数据库资源返回结果信息，可以为空
   */
  DBInDataModel(DBParamModel dbParamModel,DBConnModel dbConnModel,DBResModel dbResModel){
    if(dbParamModel==null){
      logger.error(DBConstant.PARAM_NULL_ERROR);
      throw new DBRuntimeException(DBConstant.PARAM_NULL_ERROR);
    }
    //合法数据
    dataModels.put(ProcessDataType.PARAM, dbParamModel);
    if(dbConnModel==null){
      dbConnModel=new DBConnModel();
     }
    dataModels.put(ProcessDataType.CONN, dbConnModel);
    if(dbResModel==null){
      dbResModel=new DBResModel();
    }
    dataModels.put(ProcessDataType.RESULT, dbResModel);
  }
  
  @Override
  public Object getData(ProcessDataType processDataType) {
    return dataModels.get(processDataType);
  }
  @Override
  public SourceType getSourceType() {
    return sourceType;
  }
  //************新提够的构造方法,方便开发**************
  
  /**
   * 通过Dao接口方式访问数据库的 构造方法
   * @param daoClass dao接口class
   * @param method 方法名
   * @param values 方法包含的入参,没有时传空
   */
  public DBInDataModel(Class<?> daoClass,String method,Object[] values){
    this(daoClass, method, values, DBResEnumTypes.NoConvert);
  }
  /**
   * 
   * 通过Dao接口方式访问数据库的构造方法
   * @param daoClass dao接口class
   * @param method 方法名
   * @param values 方法包含的入参,没有时传空
   * @param dbResEnumType 结果数据要转换的类型
   */
  public DBInDataModel(Class<?> daoClass,String method,Object[] values,DBResEnumTypes dbResEnumType){
    this(new DBParamModel(new InvokeModel(method,values)),new DBConnModel(daoClass),new DBResModel(dbResEnumType));
  }
  /**
   * 通过Dao接口方式访问数据库的构造方法
   * @param daoClass dao接口class
   * @param daoInvokeModel dao执行对象
   */
  public DBInDataModel(Class<?> daoClass,InvokeModel daoInvokeModel){
    this(daoClass, daoInvokeModel, DBResEnumTypes.NoConvert);
  }
  /**
   * 
   * 通过Dao接口方式访问数据库的构造方法
   * @param daoClass dao接口class
   * @param daoInvokeModel dao执行对象
   * @param dbResEnumType 结果数据要转换的类型
   */
  public DBInDataModel(Class<?> daoClass,InvokeModel daoInvokeModel,DBResEnumTypes dbResEnumType){
    this(new DBParamModel(daoInvokeModel),new DBConnModel(daoClass),new DBResModel(dbResEnumType));
  }
  /**
   * 通过sql语句的方式访问数据库的构造方法
   * 执行更新、删除、新增时返回boolean结果数据
   * 返回true表示成功，返回false表示失败。
   * @param sql 要执行的sql语句
   * 
   */
  public DBInDataModel(String sql){
    this(new DBParamModel(sql),null,null);
  }
  /*public DBInDataModel(String sql,boolean isKey,DBResEnumTypes dbResEnumType){
    this(new DBParamModel(sql,isKey),null,new DBResModel(dbResEnumType));
  }*/
  /*public DBInDataModel(String sql,GroupData sqlParams,DBResEnumTypes dbResEnumType){
    this(new DBParamModel(sql,sqlParams),null,new DBResModel(dbResEnumType));
  }*/
  
  
}

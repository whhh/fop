/**
 * 文件名    ：DBConstant.java
 * 创建日期 ：2013-10-12
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.context;

/**
 * 描述:数据库全局静态值
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class DBConstant {
  public static final String CONST_Sql="sql";
  public static final String CONST_InvokeModel="invokeModel";
  public static final String CONST_ConnInfo="connInfo";
  public static final String CONST_DBExchangeRes="dbExchangeRes";
  
  public static final String SqlExe_Class_NotFound="未找到执行sql的class";
  public static final String Method_Develop_UnFinished="功能暂不支持";
  
  public static final String PARAM_NULL_ERROR="数据库传入参数模型不能为空";
  public static final String PARAM_TYPE_ERROR="数据库传入参数错误";
  public static final String CONN_NULL_ERROR = "数据库连接信息模型不能为空。";
  public static final String CONN_TYPE_ERROR="未知的数据连接类型";
  public static final String CONN_GET_DAO_ERROR="获取DAO实例发生异常";

  public static final String SQL_IS_NULL="获取到的sql语句为空";
  public static final String SQL_UNKNOWN="未识别的sql语句";
  public static final String DAO_METHOD_NOT_EXIST="请求的方法不存在";
  public static final String DAO_METHOD_EXE_ERROR="执行dao方法发生异常";
  public static final String RESULT_UNSUPPORT_TYPE="不支持转换的结果类型数据";
  public static final String RESULT_DATA_CONVERT_ERROR="结果数据转换错误";
  
  public static final String DATA_NOT_ALLOW_REPLACED="数据不允许被替换";
  public static final String SQL_UNSupport_TYPE = "不支持的sql操作类型";
  public static final String SQL_EXE_ERROR = "执行sql语句发生异常";
  
}

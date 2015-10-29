/**
 * 文件名		：DBUtil.java
 * 创建日期	：2013-9-27
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.source.db.exception.DBRuntimeException;

/**
 * 业务无关的与数据库操作相关的工具类
 * @version 1.00
 * @author syl
 * 
 */
public class DBUtil {
  
  private static Log logger = LogFactory.getLog(DBUtil.class);
  /**
   * 通过数据源获得连接 
   */
  public static Connection getConn(DataSource dataSource){
    Connection conn=null;
    try {
      conn = dataSource.getConnection();
    } catch (SQLException e) {
      logger.error("获取Connection异常", e);
      throw new DBRuntimeException(e);
    }
    return conn;
  }
  /**
   * 关闭资源
   * @param prepStat
   * @param conn
   */
  public static void closeDB(PreparedStatement prepStat,Connection conn){
    try {
      if(prepStat!=null){
        prepStat.close();
      }
    } catch (SQLException e) {
      logger.error("关闭PreparedStatement异常", e);
      throw new DBRuntimeException(e);
    }
    try {
      if(conn!=null){
        conn.close();
      }
    } catch (SQLException e) {
      logger.error("关闭PreparedStatement异常", e);
      throw new DBRuntimeException(e);
    }
  }
//  /**
//   * 执行新增操作
//   * @param conn
//   * @param sql
//   * @return
//   */
//  public static boolean executeInsert(DataSource dataSource,String sql){
//    return executeInsert(dataSource,sql,false);
//  }
//  public static boolean executeInsert(DataSource dataSource,String sql,boolean keepConn){
//    return executeInsert(getConn(dataSource), sql,keepConn);
//  }
//  public static boolean executeInsert(Connection conn,String sql){
//    return executeInsert(conn, sql,false);
//  }
//  public static boolean executeInsert(Connection conn,String sql,boolean keepConn){
//    boolean res=false;
//    PreparedStatement prepStat=null;
//    try {
//      prepStat=conn.prepareStatement(sql);
//      prepStat.execute();
//      res=true;
//    } catch (Exception e) {
//      res=false;
//      throw new DBRuntimeException(e);
//    }finally{
//      if(!keepConn){
//        closeDB(prepStat, conn);
//      }else{
//        closeDB(prepStat, null);
//      }
//    }
//    return res;
//  }
//  /**
//   * 执行更新操作
//   * @param conn
//   * @param sql
//   * @return
//   */
//  public static int executeUpdate(DataSource dataSource,String sql){
//    return executeUpdate(dataSource, sql,false);
//  }
//  public static int executeUpdate(DataSource dataSource,String sql,boolean keepConn){
//    return executeUpdate(getConn(dataSource),sql,keepConn);
//  }
//  public static int executeUpdate(Connection conn,String sql){
//    return executeUpdate(conn, sql, false);
//  }
//  public static int executeUpdate(Connection conn,String sql,boolean keepConn){
//    int res=-1;
//    PreparedStatement prepStat=null;
//    try {
//      prepStat=conn.prepareStatement(sql);
//      res=prepStat.executeUpdate();
//    } catch (Exception e) {
//      throw new DBRuntimeException(e);
//    }finally{
//      if(!keepConn){
//        closeDB(prepStat, conn);
//      }else{
//        closeDB(prepStat, null);
//      }
//    }
//    return res;
//  }
//  /**
//   * 执行查询操作
//   * @param conn
//   * @param sql
//   * @return
//   */
//  public static LinkedList<LinkedHashMap<String,Object>> executeQuery(DataSource dataSource,String sql){
//    return executeQuery(dataSource, sql, false);
//  }
//  public static LinkedList<LinkedHashMap<String,Object>> executeQuery(DataSource dataSource,String sql,boolean keepConn){
//    return executeQuery(getConn(dataSource),sql,keepConn);
//  }
//  public static LinkedList<LinkedHashMap<String,Object>> executeQuery(Connection conn,String sql){
//    return executeQuery(conn, sql, false);
//  }
//  
////TODO 待测试
//  public static LinkedList<LinkedHashMap<String,Object>> executeQuery(Connection conn,String sql,boolean keepConn){
//    LinkedList<LinkedHashMap<String,Object>> resData=new LinkedList<LinkedHashMap<String,Object>>();
//    PreparedStatement prepStat=null;
//    try {
//      prepStat=conn.prepareStatement(sql);
//      ResultSet rs = prepStat.executeQuery(sql);
//      while(rs.next()){
//        LinkedHashMap<String,Object> currMap=new LinkedHashMap<String, Object>();
//        ResultSetMetaData rsmd = rs.getMetaData();
//        int numberOfColumns = rsmd.getColumnCount();
//        for(int i=1;i<=numberOfColumns;i++){
//          currMap.put(rsmd.getColumnName(i), rs.getObject(i));//TODO 待测试 是否支持别名
//        }
//        resData.add(currMap);
//      }
//    } catch (SQLException e) {
//      throw new DBRuntimeException(e);
//    }finally{
//      if(!keepConn){
//        closeDB(prepStat, conn);
//      }else{
//        closeDB(prepStat, null);
//      }
//    }
//    return resData;
//  }
//  
//  /**
//   * 执行删除操作
//   * @param conn
//   * @param sql
//   * @return
//   */
// 
//  public static int executeDelete(DataSource dataSource,String sql){
//    return executeDelete(dataSource, sql,false);
//  }
//  public static int executeDelete(DataSource dataSource,String sql,boolean keepConn){
//    return executeDelete(getConn(dataSource),sql,keepConn);
//  }
//  public static int executeDelete(Connection conn,String sql){
//    return executeDelete(conn, sql, false);
//  }
//  //TODO 待测试
//  public static int executeDelete(Connection conn,String sql,boolean keepConn){
//    int res=-1;
//    PreparedStatement prepStat=null;
//    try {
//      prepStat=conn.prepareStatement(sql);
//      res=prepStat.executeUpdate(sql);
//    } catch (SQLException e) {
//      throw new DBRuntimeException(e);
//    }finally{
//      if(!keepConn){
//        closeDB(prepStat, conn);
//      }else{
//        closeDB(prepStat, null);
//      }
//    }
//    return res;
//  }
  
}

/**
 * 文件名		：DBContextOper.java
 * 创建日期	：2013-10-9
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.source.BaseContext;
import com.fop.framework.source.BaseInDataModel;
import com.fop.framework.source.db.ISqlExecute;
import com.fop.framework.source.db.exception.DBRuntimeException;
import com.fop.framework.source.pub.SourceConstant;

/**
 * 描述:数据库资源操作上下文对象
 * @version 1.00
 * @author syl
 * 
 */
public class DBContext extends BaseContext{
//  /** sql语句缓存 */
//  private static HashMap<String,String> basicSqlMap=null;
  
  private Log logger = LogFactory.getLog(DBContext.class);
  
  private static final long serialVersionUID = 3861215474079185544L;
  /**实现Mybatis执行sql的Class*/
  private static Class<?> sqlExecuteClass;
  /**
   * 数据库上下文对象的构造方法
   * @param inData 资源访问的入参信息
   */
  public DBContext(BaseInDataModel inData){
    super(inData);
  }
  
  public DBContext(String sqlExecuteClassStr){
    synchronized (DBContext.class){
      if(sqlExecuteClass==null){
        try {
          DBContext.sqlExecuteClass=Class.forName(sqlExecuteClassStr);
        } catch (ClassNotFoundException e) {
          logger.error("加载类型异常", e);
          throw new DBRuntimeException(DBConstant.SqlExe_Class_NotFound+sqlExecuteClassStr,e);
        }
      }else{//代码控制只能初始化一次
        throw new DBRuntimeException(SourceConstant.OBJECT_MULTI_CREATE_ERROR);
      }
    }//synchronized
  }
  
  /**
   * 从上下文中获取默认的sql执行对象
   * @return 可执行sql的对象
   */
  public  static ISqlExecute getDefaulSqlExecute(){
    return (ISqlExecute)getBean(sqlExecuteClass);
  }
  
  
//  /**
//   * 获取基本sql 暂时设定为private类型
//   * @return
//   */
//  private  LinkedHashMap<String,LinkedHashMap<String,String>> getBasicSql(){
//    return basicSqlCache;
//  }
  
//  /**
//   * 获取处理后的基本sql,初次获取进行初始化
//   * @return
//   */
//  public  LinkedHashMap<String,String> getBasicSqlCombi(){
//    if(basicSqlCombi==null){
//      LinkedHashMap<String,String> resMap=new LinkedHashMap<String, String>();
//      LinkedHashMap<String,LinkedHashMap<String,String>> basicSqlCache=getBasicSql();
//      for(Map.Entry<String,LinkedHashMap<String,String>> entry01:basicSqlCache.entrySet()){
//        String key01=entry01.getKey();
//        LinkedHashMap<String,String> value01=entry01.getValue();
//        for(Map.Entry<String,String> entry02:value01.entrySet()){
//          resMap.put(key01+"."+entry02.getKey(),entry02.getValue());
//        }
//      }
//      basicSqlCombi=resMap;
//    }
//    return basicSqlCombi;
//  }

}

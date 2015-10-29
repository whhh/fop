/**
 * 文件名		：DBConnManagerImpl.java
 * 创建日期	：2013-10-9
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.source.BaseContext;
import com.fop.framework.source.db.context.DBConstant;
import com.fop.framework.source.db.context.DBContext;
import com.fop.framework.source.db.data.DBConnEnumTypes;
import com.fop.framework.source.db.data.DBConnModel;
import com.fop.framework.source.db.data.DBInDataModel;
import com.fop.framework.source.db.exception.DBException;
import com.fop.framework.source.db.exception.DBRuntimeException;
import com.fop.framework.source.pub.ProcessDataType;
import com.fop.framework.source.pub.SourceConstant;

/**
 * 描述:数据库连接管理的实现
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class DBConnManagerImpl implements DBConnManager  {
  
  private Log logger = LogFactory.getLog(DBConnManagerImpl.class);
  
  @Override
  public void operate(BaseContext dbContext) {
    //从上下文中获取数据库资源访问总入参对象
    DBInDataModel dbInDataModel=(DBInDataModel)(dbContext.getInData());
    //从总入参对象中得到连接信息
    DBConnModel dbConnModel=(DBConnModel)dbInDataModel.getData(ProcessDataType.CONN);
    
    DBConnEnumTypes dbConnEnumType=dbConnModel.getDbEnumConnType();
    Object connInfo=null;
    switch(dbConnEnumType){
      case Class_DefaultDbExec:
        connInfo=this.getDefaultSqlExecute();
        break;
      case Class_DBBaseDao:
        try {
          connInfo=this.getDaoImpl(dbConnModel.getDaoClass());
        } catch (Exception e) {
          logger.error("根据类型获取数据库操作实例异常", e);
          throw new DBRuntimeException(DBConstant.CONN_GET_DAO_ERROR+":"+dbConnModel.getDaoClass(),e);
        }
        break;
      default :
        logger.error("数据访问连接类型错误");
        throw new DBRuntimeException(DBConstant.CONN_TYPE_ERROR+":"+dbConnEnumType);
    }
    dbContext.put(DBConstant.CONST_ConnInfo, connInfo,false);
  }
  
  @Override
  public Object getDefaultSqlExecute() {
    return DBContext.getDefaulSqlExecute();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getDaoImpl(Class<T> clazz) throws DBException {
    // 去Spring上下文查找配置的实现类对象返回
    T res=null;
    try {
      res= (T)DBContext.getBean(clazz);
    } catch (Exception e) {
        throw new DBException(e);
    }
    return res;
  }

  @Override
  public String getDescription() {
    return SourceConstant.DB_Conn;
  }

}

/**
 * 文件名		：DBResultManagerImpl.java
 * 创建日期	：2013-10-9
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.manager;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.data.FieldData;
import com.fop.framework.data.GroupData;
import com.fop.framework.data.RepeatData;
import com.fop.framework.source.BaseContext;
import com.fop.framework.source.db.context.DBConstant;
import com.fop.framework.source.db.data.DBConnEnumTypes;
import com.fop.framework.source.db.data.DBConnModel;
import com.fop.framework.source.db.data.DBInDataModel;
import com.fop.framework.source.db.data.DBResEnumTypes;
import com.fop.framework.source.db.data.DBResModel;
import com.fop.framework.source.db.exception.DBRuntimeException;
import com.fop.framework.source.pub.ProcessDataType;
import com.fop.framework.source.pub.SourceConstant;
import com.fop.framework.source.pub.SourceUtil;

/**
 * 描述:数据库结果数据实现类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class DBResultManagerImpl implements DBResultManager{
  
  private Log logger = LogFactory.getLog(DBResultManagerImpl.class);
  
  @SuppressWarnings("unchecked")
  @Override
  public void operate(BaseContext dbContext) {
    //从上下文中获取数据库资源访问总入参对象
    DBInDataModel dbInDataModel=(DBInDataModel)(dbContext.getInData());
    //从总入参对象中得到连接信息
    DBConnModel dbConnModel=(DBConnModel)dbInDataModel.getData(ProcessDataType.CONN);
    //从总入参对象中得到结果信息
    DBResModel dbResModel=(DBResModel)dbInDataModel.getData(ProcessDataType.RESULT);
    
    DBConnEnumTypes dbConnEnumType=dbConnModel.getDbEnumConnType();
    Object srcData=dbContext.get(DBConstant.CONST_DBExchangeRes);
    Object resData=null;
    DBResEnumTypes dbEnumResType=dbResModel.getDbResEnumType();
    try {
      switch(dbConnEnumType){
      //连接类型
      case Class_DefaultDbExec:
        switch(dbEnumResType){
        case NoConvert:
          resData=this.getNoConvert(srcData);
          break;
        case FieldData:
          resData=this.getFileData((LinkedList<LinkedHashMap<String,Object>>)srcData);
          break;
        case GroupData:
          resData=this.getGroupData((LinkedList<LinkedHashMap<String,Object>>)srcData);
          break;
        case RepeatData:
          resData=this.getRepeatData((LinkedList<LinkedHashMap<String,Object>>)srcData);
          break;
        default:
          logger.error(DBConstant.RESULT_UNSUPPORT_TYPE+":"+dbEnumResType);
          throw new DBRuntimeException(DBConstant.RESULT_UNSUPPORT_TYPE+":"+dbEnumResType);
        }
        break;
      //Dao接口方式
      case Class_DBBaseDao:
        try {
          switch(dbEnumResType){
          case NoConvert:
            resData=this.getNoConvert(srcData);
            break;
          case FieldData:
            resData=SourceUtil.getFileData(srcData);
            break;
          case GroupData:
            if(srcData instanceof Map){
              resData=SourceUtil.getGroupData((Map<String,Object>)srcData);
            }else{//否则当作List去处理
              resData=SourceUtil.getGroupData((List<Object>)srcData);
            }
            break;
          case RepeatData:
            resData=SourceUtil.getRepeatData((List<Map<String,Object>>)srcData);
            break;
          default:
            logger.error(DBConstant.RESULT_UNSUPPORT_TYPE+":"+dbEnumResType);
            throw new DBRuntimeException(DBConstant.RESULT_UNSUPPORT_TYPE+":"+dbEnumResType);
          }
        } catch (Exception e) {
          logger.error(DBConstant.RESULT_DATA_CONVERT_ERROR, e);
          throw new DBRuntimeException(DBConstant.RESULT_DATA_CONVERT_ERROR,e);
        }
        break;
      default :
        logger.error(DBConstant.CONN_TYPE_ERROR+":"+dbConnEnumType);
        throw new DBRuntimeException(DBConstant.CONN_TYPE_ERROR+":"+dbConnEnumType);
      }
    }catch (Exception e) {
      logger.error(DBConstant.RESULT_DATA_CONVERT_ERROR+":"+dbConnEnumType,e);
      throw new DBRuntimeException(DBConstant.RESULT_DATA_CONVERT_ERROR+":"+dbConnEnumType,e);
    }
    dbContext.setOutData(resData);
  }
  
  @Override
  public Object getNoConvert(Object srcData) {
    return srcData;
  }

  @Override
  public FieldData getFileData(LinkedList<LinkedHashMap<String, Object>> srcData) {
    FieldData resData=null;
    if(srcData.size()>1||srcData.get(0).size()>1){
      logger.error(DBConstant.RESULT_DATA_CONVERT_ERROR);
      throw new DBRuntimeException(DBConstant.RESULT_DATA_CONVERT_ERROR);
    }else{
      for(Map.Entry<String, Object> entry01:srcData.get(0).entrySet()){
        FieldData fData=new FieldData(entry01.getKey(),entry01.getValue());
        resData=fData;
        break;//只获取第一一个
      }
    }
    return resData;
  }
  
  @Override
  public GroupData getGroupData(LinkedList<LinkedHashMap<String, Object>> srcData) {
    GroupData resData=null;
    try {
      LinkedHashMap<String,Object> tmpValue=srcData.get(0);
      resData=SourceUtil.getGroupData(tmpValue);
    } catch (Exception e) {
      logger.error(DBConstant.RESULT_DATA_CONVERT_ERROR, e);
      throw new DBRuntimeException(DBConstant.RESULT_DATA_CONVERT_ERROR,e);
    }
    return resData;
  }
  
  @Override
  public RepeatData getRepeatData(LinkedList<LinkedHashMap<String, Object>> srcData) {
    RepeatData resData=null;
    RepeatData rData=new RepeatData();
    try {
      int listSize=srcData.size();
      for(int i=0;i<listSize;i++){
        GroupData gData=SourceUtil.getGroupData(srcData.get(i));
        rData.addGroupData(gData);
      }
    } catch (Exception e) {
      logger.error(DBConstant.RESULT_DATA_CONVERT_ERROR,e);
      throw new DBRuntimeException(DBConstant.RESULT_DATA_CONVERT_ERROR,e);
    }
    resData=rData;
    return resData;
  }
  @Override
  public String getDescription() {
    return SourceConstant.DB_Res;
  }
}

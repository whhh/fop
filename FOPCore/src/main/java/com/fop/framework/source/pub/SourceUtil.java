/**
 * 文件名		：SourceUtil.java
 * 创建日期	：2013-10-9
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.pub;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.data.FieldData;
import com.fop.framework.data.GroupData;
import com.fop.framework.data.RepeatData;
import com.fop.framework.source.db.context.DBConstant;
import com.fop.framework.source.db.exception.DBRuntimeException;


/**
 * 描述:资源管理公共工具类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class SourceUtil {
  
  private static Log logger = LogFactory.getLog(SourceUtil.class);
  
  /**
   * 将Object转为FieldData,key值设置为null
   * @param srcData 待处理的原始数据
   * @return
   */
  public static FieldData getFileData(Object srcData) {
    FieldData resData=null;
    resData=new FieldData(null,srcData);
    return resData;
  }
  /**
   * 将Map转为GroupData
   * @param srcData 待处理的原始数据
   * @return
   */
  public static GroupData getGroupData(Map<String,Object> srcData) {
    GroupData resData=null;
    resData=new GroupData();
    for(Map.Entry<String, Object> entry01:srcData.entrySet()){
      resData.addFieldData(entry01.getKey(), entry01.getValue());
    }
    return resData;
  }
  /**
   * 将List<Object>转为GroupData,所有FieldData的key值均为null
   * @param exchangeRes
   * @return
   */
  public static GroupData getGroupData(List<Object> exchangeRes) {
    GroupData gData=new GroupData();
    for(Object obj:exchangeRes){
      gData.addFieldData(null,obj);
    }
    return gData;
  }
  /**
   * 将List<Map<String,Object>>转为RepeatData
   * @param srcData 待处理的原始数据
   * @return
   */
  public static RepeatData getRepeatData(List<Map<String,Object>> srcData) {
    RepeatData resData=null;
    RepeatData rData=new RepeatData();
    try {
      int listSize=srcData.size();
      for(int i=0;i<listSize;i++){
        GroupData gData=new GroupData();
        Map<String,Object> tmpValue=srcData.get(i);
        for(Map.Entry<String, Object> entry01:tmpValue.entrySet()){
          gData.addFieldData(entry01.getKey(), entry01.getValue());
        }
        rData.addGroupData(gData);
      }
    } catch (Exception e) {
      logger.error(DBConstant.RESULT_DATA_CONVERT_ERROR, e);
      throw new DBRuntimeException(DBConstant.RESULT_DATA_CONVERT_ERROR,e);
    }
    resData=rData;
    return resData;
  }
  
}

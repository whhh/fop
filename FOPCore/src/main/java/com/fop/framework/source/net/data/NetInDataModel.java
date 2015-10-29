/**
 * 文件名		：NetInDataModel.java
 * 创建日期	：2013-10-16
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.net.data;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.source.BaseInDataModel;
import com.fop.framework.source.db.exception.DBRuntimeException;
import com.fop.framework.source.net.context.NetConstant;
import com.fop.framework.source.pub.ProcessDataType;
import com.fop.framework.source.pub.SourceType;

/**
 * 描述:其他系统访问参数配置模型
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class NetInDataModel implements BaseInDataModel{
  
  private Log logger = LogFactory.getLog(NetInDataModel.class);
  /**
   * 资源类型
   */
  private static final SourceType sourceType=SourceType.Net;
  /**
   * 存入参数模型信息
   */
  private HashMap<ProcessDataType,Object> dataModels=new HashMap<ProcessDataType, Object>();
  
  @Override
  public Object getData(ProcessDataType processDataType) {
    return dataModels.get(processDataType);
  }
  @Override
  public SourceType getSourceType() {
    return sourceType;
  }
  /**
   * 其他系统资源访问的入参基本构造函数
   * @param netParamModel 参数模型
   */
  NetInDataModel(NetParamModel netParamModel){
    if(netParamModel==null){
      logger.error(NetConstant.PARAM_NULL_ERROR);
      throw new DBRuntimeException(NetConstant.PARAM_NULL_ERROR);
    }
    //合法数据
    dataModels.put(ProcessDataType.PARAM, netParamModel);
  }
  /**
   * 其他系统资源访问的入参构造函数
   * @param serviceContainer socket容器名称
   * @param messageConfigId 报文配置Id
   * @param message 报文内容
   * @param timeOut 超时时间
   */
  public NetInDataModel(String serviceContainer,String messageConfigId,Object message,int timeOut){
    this(new NetParamModel(serviceContainer, messageConfigId, message, timeOut));
  }
  /**
   * 其他系统资源访问的入参构造函数
   * @param serviceContainer socket容器名称
   * @param messageConfigId 报文配置Id
   * @param message 报文内容
   */
  public NetInDataModel(String serviceContainer,String messageConfigId,Object message){
    this(new NetParamModel(serviceContainer, messageConfigId, message));
  }

}

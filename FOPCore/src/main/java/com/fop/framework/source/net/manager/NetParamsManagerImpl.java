/**
 * 文件名		：NetParamsManagerImpl.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.net.manager;

import com.fop.framework.source.BaseContext;
import com.fop.framework.source.net.context.NetConstant;
import com.fop.framework.source.net.data.NetInDataModel;
import com.fop.framework.source.net.data.NetParamModel;
import com.fop.framework.source.pub.ProcessDataType;
import com.fop.framework.source.pub.SourceConstant;

/**
 * 描述:其他系统访问参数处理实现类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class NetParamsManagerImpl implements NetParamsManager{
  
  @Override
  public void operate(BaseContext netContext) {
  //从上下文中获取文件资源访问总入参对象
    NetInDataModel netInDataModel=(NetInDataModel)(netContext.getInData());
    //从总入参对象中得到参数信息
    NetParamModel netParamModel=(NetParamModel)netInDataModel.getData(ProcessDataType.PARAM);
    
    String serviceContainer=netParamModel.getServiceContainer();//socket容器名称
    String messageConfigId=netParamModel.getMessageConfigId();//报文配置Id
    Object message=netParamModel.getMessage(); //报文内容
    long timeOut=netParamModel.getTimeOut(); //超时时间
    
    netContext.put(NetConstant.CONST_ServiceContainer, serviceContainer,false);
    netContext.put(NetConstant.CONST_MessageConfigId, messageConfigId,false);
    netContext.put(NetConstant.CONST_Message, message,false);
    netContext.put(NetConstant.CONST_TimeOut, timeOut,false);
  }
  @Override
  public String getDescription() {
    return SourceConstant.Net_Param;
  }
  
}

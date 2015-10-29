/**
 * 文件名		：FileResultManagerImpl.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.manager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.data.FieldData;
import com.fop.framework.data.GroupData;
import com.fop.framework.source.BaseContext;
import com.fop.framework.source.file.context.FileConstant;
import com.fop.framework.source.file.data.FileExchangeEnumTypes;
import com.fop.framework.source.file.data.FileInDataModel;
import com.fop.framework.source.file.data.FileResEnumTypes;
import com.fop.framework.source.file.data.FileResModel;
import com.fop.framework.source.file.exception.FileRuntimeException;
import com.fop.framework.source.pub.ProcessDataType;
import com.fop.framework.source.pub.SourceConstant;
import com.fop.framework.source.pub.SourceUtil;
import com.jcraft.jsch.Session;

/**
 * 描述:文件资源操作结果处理实现类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class FileResultManagerImpl implements FileResultManager{
  
  private Log logger = LogFactory.getLog(FileResultManagerImpl.class);
  
  @SuppressWarnings("unchecked")
  @Override
  public void operate(BaseContext fileContext) {
    //从上下文中获取文件资源访问总入参对象
    FileInDataModel fileInDataModel=(FileInDataModel)(fileContext.getInData());
    //从总入参对象中得到结果信息
    FileResModel fileResModel=(FileResModel)fileInDataModel.getData(ProcessDataType.RESULT);
    
    FileResEnumTypes fileResEnumType=fileResModel.getFileResEnumType();
    FileExchangeEnumTypes fileExchangeEnumType=(FileExchangeEnumTypes)fileContext.get(FileConstant.CONST_fileExchangeEnumType);
    Object exchangeRes=fileContext.get(FileConstant.CONST_exchangeRes);
    Object sessionInfo=fileContext.get(FileConstant.CONST_sessionInfo);
    closeSession((Session)sessionInfo);
    Object resData=null;
    switch(fileResEnumType){
    case NoConvert:
      resData=this.getNoConvert(exchangeRes);
      break;
    case FieldData:
      {
        switch(fileExchangeEnumType){
        case Delete_Dir:
        case Delete_File:
        case IS_Dir_Exist:
        case IS_File_Exist:
        case Make_Dirs:
        case Query_DirFileNames:
        case Read_File:
        case Replace_File:
        case Write_File:
          resData=this.getFieldData(exchangeRes);
          break;
        default:
          throw new FileRuntimeException(FileConstant.Exchange_Unknown_Type+":"+fileExchangeEnumType);
        }
      }
      break;
    case GroupData:
      {
        switch(fileExchangeEnumType){
        case Query_DirFileNames:
          resData=this.getGroupData((List<String>)exchangeRes);
          break;
        case Delete_Dir:
        case Delete_File:
        case IS_Dir_Exist:
        case IS_File_Exist:
        case Make_Dirs:
        case Read_File:
        case Replace_File:
        case Write_File:
          throw new FileRuntimeException(FileConstant.Result_UnSupport_Type);
        default:
          throw new FileRuntimeException(FileConstant.Exchange_Unknown_Type+":"+fileExchangeEnumType);
        }
      }
      break;
    case RepeatData:
      {
        switch(fileExchangeEnumType){
        case Delete_Dir:
        case Delete_File:
        case IS_Dir_Exist:
        case IS_File_Exist:
        case Make_Dirs:
        case Query_DirFileNames:
        case Read_File:
        case Replace_File:
        case Write_File:
          throw new FileRuntimeException(FileConstant.Result_UnSupport_Type);
        default:
          throw new FileRuntimeException(FileConstant.Exchange_Unknown_Type+":"+fileExchangeEnumType);
        }
      }
//      break;
    default:
      throw new FileRuntimeException(FileConstant.Result_Unknown_Type); 
    }

    fileContext.setOutData(resData);
  }
  
  public void closeSession(Session sessionInfo) {
    try {
      if(sessionInfo != null && sessionInfo.isConnected()) {
        sessionInfo.disconnect();
      }
    }catch (Exception e) {
      logger.error("--------framework closeSession is error.", e);
    }
    
  }

  @Override
  public Object getNoConvert(Object exchangeRes) {
    return exchangeRes;
  }

  @Override
  public FieldData getFieldData(Object exchangeRes) {
    return SourceUtil.getFileData(exchangeRes);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public GroupData getGroupData(List<String> exchangeRes) {
    return SourceUtil.getGroupData((List)exchangeRes);
  }
  @Override
  public String getDescription() {
    return SourceConstant.File_Res;
  }
}

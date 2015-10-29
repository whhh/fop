/**
 * 文件名		：FileParamsManagerImpl.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.manager;

import com.fop.framework.source.BaseContext;
import com.fop.framework.source.file.context.FileConstant;
import com.fop.framework.source.file.context.FileContext;
import com.fop.framework.source.file.data.FileConnModel;
import com.fop.framework.source.file.data.FileInDataModel;
import com.fop.framework.source.file.data.FileParamEnumTypes;
import com.fop.framework.source.file.data.FileParamModel;
import com.fop.framework.source.file.exception.FileRuntimeException;
import com.fop.framework.source.file.util.FileUtil;
import com.fop.framework.source.pub.ProcessDataType;
import com.fop.framework.source.pub.SourceConstant;
import com.fop.framework.util.StringUtil;

/**
 * 描述:文件资源参数管理实现类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class FileParamsManagerImpl implements FileParamsManager{
  
  @Override
  public void operate(BaseContext fileContext) {
    //从上下文中获取文件资源访问总入参对象
    FileInDataModel fileInDataModel=(FileInDataModel)(fileContext.getInData());
    //从总入参对象中得到参数信息
    FileParamModel fileParamModel=(FileParamModel)fileInDataModel.getData(ProcessDataType.PARAM);
    FileConnModel fileConnModel=(FileConnModel)fileInDataModel.getData(ProcessDataType.CONN);
    String fileUrl=null;
    byte[] fileContent=null;
    String fileConnBeanId=fileConnModel.getFileConnBeanId();
    String basePath=FileContext.getServBasePath(fileConnBeanId);
    FileParamEnumTypes fileParamEnumType=fileParamModel.getFileParamEnumType();
    switch(fileParamEnumType){
    case File_None:
      fileUrl=this.getFileUrl(basePath,fileParamModel.getFileUrl());
      break;
    case File_Contain:
      fileUrl=this.getFileUrl(basePath,fileParamModel.getFileUrl());
      fileContent=this.getFileContent(fileParamModel.getFileContent());
      break;
    default:
      throw new FileRuntimeException(FileConstant.PARAM_TYPE_ERROR);
    }
    fileContext.put(FileConstant.CONST_fileUrl, fileUrl);
    fileContext.put(FileConstant.CONST_fileContent, fileContent);
    
  }

  @Override
  public String getFileUrl(String basePath,String fileUrl) {
    String resUrl=null;
    if(StringUtil.isStrEmpty(basePath)){
      resUrl=fileUrl;
    }else{
      resUrl=basePath+FileUtil.fileSplit+fileUrl;
    }
    return resUrl;
  }

  @Override
  public byte[] getFileContent(byte[] fileContent) {
    return fileContent;
  }
  @Override
  public String getDescription() {
    return SourceConstant.File_Param;
  }

}

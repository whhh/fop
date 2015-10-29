/**
 * 文件名		：FileParamModel.java
 * 创建日期	：2013-10-17
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.data;


/**
 * 描述:文件资源请求参数模型
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class FileParamModel {
  private FileParamEnumTypes fileParamEnumType;
  /**文件路径,上传时对应服务的目标文件路径，下载时对应服务的要下载的文件路径*/
  private String fileUrl;
  /**文件内容对象,上传时保存要上传的文件信息，下载时保存下载的结果内容*/
  private byte[] fileContent;
  /**
   * 查询
   * @param fileUrl 可以使用文件路径或者是目录路径
   * 为文件路径时，可用于删除文件时的参数，
   * 为目录路径时，可用于查询指定目录下的文件信息
   */
  public FileParamModel(String fileUrl){
    this.fileParamEnumType=FileParamEnumTypes.File_None;
    this.fileUrl=fileUrl;
  }
  /**
   * 新增、修改文件信息使用的构造方法
   */
  public FileParamModel(byte[] fileContent,String fileUrl){
    this.fileParamEnumType=FileParamEnumTypes.File_Contain;
    this.fileContent=fileContent;
    this.fileUrl=fileUrl;
  }
  
  /**
   * @return the fileParamEnumType
   */
  public FileParamEnumTypes getFileParamEnumType() {
    return fileParamEnumType;
  }
  /**
   * @return the fileUrl
   */
  public String getFileUrl() {
    return fileUrl;
  }
  /**
   * @return the fileContent
   */
  public byte[] getFileContent() {
    return fileContent;
  }

}

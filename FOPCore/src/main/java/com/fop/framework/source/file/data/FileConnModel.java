/**
 * 文件名		：FileConnModel.java
 * 创建日期	：2013-10-17
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.data;

import com.fop.framework.source.file.context.FileContext;
import com.fop.framework.source.file.data.FileXmlConfigBase.FileServerTypes;

/**
 * 描述:文件资源连接参数模型
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class FileConnModel {
  private FileConnEnumTypes fileConnEnumType;
 
  /**远程连接配置的beanId*/
  private String fileConnBeanId;
  /**文件服务器类型*/
  private FileServerTypes fileServerType;
  
  /**默认为FileConnEnumTypes.Conn_None*/
  protected FileConnModel(){
    this.fileConnEnumType=FileConnEnumTypes.Conn_None;
    this.fileServerType=FileServerTypes.LocalApp;
  }
//  /**
//   * 构造方法
//   * @param host 主机
//   * @param port 端口号*
//   * @param userName 用户名
//   * @param password 密码
//   * @param fileServerType 文件服务器类型
//   */
//  public FileConnModel(SftpParamModel sftpParamModel){
//    this.fileConnEnumType=FileConnEnumTypes.Conn_ByConfig;
//    this.sftpParamModel=sftpParamModel;
//    this.fileServerType=FileServerTypes.SFTP;
//  }
  /*public FileConnModel(String fileConnBeanId,FileServerTypes fileServerType){
    this.fileConnEnumType=FileConnEnumTypes.Conn_ByBeanId;
    this.fileConnBeanId=fileConnBeanId;
    this.fileServerType=fileServerType;
  }*/
  /**
   * 通过BeanId的方式获取文件连接
   * @param fileConnBeanId 文件服务器配置信息Id
   */
  public FileConnModel(String fileConnBeanId){
    this.fileConnEnumType=FileConnEnumTypes.Conn_ByBeanId;
    this.fileConnBeanId=fileConnBeanId;
    FileXmlConfigBase paramModel=(FileXmlConfigBase)FileContext.getBean(fileConnBeanId);
    this.fileServerType=paramModel.getServerTypeEnum();
  }
  /**
   * @return the fileConnEnumType
   */
  public FileConnEnumTypes getFileConnEnumType() {
    return fileConnEnumType;
  }
  /**
   * @return the fileConnBeanId
   */
  public String getFileConnBeanId() {
    return fileConnBeanId;
  }
  /**
   * @return the fileServerType
   */
  public FileServerTypes getFileServerType() {
    return fileServerType;
  }
  
}

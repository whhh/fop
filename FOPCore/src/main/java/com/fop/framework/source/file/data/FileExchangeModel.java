/**
 * 文件名		：FileExchangeModel.java
 * 创建日期	：2013-10-17
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.data;

/**
 * 描述:文件资源执行操作参数模型
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class FileExchangeModel {
  private FileExchangeEnumTypes fileExchangeEnumType;
  /**新增文件时,存在是否替换,默认false表示不替换*/
  private boolean replaceIfExist=false;
  /**更新文件时，不存在是否新建,默认false表示不更新*/
  private boolean newIfNotExist=false;
  
  /**
   * 构造函数
   * @param fileOperType 文件资源操作类型
   */
  public FileExchangeModel(FileExchangeEnumTypes fileOperType){
    this.fileExchangeEnumType=fileOperType;
  }
  /**
   * 构造函数
   * @param fileOperType 文件资源操作类型
   * @param replaceIfExist 新增文件时使用该参数,存在是否替换,false(默认)表示不替换,true表示替换
   * @param newIfNotExist 更新文件时使用该参数，不存在是否新建,false(默认)表示不新建,true表示要新建
   */
  public FileExchangeModel(FileExchangeEnumTypes fileOperType,Boolean replaceIfExist,Boolean newIfNotExist){
    this.fileExchangeEnumType=fileOperType;
    if(replaceIfExist!=null){this.replaceIfExist=replaceIfExist;}
    if(newIfNotExist!=null){this.newIfNotExist=newIfNotExist;}
  }
  
  /**
   * @return the fileExchangeEnumType
   */
  public FileExchangeEnumTypes getFileExchangeEnumType() {
    return fileExchangeEnumType;
  }
  /**
   * @return the replaceIfExist
   */
  public boolean isReplaceIfExist() {
    return replaceIfExist;
  }
  /**
   * @return the newIfNotExist
   */
  public boolean isNewIfNotExist() {
    return newIfNotExist;
  }
  
}

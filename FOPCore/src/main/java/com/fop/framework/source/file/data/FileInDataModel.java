/**
 * 文件名		：FileInDataModel.java
 * 创建日期	：2013-10-16
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.data;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.source.BaseInDataModel;
import com.fop.framework.source.db.exception.DBRuntimeException;
import com.fop.framework.source.file.context.FileConstant;
import com.fop.framework.source.pub.ProcessDataType;
import com.fop.framework.source.pub.SourceType;

/**
 * 描述:文件资源访问统一入参模型
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class FileInDataModel implements BaseInDataModel {
  
  private Log logger = LogFactory.getLog(FileInDataModel.class);
  /**
   * 资源类型
   */
  private static final SourceType sourceType=SourceType.File;
  /**
   * 存入参数模型信息
   */
  private HashMap<ProcessDataType,Object> dataModels=new HashMap<ProcessDataType, Object>();
  
  /**
   * 文件资源访问的入参基本构造函数
   * @param fileParamModel 文件资源的参数信息,必须输入
   * @param fileConnModel 文件资源的连接信息,可以为空
   * @param fileExchangeModel 文件资源的执行信息,可以为空
   * @param fileResModel 文件资源的结果信息,可以为空
   */
  FileInDataModel(FileParamModel fileParamModel,
      FileConnModel fileConnModel, FileExchangeModel fileExchangeModel,
      FileResModel fileResModel) {
    if(fileParamModel==null){
      logger.error(FileConstant.PARAM_NULL_ERROR);
      throw new DBRuntimeException(FileConstant.PARAM_NULL_ERROR);
    }
    //合法数据
    dataModels.put(ProcessDataType.PARAM, fileParamModel);
    if (fileConnModel == null) {
      fileConnModel = new FileConnModel();
    }
    dataModels.put(ProcessDataType.CONN, fileConnModel);
    if (fileExchangeModel == null) {
      logger.error(FileConstant.Exchange_NULL_ERROR);
      throw new DBRuntimeException(FileConstant.Exchange_NULL_ERROR);
    }
    //合法数据
    dataModels.put(ProcessDataType.EXCHANGE, fileExchangeModel);
    if (fileResModel == null) {
      fileResModel = new FileResModel();
    }
    dataModels.put(ProcessDataType.RESULT, fileResModel);
  }
  @Override
  public Object getData(ProcessDataType processDataType) {
    return dataModels.get(processDataType);
  }
  @Override
  public SourceType getSourceType() {
    return sourceType;
  }
  //************新提够的构造方法,方便开发**************
  /**
   * 本地资源操作构造方法
   * 读取文件,返回byte[]数组
   * @param fileUrl 资源路径
   */
  public FileInDataModel(String fileUrl){
    this(new FileParamModel(fileUrl), 
        null, 
        new FileExchangeModel(FileExchangeEnumTypes.Read_File), 
        null);
  }
  /**
   * 本地资源操作构造方法
   * @param fileUrl 资源路径
   * @param fileExchangeEnumType 资源操作类型
   * IS_File_Exist 判断文件是否存在,true存在,false不存在 
   * IS_Dir_Exist 判断文件夹是否存在,true存在,false不存在  
   * Make_Dirs 创建一个或者多个连续文件夹,true成功,false失败 
   * Read_File 获取本地文件、下载服务器文件,返回byte[]数组 
   * Delete_File 删除文件,返回Boolean,表示操作是否成功,true成功,false失败 
   * Delete_Dir 删除文件夹,返回Boolean,表示操作是否成功,true成功,false失败,如果文件下有内容则删除失败 
   * Query_DirFileNames 获取文件列表名称,返回List<String>文件名列表 
   */
  public FileInDataModel(String fileUrl,FileExchangeEnumTypes fileExchangeEnumType){
    this(new FileParamModel(fileUrl), 
        null, 
        new FileExchangeModel(fileExchangeEnumType), 
        null);
  }
  /**
   * 本地资源操作构造方法
   * 
   * @param fileUrl 资源路径
   * @param fileExchangeEnumType 资源操作类型
   * IS_File_Exist 判断文件是否存在,true存在,false不存在 
   * IS_Dir_Exist 判断文件夹是否存在,true存在,false不存在  
   * Make_Dirs 创建一个或者多个连续文件夹,true成功,false失败 
   * Read_File 获取本地文件、下载服务器文件,返回byte[]数组 
   * Delete_File 删除文件,返回Boolean,表示操作是否成功,true成功,false失败 
   * Delete_Dir 删除文件夹,返回Boolean,表示操作是否成功,true成功,false失败,如果文件下有内容则删除失败 
   * Query_DirFileNames 获取文件列表名称,返回List<String>文件名列表 
   * @param fileResEnumType 结果数据类型
   */
  public FileInDataModel(String fileUrl,FileExchangeEnumTypes fileExchangeEnumType,FileResEnumTypes fileResEnumType){
    this(new FileParamModel(fileUrl), 
        null, 
        new FileExchangeModel(fileExchangeEnumType), 
        new FileResModel(fileResEnumType));
  }
  /**
   * 服务器资源操作构造方法
   * 读取服务器文件
   * @param fileUrl fileUrl 资源路径
   * @param fileConnBeanId 配置的服务器连接BeanId
   */
  public FileInDataModel(String fileUrl,String fileConnBeanId){
    this(new FileParamModel(fileUrl), 
        new FileConnModel(fileConnBeanId), 
        new FileExchangeModel(FileExchangeEnumTypes.Read_File), 
        null);
  }
  /**
   * 服务器资源操作构造方法
   * 
   * @param fileUrl 资源路径
   * @param fileConnBeanId 配置的服务器连接BeanId
   * @param fileExchangeEnumType 资源操作类型
   * IS_File_Exist 判断文件是否存在,true存在,false不存在 
   * IS_Dir_Exist 判断文件夹是否存在,true存在,false不存在  
   * Make_Dirs 创建一个或者多个连续文件夹,true成功,false失败 
   * Read_File 获取本地文件、下载服务器文件,返回byte[]数组 
   * Delete_File 删除文件,返回Boolean,表示操作是否成功,true成功,false失败 
   * Delete_Dir 删除文件夹,返回Boolean,表示操作是否成功,true成功,false失败,如果文件下有内容则删除失败 
   * Query_DirFileNames 获取文件列表名称,返回List<String>文件名列表 
   */
  public FileInDataModel(String fileUrl,String fileConnBeanId,FileExchangeEnumTypes fileExchangeEnumType){
    this(new FileParamModel(fileUrl), 
        new FileConnModel(fileConnBeanId), 
        new FileExchangeModel(fileExchangeEnumType), 
        null);
  }
  /**
   * 服务器资源操作构造方法
   * 
   * @param fileUrl 资源路径
   * @param fileConnBeanId 配置的服务器连接BeanId
   * @param fileExchangeEnumType 资源操作类型
   * IS_File_Exist 判断文件是否存在,true存在,false不存在 
   * IS_Dir_Exist 判断文件夹是否存在,true存在,false不存在  
   * Make_Dirs 创建一个或者多个连续文件夹,true成功,false失败 
   * Read_File 获取本地文件、下载服务器文件,返回byte[]数组 
   * Delete_File 删除文件,返回Boolean,表示操作是否成功,true成功,false失败 
   * Delete_Dir 删除文件夹,返回Boolean,表示操作是否成功,true成功,false失败,如果文件下有内容则删除失败 
   * Query_DirFileNames 获取文件列表名称,返回List<String>文件名列表 
   * @param fileResEnumType 结果数据类型
   * 
   */
  public FileInDataModel(String fileUrl,String fileConnBeanId,FileExchangeEnumTypes fileExchangeEnumType,FileResEnumTypes fileResEnumType){
    this(new FileParamModel(fileUrl), 
        new FileConnModel(fileConnBeanId), 
        new FileExchangeModel(fileExchangeEnumType), 
        new FileResModel(fileResEnumType));
  }
  /**
   * 本地资源操作构造方法
   * Write_File 新增文件,返回Boolean,表示操作是否成功,true成功,false失败
   * Replace_File 替换文件,返回Boolean,表示操作是否成功,true成功,false失败
   * @param fileContent 要提交的文件内容
   * @param fileUrl 目标文件路径
   * @param fileOperType 操作类型，Write_File(新增文件)或者Replace_File(替换文件)
   * @param replaceIfExist 新增文件时使用该参数,存在是否替换,false(默认)表示不替换,true表示替换
   * @param newIfNotExist 更新文件时使用该参数，不存在是否新建,false(默认)表示不新建,true表示要新建
   */
  public FileInDataModel(byte[] fileContent,String fileUrl,FileExchangeEnumTypes fileOperType,Boolean replaceIfExist,Boolean newIfNotExist){
    this(new FileParamModel(fileContent,fileUrl), 
        null, 
        new FileExchangeModel(fileOperType, replaceIfExist, newIfNotExist), 
        null);
  }
  /**
   * 本地资源操作构造方法
   * 新增文件，返回Boolean,表示操作是否成功,true成功,false失败
   * 如果文件已存在，则返回false
   * @param fileContent 要提交的文件内容
   * @param fileUrl 目标文件路径
   */
  public FileInDataModel(byte[] fileContent,String fileUrl){
    this(new FileParamModel(fileContent,fileUrl), 
        null, 
        new FileExchangeModel(FileExchangeEnumTypes.Write_File, null, null), 
        null);
  }
  /**
   * 服务器资源操作构造方法
   * 
   * @param fileContent 要提交的文件内容
   * @param fileUrl 目标文件路径
   * @param fileConnBeanId 配置的服务器连接BeanId
   * @param fileOperType 操作类型
   * Write_File 新增文件,返回Boolean,表示操作是否成功,true成功,false失败
   * Replace_File 替换文件,返回Boolean,表示操作是否成功,true成功,false失败
   * @param replaceIfExist 新增文件时使用该参数,存在是否替换,false(默认)表示不替换,true表示替换
   * @param newIfNotExist 更新文件时使用该参数，不存在是否新建,false(默认)表示不新建,true表示要新建
   */
  public FileInDataModel(byte[] fileContent,String fileUrl,String fileConnBeanId,FileExchangeEnumTypes fileOperType,Boolean replaceIfExist,Boolean newIfNotExist){
    this(new FileParamModel(fileContent,fileUrl), 
        new FileConnModel(fileConnBeanId), 
        new FileExchangeModel(fileOperType, replaceIfExist, newIfNotExist), 
        null);
  }
  /**
   * 服务器资源操作构造方法
   * 新增文件，返回Boolean,表示操作是否成功,true成功,false失败
   * 如果文件已存在，则返回false
   * @param fileContent 要提交的文件内容
   * @param fileUrl 目标文件路径
   */
  public FileInDataModel(byte[] fileContent,String fileUrl,String fileConnBeanId){
    this(new FileParamModel(fileContent,fileUrl), 
        new FileConnModel(fileConnBeanId), 
        new FileExchangeModel(FileExchangeEnumTypes.Write_File, null, null), 
        null);
  }
  
}

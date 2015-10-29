/**
 * 文件名		：FileParamsManager.java
 * 创建日期	：2013-9-27
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.manager;

import com.fop.framework.source.BaseManager;

/**
 * 文件访问时的传入参数管理
 * 
 * @version 1.00
 * @author syl
 * 
 */
public interface FileParamsManager extends BaseManager{

  /**获取文件路径
   * @param basePath 根目录
   * @param fileUrl 相对路径
   * @return 返回要操作的文件全路径
   */
  String getFileUrl(String basePath,String fileUrl);

  /**获取文件内容对象
   * @param fileContent 文件内容
   * @return 返回文件内容
   */
  byte[] getFileContent(byte[] fileContent);
  
}

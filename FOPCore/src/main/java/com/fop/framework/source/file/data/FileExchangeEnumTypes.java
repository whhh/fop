/**
 * 文件名		：FileExchangeEnumTypes.java
 * 创建日期	：2013-10-17
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.data;

/**
 * 描述:文件资源操作执行类型
 * 
 * @version 1.00
 * @author syl
 * 
 */
public enum FileExchangeEnumTypes {
  /**判断文件是否存在,true存在,false不存在*/
  IS_File_Exist,
  /**判断文件夹是否存在,true存在,false不存在*/
  IS_Dir_Exist,
  /**本地新建文件、上传文件,返回Boolean,表示操作是否成功,true成功,false失败*/
  Write_File,
  /**修改文件,返回Boolean,表示操作是否成功,true成功,false失败*/
  Replace_File,
  /**创建一个或者多个连续文件夹,true成功,false失败*/
  Make_Dirs,
  /**获取本地文件、下载服务器文件,返回byte[]数组*/
  Read_File,
  /**删除文件,返回Boolean,表示操作是否成功,true成功,false失败*/
  Delete_File,
  /**删除文件夹,,返回Boolean,表示操作是否成功,true成功,false失败,如果文件下有内容则删除失败*/
  Delete_Dir,
  /**获取文件列表名称,返回List<String>文件名列表*/
  Query_DirFileNames,
  
}

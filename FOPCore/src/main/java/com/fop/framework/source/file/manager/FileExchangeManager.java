/**
 * 文件名		：FileExchangeManager.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.manager;

import java.util.List;

import com.fop.framework.source.BaseManager;
import com.jcraft.jsch.ChannelSftp;

/**
 * 与文件资源系统交换的处理
 * 
 * @version 1.00
 * @author syl
 * 
 */
public interface FileExchangeManager extends BaseManager{
  /**
   * 判断文件是否存在
   * @param serverPath 文件路径
   * @param sftp 与服务器的连接
   * @param closeChannel 执行请求完毕后是否关闭连接，true表示关闭，false表示不关闭
   * @return 返回true表示存在，false表示不存在
   */
  public boolean isSftpFileExist(String serverPath,ChannelSftp sftp,boolean closeChannel);
  /**
   * 判断文件是否存在
   * @param serverPath 文件路径
   * @param sftp 与服务器的连接
   * @return 返回true表示存在，false表示不存在
   */
  public boolean isSftpFileExist(String serverPath,ChannelSftp sftp);
  /**
   * 判断本地文件是否存在
   * @param serverPath 要操作的文件路径
   * @param localBasePath 本地文件操作根目录
   * @return  返回true表示存在，false表示不存在
   */
  public boolean isLocalFileExist(String serverPath,String localBasePath);
  /**
   * 判断文件夹是否存在
   * @param serverPath 文件路径
   * @param sftp 与服务器的连接
   * @param closeChannel 执行请求完毕后是否关闭连接，true表示关闭，false表示不关闭
   * @return 返回true表示存在，false表示不存在
   */
  public boolean isSftpDirExist(String serverPath,ChannelSftp sftp,boolean closeChannel);
  /**
   * 判断文件夹是否存在
   * @param serverPath 文件路径
   * @param sftp 与服务器的连接
   * @return 返回true表示存在，false表示不存在
   */
  public boolean isSftpDirExist(String serverPath,ChannelSftp sftp);
  /**
   * 判断本地文件夹是否存在
   * @param serverPath 要操作的文件路径
   * @param localBasePath 本地文件操作根目录
   * @return 返回true表示存在，false表示不存在
   */
  public boolean isLocalDirExist(String serverPath,String localBasePath);
  /**
   * 上传文件
   * @param serverPath 服务器端保存的文件路径
   * @param uploadFile 要上传文件的本地绝对路径
   * @param sftp 与服务端的连接,执行完毕后主动关闭连接
   * @return 返回true表示操作成功，false表示操作失败
   */
  public boolean uploadSftpFile(String serverPath, byte[] uploadFile, ChannelSftp sftp);
  /**
   * 上传文件
   * @param serverPath 本地服务器要保存的文件路径
   * @param uploadFile 要上传的文件内容
   * @param localBasePath 本地文件操作根目录
   * @return 返回true表示操作成功，false表示操作失败
   */
  public boolean writeToLocalFile(String serverPath, byte[] uploadFile, String localBasePath);
  
  /**
   * 新建文件夹
   * @param serverPath 服务器端对应目录
   * @param sftp 与服务端的连接,执行完毕后主动关闭连接
   * @return 返回true表示操作成功，false表示操作失败
   */
  public boolean makeSftpDirs(String serverPath, ChannelSftp sftp) ;
  /**
   * 新建文件夹
   * @param serverPath 本地服务器要新建的文件夹路径
   * @param localBasePath 本地文件操作根目录
   * @return 返回true表示操作成功，false表示操作失败
   */
  public boolean makeLocalDirs(String serverPath, String localBasePath) ;

  /**
   * 下载文件
   * @param serverPath 服务端文件路径
   * @param localSavePath 本地要保存的目标文件绝对路径
   * @param sftp 与服务端的连接,执行完毕后主动关闭连接
   * @return 返回文件内容的byte数组
   */
  public byte[] downloadSftpFile(String serverPath,ChannelSftp sftp) ;
  /**
   * 获取本地文件
   * @param serverPath 本地服务器要获取的文件路径
   * @param localBasePath 本地文件操作根目录
   * @return 返回文件内容的byte数组
   */
  public byte[] getLocalFile(String serverPath,String localBasePath) ;
  /**
   * 删除文件
   * @param serverPath 服务端文件路径
   * @param sftp 与服务端的连接,执行完毕后主动关闭连接
   * @return 返回true表示操作成功，false表示操作失败
   */
  public boolean deleteSftpFile(String serverPath, ChannelSftp sftp);
  /**
   * 删除文件
   * @param serverPath 本地服务器要删除的文件路径
   * @param localBasePath 本地文件操作根目录
   * @return 返回true表示操作成功，false表示操作失败
   */
  public boolean deleteLocalFile(String serverPath, String localBasePath);
  /**
   * 删除文件夹
   * @param serverPath 服务端要删除的文件夹路径
   * @param sftp 与服务端的连接,执行完毕后主动关闭连接
   * @return 返回true表示操作成功，false表示操作失败
   */
  public boolean deleteSftpDir(String serverPath, ChannelSftp sftp);
  /**
   * 删除文件夹
   * @param serverPath 本地服务器要删除的文件夹路径
   * @param localBasePath 本地文件操作根目录
   * @return 返回true表示操作成功，false表示操作失败
   */
  public boolean deleteLocalDir(String serverPath, String localBasePath);
  /**
   * 获取服务端指定目录下的文件名称类别
   * @param serverPath 服务器要获取的目录路径
   * @param sftp 与服务端的连接,执行完毕后主动关闭连接
   * @return 返回文件名称列表，包含目录名称
   */
  public List<String> getSftpListFileNames(String serverPath, ChannelSftp sftp);
  /**
   * 获取本机指定目录下的文件名称类别
   * @param serverPath 本地服务器要获取的目录路径
   * @param localBasePath 本地文件操作根目录
   * @return 返回文件名称列表，包含目录名称
   */
  public List<String> getLocalListFileNames(String serverPath, String localBasePath);
  
}

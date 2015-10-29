/**
 * 文件名		：LocalFileUtil.java
 * 创建日期	：2013-10-21
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.util;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 描述:本地文件操作工具类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class LocalFileUtil {
  public static void main(String[] args) {
    //删除文件
//    LocalFileUtil.deleteLocalDir("D:/", "test/aa");
    //写入文件
//    byte[] aa=FileUtil.fileToByte("D:/test/download2.txt");
//    LocalFileUtil.writeToLocalFile("test/a4/b5/k8/aae.txt", aa, "D:/");
    //读取文件
//    byte[] data=LocalFileUtil.getLocalFile("/download2.txt", "D:/test/");
//    FileUtil.byteToFile(data, "D:/test/aa.txt");
    //新建目录
//    LocalFileUtil.makeLocalDir("test/aa/bb/dd", "D:/");
    
//    List<String> res=getLocalListFileNames("D:/", "Program Data/SFtp");
//    System.out.println(res);
    
  }
  
  /**
   * 判断文件是否存在
   * @param serverPath 要操作的文件路径
   * @param localBasePath 本地文件服务器根路径
   * @return 返回true表示存在，返回false表示不存在
   */
  public static boolean isLocalFileExist(String serverPath, String localBasePath) {
    String absolutePath=getAbsolutePath(serverPath, localBasePath);
    return isLocalFileExist(absolutePath);
  }
  /**
   * 判断本地文件是否存在
   * @param absolutePath 绝对路径
   * @return 返回true表示存在，返回false表示不存在
   */
  private static boolean isLocalFileExist(String absolutePath) {
    File file=new File(absolutePath);
    boolean resBool=false;
    if(file.isFile()){
      resBool=file.exists();
    }
    return resBool;
  }
  /**
   * 判断文件夹是否存在
   * @param serverPath 要操作的文件路径
   * @param localBasePath 本地文件服务器根路径
   * @return 返回true表示存在，返回false表示不存在
   */
  public static boolean isLocalDirExist(String serverPath, String localBasePath) {
    String absolutePath=getAbsolutePath(serverPath, localBasePath);
    return isLocalDirExist(absolutePath);
  }
  /**
   * 判断本地文件夹是否存在
   * @param absolutePath 本地文件夹绝对路径
   * @return 返回true表示存在，返回false表示不存在
   */
  private static boolean isLocalDirExist(String absolutePath) {
    File file=new File(absolutePath);
    boolean resBool=false;
    if(file.isDirectory()){
      resBool=file.exists();
    }
    return resBool;
  }
  /**
   * 将文件写入到本地，如果文件对应的文件夹(支持多个)不存在则自动创建
   * @param serverPath 要操作的文件路径
   * @param uploadFile 源文件
   * @param localBasePath 本地文件服务器根路径
   * @return 返回true表示操作成功，返回false表示操作失败
   */
  public static boolean writeToLocalFile(String serverPath, byte[] uploadFile,
      String localBasePath) {
    String absolutePath=getAbsolutePath(serverPath, localBasePath);
    //判断文件夹是否存在，如果不存在则自动创建文件夹
    makeLocalDirs(FileUtil.getDirByFilePath(absolutePath));
    return FileUtil.byteToFile(uploadFile, absolutePath);
  }

  /**
   * 创建本地单个或者多个文件夹
   * 如果对应的文件夹路径已经存在，则不创建返回true
   * @param serverPath 要操作的文件路径
   * @param localBasePath 本地文件服务器根路径
   * @return 返回true表示操作成功，返回false表示操作失败
   */
  public static boolean makeLocalDirs(String serverPath, String localBasePath) {
    String absolutePath=getAbsolutePath(serverPath, localBasePath);
    return makeLocalDirs(absolutePath);
  }
  /**
   * 创建本地单个或者多个文件夹
   * 如果对应的文件夹路径已经存在，则不创建返回true
   * @param absolutePath
   * @return 返回true表示操作成功，返回false表示操作失败
   */
  public static boolean makeLocalDirs(String absolutePath) {
    File file=new File(absolutePath);
    boolean resBool=false;
    if(!file.exists()){
      resBool=file.mkdirs();
    }
    return resBool;
  }
  /**
   * 获取本地文件
   * @param serverPath 要操作的文件路径
   * @param localSaveFile
   * @param localBasePath 本地文件服务器根路径
   * @return 返回文件内容的byte数组
   */
  public static byte[] getLocalFile(String serverPath,String localBasePath) {
    String absolutePath=getAbsolutePath(serverPath, localBasePath);
    return FileUtil.fileToByte(absolutePath);
  }

  /**
   * 删除本地文件
   * @param serverPath 要操作的文件路径
   * @param localBasePath 本地文件服务器根路径
   * @return 返回true表示操作成功，返回false表示操作失败
   */
  public static boolean deleteLocalFile(String serverPath, String localBasePath) {
    String absolutePath=getAbsolutePath(serverPath, localBasePath);
    File file=new File(absolutePath);
    boolean resBool=false;
    if(file.isFile()){
      resBool=file.delete();
    }
    return resBool;
  }

  /**
   * 删除本地目录，如果文件下有文件信息则删除失败
   * @param serverPath 要操作的文件路径
   * @param localBasePath 本地文件服务器根路径
   * @return 返回true表示操作成功，返回false表示操作失败
   */
  public static boolean deleteLocalDir(String serverPath, String localBasePath) {
    String absolutePath=getAbsolutePath(serverPath, localBasePath);
    File file=new File(absolutePath);
    boolean resBool=false;
    if(file.isDirectory()){
      resBool=file.delete();
    }
    return resBool;
  }

  /**
   * 获取本地文件夹下的文件名列表
   * @param serverPath 要操作的文件路径
   * @param localBasePath 本地文件服务器根路径
   * @return 返回文件名列表
   */
  public static List<String> getLocalListFileNames(String serverPath,
      String localBasePath) {
    String absolutePath=getAbsolutePath(serverPath, localBasePath);
    File file=new File(absolutePath);
    List<String> resList=null;
    if(file.isDirectory()){
      File[] fileList=file.listFiles();
      resList=new LinkedList<String>();
      for(int i=0;i<fileList.length;i++){
        resList.add(fileList[i].getName());
      }
    }
    return resList;
  }
  /**
   * 获取绝对路径
   * @param serverPath 要操作的文件路径
   * @param localBasePath 本地文件服务器根路径
   * @return 返回绝对路径字符串
   */
  private static String getAbsolutePath(String serverPath, String localBasePath){
    StringBuffer sbf=new StringBuffer();
    sbf.append(localBasePath);//在前
    sbf.append(FileUtil.fileSplit);
    sbf.append(serverPath);//在后
    return sbf.toString();
  }
  
}

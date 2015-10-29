/**
 * 文件名		：FileParseUtil.java
 * 创建日期	：2013-9-27
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.source.file.exception.FileRuntimeException;
import com.fop.framework.util.StringUtil;

/**
 * 业务无关的文件解析静态工具
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class FileUtil {
  /**文件路径分隔符*/
  public static final String fileSplit="/";
//  public static void main(String[] args) {
//    String s="D:/fe///a.t/xt";
////    String res=getDirByFilePath(s);
////    File fi=new File(res);
////    fi.mkdirs();
////    System.out.println(res);
//    String[] strArr=getDirArr(getDirByFilePath(s));
//    for(int i=0;i<strArr.length;i++){
//      System.out.println(strArr[i]);
//    }
//  }
  private static Log logger=LogFactory.getLog(FileUtil.class);
//  /**
//   * 将原始文件内容通过字节流的方式复制到目标文件中
//   * @param srcFile 原始文件
//   * @param goalFile 目标文件
//   * @throws IOException 
//   */
//  public static boolean copyFileContentByte(File srcFile, File goalFile) throws IOException {
//    boolean resBool=false;
//    FileInputStream is = null;
//    FileOutputStream os = null;
//    try {
//      is = new FileInputStream(srcFile);// 创建文件输入流对象
//      os = new FileOutputStream(goalFile);// 创建文件输出流对象
//      int count = 512;// 设定读取的字节数
//      byte buffer[] = new byte[count];
//      while ((is.read(buffer, 0, count) != -1) && (count > 0)) {// 读取输入流
//        os.write(buffer, 0, count);// 写入输出流
//      }
//      os.flush();
//      resBool=true;
//    }finally {
//      try {is.close();} catch (IOException e) { }
//      try {os.close();} catch (IOException e) { }
//    }//finally
//    return resBool;
//  }

  /**
   * 将byte数组信息写入到目标文件中去,如果目标文件路径不存在，则提示错误
   * @param data byte数组信息
   * @param goalPath 目标文件目录
   * @return 返回true表示操作成功，返回false表示操作失败
   */
  public static boolean byteToFile(byte[] data,String goalPath){
    boolean resBool=false;
    logger.info("----framework: byteToFile(): goalPath = " + goalPath);
    File file = new File(goalPath);
    OutputStream output=null;
    BufferedOutputStream bufferedOutput =null;
    try {
      logger.info("----framework: b1");
      output = new FileOutputStream(file);
      bufferedOutput = new BufferedOutputStream(output);
      logger.info("----framework: b2");
      bufferedOutput.write(data);
      resBool=true;
    } catch (Exception e) {
      throw new FileRuntimeException(e); 
    }finally{
      try {
        if(bufferedOutput!=null){bufferedOutput.close();}
      } catch (IOException e) {
        logger.error("----framework: byteToFile error1:" + e.getMessage(), e);
      }
      try {
        if(output!=null){output.close();}
      } catch (IOException e) {
        logger.error("----framework: byteToFile error2:" + e.getMessage(), e);
      }
    }
    return resBool;
  }
  /**
   * 将文件信息转为byte数组输出
   * @param filePath 要转换的本地文件全路径
   * @return 返回文件内容的byte数组
   */
  public static byte[] fileToByte(String filePath){
    byte[] resData = null;
    File file = new File(filePath);
    InputStream input=null;
    try {
      input = new FileInputStream(file);
      resData = new byte[input.available()];
      input.read(resData);
    } catch (Exception e) {
      logger.error("----framework: fileToByte error3:" + e.getMessage(), e);
    }finally{
      try {
        if(input!=null){input.close();}
      } catch (IOException e) {
        logger.error("----framework: fileToByte error4:" + e.getMessage(), e);
      }
    }
    return resData;
  }
  /**
   * 通过文件路径得到该文件所在目录
   * 如果文件文件路径不正确则返回null
   * @param filePath 文件路径
   * @return 返回文件所在目录
   */
  public static String getDirByFilePath(String filePath){
    if(StringUtil.isStrEmpty(filePath)){return null;}
    int lastSplit=filePath.lastIndexOf(fileSplit);
    if(lastSplit==-1){
      return null;
    }else{
      return filePath.substring(0, lastSplit);
    }
  }
  /**
   * 根据文件夹路径得到文件夹名称顺序数组
   * 例如传入：D:/dir1/dir2/,则返回{"D:","dir1","dir2"}
   * @param dirPath 传入的文件夹路径
   * @return 返回文件夹顺序数组
   */
  public static String[] getDirArr(String dirPath){
    if(dirPath==null){return null;}
    LinkedList<String> dirListName=new LinkedList<String>();
    String[] dirArr=dirPath.split(fileSplit);
    for(int i=0;i<dirArr.length;i++){
      String currStr=dirArr[i];
      if(StringUtil.isStrEmpty(currStr)||fileSplit.equals(currStr)){
        continue;
      }else{
        dirListName.add(currStr);
      }
    }
    String[] resultArr=new String[dirListName.size()];
    return dirListName.toArray(resultArr);
  }
  
}


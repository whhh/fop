/**
 * 文件名		：SSHClientUtil.java
 * 创建日期	：2013-10-21
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.source.file.data.SSHParamModel;
import com.fop.framework.source.file.exception.FileRuntimeException;
import com.fop.framework.util.StringUtil;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * 描述:对SFTP服务端文件操作的工具类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class SSHClientUtil {
  //SftpException错误码
  //  public static final int SSH_FX_OK=                            0;
  //  public static final int SSH_FX_EOF=                           1;
  //  public static final int SSH_FX_NO_SUCH_FILE=                  2;
  //  public static final int SSH_FX_PERMISSION_DENIED=             3;
  //  public static final int SSH_FX_FAILURE=                       4;
  //  public static final int SSH_FX_BAD_MESSAGE=                   5;
  //  public static final int SSH_FX_NO_CONNECTION=                 6;
  //  public static final int SSH_FX_CONNECTION_LOST=               7;
  //  public static final int SSH_FX_OP_UNSUPPORTED=                8;

  /**定义日志对象*/
  private static Log logger=LogFactory.getLog(SSHClientUtil.class);
  /**
   * 通过SftpParamModel获取连接
   * @param paramModel 文件服务器连接的参数信息
   * @return 返回文件服务器的session连接
   */
  public static Session getSshSession(SSHParamModel paramModel){
    if(paramModel==null){return null;}
    if(paramModel.getKeyFile()==null){//如果没有设置密钥文件
      return getSshSession(paramModel.getHost(), paramModel.getPort(), paramModel.getUserName(), paramModel.getPassword());
    }else{
      return getSshSession(paramModel.getHost(), paramModel.getPort(), paramModel.getUserName(), paramModel.getPassword(), 
          paramModel.getKeyFile(), paramModel.getPassphrase());
    }
  }
  /**
   * 通过用户名、密码的方式获取连接
   * @param host 主机号
   * @param port 端口号
   * @param username 用户名
   * @param password 密码
   * @return 返回文件服务器的session连接
   */
  private static Session getSshSession(String host, int port, String username,
      String password) {
    JSch jsch = new JSch();
    Session sshSession = null;
    try {
      sshSession = jsch.getSession(username, host, port);
    } catch (JSchException e) {
      logger.error("----framework: getSshSession(): error1:" + e.getMessage(), e);
      throw new FileRuntimeException(e);
    }
    Properties sshConfig = new Properties();
    sshConfig.put("StrictHostKeyChecking", "no");
    sshSession.setConfig(sshConfig);
    sshSession.setPassword(password);
    return sshSession;
  }
  /**
   * 通过用户名、密钥文件（密码可选）方式获取连接
   * @param keyFile 密钥文件路径
   * @param host 主机
   * @param port 端口
   * @param username 用户名
   * @param password 密码（可选）
   * @param passphrase 密钥文件解密密码（可为空，表示密钥文件不加密）
   * @return 返回文件服务器的session连接
   */
  private static Session getSshSession(String host, int port,
      String username, String password,String keyFile,String passphrase) {
    JSch jsch = new JSch();
    Session sshSession = null;
    try {
      if(StringUtil.isStrEmpty(passphrase)){
        jsch.addIdentity(keyFile);
      }else{//如果密钥文件设置了加密
        jsch.addIdentity(keyFile, passphrase);
      }
      sshSession = jsch.getSession(username, host, port);
    } catch (JSchException e) {
      logger.error("----framework: getSshSession(): error2:" + e.getMessage(), e);
      throw new FileRuntimeException(e);
    }
    sshSession.setPassword(password);
    return sshSession;
  }

  //  /**
  //   * 通过用户名、密钥文件（密码可选）方式获取连接
  //   * @param keyFile
  //   * @param host
  //   * @param port
  //   * @param username
  //   * @param userInfo
  //   * @return
  //   */
  //  private static Session getSshSession(String host, int port,
  //      String username, UserInfo userInfo,String keyFile) {
  //    JSch jsch = new JSch();
  //    Session sshSession = null;
  //    try {
  //      jsch.addIdentity(keyFile);
  //      sshSession = jsch.getSession(username, host, port);
  //    } catch (JSchException e) {
  //      throw new FileRuntimeException(e);
  //    }
  //   // password and passphrase will be given
  //   // via UserInfo interface.
  //    sshSession.setUserInfo(userInfo);
  //    return sshSession;
  //  }
  /**
   * 通过服务器Session得到可执行的连接
   * @param sshSession 服务器Session
   * @param channelType 服务器类型
   * @return 返回文件服务器可执行的channel通道
   */
  public static ChannelSftp openChannel(Session sshSession, String channelType) {
    ChannelSftp sftp = null;
    try {
      /*if (!sshSession.isConnected()) {
        sshSession.connect();
      }*/
      Channel channel = sshSession.openChannel(channelType);
      channel.connect();
      sftp = (ChannelSftp) channel;
      logger.info("----framework: openChannel(): get sftp ok");
    } catch (Exception e) {
      logger.error("----framework: openChannel(): error:" + e.getMessage(), e);
      try {
        sshSession.disconnect();
        logger.info("----framework: sshSession.disconnect(); ok");
      } catch (Exception e2) {
        logger.error("----framework: openChannel(): error2:" + e2.getMessage(), e2);
      }
      throw new FileRuntimeException(e);
    }
    return sftp;
  }

  /**
   * 上传文件
   * @param serverPath 服务器端保存的文件路径
   * @param uploadFile 要上传的文件对象
   * @param sftp 与服务端的连接
   * @return 返回true表示操作成功，返回false表示操作失败
   */
  public static boolean upload(String serverPath, byte[] uploadFile, ChannelSftp sftp) {
    return upload(serverPath, uploadFile, sftp, true);
  }
  /**
   * 上传文件
   * 如果文件对应的文件夹(支持多个)不存在则自动创建
   * @param serverPath 服务器端保存的文件路径
   * @param uploadFile 要上传的文件对象
   * @param sftp 与服务端的连接
   * @param closeChannel true表示关闭，false表示不关闭
   * @return 返回true表示操作成功，返回false表示操作失败
   */
  public static boolean upload(String serverPath, byte[] uploadFile, ChannelSftp sftp,boolean closeChannel) {
    logger.info("----framework: upload(): serverPath=" + serverPath);
    serverPath = addSlash(serverPath);
    boolean resBool=false;
    InputStream uploadIs=new ByteArrayInputStream(uploadFile);
    //如果文件对应的文件夹(支持多个)不存在则自动创建
    makeDirs(FileUtil.getDirByFilePath(serverPath), sftp, false);
    try {
      sftp.put(uploadIs, serverPath);
      resBool=true;
    } catch (Exception e) {
      logger.error("----framework: upload(): error:" + e.getMessage(), e);
      throw new FileRuntimeException(e);
    }finally{
      closeInputStream(uploadIs);//写在前
      if(closeChannel){closeChannelSftp(sftp);}
    }
    return resBool;
  }
  /**
   * 新建单个文件夹
   * @param serverPath 服务器端对应目录
   * @param sftp 与服务端的连接
   * @return 返回true表示操作成功，返回false表示操作失败
   */
  public static boolean makeDir(String serverPath, ChannelSftp sftp) {
    return makeDir(serverPath, sftp, true);
  }
  /**
   * 新建单个文件夹
   * @param serverPath 服务器端对应目录
   * @param sftp 与服务端的连接
   * @param closeChannel true表示关闭，false表示不关闭
   * @return 返回true表示操作成功，返回false表示操作失败
   */
  private static boolean makeDir(String serverPath, ChannelSftp sftp,boolean closeChannel) {
    logger.info("----framework: makeDir(): serverPath=" + serverPath);
    serverPath = addSlash(serverPath);
    boolean resBool=false;
    try {
      sftp.mkdir(serverPath);
      logger.info("----framework: makeDir(): mkdir executed");
      resBool=true;
    } catch (Exception e) {
      logger.error("----framework: makeDir(): error:" + e.getMessage(), e);
      throw new FileRuntimeException(e); 
    }finally{
      if(closeChannel){closeChannelSftp(sftp);}
    }
    return resBool;
  }
  /**
   * 创建单个或多个文件夹
   * 如果对应的文件夹路径已经存在，则不创建返回true
   * @param serverPath 服务器端对应目录
   * @param sftp 与服务端的连接
   * @param closeChannel true表示关闭，false表示不关闭
   * @return 返回true表示操作成功，返回false表示操作失败
   */
  public static boolean makeDirs(String serverPath, ChannelSftp sftp,boolean closeChannel) {
    logger.info("----framework: makeDirs(): serverPath=" + serverPath);
    serverPath = addSlash(serverPath);
    boolean resBool=false;
    try {
      if(!isSftpDirExist(serverPath, sftp, false)){
        String[] dirNames=FileUtil.getDirArr(serverPath);
        StringBuffer dirSbf=new StringBuffer();
        logger.info("----framework: makeDirs(): dirNames.length=" + dirNames.length);
        for(int i=0;i<dirNames.length;i++){//反向遍历,待优化
          dirSbf.append(FileUtil.fileSplit).append(dirNames[i]);
          if(!isSftpDirExist(dirSbf.toString(), sftp, false)){
            logger.info("----framework: makeDirs(): dirSbf=" + dirSbf.toString());
            makeDir(dirSbf.toString(), sftp, false);
          }
        }
      }
      resBool=true;
    }catch (Exception e) {
      logger.error("----framework: makeDirs(): error:" + e.getMessage(), e);
      throw new FileRuntimeException(e);
    }finally{
      if(closeChannel){closeChannelSftp(sftp);}
    }
    return resBool;
  }
  /**
   * 创建单个或者多个文件夹
   * @param serverPath 服务器端对应目录
   * @param sftp 与服务端的连接
   * @return 返回true表示操作成功，返回false表示操作失败
   */
  public static boolean makeDirs(String serverPath, ChannelSftp sftp) {
    return makeDirs(serverPath, sftp, true);
  }
  /**
   * 将服务端的serverPath文件信息下载到byte数组中
   * @param serverPath 服务端文件路径
   * @param saveFile 要保持到的文件对象
   * @param sftp 与服务端的连接
   * @return 返回文件内容的byte数组
   */
  public static byte[] download(String serverPath,ChannelSftp sftp) {
    return download(serverPath,sftp, true);
  }
  /**
   * 将服务端的serverPath文件信息下载到byte数组中
   * @param serverPath 服务端文件路径
   * @param saveFile 要保持到的文件对象
   * @param sftp 与服务端的连接
   * @param closeChannel true表示关闭，false表示不关闭
   * @return 返回文件内容的byte数组
   */
  public static byte[] download(String serverPath,ChannelSftp sftp,boolean closeChannel) {
    logger.info("----framework: download(): serverPath=" + serverPath);
    serverPath = addSlash(serverPath);
    logger.info("----framework: download(): serverPath2=" + serverPath);
    InputStream downloadIs=null;
    byte[] saveFile=null;
    try {
      downloadIs=sftp.get(serverPath);
      logger.info("----framework: download(): sftp.get(serverPath) executed");
      saveFile=IOUtils.toByteArray(downloadIs);//commons-io
      logger.info("----framework: download(): toByteArray executed");
      //      saveFile=new byte[downloadIs.available()];//此方式有问题
      //      downloadIs.read(saveFile);
    } catch (Exception e) {
      logger.error("----framework: download(): error:" + e.getMessage(), e);
      throw new FileRuntimeException(e);
    }finally{
      closeInputStream(downloadIs);//写在前
      logger.info("----framework: download(): closeInputStream executed. closeChannel=" + String.valueOf(closeChannel));
      if(closeChannel){
        closeChannelSftp(sftp);
        logger.info("----framework: download(): closeChannelSftp executed.");
      }
    }
    return saveFile;
  }

  /**
   * 删除文件
   * @param serverPath 服务器端对应目录
   * @param sftp 与服务端的连接
   * @return 返回true表示操作成功，返回false表示操作失败
   */
  public static boolean delete(String serverPath, ChannelSftp sftp) {
    return delete(serverPath, sftp, true);
  }
  /**
   * 删除文件
   * @param serverPath 服务器端对应目录
   * @param sftp 与服务端的连接
   * @param closeChannel true表示关闭，false表示不关闭
   * @return 返回true表示操作成功，返回false表示操作失败
   */
  public static boolean delete(String serverPath, ChannelSftp sftp,boolean closeChannel) {
    logger.info("----framework: delete() serverPath=" + serverPath);
    serverPath = addSlash(serverPath);
    boolean resBool=false;
    try {
      sftp.rm(serverPath);
      resBool=true;
    } catch (Exception e) {
      logger.error("----framework: delete(): error:" + e.getMessage(), e);
      throw new FileRuntimeException(e);
    }finally{
      if(closeChannel){closeChannelSftp(sftp);}
    }
    return resBool;
  }

  /**
   * 删除文件夹，如果文件下有文件信息则删除失败
   * @param serverPath 服务端要删除的文件夹路径
   * @param sftp 与服务端的连接
   * @return 返回true表示操作成功，返回false表示操作失败
   */
  public static boolean deleteDir(String serverPath, ChannelSftp sftp) {
    return deleteDir(serverPath, sftp, true);
  }
  /**
   * 删除文件夹，如果文件下有文件信息则删除失败
   * @param serverPath 服务端要删除的文件夹路径
   * @param sftp 与服务端的连接
   * @param closeChannel true表示关闭，false表示不关闭
   * @return 返回true表示操作成功，返回false表示操作失败
   */
  public static boolean deleteDir(String serverPath, ChannelSftp sftp,boolean closeChannel) {
    logger.info("----framework: deleteDir serverPath=" + serverPath);
    serverPath = addSlash(serverPath);
    boolean resBool=false;
    try {
      sftp.rmdir(serverPath);
      resBool=true;
    } catch (Exception e) {
      logger.error("----framework: deleteDir(): error:" + e.getMessage(), e);
      throw new FileRuntimeException(e);
    }finally{
      if(closeChannel){closeChannelSftp(sftp);}
    }
    return resBool;
  }

  /**
   * 获取服务端指定目录下的文件,包含文件和文件夹
   * @param serverPath 服务器端对应目录
   * @param sftp 与服务端的连接
   * @return 返回文件夹下的文件信息列表
   */
  public static Vector<LsEntry> listFiles(String serverPath, ChannelSftp sftp) {
    return listFiles(serverPath, sftp, true);
  }
  /**
   * 获取服务端指定目录下的文件,包含文件和文件夹
   * @param serverPath 服务器端对应目录
   * @param sftp 与服务端的连接
   * @param closeChannel true表示关闭，false表示不关闭
   * @return 返回文件夹下的文件信息列表
   */
  @SuppressWarnings("unchecked")
  public static Vector<LsEntry> listFiles(String serverPath, ChannelSftp sftp,boolean closeChannel) {
    logger.info("----framework: listFiles serverPath=" + serverPath);
    serverPath = addSlash(serverPath);
    Vector<LsEntry> vector = null;
    try {
      vector = sftp.ls(serverPath);
    } catch (Exception e) {
      logger.error("----framework: listFiles(): error:" + e.getMessage(), e);
      throw new FileRuntimeException(e);
    }finally{
      if(closeChannel){closeChannelSftp(sftp);}
    }
    return vector;
  }
  /**
   * 判断sftp服务端文件是否存在
   * @param serverPath 服务器端对应目录
   * @param sftp 与服务端的连接
   * @return true表示存在，false表示不存在
   */
  public static boolean isSftpFileExist(String serverPath,ChannelSftp sftp){
    return isSftpFileExist(serverPath, sftp, true);
  }
  /**
   * 判断sftp服务端文件是否存在
   * @param serverPath 服务器端对应目录
   * @param sftp 与服务端的连接
   * @param closeChannel true表示关闭，false表示不关闭
   * @return true表示存在，false表示不存在
   */
  public static boolean isSftpFileExist(String serverPath,ChannelSftp sftp,boolean closeChannel){
    logger.info("----framework: isSftpFileExist serverPath=" + serverPath);
    serverPath = addSlash(serverPath);
    boolean resBool=true;
    try {
      sftp.get(serverPath);
    } catch (SftpException e) {
      if(ChannelSftp.SSH_FX_NO_SUCH_FILE==e.id){
        resBool=false;//表示不存在
      }else{
        logger.error("----framework: isSftpFileExist(): error:" + e.getMessage(), e);
        throw new FileRuntimeException(e);
      }
    }finally{
      if(closeChannel){closeChannelSftp(sftp);}
    }
    return resBool;
  }
  /**
   * 判断sftp服务端文件夹是否存在
   * @param serverPath 服务器端对应目录
   * @param sftp 与服务端的连接
   * @return true表示存在，false表示不存在
   */
  public static boolean isSftpDirExist(String serverPath,ChannelSftp sftp){
    return isSftpDirExist(serverPath, sftp, true);
  }
  /**
   * 判断sftp服务端文件夹是否存在
   * @param serverPath
   * @param sftp
   * @param closeChannel true表示关闭，false表示不关闭
   * @return true表示存在，false表示不存在
   */
  public static boolean isSftpDirExist(String serverPath,ChannelSftp sftp,boolean closeChannel){
    serverPath = addSlash(serverPath);
    boolean resBool=true;
    try {
      sftp.cd(serverPath);
    } catch (SftpException e) {
      if(ChannelSftp.SSH_FX_NO_SUCH_FILE==e.id){
        resBool=false;//表示不存在
      }else{
        logger.error("----framework: isSftpDirExist(): error:" + e.getMessage(), e);
        throw new FileRuntimeException(e);
      }
    }finally{
      if(closeChannel){closeChannelSftp(sftp);}
    }
    return resBool;
  }
  /**
   * 统一从跟目录寻找路径，将路径前面统一添加"/"
   * @param s 待处理的路径
   * @return 返回处理后的路径
   */
  private static String addSlash(String s) {
    if(!s.startsWith(FileUtil.fileSplit)){
      s=FileUtil.fileSplit + s;
    }
    return s;
  }
  /**
   * 关闭连接
   * @param sftp 与服务器的连接
   */
  public static void closeChannelSftp(ChannelSftp sftp){
    if(sftp!=null){
      logger.info("----framework: closeChannelSftp.");
      sftp.disconnect();
    }
  }
  
  public static void closeSession(Session sshSession, String channelType) {
    if(sshSession != null) {
      logger.info("----framework: closeSession.");
      sshSession.disconnect();
    }
  }
  /**
   * 关闭InputStream
   * @param is 输入流
   */
  public static void closeInputStream(InputStream is){
    if(is!=null){
      try {
        logger.info("----framework: closeInputStream.");
        is.close();
      } catch (IOException e) {
        logger.error("----framework: closeInputStream error1:" + e.getMessage(), e);
      }
    }
  }


  //  public static void main(String[] args) throws Exception {
  //    String directory = "/nyBankDir/serverDir/";// 根目录的话就使用'/'
  ////    String keyFile = "D:/Program Files/freeSSHd/privatekey.rsa";
  //    String keyFile = "D:/test/pubkeys/test.ppk";
  ////    String keyFile = "C:/Users/SZJSB/AppData/Roaming/Globalscape/CuteFTP/9.0/Security/myTestkey.txt";
  //    String host = "127.0.0.1";
  //    int port = 22;
  //    String username = "test";
  //    String password = "test";
  //    String passphrase = "";
  //    
  //    Session sshSession=SSHClientUtil.getSshSession(host, port, username, password);
  //    sshSession.connect(0);
  //    ChannelSftp sftp = null;
  //    sftp=SSHClientUtil.openChannel(sshSession, "sftp");
  //    //下载文件
  ////    byte[] arr=SSHClientUtil.download("upload.txt", sftp);
  ////    FileUtil.byteToFile(arr, "D:/test999.txt");
  //    //上传文件
  //    boolean res =SSHClientUtil.upload("fe/te/te2/upload.txt", FileUtil.fileToByte("D:/test999.txt"), sftp);
  //    System.out.println(res);
  //    //删除文件
  ////    SSHClientUtil.deleteDir("rrr", sftp);
  //    //判断文件是否存在
  ////    boolean exist=SSHClientUtil.isSftpDirExist("/新建文件夹", sftp);
  ////    System.out.println(exist);
  //    
  //    //创建目录
  ////    SSHClientUtil.makeDirs("a2/b/e/r", sftp);
  //    sftp.disconnect();
  //    sshSession.disconnect();
  //    
  //    
  //    /**
  //     * 区分方式一与方式二,传入的路径之前是否包含/，会影响路径的选择，有/会从根目录开始查找，不包含的话会从当前cd的目录开始查找
  //     * 已经统一处理为从从根目录开始查找,一些方法均已测试通过
  //     */
  //    //是否会影响后面的，如果后面文件路径不从/开始，则会受影响。
  ////    sftp.cd("/dircav");//是否会影响后面的，如果后面文件路径不从/开始，则会受影响。
  ////    sftp.cd("yuyu");//方式一,相对路径
  ////    sftp.cd("/yuyu");//方式二,绝对路径,与方式一有区别
  ////    System.out.println(sftp.pwd());//作用
  ////   sf.upload("upload.txt", "D:\\test\\upload.txt", sftp);//方式一
  ////   sf.upload("/upload.txt", "D:\\test\\upload.txt", sftp);//方式二
  ////   sf.download("upload.txt","D:\\test\\download1.txt", sftp);
  ////   sf.download("/upload.txt","D:\\test\\download2.txt", sftp);
  ////   sf.makeDir("ss", sftp);
  ////   sf.makeDir("/ss", sftp);
  ////   sf.delete("upload.txt",sftp);
  ////   sf.delete("/upload.txt",sftp);
  ////   sf.deleteDir("ss", sftp);
  ////   sf.deleteDir("/ss", sftp);
  //     
  ////    System.out.println(sftp.getHome());
  ////    System.out.println(sftp.isClosed());
  ////    sftp.disconnect();
  ////    sftp.exit();
  ////    System.out.println(sftp.isClosed());
  ////    System.out.println("执行完成。。。");
  //  }
}

/**
 * 文件名		：FileExchangeManagerImpl.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.manager;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.fop.framework.source.BaseContext;
import com.fop.framework.source.file.context.FileConstant;
import com.fop.framework.source.file.data.FileExchangeEnumTypes;
import com.fop.framework.source.file.data.FileExchangeModel;
import com.fop.framework.source.file.data.FileInDataModel;
import com.fop.framework.source.file.data.FileXmlConfigBase.FileServerTypes;
import com.fop.framework.source.file.exception.FileRuntimeException;
import com.fop.framework.source.file.util.LocalFileUtil;
import com.fop.framework.source.file.util.SSHClientUtil;
import com.fop.framework.source.pub.ProcessDataType;
import com.fop.framework.source.pub.SourceConstant;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;

/**
 * 描述:执行文件操作实现类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class FileExchangeManagerImpl implements FileExchangeManager{
  @Override
  public void operate(BaseContext fileContext) {
    //从上下文中获取文件资源访问总入参对象
    FileInDataModel fileInDataModel=(FileInDataModel)(fileContext.getInData());
    //从总入参对象中得到执行信息
    FileExchangeModel fileExchangeModel=(FileExchangeModel)fileInDataModel.getData(ProcessDataType.EXCHANGE);
    
    FileExchangeEnumTypes fileExchangeEnumType=fileExchangeModel.getFileExchangeEnumType();
    String fileUrl=(String)fileContext.get(FileConstant.CONST_fileUrl);
    byte[] fileContent=(byte[])fileContext.get(FileConstant.CONST_fileContent);
    FileServerTypes fileServerType=(FileServerTypes)fileContext.get(FileConstant.CONST_fileServerType);
    Object connInfo=fileContext.get(FileConstant.CONST_connInfo);
    Object exchangeRes=null;
    switch(fileExchangeEnumType){
    case Delete_Dir:
      {
        switch(fileServerType){
        case LocalApp:
          exchangeRes=this.deleteLocalDir(fileUrl, (String)connInfo);
          break;
        case SFTP:
          exchangeRes=this.deleteSftpDir(fileUrl, (ChannelSftp)connInfo);
          break;
        default:
          throw new FileRuntimeException(FileConstant.CONN_TYPE_ERROR+":"+fileServerType);
        }
      }
      break;
    case IS_Dir_Exist:
      {
        switch(fileServerType){
        case LocalApp:
          exchangeRes=this.isLocalDirExist(fileUrl, (String)connInfo);
          break;
        case SFTP:
          exchangeRes=this.isSftpDirExist(fileUrl, (ChannelSftp)connInfo);
          break;
        default:
          throw new FileRuntimeException(FileConstant.CONN_TYPE_ERROR+":"+fileServerType);
        }
      }
      break;
    case Delete_File:
      {
        switch(fileServerType){
        case LocalApp:
          exchangeRes=this.deleteLocalFile(fileUrl, (String)connInfo);
          break;
        case SFTP:
          exchangeRes=this.deleteSftpFile(fileUrl, (ChannelSftp)connInfo);
          break;
        default:
          throw new FileRuntimeException(FileConstant.CONN_TYPE_ERROR+":"+fileServerType);
        }
      }
      break;
    case IS_File_Exist:
      {
        switch(fileServerType){
        case LocalApp:
          exchangeRes=this.isLocalFileExist(fileUrl, (String)connInfo);
          break;
        case SFTP:
          exchangeRes=this.isSftpFileExist(fileUrl, (ChannelSftp)connInfo);
          break;
        default:
          throw new FileRuntimeException(FileConstant.CONN_TYPE_ERROR+":"+fileServerType);
        }
      }
      break;
    case Make_Dirs:
      {
        switch(fileServerType){
        case LocalApp:
          exchangeRes=this.makeLocalDirs(fileUrl, (String)connInfo);
          break;
        case SFTP:
          exchangeRes=this.makeSftpDirs(fileUrl, (ChannelSftp)connInfo);
          break;
        default:
          throw new FileRuntimeException(FileConstant.CONN_TYPE_ERROR+":"+fileServerType);
        }
      }
      break;
    case Query_DirFileNames:
      {
        switch(fileServerType){
        case LocalApp:
          exchangeRes=this.getLocalListFileNames(fileUrl, (String)connInfo);
          break;
        case SFTP:
          exchangeRes=this.getSftpListFileNames(fileUrl, (ChannelSftp)connInfo);
          break;
        default:
          throw new FileRuntimeException(FileConstant.CONN_TYPE_ERROR+":"+fileServerType);
        }
      }
      break;
    case Read_File:
      {
        switch(fileServerType){
        case LocalApp:
          exchangeRes=this.getLocalFile(fileUrl, (String)connInfo);
          break;
        case SFTP:
          exchangeRes=this.downloadSftpFile(fileUrl, (ChannelSftp)connInfo);
          break;
        default:
          throw new FileRuntimeException(FileConstant.CONN_TYPE_ERROR+":"+fileServerType);
        }
      }
      break;
    case Replace_File:
      {
        //执行校验
        if(!fileExchangeModel.isNewIfNotExist()){//如果要求当文件名不存在的时候不新建
          switch(fileServerType){
          case LocalApp:
            if(!this.isLocalFileExist(fileUrl, (String)connInfo)){
              throw new FileRuntimeException(FileConstant.Exchange_File_NotExist_Error+":"+fileUrl);
            }
            break;
          case SFTP:
            if(!this.isSftpFileExist(fileUrl, (ChannelSftp)connInfo,false)){
              SSHClientUtil.closeChannelSftp((ChannelSftp)connInfo);
              throw new FileRuntimeException(FileConstant.Exchange_File_NotExist_Error+":"+fileUrl);
            }
            break;
          default:
            throw new FileRuntimeException(FileConstant.CONN_TYPE_ERROR+":"+fileServerType);
          }
        }
        //执行替换或新增
        switch(fileServerType){
        case LocalApp:
          exchangeRes=this.writeToLocalFile(fileUrl, fileContent, (String)connInfo);
          break;
        case SFTP:
          exchangeRes=this.uploadSftpFile(fileUrl, fileContent, (ChannelSftp)connInfo);
          break;
        default:
          throw new FileRuntimeException(FileConstant.CONN_TYPE_ERROR+":"+fileServerType);
        }
      }
      break;
    case Write_File:
      {
        //执行校验
        if(!fileExchangeModel.isReplaceIfExist()){//如果要求当文件名存在的时候不替换
          switch(fileServerType){
          case LocalApp:
            if(this.isLocalFileExist(fileUrl, (String)connInfo)){
              throw new FileRuntimeException(FileConstant.Exchange_File_Exist_Error+":"+fileUrl);
            }
            break;
          case SFTP:
            if(this.isSftpFileExist(fileUrl, (ChannelSftp)connInfo,false)){
              SSHClientUtil.closeChannelSftp((ChannelSftp)connInfo);
              throw new FileRuntimeException(FileConstant.Exchange_File_Exist_Error+":"+fileUrl);
            }
            break;
          default:
            throw new FileRuntimeException(FileConstant.CONN_TYPE_ERROR+":"+fileServerType);
          }
        }
        //执行替换或新增
        switch(fileServerType){
        case LocalApp:
          exchangeRes=this.writeToLocalFile(fileUrl, fileContent, (String)connInfo);
          break;
        case SFTP:
          exchangeRes=this.uploadSftpFile(fileUrl, fileContent, (ChannelSftp)connInfo);
          break;
        default:
          throw new FileRuntimeException(FileConstant.CONN_TYPE_ERROR+":"+fileServerType);
        }
      }
      break;
    default:
      throw new FileRuntimeException(FileConstant.Exchange_Unknown_Type+":"+fileExchangeEnumType);
    }
    fileContext.put(FileConstant.CONST_exchangeRes, exchangeRes);
    fileContext.put(FileConstant.CONST_fileExchangeEnumType, fileExchangeEnumType);
  }

  @Override
  public boolean isSftpFileExist(String serverPath, ChannelSftp sftp,boolean closeChannel) {
    return SSHClientUtil.isSftpFileExist(serverPath, sftp,closeChannel);
  }
  @Override
  public boolean isSftpFileExist(String serverPath, ChannelSftp sftp) {
    return SSHClientUtil.isSftpFileExist(serverPath, sftp,true);
  }
  @Override
  public boolean isLocalFileExist(String serverPath, String appBasePath) {
    return LocalFileUtil.isLocalFileExist(serverPath,appBasePath);
  }

  @Override
  public boolean isSftpDirExist(String serverPath, ChannelSftp sftp,boolean closeChannel) {
    return SSHClientUtil.isSftpDirExist(serverPath, sftp,closeChannel);
  }
  @Override
  public boolean isSftpDirExist(String serverPath, ChannelSftp sftp) {
    return SSHClientUtil.isSftpDirExist(serverPath, sftp,true);
  }
  @Override
  public boolean isLocalDirExist(String serverPath, String appBasePath) {
    return LocalFileUtil.isLocalDirExist(serverPath,appBasePath);
  }

  @Override
  public boolean uploadSftpFile(String serverPath, byte[] uploadFile,
      ChannelSftp sftp) {
    return SSHClientUtil.upload(serverPath, uploadFile, sftp);
  }

  @Override
  public boolean writeToLocalFile(String serverPath, byte[] uploadFile,
      String appBasePath) {
    return LocalFileUtil.writeToLocalFile(serverPath,uploadFile,appBasePath);
  }

  @Override
  public boolean makeSftpDirs(String serverPath, ChannelSftp sftp) {
    return SSHClientUtil.makeDirs(serverPath, sftp);
  }

  @Override
  public boolean makeLocalDirs(String serverPath, String appBasePath) {
    return LocalFileUtil.makeLocalDirs(serverPath,appBasePath);
  }

  @Override
  public byte[] downloadSftpFile(String serverPath,ChannelSftp sftp) {
    return SSHClientUtil.download(serverPath,sftp);
  }

  @Override
  public byte[] getLocalFile(String serverPath, String appBasePath) {
    return LocalFileUtil.getLocalFile(serverPath,appBasePath);
  }

  @Override
  public boolean deleteSftpFile(String serverPath, ChannelSftp sftp) {
    return SSHClientUtil.delete(serverPath, sftp);
  }

  @Override
  public boolean deleteLocalFile(String serverPath, String appBasePath) {
    return LocalFileUtil.deleteLocalFile(serverPath,appBasePath);
  }

  @Override
  public boolean deleteSftpDir(String serverPath, ChannelSftp sftp) {
    return SSHClientUtil.deleteDir(serverPath, sftp);
  }

  @Override
  public boolean deleteLocalDir(String serverPath, String appBasePath) {
    return LocalFileUtil.deleteLocalDir(serverPath,appBasePath);
  }

  @Override
  public List<String> getSftpListFileNames(String serverPath, ChannelSftp sftp) {
    Vector<LsEntry> fileVec= SSHClientUtil.listFiles(serverPath, sftp);
    List<String> fileNames=new LinkedList<String>();
    for(LsEntry lsEntry:fileVec){
      fileNames.add(lsEntry.getFilename());
    }
    return fileNames;
  }

  @Override
  public List<String> getLocalListFileNames(String serverPath, String appBasePath) {
    return LocalFileUtil.getLocalListFileNames(serverPath,appBasePath);
  }
  @Override
  public String getDescription() {
    return SourceConstant.File_Exchange;
  }

}

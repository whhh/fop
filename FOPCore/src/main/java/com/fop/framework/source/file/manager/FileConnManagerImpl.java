/**
 * 文件名		：FileConnManagerImpl.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.manager;

import com.fop.framework.source.BaseContext;
import com.fop.framework.source.file.context.FileConstant;
import com.fop.framework.source.file.context.FileContext;
import com.fop.framework.source.file.data.FileConnEnumTypes;
import com.fop.framework.source.file.data.FileConnModel;
import com.fop.framework.source.file.data.FileInDataModel;
import com.fop.framework.source.file.data.FileXmlConfigBase.FileServerTypes;
import com.fop.framework.source.file.data.SSHParamModel;
import com.fop.framework.source.file.exception.FileRuntimeException;
import com.fop.framework.source.file.util.SSHClientUtil;
import com.fop.framework.source.pub.ProcessDataType;
import com.fop.framework.source.pub.SourceConstant;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;

/**
 * 描述:文件资源连接管理实现
 * @version 1.00
 * @author syl
 * 
 */
public class FileConnManagerImpl implements FileConnManager{
  @Override
  public void operate(BaseContext fileContext) {
    //从上下文中获取文件资源访问总入参对象
    FileInDataModel fileInDataModel=(FileInDataModel)(fileContext.getInData());
    //从总入参对象中得到连接信息
    FileConnModel fileConnModel=(FileConnModel)fileInDataModel.getData(ProcessDataType.CONN);
    FileConnEnumTypes fileConnEnumType=fileConnModel.getFileConnEnumType();
    Object sessionInfo = null;
    Object connInfo=null;
    switch(fileConnEnumType){
    case Conn_None:
      {
        FileServerTypes fileServerType=fileConnModel.getFileServerType();
        switch(fileServerType){
        case LocalApp:
          connInfo=this.getLocalBasePath();
          break;
        case SFTP:
        default:
          showConnUnSupportError(fileConnEnumType, fileServerType);
          break;
        }
      }
      break;
    case Conn_ByBeanId:
      {
        FileServerTypes fileServerType=fileConnModel.getFileServerType();
        switch(fileServerType){
        case LocalApp:
          showConnUnSupportError(fileConnEnumType, fileServerType);
          break;
        case SFTP:
//        case FTP://在此处可添加SSH支持的服务器类型,统一由下面的方法处理,方便扩展使用
          SSHParamModel paramModel=(SSHParamModel)FileContext.getBean(fileConnModel.getFileConnBeanId());
          sessionInfo = this.getSession(paramModel);
          connInfo = this.getSshChannel((Session)sessionInfo, paramModel.getServerType());
//          connInfo=this.getSshChannel(fileConnModel.getFileConnBeanId());
          break;
        default:
          showConnUnSupportError(fileConnEnumType, fileServerType);
          break;
        }
      }
      break;
//    case Conn_ByConfig:
//      {
//        FileServerTypes fileServerType=fileConnModel.getFileServerType();
//        switch(fileServerType){
//        case LocalApp:
//          throw new FileRuntimeException(FileConstant.CONN_UnSupport_Config+":Conn_ByConfig-->"+fileServerType);
//        case SFTP:
//          {
//            SftpParamModel sftpParamModel =fileConnModel.getSftpParamModel();
//            connInfo=this.getSshChannel(sftpParamModel);
//          }
//          break;
//        default:
//          throw new FileRuntimeException(FileConstant.CONN_FileServer_TYPE_UnSupport+":"+fileServerType);
//        }
//      }
//      break;
    default:
      throw new FileRuntimeException(FileConstant.CONN_TYPE_ERROR);
    }
    fileContext.put(FileConstant.CONST_sessionInfo, sessionInfo);
    fileContext.put(FileConstant.CONST_connInfo, connInfo);
    fileContext.put(FileConstant.CONST_fileServerType, fileConnModel.getFileServerType());
  }

  @Override
  public String getLocalBasePath() {
    return FileContext.getLocalBasePath();
  }
  
  public synchronized Session getSession(SSHParamModel paramModel) {
    return FileContext.getSshSession(paramModel);
  }

  @Override
  public synchronized ChannelSftp getSshChannel(String sshParamBeanId) {
  //public ChannelSftp getSshChannel(String sshParamBeanId) { //syl--del
    SSHParamModel paramModel=(SSHParamModel)FileContext.getBean(sshParamBeanId);
    Session session=FileContext.getSshSession(paramModel);
    return getSshChannel(session, paramModel.getServerType());
  }

  @Override
  public synchronized ChannelSftp getSshChannel(Session sshSession, String channelType) {
//  public ChannelSftp getSshChannel(Session sshSession, String channelType) {//syl--del
    return SSHClientUtil.openChannel(sshSession, channelType);
  }
  
  public synchronized void closeSession(Session sshSession, String channelType) {
    SSHClientUtil.closeSession(sshSession, channelType);
  }
  
  public synchronized void closeChannelSftp(ChannelSftp sftp) {
    SSHClientUtil.closeChannelSftp(sftp);
  }
  
  private void showConnUnSupportError(FileConnEnumTypes fileConnEnumType,FileServerTypes fileServerType){
    StringBuffer sbf=new StringBuffer();
    sbf.append(":");
    sbf.append(fileConnEnumType);
    sbf.append("-->");
    sbf.append(fileServerType);
    throw new FileRuntimeException(FileConstant.CONN_UnSupport_Config+sbf.toString());
  }
  @Override
  public String getDescription() {
    return SourceConstant.File_Conn;
  }
}

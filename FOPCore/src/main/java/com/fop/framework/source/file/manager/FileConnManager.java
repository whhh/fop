/**
 * 文件名		：FileConnManager.java
 * 创建日期	：2013-9-27
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.manager;

import com.fop.framework.source.BaseManager;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;

/**
 * 文件资源管理，包括文件访问连接、文件路径
 * 
 * @version 1.00
 * @author syl
 * 
 */
public interface FileConnManager extends BaseManager{
  /**
   * 获取本地应用部署根目录
   */
  public String getLocalBasePath();
  
  /**
   * 通过SSHParamModel配置信息获取Channel对象
   * @param beanId 对应配置的SSHParamModel的实例id
   * @return 返回文件服务器交互通道
   */
  public ChannelSftp getSshChannel(String sshParamBeanId);
  
//  /**
//   * 通过SftpParamModel获取连接
//   * @param paramModel
//   * @return
//   */
//  public ChannelSftp getSshChannel(SftpParamModel paramModel);
  
  /**
   * 通过session获取连接,此时session已连接
   * @param sshSession 文件服务器session对象
   * @param channelType 连接类型，例如"sftp"
   * @return 返回可执行的文件服务器channel通道
   */
  public ChannelSftp getSshChannel(Session sshSession, String channelType);
  
}

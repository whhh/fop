/**
 * 文件名		：FileContext.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.context;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.source.BaseContext;
import com.fop.framework.source.BaseInDataModel;
import com.fop.framework.source.file.data.SSHParamModel;
import com.fop.framework.source.file.exception.FileRuntimeException;
import com.fop.framework.source.file.util.FileUtil;
import com.fop.framework.source.file.util.SSHClientUtil;
import com.fop.framework.source.pub.SourceConstant;
import com.fop.framework.util.StringUtil;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * 描述:文件资源操作上下文对象
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class FileContext extends BaseContext {
  /**日志打印对象**/
  private static Log logger = LogFactory.getLog(FileContext.class);
  
  /**本地文件操作根目录*/
  private static String basePath;
  /**服务器文件操作根目录存储,key对应服务器配置beandid，value对应根目录赋值*/
  private static HashMap<String,String> servBasePath=null;
  /** sshSession池,启动时完成初始化工作*/
  private static ConcurrentHashMap <SSHParamModel, Session> sshSessionPool=null;
  /**
   * 文件资源操作上下文构造方法
   * @param inData
   */
  public FileContext(BaseInDataModel inData){
    super(inData);
  }
  /**
   * 文件资源操作上下文构造方法
   * @param basePathStr 本机文件服务根目录
   * @param sshParamModelSet 远程文件服务器列表
   * @param servBasePathTmp 远程服务器根目录配置列表
   */
  public FileContext(String basePathStr,Set<SSHParamModel> sshParamModelSet,HashMap<String, String> servBasePathTmp){
    synchronized (FileContext.class) {//synchronized
      if(basePath==null){
          basePath=basePathStr;
      }else{//代码控制只能初始化一次
        throw new FileRuntimeException(SourceConstant.OBJECT_MULTI_CREATE_ERROR);
      }
      /*if(sshSessionPool==null){
        sshSessionPool=new ConcurrentHashMap<SSHParamModel, Session>();
        for(SSHParamModel sshParamModel:sshParamModelSet){
          String keyFile=sshParamModel.getKeyFile();
          if(!StringUtil.isStrEmpty(keyFile)){
            keyFile=System.getProperty(SourceConstant.WebApp_SysKey)+FileUtil.fileSplit+keyFile;
            sshParamModel.setKeyFile(keyFile);
          }
          sshSessionPool.put(sshParamModel,getSessionAndConn0(sshParamModel));
        }
      }else{
        throw new FileRuntimeException(SourceConstant.OBJECT_MULTI_CREATE_ERROR);
      }*/
      if(servBasePath==null){
        servBasePath=servBasePathTmp;
      }else{
        throw new FileRuntimeException(SourceConstant.OBJECT_MULTI_CREATE_ERROR);
      }
    }
  }
  /**
   * 从上下文中获取本机文件服务器配置的根目录
   * @return 返回根目录字符串
   */
  public static String getLocalBasePath(){
    return basePath;
  }
  
  /**
   * 从上下文中获取sshSession实例,如果存在且isConnected则直接返回，
   * 否则根据请求的连接参数信息重新创建isConnected的服务器session
   * @param paramModel 文件服务器连接信息实例
   * @return 返回已连接的文件服务器Session信息
   */
  public static synchronized Session getSshSession(SSHParamModel paramModel){
//  public static Session getSshSession(SSHParamModel paramModel){//syl--del
    /*Session currSession=sshSessionPool.get(paramModel);*/
//    synchronized (FileContext.class) {//syl--del
      /*if(currSession==null||!currSession.isConnected()){
        logger.info("----framework:FileServer Session ["+paramModel+"] is disConnected,try to recreate.");
        if(sshSessionPool.containsKey(paramModel)){
          currSession=getSessionAndConn0(paramModel);
          sshSessionPool.put(paramModel, currSession);
        }else{//如果传入的参数信息未定义
          throw new FileRuntimeException(FileConstant.UnDefined_FileServer_Info);
        }
      }*/
//    }//syl--del
    Session currSession = getSessionAndConn0(paramModel);
    return currSession;
  }
  /**
   * 根据参数信息创建文件服务器的session连接,设置session不超时0
   * 若连接失败，则抛出连接错误异常信息
   * @param sshParamModel 服务器连接参数信息
   * @return 返回连接成功的Session信息
   */
  public static Session getSessionAndConn0(SSHParamModel sshParamModel){
    Session tmpSession=SSHClientUtil.getSshSession(sshParamModel);
    try {
      tmpSession.connect(0);
      logger.info("----framework:FileServer ["+sshParamModel+"] create success.");
    } catch (JSchException e) {
      logger.error("----framework:FileServer ["+sshParamModel+"] create fail.", e);
      throw new FileRuntimeException(FileConstant.FILE_SESSION_CREATE_ERROR, e);
    }
    return tmpSession;
  }
  /**
   * 通过beanId获取服务器根目录配置
   * @param beanId 服务器连接信息配置的beanId
   * @return 设定的根目录
   */
  public static String getServBasePath(String beanId){
    return servBasePath.get(beanId);
  }
  
}

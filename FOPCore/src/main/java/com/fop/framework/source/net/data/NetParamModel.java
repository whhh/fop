/**
 * 文件名		：NetParamModel.java
 * 创建日期	：2013-10-25
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.net.data;

/**
 * 描述:TODO.....
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class NetParamModel {
//private static final String defaultServiceContainer="default";
  private static final long defaultTimeOut=-1;
  /**socket容器名称*/
  private String serviceContainer;
  /**报文配置Id*/
  private String messageConfigId;
  /**报文内容*/
  private Object message;
  /**超时时间*/
  private long timeOut;
  
  /**
   * 基本构造方法
   * @param serviceContainer socket容器名称
   * @param messageConfigId 报文配置Id
   * @param message 报文内容
   * @param timeOut 超时时间
   */
  public NetParamModel(String serviceContainer,String messageConfigId,Object message,long timeOut){
    this.serviceContainer=serviceContainer;
    this.messageConfigId=messageConfigId;
    this.message=message;
    this.timeOut=timeOut;
  }
  //***提供默认配置的构造方法
//  public NetInDataModel(String messageConfigId,Object message,int timeOut){
//    this.serviceContainer=defaultServiceContainer;
//    this.messageConfigId=messageConfigId;
//    this.message=message;
//    this.timeOut=timeOut;
//  }
  /**
   * 其他系统访问输入参数构造方法
   * @param serviceContainer socket容器名称
   * @param messageConfigId 报文配置Id
   * @param message 报文内容
   */
  public NetParamModel(String serviceContainer,String messageConfigId,Object message){
    this.serviceContainer=serviceContainer;
    this.messageConfigId=messageConfigId;
    this.message=message;
    this.timeOut=defaultTimeOut;
  }
  
//  public NetInDataModel(String messageConfigId,Object message){
//    this.serviceContainer=defaultServiceContainer;
//    this.messageConfigId=messageConfigId;
//    this.message=message;
//    this.timeOut=defaultTimeOut;
//  }
  
  /**
   * @return the serviceContainer
   */
  public String getServiceContainer() {
    return serviceContainer;
  }

  /**
   * @return the messageConfigId
   */
  public String getMessageConfigId() {
    return messageConfigId;
  }

  /**
   * @return the message
   */
  public Object getMessage() {
    return message;
  }

  /**
   * @return the timeOut
   */
  public long getTimeOut() {
    return timeOut;
  }
}

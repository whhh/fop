/**
 * 文件名		：InvokeModel.java
 * 创建日期	：2013-10-12
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.pub;

/**
 * 描述:javaInvoke方法执行所需的参数模型
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class InvokeModel {
  /**方法名*/
  private String methodName;
  /**方法执行所需参数*/
  private Object[] parms;
  /**
   * 反射模型信息构造方法
   */
  public InvokeModel(){}
  /**
   * 反射模型信息构造方法
   * @param methodName 方法名
   * @param parms 方法执行所需参数
   */
  public InvokeModel(String methodName,Object[] parms){
    this.methodName=methodName;
    this.parms=parms;
  }
  /**
   * 反射模型信息构造方法
   * @param methodName 方法名
   */
  public InvokeModel(String methodName){
    this.methodName=methodName;
    this.parms=null;
  }
  /**
   * @return the methodName
   */
  public String getMethodName() {
    return methodName;
  }
  /**
   * @param methodName the methodName to set
   */
  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }
  /**
   * @return the parms
   */
  public Object[] getParms() {
    return parms;
  }
  /**
   * @param parms the parms to set
   */
  public void setParms(Object[] parms) {
    this.parms = parms;
  }
  
}

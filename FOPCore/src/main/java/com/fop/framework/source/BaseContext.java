/**
 * 文件名		：BaseContext.java
 * 创建日期	：2013-10-15
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;

import com.fop.framework.context.ApplicationContextUtil;
import com.fop.framework.context.FOPContext;
import com.fop.framework.source.db.context.DBConstant;
import com.fop.framework.source.db.exception.DBRuntimeException;

/**
 * 描述:数据上下文基类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class  BaseContext {
  
  private Log logger = LogFactory.getLog(BaseContext.class);
  /**存储上下文数据*/
  private final FOPContext fopContext=new FOPContext();;
  /**保持fopContext是不可被替换的key信息*/
  private final HashMap<String,Boolean> isAllowInfoMap=new HashMap<String,Boolean>();
  /**传入参数存储*/
  private BaseInDataModel inData;
  /**结果数据*/
  private Object outData;
  
  public BaseContext(){}
  public BaseContext(BaseInDataModel inData){
    this.inData=inData;
  }
  /**
   * 存入数据，默认允许被替换
   * @param key
   * @param value
   */
  public void put(String key,Object value){
    put(key, value, true);
  }
  /**
   * 存入数据
   * @param key
   * @param value
   * @param allowReplace false表示， 不允许替换，若key值存在则抛出异常； 为true时表示允许替换，
   * 可控制key-value在当前Context中是否允许被替换
   */
  public synchronized void put(String key, Object value, boolean allowReplace) {
    if(isAllowInfoMap.keySet().contains(key)){//已经存在对应的key
      if(isAllowInfoMap.get(key)){//允许被替换
        fopContext.addFieldData(key, value);//执行替换
      }else{//不允许，抛出异常
        logger.error(DBConstant.DATA_NOT_ALLOW_REPLACED+":key="+key);
        throw new DBRuntimeException(DBConstant.DATA_NOT_ALLOW_REPLACED+":key="+key);
      }
    }else{//初次赋值
      isAllowInfoMap.put(key, allowReplace);//保存是否可被替换的信息
      fopContext.addFieldData(key, value);//执行初次赋值
    }
  }
  /**
   * 获取对应数据
   * @param key
   * @return 返回对应取值
   */
  public Object get(String key){//是否返回clone数据?
    return fopContext.getFieldDataValue(key);
  }
  /**
   * 获取入参数据
   * @return 返回入参数据对象
   */
  public BaseInDataModel getInData() {
    return inData;
  }
  /**
   * 获取输出数据
   * @return 返回结果数据对象
   */
  public Object getOutData() {
    return outData;
  }
  /**
   * 设置输出数据
   * @param outData 输出数据
   */
  public void setOutData(Object outData) {
    this.outData = outData;
  }
  
//  读取springBean的代码
//  String[] tmpS=applicationContext.getBeanDefinitionNames();
//  for(String s:tmpS){
//    System.out.println(s);
//  }
  /**
   * 获取bean实例
   * @param beanid bean标识
   * @return 返回spring 上下文bean实例
   * @throws BeansException 获取bean失败时会抛出异常
   */
  public static Object getBean(String beanId) throws BeansException{
    return ApplicationContextUtil.getBean(beanId);
  }
  /**
   * 获取bean实例
   * @param Class<?> clazz class标识
   * @return 返回spring 上下文bean实例
   * @throws BeansException  获取bean失败时会抛出异常
   */
  public static Object getBean(Class<?> clazz) throws BeansException{
    return ApplicationContextUtil.getBean(clazz);
  }
}

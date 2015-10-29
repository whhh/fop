/**
 * 文件名		：CacheDataUtil.java
 * 创建日期	：2013-11-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述:缓存数据操作
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class CacheDataUtil {

  /**
   * cache数据存储容器
   */
  private static final Map<String, Object> cacheContext = new ConcurrentHashMap<String, Object>();
  
  /**
   * 线程缓存数据
   */
  private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

  /**
   * 添加cache值
   * @param cacheName 缓存变量名称
   * @param cacheObj 缓存值
   */
  public static void addCacheValue(String cacheName, Object cacheObj){
    //为空则返回null
    if (cacheName == null || cacheName.trim().length() < 1){
      return;
    }
    cacheContext.put(cacheName, cacheObj);
  }

  /**
   * 获取cache table值
   * @param cacheName 缓存变量名称
   * @return
   */
  public static Object getCacheTableValue(String cacheName){
    //为空则返回null
    if (cacheName == null || cacheName.trim().length() < 1){
      return null;
    }
    Object cacheObj = cacheContext.get(cacheName);
    return cacheObj;
  }

  /**
   * 取得容器所有cache值集合
   * @return
   */
  public static Map<String, Object> getAllValues() {
    return cacheContext;
  }
  /**
   * 获取线程数据
   * @return
   */
  public static Map<String, Object> getThreadCacheMap() {
    return threadLocal.get();
  }
  
  /**
   * 添加线程缓存数据
   * @param cacheName
   * @param cacheObj
   */
  public static void addThreadCacheData(String cacheName, Object cacheObj) {
    Map<String, Object> threadLocalMap = threadLocal.get();
    if(threadLocalMap == null) {
      threadLocalMap = new ConcurrentHashMap<String, Object>();
      threadLocal.set(threadLocalMap);
    }
    threadLocalMap.put(cacheName, cacheObj);
  }
  /**
   * 获取线程缓存数据
   * @param cacheName
   * @return
   */
  public static Object getThreadCacheDataValue(String cacheName) {
    Map<String, Object> threadLocalMap = threadLocal.get();
    if(threadLocalMap == null) {
      threadLocalMap = new ConcurrentHashMap<String, Object>();
      threadLocal.set(threadLocalMap);
    }
    return threadLocalMap.get(cacheName);
  }
  
  /**
   * 清除线程缓存数据
   */
  public static void clearThreadCacheData() {
    threadLocal.remove();
  }
}

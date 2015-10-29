/**
 * 文件名		：CacheDataProcess.java
 * 创建日期	：2013-11-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.context;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fop.framework.constant.FOPConstants;


/**
 * 描述:缓存业务数据处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class CacheDataProcess implements FOPDataProcess{

  /**
   * 数据处理对象集合
   */
  private List<CacheData> listcacheprocess;

  /* (non-Javadoc)
   * @see com.fop.framework.context.FOPDataProcess#init()
   */
  @Override
  public void init() {
    if (listcacheprocess == null){ //为空不做处理
      return;
    }
    int lsize = listcacheprocess.size(); //取得集合大小
    CacheData dataprocess = null; //数据处理对象
    for (int i = 0; i < lsize; i++){ //遍历进行数据处理
      dataprocess = listcacheprocess.get(i);
      dataprocess.setIsload(false); //标识未被加载
      String cachemode = dataprocess.getCachemode(); //取到缓存模式
      if (FOPConstants.CACHE_MODE_EAGER.equals(cachemode)){ //立即加载方式
        dataprocess.load(); //执行加载数据处理
      }
    }
  }

  /* (non-Javadoc)
   * @see com.fop.framework.context.FOPDataProcess#destroy()
   */
  @SuppressWarnings("rawtypes")
  @Override
  public void destroy() {
    Map<String, Object> cachemap = CacheDataUtil.getAllValues();
    //遍历处理
    Iterator it = cachemap.entrySet().iterator();
    try {
      while(it.hasNext()){
        Entry entry = (Entry)it.next();
        //取到key
        String key = String.valueOf(entry.getKey());
        //System.out.println("*****key====" + key);
        //移除bean
        cachemap.remove(key);
      }
      System.out.println("******************destroy cache ok.");
    } catch (Exception e) {
      System.out.println("******************destroy cache failed, " + e.getMessage());
      System.out.println(e);
    }
  }

  /**
   * @param listcacheprocess the listcacheprocess to set
   */
  public void setListcacheprocess(List<CacheData> listcacheprocess) {
    this.listcacheprocess = listcacheprocess;
  }
  
}

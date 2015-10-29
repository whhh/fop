/**
 * 文件名		：CacheDataForTableProcess.java
 * 创建日期	：2013-11-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.context;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.source.SourceAccessUtil;
import com.fop.framework.source.db.data.DBInDataModel;
import com.fop.framework.util.StringUtil;

/**
 * 描述:数据表缓存处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
@SuppressWarnings("all")
public class CacheDataForTableProcess implements CacheData {

  /** 缓存名称 */
  public static final String CACHE_NAME = "cacheName";

  /** 数据存放实例 */
  public static final String CACHE_DATASETCLASS = "datasetClass";

  /** 加载数据接口 */
  public static final String CACHE_DAOCLASS = "daoClass";

  /** 加载数据方法 */
  public static final String CACHE_DAOMETHODNAME = "daoMethodName";

  public static final String CACHE_KEY = "key";

  /** 加载模式： lazy.使用的时候加载 eager.启动时加载， 默认为eager */
  private static String cachemode = FOPConstants.CACHE_MODE_EAGER;

  /** 标识是否已加载 true.已加载，false.未加载 */
  private static boolean isload = false;

  /** 配置信息 */
  private List<Map> configs;

  /*
   * (non-Javadoc)
   * 
   * @see com.fop.framework.context.CacheData#load()
   */
  @Override
  public void load() {
    isload = true; // 标识load方法已执行

    if (configs == null) {
      return;
    }
    int lsize = configs.size(); // 取得集合大小
    Map<String, String> mapConfig = null; // 数据配置对象
    List<Object> listdata = null; // 读取数据表结果
    for (int i = 0; i < lsize; i++) { // 遍历进行数据处理
      // 取得单项配置
      mapConfig = (Map<String, String>) configs.get(i);
      String cacheName = mapConfig.get(CACHE_NAME); // 存值名称
      String datasetClass = mapConfig.get(CACHE_DATASETCLASS); // 存值实例
      String daoClass = mapConfig.get(CACHE_DAOCLASS); // 数据接口
      String daoMethodName = mapConfig.get(CACHE_DAOMETHODNAME); // 数据接口方法
      String key = mapConfig.get(CACHE_KEY); // 数据接口方法

      try {
        // 1.--------获取实例------------------
        // 1-1.---数据存放实例(domain类型)-------------
        // Class类型
        Class datadomainclass = Class.forName(datasetClass);
        // 获取一个Object类的实例对象。
        // Object datadomain = datadomainclass.newInstance();
        // 1-2.---数据存放实例(dao类型)-------------
        // Class类型
        Class datadaoclass = Class.forName(daoClass);
        // 获取一个Object类的实例对象。
        // Object datadao = datadaoclass.newInstance();

        // 2.--------执行dao对象方法获取数据------------------
        try {
          // 定义DataModel模型对象
          DBInDataModel datamodel = new DBInDataModel(datadaoclass,
              daoMethodName, null);
          // 执行操作
          Object retvalue = SourceAccessUtil.operate(datamodel);
          listdata = (List<Object>) retvalue;
        } catch (Exception e) {
          System.out.println("----" + daoMethodName + "方法执行出错: "
              + e.getMessage() + ", " + e);
          ;
        }

        // 3.--------存值到指定的实例------------------
        if (listdata == null || listdata.isEmpty()) {
          continue; // 如果获取数据为空，继续下一个处理
        }
        // 通过list第一个元素，判断是否为配置指定的实例对象
        if (!datadomainclass.equals(listdata.get(0).getClass())) {
          throw new Exception("取值类型与配置指定类型不一致！");
        }
        
        Object sdata = null;
        if(!StringUtil.isStrEmpty(key)) {
          Map<Object, Object> mapdata = new HashMap<Object, Object>();
          if (listdata.get(0) instanceof Map) {
            // 循环组装键值
            for (Object data : listdata) {
              Map map = (Map) data;
              mapdata.put(map.get(key), data);
            }
          } else {
            //获取key首字母大写
            key = key.substring(0, 1).toUpperCase() + key.substring(1);
            //获取get方法
            Method method = listdata.get(0).getClass()
                .getMethod("get" + key, new Class[] {});
            //如果存在get函数就获取键值
            if (method != null) {
              for (Object data : listdata) {
                //获取键值组装数据
                mapdata.put(method.invoke(data, new Object[] {}), data);
              }
            } else {
              continue;
            }
          }
          sdata = mapdata;
        } else {
          sdata = listdata;
        }
       

        // 添加缓存数据
        CacheDataUtil.addCacheValue(cacheName, sdata);
        System.out.println("*****init cache: " + cacheName + "...");
      } catch (Exception e) {
        System.out.println("----CacheDataForTableProcess load error: "
            + e.getMessage());
        System.out.println(e);
      }

    }
    System.out.println("******************init cache ok.");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.fop.framework.context.CacheData#getConfigs()
   */
  @Override
  public List getConfigs() {
    return this.configs;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.fop.framework.context.CacheData#getCachemode()
   */
  @Override
  public String getCachemode() {
    return this.cachemode;
  }

  /**
   * @param configs
   *          the configs to set
   */
  public void setConfigs(List<Map> configs) {
    this.configs = configs;
  }

  /**
   * @param cachemode
   *          the cachemode to set
   */
  public void setCachemode(String cachemode) {
    this.cachemode = cachemode;
  }

  /**
   * @return the isload
   */
  public static boolean isIsload() {
    return isload;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.fop.framework.context.CacheData#setIsload(boolean)
   */
  @Override
  public void setIsload(boolean isload) {
    this.isload = isload;
  }

}

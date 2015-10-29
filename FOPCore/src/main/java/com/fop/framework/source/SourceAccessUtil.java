/**
 * 文件名		：SourceAccessUtil.java
 * 创建日期	：2013-10-15
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source;

import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.source.pub.SourceType;

/**
 * 描述:资源分发器,资源访问统一入口
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class SourceAccessUtil {
//  public static void main(String[] args) {
//    System.out.println(SourceType.valueOf("DataBase").equals(SourceType.DataBase));
//  }
  /**日志打印对象**/
  private static Log logger = LogFactory.getLog(SourceAccessUtil.class);
  /**
   * 资源工厂列表
   * key--SourceType，工厂处理的数据类型
   * value--SourceManagerFactory工厂的实例
   */
  private static HashSet<SourceManagerFactory> factorySet;
  
  /**
   * 系统启动时注入可用的工厂
   * @param factorySet 可用的工厂Set集合
   */
  public synchronized static void setFactorySet(HashSet<SourceManagerFactory> factorySet) {
      if(SourceAccessUtil.factorySet==null){
        SourceAccessUtil.factorySet = factorySet;
      }else{
        throw new RuntimeException("资源工厂列表不能实例化多次。");
      }
  }

  /**
   * 资源操作入口
   * @param baseInData 资源操作的传入参数信息
   * @return 返回资源处理结果对象
   */
  public static Object operate(BaseInDataModel baseInData){
    Long startTime=System.currentTimeMillis();
    //1.得到当前的数据类型
    SourceType sourceType=baseInData.getSourceType();
    //2.根据数据类型找到对应处理工厂
    SourceManagerFactory currentFactory=null;
    for(SourceManagerFactory tmpFactory:factorySet){
      if(tmpFactory.getSourceType().equals(sourceType)){
        currentFactory=tmpFactory;
        break;
      }
    }
    if(currentFactory==null){throw new RuntimeException("未找到对应的资源处理工厂。"+sourceType);}
    //3.执行资源请求操作
    BaseContext baseContext=new BaseContext(baseInData);//将入参模型数据存入上下文
    Object resData= currentFactory.operate(baseContext);
    Long endTime=System.currentTimeMillis();
    logger.debug("----framework: source operate "+sourceType+" total used time: " + (endTime - startTime) + "ms");
    return resData;
  }

}

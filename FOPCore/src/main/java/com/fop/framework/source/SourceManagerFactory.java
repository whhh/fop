/**
 * 文件名		：SourceManagerFactory.java
 * 创建日期	：2013-9-28
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.source.pub.SourceType;
import com.fop.framework.util.StringUtil;

/**
 * 资源管理工厂
 * 通过设置各个过程组件，完成执行该类资源处理
 * @version 1.00
 * @author syl
 * 
 */
public class SourceManagerFactory {
  /**日志打印对象**/
  private static Log logger = LogFactory.getLog(SourceManagerFactory.class);
  
  /**支持处理的资源类型*/
  private SourceType sourceType;
  /**资源处理组件链*/
  private ArrayList<BaseManager> managerList;
  
  /**
   * 设置当前资源管理工厂包含的管理工具列表
   * @param managerList the managerList to set
   */
  public synchronized void setManagerList(ArrayList<BaseManager> managerList) {
      if(this.managerList==null){
        this.managerList = managerList;
      }else{
        throw new RuntimeException("资源处理组件链不能实例化多次。");
      }
  }
  /**
   * 设定资源类型
   * @param sourceType 资源类型
   */
  public void setSourceType(SourceType sourceType) {
    this.sourceType = sourceType;
  }
  /**
   * 获取资源类型
   * @return the sourceType
   */
  public SourceType getSourceType() {
    return sourceType;
  }
  /**
   * 资源信息处理入口
   * 例如：
   * 1.获取参数
   * 2.得到可执行的连接
   * 3.执行请求
   * 4.结果数据转换
   * 5.返回数据
   * 该执行顺序和过程在配置文件中初始定义
   * @param inData 资源处理入参总信息
   * @return 返回资源处理结果对象
   */
  public Object operate(BaseContext baseContext){
    //顺序执行管理工具的处理方法
    for(int i=0;i<managerList.size();i++){
      Long startTime=System.currentTimeMillis();
      BaseManager currManager=managerList.get(i);
      currManager.operate(baseContext);
      //如果描述信息不为空,则打印执行时间的日志
      if(!StringUtil.isStrEmpty(currManager.getDescription())){
        Long endTime=System.currentTimeMillis();
        logger.debug("----framework: source operate "+currManager.getDescription()+" used time: " + (endTime - startTime) + "ms");
      }
    }
    return baseContext.getOutData();
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((sourceType == null) ? 0 : sourceType.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SourceManagerFactory other = (SourceManagerFactory) obj;
    if (sourceType != other.sourceType)
      return false;
    return true;
  }
  
}

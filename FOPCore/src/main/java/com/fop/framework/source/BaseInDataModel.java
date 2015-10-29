/**
 * 文件名		：BaseInDataModel.java
 * 创建日期	：2013-10-16
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source;

import com.fop.framework.source.pub.ProcessDataType;
import com.fop.framework.source.pub.SourceType;

/**
 * 描述:基本数据模型接口
 * 
 * @version 1.00
 * @author syl
 * 
 */
public interface BaseInDataModel {
  /**
   * 根据过程数据类型得到数据取值
   * @param processDataType 过程处理数据类型标识
   * @return 返回对应过程数据对象
   */
  public Object getData(ProcessDataType processDataType);
  /**
   * 获取资源类型
   * @return 返回资源类型
   */
  public SourceType getSourceType();
  
}

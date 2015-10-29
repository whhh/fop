/**
 * 文件名		：FileResultManager.java
 * 创建日期	：2013-9-27
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.manager;

import java.util.List;

import com.fop.framework.data.FieldData;
import com.fop.framework.data.GroupData;
import com.fop.framework.source.BaseManager;

/**
 * 文件访问返回结果数据处理
 * 
 * @version 1.00
 * @author syl
 * 
 */
public interface FileResultManager extends BaseManager{

  /**
   * 结果数据转换:返回数据本身
   * @param exchangeRes 待处理的数据
   * @return 返回处理后的数据
   */
  Object getNoConvert(Object exchangeRes);

  /**
   * 结果数据转换:返回FieldData
   * @param exchangeRes
   * @return 返回处理后的FieldData数据
   */
  FieldData getFieldData(Object exchangeRes);

  /**
   * 结果数据转换:返回GroupData
   * @param exchangeRes
   * @return 返回处理后的GroupData数据
   */
  GroupData getGroupData(List<String> exchangeRes);
  
}

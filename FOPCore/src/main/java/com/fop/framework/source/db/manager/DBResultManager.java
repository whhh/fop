/**
 * 文件名		：DBResultManager.java
 * 创建日期	：2013-9-27
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.manager;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.fop.framework.data.FieldData;
import com.fop.framework.data.GroupData;
import com.fop.framework.data.RepeatData;
import com.fop.framework.source.BaseManager;

/**
 * 对返回结果的处理
 * 返回结果包括：操作是否成功、异常信息、结果列表、结果Map
 * @version 1.00
 * @author syl
 * 
 */
public interface DBResultManager extends BaseManager{
  /**
   * 返回数据本身
   * @param data 待处理的数据
   * @return 返回处理的结果数据
   */
  public Object getNoConvert(Object srcData);
  /**
   * 返回对应数据的FieldData格式数据
   * @param srcData 原始数据
   * @return 返回处理的结果数据
   */
  public FieldData getFileData(LinkedList<LinkedHashMap<String,Object>> srcData);
  /**
   * 返回对应数据的GroupData格式数据
   * @param srcData 原始数据
   * @return 返回处理的结果数据
   */
  public GroupData getGroupData(LinkedList<LinkedHashMap<String,Object>> srcData);
  /**
   * 返回对应数据的RepeatData格式数据
   * @param srcData 原始数据
   * @return 返回处理的结果数据
   */
  public RepeatData getRepeatData(LinkedList<LinkedHashMap<String,Object>> srcData);
}

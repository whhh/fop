/**
 * 文件名		：DBEnumResTypes.java
 * 创建日期	：2013-10-11
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.data;

/**
 * 描述:数据库结果返回数据可选类型
 * 
 * @version 1.00
 * @author syl
 * 
 */
public enum DBResEnumTypes {
  /**直接返回数据，不作类型转换*/
  NoConvert,
  /**单个基本元素数据,类似于 value*/
  FieldData,
  /**多个基本元素的数据,类似于  Map*/
  GroupData,
  /**多组数据,类似于  List<Map>*/
  RepeatData
}

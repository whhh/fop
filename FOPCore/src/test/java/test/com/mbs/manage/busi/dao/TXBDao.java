/**
 * 文件名		：TXBDao.java
 * 创建日期	：2013-10-25
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package test.com.mbs.manage.busi.dao;

import java.util.Map;

/**
 * 描述:表B记录更改
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface TXBDao {

  /**String rmflag, String rmid*/
  void updateB(Map map);
  
  String queryB(String rmid);
}

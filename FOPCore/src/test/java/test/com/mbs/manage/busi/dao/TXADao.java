/**
 * 文件名		：TXADao.java
 * 创建日期	：2013-10-25
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package test.com.mbs.manage.busi.dao;

import java.util.Map;

/**
 * 描述:表A记录更改
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface TXADao {
 
   /**String rmflag, String rmid*/
   void updateA(Map map);
   
   String queryA(String rmid);
   
}

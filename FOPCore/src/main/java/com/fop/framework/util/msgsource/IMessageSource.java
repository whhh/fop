/**
 * 文件名		：IMessageSource.java
 * 创建日期	：2013-9-22
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.util.msgsource;

/**
 * 描述:国际化处理接口
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface IMessageSource {

  /**
   * 获取资源文件信息
   * @param msgkey the key
   * @param defaultmsg 默认消息
   * @return
   */
  public String getMessage(String msgkey, String defaultmsg);
  
  /**
   * 获取资源文件信息
   * @param msgkey the key
   * @return
   */
  public String getMessage(String msgkey);
  
}

/**
 * 文件名		：MsgSourceUtil.java
 * 创建日期	：2013-9-22
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.util;

import com.fop.framework.util.msgsource.IMessageSource;
import com.fop.framework.util.msgsource.IMessageSourceImpl;

/**
 * 描述:国际化处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class MsgSourceUtil {
  
  /**国际化处理对象*/
  private static IMessageSource msgsrc = new IMessageSourceImpl();

  /**
   * 获取资源信息
   * @param msgkey 资源定义key
   * @param defaultmsg 默认消息
   * @return 处理后的信息
   */
  public static String getMessage(String msgkey, String defaultmsg){
    return msgsrc.getMessage(msgkey, defaultmsg);
  }
  
  /**
   * 获取资源信息
   * @param msgkey 资源定义key
   * @return 处理后的信息
   */
  public static String getMessage(String msgkey){
    return getMessage(msgkey, null);
  }
}

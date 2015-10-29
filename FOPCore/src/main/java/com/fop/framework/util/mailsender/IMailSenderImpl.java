/**
 * 文件名		：IMailSenderImpl.java
 * 创建日期	：2013-9-22
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.util.mailsender;

import java.util.List;
import java.util.Map;

/**
 * 描述:邮件发送接口实现
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class IMailSenderImpl implements IMailSender{

  /* (non-Javadoc)
   * @see com.fop.framework.util.mailsender.IMailSender#send(java.lang.String, java.lang.String, java.lang.String, java.util.List)
   */
  @Override
  public boolean send(String subject, String content, String toAddress,
      List<Map<String, Object>> listfj) {
    // TODO Auto-generated method stub
    return false;
  }

}

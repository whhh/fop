/**
 * 文件名		：IMailSender.java
 * 创建日期	：2013-9-22
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.util.mailsender;

import java.util.List;
import java.util.Map;

/**
 * 描述:邮件发送接口
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface IMailSender {

  /**
   * 邮件发送
   * @param subject 邮件主题 
   * @param content 邮件内容
   * @param toAddress 邮件接收者的地址
   * @param listfj 附件，List附件列表<Map<String附件显示名称，Object附件内容>>
   * @return true.发送成功 false.发送失败
   */
  public boolean send(String subject, String content, String toAddress, List<Map<String, Object>> listfj);
  
}

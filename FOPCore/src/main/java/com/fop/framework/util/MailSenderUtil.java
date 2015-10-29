/**
 * 文件名		：MailSenderUtil.java
 * 创建日期	：2013-9-22
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.util;

import java.util.List;
import java.util.Map;

import com.fop.framework.util.mailsender.IMailSender;
import com.fop.framework.util.mailsender.IMailSenderImpl;

/**
 * 描述:邮件发送
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class MailSenderUtil {

  /**
   * 邮件发送--带附件
   * @param subject 邮件主题 
   * @param content 邮件内容
   * @param toAddress 邮件接收者的地址
   * @param listfj 附件，List附件列表<Map<String附件显示名称，Object附件内容>>
   * @return true.发送成功 false.发送失败
   */
  public static boolean send(String subject, String content, String toAddress, List<Map<String, Object>> listfj){
    IMailSender mailsender = new IMailSenderImpl();
    return mailsender.send(subject, content, toAddress, listfj);
  }
  
  /**
   * 邮件发送--不带附件
   * @param subject 邮件主题 
   * @param content 邮件内容
   * @param toAddress 邮件接收者的地址
   * @return true.发送成功 false.发送失败
   */
  public static boolean send(String subject, String content, String toAddress){
    IMailSender mailsender = new IMailSenderImpl();
    return mailsender.send(subject, content, toAddress, null);
  }
}

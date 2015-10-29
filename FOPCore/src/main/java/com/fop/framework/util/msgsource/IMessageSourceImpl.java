/**
 * 文件名		：IMessageSourceImpl.java
 * 创建日期	：2013-9-22
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.util.msgsource;

import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.context.CacheDataUtil;
import com.fop.framework.util.StringUtil;

/**
 * 描述:国际化处理接口实现
 * 取值优先级：从i18n文件中取--从数据库中取--取所传默认值
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class IMessageSourceImpl implements IMessageSource{

  /**消息资源对象*/
  private static MessageSource messageSource;
  /**日志对象*/
  private static Log logger = LogFactory.getLog(IMessageSourceImpl.class);

  /* (non-Javadoc)
   * @see com.fop.framework.util.msgsource.IMessageSource#getMessage(java.lang.String, java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public String getMessage(String msgkey, String defaultmsg) {
    if (messageSource == null){
      //取得消息资源对象
      messageSource = new ClassPathXmlApplicationContext("message-source.xml");
      logger.debug("----messageSource init ok");
    }
    if (StringUtil.isStrEmpty(msgkey)){
      logger.debug("----the msgkey is empty");
      return "";
    }
    //取资源文件值
    String strmsg = messageSource.getMessage(msgkey, new Object[1], null, Locale.CHINESE);
    if (StringUtil.isStrEmpty(strmsg)){ //如果为空,取数据库配置值
      //取得缓存信息
      Object listerrorinfo = CacheDataUtil.getCacheTableValue(FOPConstants.CACHE_VALUE_ERRORINFO);
      if (listerrorinfo != null){
        List<IDBErrorMessage> listError = (List<IDBErrorMessage>)listerrorinfo;
        strmsg = getDBMessage(listError,  msgkey); //获取数据库对应的错误信息
      }
    }
    if (StringUtil.isStrEmpty(strmsg)){ //如果为空,取默认值
      strmsg = defaultmsg;
    }
    return strmsg;
  }

  /* (non-Javadoc)
   * @see com.fop.framework.util.msgsource.IMessageSource#getMessage(java.lang.String)
   */
  @Override
  public String getMessage(String msgkey) {
    return getMessage(msgkey, null);
  }

  /**
   * 返回错误码对象的错误信息
   * @param listError 错误信息集合
   * @param msgkey 错误码
   * @return
   */
  private String getDBMessage(List<IDBErrorMessage> listError, String errcode){
    String dbemgs = null; //错误信息
    if (listError == null){
      return null; 
    }
    int lsize = listError.size(); //集合大小 
    IDBErrorMessage dberror = null; //错误消息对象
    for (int i = 0; i < lsize; i++){ //遍历集合处理
      dberror = listError.get(i);
      //如果（错误码和语言值）不匹配，继续下一个 
      if (!(errcode.equals(dberror.getErrcode()) && "zh_CN".equalsIgnoreCase(dberror.getSlanguage()))){ 
        continue;
      }
      if (IDBErrorMessage.MSG_FLAG_TRUE.equals(dberror.getMsgflag())){
        dbemgs = dberror.getOtherErrmsg(); //转义取值
      } else {
        dbemgs = dberror.getErrmsg(); //非转义取值
      }
      break; //找到一次，终止遍历处理
    }
    return dbemgs;
  }

}

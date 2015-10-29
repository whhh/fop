/**
 * 文件名		：TradeFormatDataProcess.java
 * 创建日期	：2013-10-24
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.context;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fop.framework.service.template.NomalServiceTemplate;
import com.fop.framework.service.template.format.TemplateFormat;
import com.fop.framework.service.trade.ServiceConfig;

/**
 * 描述:交易数据format处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class TradeFormatDataProcess implements FOPDataProcess{

  /**交易配置文件**/
  private String tradelistfile;

  /**默认交易配置文件**/
  private final String DEFAUL_TRADE_FILE = "trade/tradelist.xml";

  /**模板解析器**/
  private TemplateFormat templateFormat;

  /* (non-Javadoc)
   * @see com.fop.framework.context.FOPDataProcess#init()
   */
  @SuppressWarnings("rawtypes")
  @Override
  public void init() {
    if (tradelistfile == null){
      tradelistfile = DEFAUL_TRADE_FILE;
    }
    //取得所有交易定义集合
    ApplicationContext appcontext = new ClassPathXmlApplicationContext(tradelistfile);
    //选出NomalServiceTemplate定义集合
    Map<String, NomalServiceTemplate> nomaltlbeans = appcontext.getBeansOfType(NomalServiceTemplate.class);
    //遍历处理
    Iterator it = nomaltlbeans.entrySet().iterator();
    try {
      while(it.hasNext()){
        Entry entry = (Entry)it.next();
        //取到beanid
        String key = String.valueOf(entry.getKey());  
        Object templateConfig = appcontext.getBean(key);
        //解析流程配置
        ServiceConfig serviceConfig = templateFormat.format(templateConfig);
        //将解析后的配置对象添加到容器
        ServiceBeanFactoryUtil.addBean(key, serviceConfig);
        System.out.println("*****init trade: " + key + "...");
      }
      System.out.println("******************init trade ok.");
    } catch (Exception e) {
      System.out.println("******************init trade failed, " + e.getMessage());
      System.out.println(e);
    }
  }

  /* (non-Javadoc)
   * @see com.fop.framework.context.FOPDataProcess#destroy()
   */
  @SuppressWarnings("rawtypes")
  @Override
  public void destroy() {
//    Map<String, ServiceConfig> servicebeans = ServiceBeanFactoryUtil.getAllBeans();
    Map<String, List<ServiceConfig>> servicebeans = ServiceBeanFactoryUtil.getAllBeans();
    //遍历处理
    Iterator it = servicebeans.entrySet().iterator();
    try {
      while(it.hasNext()){
        Entry entry = (Entry)it.next();
        //取到beanid
        String key = String.valueOf(entry.getKey());
        //System.out.println("*****key====" + key);
        //移除bean
        servicebeans.remove(key);
      }
      System.out.println("******************destroy trade ok.");
    } catch (Exception e) {
      System.out.println("******************destroy trade failed, " + e.getMessage());
      System.out.println(e);
    }
  }

  /**
   * @param tradelistfile the tradelistfile to set
   */
  public void setTradelistfile(String tradelistfile) {
    this.tradelistfile = tradelistfile;
  }

  /**
   * @param templateFormat the templateFormat to set
   */
  public void setTemplateFormat(TemplateFormat templateFormat) {
    this.templateFormat = templateFormat;
  }

}

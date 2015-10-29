/**
 * 文件名		：FOPConstants.java
 * 创建日期	：2013-9-17
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.constant;


/**
 * 描述:框架常量类
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
  
public class FOPConstants {
  /**验证结果对象名称*/
  public static final String VALIDATE_RESULT = "_validateResult";
  
  /**流程配置各个子项描述*/
  public static final String TEMPLATE_NODE_DESC = "description";
  
  /**交易流程视图类型*/
  public static final String TRADE_VIEW_TYPE = "viewtype";
  
  /**交易流程视图类型值:jsp*/
  public static final String TRADE_VIEW_TYPE_JSP = "jsp";
  
  /**交易流程视图类型值:json*/
  public static final String TRADE_VIEW_TYPE_JSON = "json";
  
  /**HTPP渠道接入层--视图名称:jsonView*/
  public static final String TRADE_VIEW_JSONVIEWNAME = "jsonView";
  
  /**交易流程视图值*/
  public static final String TRADE_VIEW_VALUE = "viewobj";
  
  /**交易流程视图结果*/
  public static final String TRADE_VIEW_RESULT = "viewresult";
  
  /**交易流程默认视图名称*/
  public static final String TRADE_VIEW_DEFAULT = "DefaultView";
  
  /**交易请求码名称*/
  public static final String TRADE_ID = "_tradeId";
  
  /**交易配置数据*/
  public static final String SERVICE_CONFIG_DATA = "_serviceConfig";
  
  /**节点交易返回变量名称*/
  public static final String DOT_RESULT_RET = "ret";
  
  /**流程配置入参定义*/
  public static final String TEMPLATE_INPUT = "inparameter";
  
  /**流程配置出参定义*/
  public static final String TEMPLATE_OUTPUT = "outparameter";
  
  /**流程配置扩展信息定义*/
  public static final String TEMPLATE_EXTINFO = "extinfo";
  
  /**HTTP渠道接入层--数据转换--bean定义*/
  public static final String HTTPDATACONVERTER = "httpDataConverter";
  
  /**控制处理层--处理入口--bean定义*/
  public static final String CONTROLPROCESS = "controlProcess";
  
  /**服务装配层--处理入口--bean定义*/
  public static final String SERVICEPROCESS = "serviceProcess";
  
  /**控制处理层--Service出、入参处理--bean定义*/
  public static final String CTRLSERVICEPARAM = "ctrlServiceParam";
  
  /**控制处理层--制策略处理链--bean定义*/
  public static final String CONTROLHANDLERCHAIN = "controlHandlerChain";
  
  /**控制处理层--记录日志--bean定义*/
  public static final String RECORDLOG = "recordLog";
  
  /**控制处理层--视图响应处理--bean定义*/
  public static final String CONTROLVIEWRESOLVER = "controlViewResolver";
  
  /**业务流程的request数据存储定义*/
  public static final String CONTEXT_REQUEST = "requestContext";
  
  /**业务流程的session数据存储定义*/
  public static final String CONTEXT_SESSION = "sessionContext";
  
  /**业务流程的service数据存储定义*/
  public static final String CONTEXT_SERVICE = "serviceContext";
  
  /**业务流程的dot数据存储定义*/
  public static final String CONTEXT_DOT = "dotContext";
  
  /**流程配置--解析--入参*/
  public static final String TEMPLATE_FORMAT_INPARAM = "inParameterFormat";
  
  /**流程配置--解析--出参*/
  public static final String TEMPLATE_FORMAT_OUTPARAM = "outParameterFormat";
  
  /**流程配置--解析--基本信息*/
  public static final String TEMPLATE_FORMAT_BASICINFO = "basicInfoFormat";
  
  /**流程配置--解析--输入检查*/
  public static final String TEMPLATE_FORMAT_CHECKCHAIN = "checkChainFormat";
  
  /**流程配置--解析--节点*/
  public static final String TEMPLATE_FORMAT_DOTCHAIN = "dotChainFormat";
  
  /**流程配置--解析--视图定义*/
  public static final String TEMPLATE_FORMAT_VIEWRESULT = "viewResultFormat";
  
  /**数据类型--FieldData*/
  public static final String DATA_TYPE_FIELDDATA = "FieldData";
  
  /**数据类型--GroupData*/
  public static final String DATA_TYPE_GROUPDATA = "GroupData";
  
  /**数据类型--RepeatData*/
  public static final String DATA_TYPE_REPEATDATA = "RepeatData";

  /**服务装配层--输入要素检查--bean定义*/
  public static final String SERVICECHECKCHAIN = "serviceCheckChain";
  
  /**服务装配层--流程执行--bean定义*/
  public static final String SERVICEDOTCHAIN = "serviceDotChain";
  
  /**服务装配层--Dot出、入参处理--bean定义*/
  public static final String SERVICEDOTPARAMETER = "serviceDotParameter";
  
  /**模板解析关系配置--bean定义*/
  public static final String TEMPLATEFORMATCONFIG = "templateFormatConfig";
  
  /**子流程标识*/
  public static final String DOT_SUBTRADE_FLAG = "service:";
  
  /**会话数据标识*/
  public static final String CONTEXT_SESSION_PREFLAG = "s:";
  
  /**HTTP头信息定义*/
  public static final String REQUEST_HEADS = "_heads";
  
  /**交易流程视图key*/
  public static final String TRADE_VIEW_KEY = "viewkey";
  
  /**交易流程走向: next*/
  public static final String DOT_TO_NEXT = "next";
  
  /**交易流程走向: end*/
  public static final String DOT_TO_END = "end";
  
  /**交易流程走向: view*/
  public static final String DOT_TO_VIEW = "viewname";
  
  /**交易状态**/
  public static final String STATUS = "status";
  
  /**交易成功码**/
  public static final String STATUS_SECCESS = "0000";
  
  /**错误信息**/
  public static final String ERROR_MESSAGE = "error_message";
  
  /**客户标识 **/
  public static final String ID_NUM = "ID_Num";
  
  /**交易异常信息对象 **/
  public static final String TRADE_EXCEPTION = "_exception";
  
  /**加载模式：eager */
  public static final String CACHE_MODE_EAGER = "eager";
  
  /**加载模式： lazy */
  public static final String CACHE_MODE_LAZY = "lazy";
  
  /**缓存变量名称,见cachetable.xml配置 */
  public static final String CACHE_VALUE_ERRORINFO = "errorinfo";
  
}

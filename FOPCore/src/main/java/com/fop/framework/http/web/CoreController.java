/**
 * 文件名    ：CoreController.java
 * 创建日期 ：2013-9-10
 * Copyright (c) 2003-2013 北京联龙博通
 *
 * All rights reserved.
 * 
 * 修改记录：
 * 1.修改时间：2013-11-13
 *   修改人：xieyg
 *   生成版本：1.01
 *   修改内容：把ecobj.getMessage()改为ecobj.getErrMessage()
 */
package com.fop.framework.http.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.context.FOPContext;
import com.fop.framework.control.ControlProcess;
import com.fop.framework.exception.FOPConvertException;
import com.fop.framework.exception.FOPException;
import com.fop.framework.http.data.DataConverter;

/**
 * 描述:HTTP渠道接入处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
@Controller
public class CoreController{

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(CoreController.class);

  /**渠道数据转换对象**/
  @Autowired
  private DataConverter httpDataConverter;

  /**控制处理层对象**/
  @Autowired
  private ControlProcess controlProcess;
  
//  private Object lock = new Object();

  @RequestMapping(value = "/*")
  public Object coreProcess(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
  throws FOPException, FOPConvertException{
    logger.debug("----framework: trade begin");
    //-------1.判断是否取得交易请求码--------------------------------
//    String tradeid = request.getParameter(FOPConstants.TRADE_ID);//交易流程开始点
    
    //ADD BY LIHUI 2013-11-27 从URL中截取tradeid
    String tradeid = null;  //交易流程开始点
    String url = request.getRequestURL().toString();
    logger.info("url:" + url);
    int index = url.indexOf("?");
    if (index != -1) {
      tradeid = url.substring(0, index);
    } else {
      tradeid = url;
    }
    index = tradeid.lastIndexOf("/");
    tradeid = tradeid.substring(index + 1);
    tradeid = tradeid.substring(0, tradeid.lastIndexOf(".html"));
    /*try {
      ServiceBeanFactoryUtil.getBean(tradeid);
    } catch (Exception e) {
      logger.warn("----framework: 未上送交易请求码");
      throw new FOPException("FOP000200", "未上送交易请求码");
    }*/
    //ADD BY LIHUI 2013-11-27
    
//    logger.info("----framework: tradeid=" + tradeid);
//    if (StringUtil.isStrEmpty(tradeid)){
//      logger.warn("----framework: 未上送交易请求码");
//      throw new FOPException("FOP000200", "未上送交易请求码");
//    }
    
    //-------2.定义requestContext上下文对象，作用域：单次request请求--------
    //定义requestContext上下文对象
    FOPContext requestContext = new FOPContext(FOPConstants.CONTEXT_REQUEST);
    //定义sessionContext上下文对象，存放HttpSession数据
    FOPContext sessionContext = new FOPContext(FOPConstants.CONTEXT_SESSION);
    //sessionContext做为requestContext的子节点
    requestContext.addChildContext(sessionContext);
    //ADD BY LIHUI 2013-11-27 将URL截取的tradeid写入context中, 覆盖掉请求参数中的tradeid
    requestContext.addFieldData(FOPConstants.TRADE_ID, tradeid);
    //ADD BY LIHUI 2013-11-27

    //-------3.执行数据转换（转入）--------------------------------
    httpDataConverter.inContext(request, requestContext);
    requestContext.addFieldData("_request", request);
    requestContext.addFieldData("_response", response);
    
    //-------4.调用控制处理层--------------------------------
    FOPException ecobj = null;
    String viewtype = null;
    try {
      controlProcess.ctrlProcess(requestContext);
    } catch (FOPException e) {
      logger.error("----framework: tradeid=" + tradeid + ", error1:" + e.getMessage(), e);
      ecobj = e;
    } catch (Exception e) {
      logger.error("----framework: tradeid=" + tradeid + ", error2:" + e.getMessage(), e);
      ecobj = new FOPException("FOP000201", "调用控制处理层出错");
    } 
    
    //-------5.执行数据转换（转出）--------------------------------
    try {
      httpDataConverter.outContext(request, response, requestContext);
    } catch (FOPConvertException e2) {
      logger.error("----framework: tradeid=" + tradeid + ", error3:" + e2.getMessage(), e2);
      ecobj = new FOPException("FOP000205", "响应数据转换出错");
    }
    //视图类型
    viewtype = (String)requestContext.getFieldDataValue(FOPConstants.TRADE_VIEW_TYPE);
    //转换完之后，再抛出异常，否则视图拿不到context的数据
    if ((ecobj != null) && (!FOPConstants.TRADE_VIEW_TYPE_JSON.equals(viewtype))){
      throw ecobj;
    }

    //-------6.处理返回结果和视图转向--------------------------------
    ModelAndView mv = new ModelAndView(); //视图模型对象
    Object viewvalue = null; //视图值
    Object viewresult = null; //视图结果
    try {
      viewresult = requestContext.getFieldDataValue(FOPConstants.TRADE_VIEW_RESULT);
      if (viewresult != null){
        //不为null的情况直接取结果
        mv = (ModelAndView)viewresult;
      } else {
        //根据类型处理
        viewvalue = requestContext.getFieldDataValue(FOPConstants.TRADE_VIEW_VALUE);
        if (FOPConstants.TRADE_VIEW_TYPE_JSP.equals(viewtype)){
          //jsp，直接设置jsp名称
          mv.setViewName(String.valueOf(viewvalue));
        } else if (FOPConstants.TRADE_VIEW_TYPE_JSON.equals(viewtype)){
          if(viewvalue == null){//json返回数据为空时，设置空json
            viewvalue = new HashMap<String, String>();
          }
          //json，将viewvalue数据填充到模型，待转换为json格式
          Object viewkey = requestContext.getFieldDataValue(FOPConstants.TRADE_VIEW_KEY);
          String sviewkey = String.valueOf(viewkey);
          if (ecobj != null){
            //处理出错情况
            modelMap.addAttribute(FOPConstants.STATUS, ecobj.getErrCode());
            modelMap.addAttribute(FOPConstants.ERROR_MESSAGE, ecobj.getErrMessage());
            modelMap.addAttribute(sviewkey, viewvalue);
          } else {
            //处理成功情况
            modelMap.addAttribute(FOPConstants.STATUS, FOPConstants.STATUS_SECCESS);
            modelMap.addAttribute(FOPConstants.ERROR_MESSAGE, "");
            modelMap.addAttribute(sviewkey, viewvalue);
          }
          mv.addAllObjects(modelMap);
          //设置指定的view名称
          mv.setViewName(FOPConstants.TRADE_VIEW_JSONVIEWNAME);
        } else {
          //其它视图
          mv.setViewName(String.valueOf(viewvalue));
        }
      }
    } catch (Exception e) {
      logger.error("-----framework: tradeid=" + tradeid + ", error3" + e.getMessage(), e);
      throw new FOPException("FOP000202", "视图模型处理出错");
    }

    logger.debug("----framework: trade end");
    return mv;
  }
}

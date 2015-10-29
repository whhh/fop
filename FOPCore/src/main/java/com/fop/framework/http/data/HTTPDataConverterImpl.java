/**
 * 文件名    ：HTTPDataConverterImpl.java
 * 创建日期 ：2013-9-10
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.http.data;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.context.FOPContext;
import com.fop.framework.data.FieldData;
import com.fop.framework.data.GroupData;
import com.fop.framework.exception.FOPConvertException;
import com.fop.framework.http.encrypt.CryptoUtil;
import com.fop.framework.util.StringUtil;

/**
 * 描述: HTTP渠道数据上下文转换
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class HTTPDataConverterImpl implements DataConverter{

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(HTTPDataConverterImpl.class);

  /**
   * 将输入的请求参数，转换成context形式数据
   * @param request
   * @param requestContext
   * @return
   */
  @SuppressWarnings("rawtypes")
  @Override
  public Object inContext(HttpServletRequest request, FOPContext requestContext)
  throws FOPConvertException {
    try {
      /**
       * 获取json是否为json数据
       */
      String jsonData=request.getParameter("json");
      if(jsonData != null){
        parseJsonToContext(request, requestContext);
      } else {
        parseParameterToContext(request, requestContext);
      }
      //-------session数据转入--------------------------------
      HttpSession session = request.getSession(false);
      if (session != null){
        //取得sessionContext
        FOPContext sessionContext = requestContext.getChildContext(FOPConstants.CONTEXT_SESSION);
        Enumeration ern = session.getAttributeNames();
        String sessionkey = null; //参数名称
        Object sessionValue = null; //参数值
        for (;ern.hasMoreElements();){
          sessionkey = (String)ern.nextElement(); 
          sessionValue = session.getAttribute(sessionkey); 
          sessionContext.addFieldData(sessionkey, sessionValue);
          //logger.debug("----framework: inContext session:" + sessionkey + "=" + sessionValue);   
        }
      }
    } catch (Exception e) {
      logger.error("----framework: error1:" + e.getMessage(), e);
      throw new FOPConvertException("FOP000203", "请求数据转换出错");
    }
    return requestContext;
  }
  
  /**
   * 
   * @param context
   * @return
   */
  @SuppressWarnings("rawtypes")
  private Object parseParameterToContext(HttpServletRequest request, FOPContext context) {
    //-------1.Parameter数据转入--------------------------------
    //获取上传参数集合
    Map parameterMap = request.getParameterMap();
    String pname = ""; //参数名称
    String pvalue = ""; //参数值
    for(Object mobj: parameterMap.keySet()){
      pname = String.valueOf(mobj);
      pvalue = String.valueOf(request.getParameter(pname));
      context.addFieldData(pname, pvalue);
      //logger.debug("----framework: inContext parameter:" + pname + "=" + pvalue);   
    }
    //-------2.heads数据处理--------------------------------
    Enumeration rheads = request.getHeaderNames();
    GroupData headsgroup = new GroupData(FOPConstants.REQUEST_HEADS);
    String hkey = null; //参数名称
    Object hValue = null; //参数值
    for (;rheads.hasMoreElements();){
      hkey = (String)rheads.nextElement(); 
      hValue = request.getHeader(hkey); 
      headsgroup.addFieldData(hkey, hValue);
      //logger.debug("----framework: inContext heads:" + hkey + "=" + hValue);   
    }
    String clientIp = null;
    clientIp = request.getHeader("x-forwarded-for");
    if(StringUtil.isStrEmpty(clientIp)) {
      clientIp = request.getRemoteAddr();
    }
    //获取客户端IP
    headsgroup.addFieldData("clientIp", clientIp);
    //获取服务器IP
    headsgroup.addFieldData("serverIp",request.getLocalAddr());
    context.addFOPData(headsgroup);
    return context;
  }
  
  /**
   * 解析Json参数到上下文
   * @param jsonStr json数据
   * @param context 数据上下文
   * @return 数据上下文
   */
  private Object parseJsonToContext(HttpServletRequest request, FOPContext context) {
    String jsonStr = request.getParameter("json");
    //jsonStr = CryptoUtil.decrypt(jsonStr);  
    // 最外层解析
    JSONObject json = JSONObject.fromObject(jsonStr);
    //-------1.Parameter数据转入--------------------------------
    //获取params字符串,add by panyk on 20150123
    //String paramsStr = json.getString("params");
    Object paramsStr = json.get("params");
    //只针对接收到的paramsStr进行解密,add  by panyk on 20150123
    if(paramsStr != null && !"".equals(paramsStr) && !"null".equals(paramsStr)){
      jsonStr = CryptoUtil.decrypt(String.valueOf(paramsStr),context);
    }
    //解密后再把解密后的串转换成json对象,add  by panyk on 20150123
    JSONObject params = JSONObject.fromObject(jsonStr);
    //按json方式解析params对象
    for(Object param : params.keySet()){
      String pname = String.valueOf(param);
      Object pvalue = params.get(pname);
      context.addFieldData(pname, pvalue);
      //logger.debug("----framework: inContext parameter:" + pname + "=" + pvalue);   
    }
    //-------2.heads数据处理--------------------------------
    JSONObject headers = json.getJSONObject("header");
    GroupData headsgroup = new GroupData(FOPConstants.REQUEST_HEADS);
    for (Object header : headers.keySet()){
      String hkey = String.valueOf(header); 
      Object hValue = headers.get(hkey);
      headsgroup.addFieldData(hkey, hValue);
      //logger.debug("----framework: inContext heads:" + hkey + "=" + hValue);   
    }
    String clientIp = null;
    clientIp = request.getHeader("x-forwarded-for");
    if(StringUtil.isStrEmpty(clientIp)) {
      clientIp = request.getRemoteAddr();
    }
    //获取客户端IP
    headsgroup.addFieldData("clientIp", clientIp);
    //获取服务器IP
    headsgroup.addFieldData("serverIp",request.getLocalAddr());
    context.addFOPData(headsgroup);
    
    return context;
  }


  /**
   * 将context形式数据，转换成响应所需格式参数
   * @param request
   * @param response
   * @param requestContext
   * @return
   */
  @Override
  public Object outContext(HttpServletRequest request, HttpServletResponse response, FOPContext requestContext)
  throws FOPConvertException {
    try {
      //-------1.Parameter数据转入--------------------------------
      GroupData ctxmap = (GroupData)requestContext.getDataElement();
      String pname = "";//参数名称
      Object pobj = null;//参数值
      Object pvalue = null;//参数值，在pobj基础上获取
      for(Object mobj: ctxmap.keySet()){
        pname = String.valueOf(mobj);
        pobj = requestContext.get(pname);
        if (pobj instanceof FieldData) {  //对于FieldData类型的数据处理
          pvalue = ((FieldData)pobj).getValue();
        }
        request.setAttribute(pname, pvalue);
        //logger.debug("----framework: outContext attribute: " + pname + "=" + pvalue);   
      }

      //-------2.加入httpheads数据--------------
      //需要有针对性的设置信息，遍历设置会造成页面无法接收json数据

      //-------3.session数据转入--------------------------------
      FOPContext sessionContext = requestContext.getChildContext(FOPConstants.CONTEXT_SESSION);
      GroupData gdata = sessionContext.getDataElement();
      pname = "";//参数名称
      pobj = null;//参数值
      pvalue = null;//参数值，在pobj基础上获取
      HttpSession httpsession = request.getSession();
      for(Object mobj: gdata.keySet()){
        pname = String.valueOf(mobj);
        //判断s:开头的变量名
//        if (pname.indexOf(FOPConstants.CONTEXT_SESSION_PREFLAG) == 0){
          pobj = sessionContext.get(pname);
          if (pobj instanceof FieldData) {  //对于FieldData类型的数据处理
            pvalue = ((FieldData)pobj).getValue();
          }
          //去掉s:前缀再添加变量到httpsession add by 2013/10/29
//          pname = pname.substring(2, pname.length());

          httpsession.setAttribute(pname, pvalue); //设置数据
          //logger.debug("----framework: outContext session: " + pname + "=" + pvalue); 
//        } 
      }
    } catch (Exception e) {
      logger.error("----framework: error2:" + e.getMessage(), e);
      throw new FOPConvertException("FOP000204", "响应数据转换出错");
    }
    return request;
  }

}

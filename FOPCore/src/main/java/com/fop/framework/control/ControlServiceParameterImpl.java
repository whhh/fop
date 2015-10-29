/**
 * 文件名		：ControlServiceParameterImpl.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.control;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.context.FOPContext;
import com.fop.framework.data.FOPData;
import com.fop.framework.data.FieldData;
import com.fop.framework.data.GroupData;
import com.fop.framework.exception.FOPException;
import com.fop.framework.service.ServiceSessionDataConfig;
import com.fop.framework.service.trade.TradeInparameter;
import com.fop.framework.service.trade.TradeOutparameter;
import com.fop.framework.util.StringUtil;

/**
 * 描述:处理Service的输入、输出参数
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
@SuppressWarnings("rawtypes")
public class ControlServiceParameterImpl implements ControlServiceParameter{

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());

  @Autowired
  private ServiceSessionDataConfig serviceSessionDataConfig;

  /* (non-Javadoc)
   * @see com.fop.framework.control.ControlServiceParameter#inputProcess(com.fop.framework.context.FOPContext, java.util.List)
   */
  @Override
  public FOPContext inputProcess(FOPContext requestContext, List listinput)
  throws FOPException {
    FOPContext serviceContext = new FOPContext();
    if (listinput == null){
      return serviceContext;
    }
    try {
      //取得参数集合大小 
      int lpsize = listinput.size();
      for (int i = 0;  i < lpsize; i++){
        //取得单个参数
        TradeInparameter tparam = (TradeInparameter)listinput.get(i);
        String sname = tparam.getName();
        //获取原对象的值
//        Object svalue = requestContext.getFieldDataValue(sname);
        FOPData svalue = requestContext.getFOPData(sname);
        logger.debug("----framework: service tparam: sname=" + sname + ", svalue=" + svalue);
        //获取已设定的默认值
        String sdefaultvalue = tparam.getValue();
        logger.debug("----framework: service tparam: sdefaultvalue=" + sdefaultvalue);
        if (!StringUtil.isStrEmpty(sdefaultvalue)){
          //默认值不为空，已默认值为准
//          svalue = sdefaultvalue;
          svalue = new FieldData(sname, sdefaultvalue);
        }
        //设置FieldData
        String srealname = tparam.getRealname();
        logger.debug("----framework: service tparam: srealname=" + srealname);
//        serviceContext.addFieldData(srealname, svalue);
        if (svalue != null) {
          svalue.setName(srealname);
          serviceContext.addFOPData(svalue);
        }
      }
    } catch (Exception e) {
      logger.error("----framework: inputProcess error: " + e.getMessage(), e);
      throw new FOPException("FOP000402", "入参处理出错");
    }

    //--------------加入httpheads数据--------------
    GroupData httpheads = (GroupData)requestContext.get(FOPConstants.REQUEST_HEADS);
    serviceContext.addFOPData(httpheads);
    
    serviceContext.addFieldData("_request", requestContext.getFieldDataValue("_request"));
    serviceContext.addFieldData("_response", requestContext.getFieldDataValue("_response"));

    //--------------对于session数据，使用"s:变量名"形式将数据存入
    try{
      //系统会话值集合
      FOPContext sessionContext = requestContext.getChildContext(FOPConstants.CONTEXT_SESSION);
      //用户自定义会话值集合
      Map<String, String> sessionMap = serviceSessionDataConfig.getConfigs(); 
      //系统会话值集合
      GroupData sctxmap = (GroupData)sessionContext.getDataElement();
      String spvaluetype = ""; //参数类型
      Object spvalue = null; //会话参数值
      FOPData fdata = null;
      for(String pname: sessionMap.keySet()){ //遍历定义的会话数据集合
        spvaluetype = sessionMap.get(pname);
        logger.debug("----framework: inputProcess session:" + pname + "=" + spvaluetype);
        //取得源session变量
        spvalue = sctxmap.get(pname);
        if (spvalue == null){ //没有值的情况下，赋值为null
          serviceContext.addFieldData(FOPConstants.CONTEXT_SESSION_PREFLAG + pname, spvalue);
          continue;
        } else {
          spvalue = ((FieldData)spvalue).getValue();
        }
        //转换类型，不成功则取值为null
        spvalue = convertType(spvalue, spvaluetype);
        try {
          //取得值fdata，一般为FieldData类型
          fdata = sctxmap.getFOPData(pname);
          if (fdata != null){
            //存在的情况，重新设置
            serviceContext.setFieldDataValue(FOPConstants.CONTEXT_SESSION_PREFLAG + pname, spvalue);
          } else {
            //不存在的情况，添加一个
            serviceContext.addFieldData(FOPConstants.CONTEXT_SESSION_PREFLAG + pname, spvalue);
          }
        } catch (Exception e) {
          logger.warn("session data: " + pname + " not found, add new data," + e.getMessage());
          //不存在的情况，添加一个
          serviceContext.addFieldData(FOPConstants.CONTEXT_SESSION_PREFLAG + pname, spvalue);
        }
      }
    } catch (Exception e) {
      logger.error("----framework: inputProcess error: " + e.getMessage(), e);
      throw new FOPException("FOP000405", "会话数据入参处理出错");
    }
    return serviceContext;
  }

  /* (non-Javadoc)
   * @see com.fop.framework.control.ControlServiceParameter#outputProcess(com.fop.framework.context.FOPContext, com.fop.framework.context.FOPContext, java.util.List, java.util.List)
   */
  @Override
  public void outputProcess(FOPContext requestContext, FOPContext serviceContext, List listinput, List listoutput)
  throws FOPException {
    if (listoutput == null){
      return;
    }

    //--------------加入httpheads数据--------------
    GroupData httpheads = (GroupData)serviceContext.get(FOPConstants.REQUEST_HEADS);
    requestContext.addFOPData(httpheads);

    //对于session数据，使用"s:变量名"形式将数据存入
    //-----------赋值会话数据------------
    try {
      //取出(现存)值
//      GroupData gdata = serviceContext.getDataElement();
//      String pname = "";//参数名称
      Object pobj = null;//参数值
      Object pvalue = null;//参数值，在pobj基础上获取
      FOPContext sessionContext = requestContext.getChildContext(FOPConstants.CONTEXT_SESSION);
      //用户自定义会话值集合
      Map<String, String> sessionMap = serviceSessionDataConfig.getConfigs(); 
      for(String pname: sessionMap.keySet()) {
//      for(Object mobj: gdata.keySet()){
//        pname = String.valueOf(mobj);
        //判断s:开头的变量名
//        if (pname.indexOf(FOPConstants.CONTEXT_SESSION_PREFLAG) == 0){
          pobj = serviceContext.get(FOPConstants.CONTEXT_SESSION_PREFLAG + pname);
          if (pobj instanceof FieldData) {  //对于FieldData类型的数据处理
            pvalue = ((FieldData)pobj).getValue();
          }
          sessionContext.addFieldData(pname, pvalue);
          logger.debug("----framework: outputProcess session: " + pname + "=" + pvalue); 
//        }// end if
      } // end for
    } catch (Exception e) {
      logger.error("----framework: inputProcess error: " + e.getMessage(), e);
      throw new FOPException("FOP000404", "会话数据出参处理出错");
    }
    
    try {
      //取得参数集合大小 
      int lpsize = listoutput.size();
      for (int i = 0;  i < lpsize; i++){
        //取得单个参数
        TradeOutparameter tparam = (TradeOutparameter)listoutput.get(i);
        String sname = tparam.getName();
        //获取原对象的值
        FOPData svalue = serviceContext.getFOPData(sname);
//        Object svalue = serviceContext.getFieldDataValue(tparam.getName());
        logger.debug("----framework: service tparam: sname=" + sname + ", svalue=" + svalue);
        //获取已设定的默认值
        String sdefaultvalue = tparam.getValue();
        logger.debug("----framework: service tparam: sdefaultvalue=" + sdefaultvalue);
        if (!StringUtil.isStrEmpty(sdefaultvalue)){
          //默认值不为空，已默认值为准
//          svalue = sdefaultvalue;
          svalue = new FieldData(sname, sdefaultvalue);
        }
        //设置FieldData
        String srealname = tparam.getRealname();
        logger.debug("----framework: service tparam: srealname=" + srealname);
//        requestContext.addFieldData(srealname, svalue);
        if (svalue != null) {
          svalue.setName(srealname);
          requestContext.addFOPData(svalue);
        }
      }
      Object vresult = serviceContext.getFieldDataValue(FOPConstants.VALIDATE_RESULT);
      logger.debug("----framework: service param check result:" + vresult);
      requestContext.addFieldData(FOPConstants.VALIDATE_RESULT, vresult);
      //默认加入返回视图信息
      Object viewtype = serviceContext.getFieldDataValue(FOPConstants.TRADE_VIEW_TYPE);
      Object viewvalue = serviceContext.getFieldDataValue(FOPConstants.TRADE_VIEW_VALUE);
      Object viewkey = serviceContext.getFieldDataValue(FOPConstants.TRADE_VIEW_KEY);
      logger.debug("----framework: service tparam: viewtype=" + viewtype 
          + ",viewvalue=" + viewvalue
          + ",viewkey=" + viewkey);
      requestContext.addFieldData(FOPConstants.TRADE_VIEW_TYPE, viewtype);
      requestContext.addFieldData(FOPConstants.TRADE_VIEW_VALUE, viewvalue);
      requestContext.addFieldData(FOPConstants.TRADE_VIEW_KEY, viewkey);
    } catch (Exception e) {
      logger.error("----framework: inputProcess error: " + e.getMessage(), e);
      throw new FOPException("FOP000403", "出参处理出错");
    }
  }

  /**
   * 将obj转换为目标类型，如果不能正常转换，返回null
   * @param obj
   * @param targetType
   * @return
   */
  private Object convertType(Object obj, String targetType){
    if (obj == null){
      return obj;
    }
    //取得源类型
    String srcType = obj.getClass().getName();
    try {
      if(Class.forName(targetType).isAssignableFrom(obj.getClass())) {
        return obj;
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    if (!srcType.equals(targetType)){//比较类型
      return null;
    }
    return obj;
  }

}

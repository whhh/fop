/**
 * 文件名		：ServiceDotParameterImpl.java
 * 创建日期	：2013-10-12
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.context.FOPContext;
import com.fop.framework.data.FOPData;
import com.fop.framework.data.FieldData;
import com.fop.framework.data.GroupData;
import com.fop.framework.exception.FOPException;
import com.fop.framework.service.trade.TradeInparameter;
import com.fop.framework.service.trade.TradeOutparameter;
import com.fop.framework.util.StringUtil;

/**
 * 描述:Dot出、入参处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
@SuppressWarnings("rawtypes") 
public class ServiceDotParameterImpl implements ServiceDotParameter{

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());

  /* (non-Javadoc)
   * @see com.fop.framework.service.ServiceDotParameter#inputProcess(com.fop.framework.context.FOPContext, java.util.List)
   */
  @Override
  public FOPContext inputProcess(FOPContext serviceContext, List listinput)
  throws FOPException {
    //定义输出对象
    FOPContext dotContext = new FOPContext(FOPConstants.CONTEXT_DOT);
    //添加tradeid到每个context add by 2013/10/29
    Object tradeid = serviceContext.getFieldDataValue(FOPConstants.TRADE_ID);
    dotContext.addFieldData(FOPConstants.TRADE_ID, tradeid);
    try {
      //-----------赋值会话数据------------
      //取出(现存)值
      GroupData gdata = serviceContext.getDataElement();
      String pname = "";//参数名称
      Object pobj = null;//参数值
      Object pvalue = null;//参数值，在pobj基础上获取
      for(Object mobj: gdata.keySet()){
        pname = String.valueOf(mobj);
        //判断s:开头的变量名
        if (pname.indexOf(FOPConstants.CONTEXT_SESSION_PREFLAG) == 0){
          pobj = serviceContext.get(pname);
          if (pobj instanceof FieldData) {  //对于FieldData类型的数据处理
            pvalue = ((FieldData)pobj).getValue();
          }
          dotContext.addFieldData(pname, pvalue);
          logger.debug("----framework: inputProcess session: " + pname + "=" + pvalue); 
        } 
      } 
    } catch (Exception e) {
      logger.error("----framework: inputProcess error1: " + e.getMessage(), e);
      throw new FOPException("FOP000505", "会话入参处理出错");
    }

    //--------------加入httpheads数据--------------
    GroupData httpheads = (GroupData)serviceContext.get(FOPConstants.REQUEST_HEADS);
    dotContext.addFOPData(httpheads);
    
    dotContext.addFieldData("_request", serviceContext.getFieldDataValue("_request"));
    dotContext.addFieldData("_response", serviceContext.getFieldDataValue("_response"));

    if (listinput == null){
      return dotContext;
    }
    try {
      //取得参数集合大小 
      int lpsize = listinput.size();
      for (int i = 0;  i < lpsize; i++){
        //取得单个参数
        TradeInparameter tparam = (TradeInparameter)listinput.get(i);
        String sname = tparam.getName();
        //获取原对象的值
//        Object svalue = serviceContext.getFieldDataValue(sname);
        
        //ADD BY LIHUI 2013-11-27 修改入参只能为FieldData的BUG
        FOPData svalue = serviceContext.getFOPData(sname);
        //ADD BY LIHUI 2013-11-27
        
        logger.debug("----framework: dot tparam: sname=" + sname + ", svalue=" + svalue);
        //获取已设定的默认值
        String sdefaultvalue = tparam.getValue();
        logger.debug("----framework: dot tparam: sdefaultvalue=" + sdefaultvalue);
        if (!StringUtil.isStrEmpty(sdefaultvalue)){
          //默认值不为空，已默认值为准
//          svalue = sdefaultvalue;
          
        //ADD BY LIHUI 2013-11-27 修改入参只能为FieldData的BUG
          svalue = new FieldData(sname, sdefaultvalue);
        //ADD BY LIHUI 2013-11-27
        }
        //设置FieldData
        String srealname = tparam.getRealname();
        logger.debug("----framework: dot tparam: srealname=" + srealname);
//        dotContext.addFieldData(srealname, svalue);
        
        //ADD BY LIHUI 2013-11-27 修改入参只能为FieldData的BUG
        if (svalue != null) {
          svalue.setName(srealname);
          dotContext.addFOPData(svalue);
        }
        //ADD BY LIHUI 2013-11-27
      }
    } catch (Exception e) {
      logger.error("----framework:inputProcess error2: " + e.getMessage(), e);
      throw new FOPException("FOP000506", "入参处理出错");
    }
    //添加 dotContext
    serviceContext.addChildContext(dotContext);
    return dotContext;
  }


  @Override
  public void outputProcess(FOPContext serviceContext, FOPContext dotContext, List listinput, List listoutput)
  throws FOPException {
    //默认在listoutput加上retvalue
    if (listoutput == null){
      return;
    }
    
    //--------------加入httpheads数据--------------
    GroupData httpheads = (GroupData)dotContext.get(FOPConstants.REQUEST_HEADS);
    serviceContext.addFOPData(httpheads);

    try{
      //-----------赋值会话数据------------
      //取出(现存)值
      GroupData gdata = dotContext.getDataElement();
      String pname = "";//参数名称
//      Object pobj = null;//参数值
      FOPData pobj = null;//参数值
//      Object pvalue = null;//参数值，在pobj基础上获取
      for(Object mobj: gdata.keySet()){
        pname = String.valueOf(mobj);
        //判断s:开头的变量名
        if (pname.indexOf(FOPConstants.CONTEXT_SESSION_PREFLAG) == 0){
          pobj = dotContext.get(pname);
//          if (pobj instanceof FieldData) {  //对于FieldData类型的数据处理
//            pvalue = ((FieldData)pobj).getValue();
//          }
//          serviceContext.addFieldData(pname, pvalue);
          
          //ADD BY LIHUI 2013-11-27 修改出参只能为FieldData的BUG
          if (pobj != null) {
            serviceContext.addFOPData(pobj);
          }
          //ADD BY LIHUI 2013-11-27
          //logger.debug("----framework: outputProcess session: " + pname + "=" + pvalue); 
        } 
      }
    } catch (Exception e) {
      logger.error("----framework: outputProcess error2: " + e.getMessage(), e);
      throw new FOPException("FOP000508", "会话出参处理出错");
    }
    
    try {
      //取得参数集合大小 
      int lpsize = listoutput.size();
      for (int i = 0;  i < lpsize; i++){
        //取得单个参数
        TradeOutparameter tparam = (TradeOutparameter)listoutput.get(i);
        String sname = tparam.getName();
        //获取原对象的值
//        Object svalue = dotContext.getFieldDataValue(sname);
        
        //ADD BY LIHUI 2013-11-27 修改出参只能为FieldData的BUG
        FOPData svalue = dotContext.getFOPData(sname);
        //ADD BY LIHUI 2013-11-27
        
        //logger.debug("----framework: dot tparam: sname=" + sname + ", svalue=" + svalue);
        //获取已设定的默认值
        String sdefaultvalue = tparam.getValue();
        logger.debug("----framework: dot tparam: sdefaultvalue=" + sdefaultvalue);
        if (!StringUtil.isStrEmpty(sdefaultvalue)){
          //默认值不为空，已默认值为准
//          svalue = sdefaultvalue;
          
          //ADD BY LIHUI 2013-11-27 修改出参只能为FieldData的BUG
          svalue = new FieldData(sname, sdefaultvalue);
          //ADD BY LIHUI 2013-11-27
        }
        //设置FieldData
        String srealname = tparam.getRealname();
        logger.debug("----framework: dot tparam: srealname=" + srealname);
//        serviceContext.addFieldData(srealname, svalue);
        
        //ADD BY LIHUI 2013-11-27 修改出参只能为FieldData的BUG
        if (svalue != null) {
          svalue.setName(srealname);
          serviceContext.addFOPData(svalue);
        }
        //ADD BY LIHUI 2013-11-27
      }
    } catch (Exception e) {
      logger.error("----framework: outputProcess error1: " + e.getMessage(), e);
      throw new FOPException("FOP000507", "出参处理出错");
    }
  }

}

/**
 * 文件名		：ServiceDotChainImpl.java
 * 创建日期	：2013-9-22
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 * 
 * 修改记录：
 * 1.修改时间：2013-11-13
 *   修改人：xieyg
 *   生成版本：1.01
 *   修改内容：执行Dot出现异常的情况下，仍然
 *            需要做视图信息处理。因此调整为finally方式
 */
package com.fop.framework.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.context.ApplicationContextUtil;
import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;
import com.fop.framework.exception.FOPRuntimeException;
import com.fop.framework.service.trade.ITradeDot;
import com.fop.framework.service.trade.ITradeDotInterceptor;
import com.fop.framework.service.trade.ITradeDotInterceptorImpl;
import com.fop.framework.service.trade.ServiceDot;
import com.fop.framework.service.trade.ServiceView;
import com.fop.framework.service.trade.SubServiceDot;
import com.fop.framework.util.StringUtil;

/**
 * 描述:执行业务流程
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
@SuppressWarnings("rawtypes")
public class ServiceDotChainImpl implements ServiceDotChain {

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());

  @Override
  public void process(List<Map<String, ServiceDot>> listDotChain,
      Map<String, ServiceView> mapViewResult, FOPContext serviceContext)
  throws FOPException {
    String retviewname = ""; //返回视图
    try {
      int csize = listDotChain.size();
      logger.debug("----framework: DotChain.size is " + csize + ".");

      //--------1.把Dot标识与具体list中所处索引的对应关系事先存放，方便直接获取索引------------
      Map<String, Integer> dotIndexMap = new HashMap<String, Integer>();
      for (int n = 0; n < csize; n++){
        //取得单项配置
        Map map = listDotChain.get(n);
        Iterator it = map.entrySet().iterator();   
        it.hasNext();
        Entry entry = (Entry)it.next();   
        String strkey = String.valueOf(entry.getKey());
        dotIndexMap.put(strkey, n);
      }
      logger.debug("----framework: 存放dotIndexMap ok.");

      //--------2.执行listDotChain遍历------------
      String retvalue = "";//单点执行返回值
      String visitto = ""; //接下来访问的点的定位串(流程走向定位串)
      String nextDot = ""; //接下来访问的点
      String strack = ""; //执行轨迹
      FOPContext dotContext = null; //dotContext信息。
      Map<String, Boolean> mapAlreadyExecDot = new HashMap<String, Boolean>(); //已经执行过的Dot集合
      /*死循环判断标识，如Dot已经执行过之后，
       * 再次被执行，视为出现死循环特征*/
      Boolean deadLoopFlag = false;
      try {
        logger.debug("----framework: for dot execute begin.");
        for (int index = 0; index < csize; index++){
          //--------3.识别Dot单项配置------------
          //取得单项配置
          Map map = listDotChain.get(index);
          Iterator it = map.entrySet().iterator();     
          it.hasNext();
          Entry entry = (Entry)it.next();   
          Object key = entry.getKey();   //流程点标识
          String strkey = String.valueOf(key);
          ServiceDot tlineDot =  (ServiceDot)entry.getValue(); //流程节点
          //logger.debug("----framework: key=" + key + ",value=" + tlineDot);
          String dotId = tlineDot.getId(); //tradeDot.getClass().getSimpleName();
          ITradeDot tradeDot = null; //流程点对象
          boolean isSubTrade = false; //是否为子流程
          //先取出父tradeid，进行保存
          String tradeid = (String)serviceContext.getFieldDataValue(FOPConstants.TRADE_ID);

          //取得已执行的Dot信息
          if (!mapAlreadyExecDot.isEmpty()){
            deadLoopFlag =  mapAlreadyExecDot.get(dotId);
            logger.debug("----framework: get mapAlreadyExecDot(" + dotId + ") is " + deadLoopFlag);
          }
          if (deadLoopFlag != null && deadLoopFlag){
            logger.warn("----framework: DotChain出现有可能的死循环: tradeid=" + tradeid);
            break; //如果为死循环，强制终止
          }

          //--------4.获取单个流程实例(流程或子流程)------------
          if (strkey.indexOf(FOPConstants.DOT_SUBTRADE_FLAG) != -1){ //嵌入子流程的情况
            logger.debug("----framework: subtrade preprocess begin");
            isSubTrade = true;
            String subtradeid = StringUtil.getFirstAfterColonStr(strkey); //子流程标识
            logger.debug("----framework: subtradeid: " + subtradeid);
            //设置子流程标识
            dotContext.addFieldData(FOPConstants.TRADE_ID, subtradeid);
            //获取Dot对象，使用额外的的Dot对象执行
            tradeDot = new SubServiceDot();
            logger.debug("----framework: subtrade preprocess end");
          } else {
            //获取Dot对象
            tradeDot = (ITradeDot)ApplicationContextUtil.getBean(strkey);
          }

          //--------5.Dot入参处理(将serviceContext的输入参数及每次Dot执行结果合并后的集合赋值给indotContext里面)-------------------
          dotContext = new FOPContext(FOPConstants.CONTEXT_DOT);
          ServiceDotParameter sdotparam = (ServiceDotParameter)ApplicationContextUtil.getBean(FOPConstants.SERVICEDOTPARAMETER);
          //取到单个ServiceDot
          ServiceDot singleDot = (ServiceDot)map.get(strkey);
          //取到单个ServiceDot入参
          List listinput =  singleDot.getInparameter(); 
          //取到单个ServiceDot出参
          List listoutput =  singleDot.getOutparameter(); 
          logger.debug("----framework: get input & output ok");
          //取到处理后的结果
          FOPContext tempindata = sdotparam.inputProcess(serviceContext, listinput);
          if (tempindata != null){
            //不为空，以处理后的为准，否则为默认的初始对象
            dotContext = tempindata;
          }
          logger.debug("----framework: inputProcess ok");

          //--------6.Dot执行前拦截处理-----------------
          ITradeDotInterceptor tdotInterceptor = new ITradeDotInterceptorImpl();
          tdotInterceptor.setDotId(dotId);
          //Dot执行前处理
          tdotInterceptor.beforeDot(dotContext);
          logger.debug("----framework: beforeDot ok");
          Exception ex = null;
          try {
            //--------7.Dot执行-------------------
            tradeDot.process(dotContext);
            logger.debug("----framework: tradeDot.process ok, dotId=" + dotId);
          } catch (Exception e) {
            ex = e;
            logger.error("执行Dot异常：", e);
          } finally {
           //--------8.Dot执行后拦截处理-----------------
            tdotInterceptor.afterDot(dotContext);
            logger.debug("----framework: afterDot ok");

            //--------9.Dot出参处理-------------------
            //处理出参
            sdotparam.outputProcess(serviceContext, dotContext, listinput, listoutput);
            logger.debug("----framework: outputProcess ok");
            if(ex != null) {
              if(ex instanceof FOPRuntimeException) {
                ex = new FOPException(((FOPRuntimeException) ex).getErrCode(),((FOPRuntimeException) ex).getErrMessage());
              }
              throw ex;
            }
          }

          //--------10.Dot流程走向处理-------------------
          retvalue = String.valueOf(dotContext.getFieldDataValue(FOPConstants.DOT_RESULT_RET));
          logger.debug("----framework: " + dotId +" return=" + retvalue);
          if (StringUtil.isStrEmpty(retvalue) || "null".equalsIgnoreCase(retvalue)){
            retvalue = "0";  //默认为0
          }     

          //--------11.流程走向识别处理-----------------
          if (isSubTrade){
            //执行完后将父tradeid，重新赋值
            serviceContext.setFieldDataValue(FOPConstants.TRADE_ID, tradeid);
            logger.debug("----framework: isSubTrade to reset tradeid is " + tradeid);
          }

          strack += dotId + ":" + retvalue + "-->";
          //组合可识别的流程走向定位串
          visitto = "ret" + retvalue;
          //取到下一个执行点
          try {
            nextDot = tlineDot.getRet().get(visitto);
          } catch (Exception exc){
            logger.error("----framework: 未定义流程走向: " + exc.getMessage(), exc);
            throw new FOPException("FOP000502", dotId + "未定义流程走向");
          }

          if (FOPConstants.DOT_TO_NEXT.equals(nextDot)){ //如果为next，继续往下执行
            continue;
          } else if (FOPConstants.DOT_TO_END.equals(nextDot)){ //如果为end，停止执行
            //设置视图名称
            retviewname = tlineDot.getViewName();
            break;
          } else {
            /*
             *需使用dotIndexMap，而不是tlineDot.getIndex()
             *因为在循环中还会默认加一，故先减一，才能匹配索引
             */
            index = dotIndexMap.get(nextDot) - 1; 
          }
          if ((index == csize - 1) && (StringUtil.isStrEmpty(retviewname))){
            //全部流程点执行完毕，依然未找到用户定义视图，则设置默认视图
            retviewname = FOPConstants.TRADE_VIEW_DEFAULT; 
          }
          //已执行过Dot加入到集合
          mapAlreadyExecDot.put(dotId, true);
          logger.debug("----framework: add dotId: " + dotId + " is true to mapAlreadyExecDot");

          if (deadLoopFlag != null && deadLoopFlag){
            throw new FOPException("FOP000503", dotId + "流程点二次执行出错");
          }
        }
        logger.debug("----framework: for dot execute end.");
      } catch (FOPException ex) {
        //update by xieyg for 2013-11-13 为空打印默认视图
        if (StringUtil.isStrEmpty(retviewname)){
          retviewname = FOPConstants.TRADE_VIEW_DEFAULT; //视图为空，设置默认视图
        }
        logger.error("----framework: DotChain track: " + strack + "view:" + retviewname + "," + ex.getMessage());
        throw ex;
      }
      //打印执行痕迹
      logger.info("----framework: DotChain track: " + strack + "viewname:" + retviewname);

      //--------12.视图信息处理-----------------
      //-----------------根据视图名称，取得拆分出视图类型与视图值------------------------
      ServiceView tlineview = mapViewResult.get(retviewname);
      //视图值（临时）
      String viewvalueflag = String.valueOf(tlineview.getValue());
      //视图类型
      String viewtype = tlineview.getType();
      logger.debug("----framework: viewtype=" + viewtype + ", viewvalueflag=" + viewvalueflag );
      //视图值
      Object viewvalue = viewvalueflag;
      if (viewvalueflag.indexOf("$") == 0){
        //首个串为$符，表示实际为变量，需取出变量的值
        viewvalueflag = StringUtil.firstCharDel(viewvalueflag); //去掉首字符
        viewvalue = serviceContext.getFieldDataValue(viewvalueflag);
      }
      //--------设置视图信息-----------------
      serviceContext.addFieldData(FOPConstants.TRADE_VIEW_KEY, viewvalueflag);
      serviceContext.addFieldData(FOPConstants.TRADE_VIEW_TYPE, viewtype);
      serviceContext.addFieldData(FOPConstants.TRADE_VIEW_VALUE, viewvalue);
    } catch (FOPException e) {
      throw e;
    } catch (Exception e) {
      logger.error("----framework: error:" + e.getMessage(), e);
      throw new FOPException("FOP000504", "流程执行出错");
    } finally {
      //update by xieyg for 2013-11-13 调整为finally方式 
      //--------12.视图信息处理-----------------
      //-----------------根据视图名称，取得拆分出视图类型与视图值------------------------
      if (StringUtil.isStrEmpty(retviewname)){
        retviewname = FOPConstants.TRADE_VIEW_DEFAULT; //视图为空，设置默认视图
      }
      ServiceView tlineview = mapViewResult.get(retviewname);
      if (tlineview == null){ //取不到指定的视图，自定义一个视图做为在出现异常之后的默认转向
        tlineview = new ServiceView();
        tlineview.setDesc("默认视图");
        tlineview.setName(FOPConstants.TRADE_VIEW_DEFAULT);
        tlineview.setType(FOPConstants.TRADE_VIEW_TYPE_JSON); //默认为json类型
        tlineview.setValue("");
      }
      //视图值（临时）
      String viewvalueflag = String.valueOf(tlineview.getValue());
      //视图类型
      String viewtype = tlineview.getType();
      logger.debug("----framework: viewtype=" + viewtype + ", viewvalueflag=" + viewvalueflag );
      //视图值
      Object viewvalue = viewvalueflag;
      if (viewvalueflag.indexOf("$") == 0){
        //首个串为$符，表示实际为变量，需取出变量的值
        viewvalueflag = StringUtil.firstCharDel(viewvalueflag); //去掉首字符
        viewvalue = serviceContext.getFieldDataValue(viewvalueflag);
      }
      //--------设置视图信息-----------------
      serviceContext.addFieldData(FOPConstants.TRADE_VIEW_KEY, viewvalueflag);
      serviceContext.addFieldData(FOPConstants.TRADE_VIEW_TYPE, viewtype);
      serviceContext.addFieldData(FOPConstants.TRADE_VIEW_VALUE, viewvalue);
    }
  }

}

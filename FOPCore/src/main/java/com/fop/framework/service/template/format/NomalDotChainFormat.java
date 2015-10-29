/**
 * 文件名		：NomalDotChainFormat.java
 * 创建日期	：2013-10-8
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.template.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.context.ApplicationContextUtil;
import com.fop.framework.exception.FOPException;
import com.fop.framework.service.trade.ServiceDot;
import com.fop.framework.service.trade.TradeInparameter;
import com.fop.framework.service.trade.TradeOutparameter;

/**
 * 描述:交易流程链解析
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class NomalDotChainFormat implements DotChainFormat{

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());
  
  /* (non-Javadoc)
   * @see com.fop.framework.service.template.format.DotChainFormat#format(java.util.List)
   */
  @SuppressWarnings("rawtypes")
  @Override
  public List<Map<String, ServiceDot>> format(List configDotChain)
  throws FOPException {
    //定义dotChain对象
    List<Map<String, ServiceDot>> dotChain = new ArrayList<Map<String, ServiceDot>>();
    //计算configDotChain大小
    int csize = configDotChain.size();
    logger.debug("----framework: DotChain.size is " + csize + ".");
    Map<String, ServiceDot> mapDot = null; //流程项定义
    for (int index = 0; index < csize; index++){
      //取得单项配置
      String strkey = ""; //Dot名称
      String strvalue = ""; //Dot执行结果转向
      Map mapChain = (Map)configDotChain.get(index);
      Iterator it = mapChain.entrySet().iterator();
      Map<String, String> retVisitMap = null; //流程走向
      String desc = String.valueOf(mapChain.get(FOPConstants.TEMPLATE_NODE_DESC)); //描述
      desc = desc.trim(); //去空格处理
      String input = String.valueOf(mapChain.get(FOPConstants.TEMPLATE_INPUT)); //入参
      input = input.trim(); //去空格处理
      String output = String.valueOf(mapChain.get(FOPConstants.TEMPLATE_OUTPUT)); //出参
      output = output.trim(); //去空格处理
      logger.debug("----framework: desc=" + desc);
      logger.debug("----framework: input=" + input);
      logger.debug("----framework: output=" + output);
      String viewRealVisit = ""; //结束点视图名称
      while(it.hasNext()){
        Entry entry = (Entry)it.next();
        String key = String.valueOf(entry.getKey());   //Dot名称
        key = key.trim(); //去空格处理
        if (!(FOPConstants.TEMPLATE_INPUT.equalsIgnoreCase(key)
            || FOPConstants.TEMPLATE_OUTPUT.equalsIgnoreCase(key)
            || FOPConstants.TEMPLATE_NODE_DESC.equalsIgnoreCase(key))){
          logger.debug("----framework: key=" + key + ",value=" + entry.getValue());
          //排除三种已知的定义key的情况，取值为Dot名称 
          strkey = key;
          Object value = entry.getValue(); //Dot执行结果转向
          strvalue = String.valueOf(value);
          strvalue = strvalue.trim(); //去空格处理
          logger.debug("----framework: strvalue=" + strvalue);
          //重新封装成Map结构，例ret0=next,ret1=Dot4
          String[] retVisits = strvalue.split(",");
          String retVisit = ""; //访问对应关系
          String[] detailRetVisits = null; //具体访问对应关系
          retVisitMap = new HashMap<String, String>();

          for (int j = 0; j < retVisits.length; j++){
            retVisit = retVisits[j];
            retVisit = retVisit.trim(); 
            //对于简写方式，先补全完整，简写串实际为“ret0=简写串”
            if (FOPConstants.DOT_TO_NEXT.equals(retVisit)){
              retVisit = "ret0=" + FOPConstants.DOT_TO_NEXT;
            } 
            if (FOPConstants.DOT_TO_END.equals(retVisit)){
              retVisit = "ret0=" + FOPConstants.DOT_TO_END;
            }
            detailRetVisits = retVisit.split("=");
            String devisitfirst = detailRetVisits[0].trim();
            String devisittwo = detailRetVisits[1].trim();
            if (!FOPConstants.DOT_TO_VIEW.equals(devisitfirst)){
              //取等号两边的值做为key与value存储到map
              retVisitMap.put(devisitfirst, devisittwo);
            }
            //在转向为end的情况下，设置视图名称(排除有可能写为viewname=end的情况)
            if ((!FOPConstants.DOT_TO_VIEW.equals(devisitfirst)) && FOPConstants.DOT_TO_END.equals(devisittwo)){
              String viewVisitTo = ""; //结果视图定义
              String[] detailviewVisits = null; //分解后的结果视图定义
              try {
                viewVisitTo = retVisits[j + 1]; //紧跟追溯到后一个元素
                detailviewVisits = viewVisitTo.split("=");
                viewRealVisit = detailviewVisits[1].trim();
                //end后面没有紧跟viewname设置的情况，设置为默认视图
                if (!FOPConstants.DOT_TO_VIEW.equals(detailviewVisits[0].trim())){
                  viewRealVisit = FOPConstants.TRADE_VIEW_DEFAULT; 
                }
              } catch (ArrayIndexOutOfBoundsException e) {
                logger.warn("----framework: Cannot find view, set this node's resultview to DefaultView.");
                viewRealVisit = FOPConstants.TRADE_VIEW_DEFAULT; //取不到设置为默认视图
              } //end try
            } //end if VIEWNAME
          } // end if retVisits
        } // end if key
      }// end while(it.hasNext())
      //解析入参
      NomalInparameterFormat inparamfmt = (NomalInparameterFormat)ApplicationContextUtil.getBean(FOPConstants.TEMPLATE_FORMAT_INPARAM);
      List<TradeInparameter> listinput = inparamfmt.format(input);
      //解析出参
      NomalOutparameterFormat outparamfmt = (NomalOutparameterFormat)ApplicationContextUtil.getBean(FOPConstants.TEMPLATE_FORMAT_OUTPARAM);
      List<TradeOutparameter> listoutput = outparamfmt.format(output);

      //设置单个ServiceDot对象
      mapDot = new HashMap<String, ServiceDot>();
      ServiceDot tlineDot = new ServiceDot();
      tlineDot.setRet(retVisitMap);
      tlineDot.setId(strkey);
      tlineDot.setDescription(desc);
      tlineDot.setIndex(index);
      tlineDot.setViewName(viewRealVisit);
      tlineDot.setInparameter(listinput);
      tlineDot.setOutparameter(listoutput);
      mapDot.put(strkey, tlineDot);
      dotChain.add(mapDot);
    } //end for index

    return dotChain;
  }

}

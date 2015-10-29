/**
 * 文件名		：NomalViewResultFormat.java
 * 创建日期	：2013-10-8
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.template.format;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.exception.FOPException;
import com.fop.framework.service.trade.ServiceView;

/**
 * 描述:视图定义解析
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class NomalViewResultFormat implements ViewResultFormat{

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());

  /* (non-Javadoc)
   * @see com.fop.framework.service.template.format.ViewResultFormat#format(java.util.List)
   */
  @SuppressWarnings("rawtypes")
  @Override
  public Map<String, ServiceView> format(List configViewResult)
  throws FOPException {
    //定义viewResult对象
    Map<String, ServiceView> viewResult = new HashMap<String, ServiceView>();
    try {
      //计算configViewResult大小
      int csize = configViewResult.size();
      logger.debug("----framework: ViewResult.size is " + csize + ".");
      ServiceView tlineView = null; //单一视图对象 
      //遍历configViewResult，重新封装
      for (int index = 0; index < csize; index++){
        //取得单项配置
        Map map = (Map)configViewResult.get(index);
        Iterator it = map.entrySet().iterator();    
        it.hasNext();
        Entry entry = (Entry)it.next();   
        Object key = entry.getKey();   //视图名称
        Object value = entry.getValue(); //视图结果（类型，值）
        logger.debug("----framework: key=" + key + ",value=" + value);
        if (key == null){
          continue; //为null不做设置，继续下一个
        }
        String desc = String.valueOf(map.get(FOPConstants.TEMPLATE_NODE_DESC)); //描述
        desc = desc.trim(); //去空格处理
        logger.debug("----framework: desc=" + desc);
        String strkey = String.valueOf(key);
        strkey = strkey.trim(); //去空格处理
        String strvalue = String.valueOf(value);
        strvalue = strvalue.trim(); //去空格处理

        /*
         * 分解视图结果strvalue: 
         * view情况1.例：type=jsp,value=index/index2
         * view情况2.首字符$代表变量，例：type=json,value=$resultobj
         * 此处没有Context对象，故不区分情况1和情况2，统一做为字符串先存放起来
         */
        String[] viewResults = strvalue.split(",");
        String viewType = ""; //视图类型
        String viewRealType = ""; //实际视图类型
        Object viewRealValue = ""; //实际视图值
        String[] detailViewResults = null; //详细视图信息
        for (int j = 0; j < viewResults.length; j++){
          viewType = viewResults[j].trim();
          detailViewResults = viewType.split("=");
          String detailFlag = detailViewResults[0].trim(); //取得等号左边的标识
          //为"type"的情况，表示第二个元素为实际视图类型
          if ("type".equals(detailFlag)){ 
            viewRealType = detailViewResults[1].trim();
          } 
          //为"value"的情况，表示第二个元素为实际视图值
          if ("value".equals(detailFlag)){
            viewRealValue = detailViewResults[1].trim();
          } 
        }
        //设置单一视图对象信息
        tlineView = new ServiceView();
        tlineView.setName(strkey);
        tlineView.setDesc(desc);
        tlineView.setType(viewRealType);
        tlineView.setValue(viewRealValue);
        viewResult.put(strkey, tlineView);
      }
    } catch (Exception e) {
      logger.error("----framework: error:" + e.getMessage(), e);
      throw new FOPException("FOP000515", "ViewResult解析出错");
    }
    return viewResult;
  }

}

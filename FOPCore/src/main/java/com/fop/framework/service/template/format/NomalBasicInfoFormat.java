/**
 * 文件名		：NomalBasicInfoFormat.java
 * 创建日期	：2013-10-8
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.template.format;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.context.ApplicationContextUtil;
import com.fop.framework.exception.FOPException;
import com.fop.framework.service.trade.ServiceBasicInfo;
import com.fop.framework.service.trade.TradeInparameter;
import com.fop.framework.service.trade.TradeOutparameter;

/**
 * 描述:基本信息解析
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
@SuppressWarnings("rawtypes")
public class NomalBasicInfoFormat implements BasicInfoFormat{

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());

  /* (non-Javadoc)
   * @see com.fop.framework.service.template.format.BasicInfoFormat#format(java.util.Map)
   */

  @Override
  public ServiceBasicInfo format(Map mapBasicInfo) throws FOPException {
    //定义basicInfo对象
    ServiceBasicInfo basicInfo = new ServiceBasicInfo();
    try {
      //计算mapBasicInfo大小
      int csize = mapBasicInfo.size();
      logger.debug("----framework: mapBasicInfo.size is " + csize + ".");

      String desc = String.valueOf(mapBasicInfo.get(FOPConstants.TEMPLATE_NODE_DESC)); //描述
      desc = desc.trim(); //去空格处理
      String input = String.valueOf(mapBasicInfo.get(FOPConstants.TEMPLATE_INPUT)); //入参
      input = input.trim(); //去空格处理
      String output = String.valueOf(mapBasicInfo.get(FOPConstants.TEMPLATE_OUTPUT)); //出参
      output = output.trim(); //去空格处理
      //交易码
      String tradeid = (String)mapBasicInfo.get("tradeid");
      basicInfo.setTradeId(tradeid);
      Map extinfo = (Map)mapBasicInfo.get(FOPConstants.TEMPLATE_EXTINFO); //扩展信息
      logger.debug("----framework: desc=" + desc + ",input=" + input + ",output=" + output);
      //设置描述
      basicInfo.setDescription(desc);
      //解析入参
      NomalInparameterFormat inparamfmt = (NomalInparameterFormat)ApplicationContextUtil.getBean(FOPConstants.TEMPLATE_FORMAT_INPARAM);
      List<TradeInparameter> listinput = inparamfmt.format(input);
      //设置入参
      basicInfo.setInparameter(listinput);
      //解析出参
      NomalOutparameterFormat outparamfmt = (NomalOutparameterFormat)ApplicationContextUtil.getBean(FOPConstants.TEMPLATE_FORMAT_OUTPARAM);
      List<TradeOutparameter> listoutput = outparamfmt.format(output);
      //设置出参
      basicInfo.setOutparameter(listoutput);
      //处理扩展信息
      Map extinfonew = extinfoformat(extinfo);
      //设置扩展信息
      basicInfo.setExtInfo(extinfonew);
    } catch (Exception e) {
      logger.error("----framework: error:" + e.getMessage(), e);
      throw new FOPException("FOP000511", "BasicInfo解析出错");
    }

    return basicInfo;
  }

  /**
   * extinfo format
   * @param extinfo
   * @return
   */
  private Map extinfoformat(Map extinfo){
    //使用标配，不做特殊处理
    return extinfo;
  }

}

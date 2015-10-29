/**
 * 文件名		：NomalInparameterFormat.java
 * 创建日期	：2013-10-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.template.format;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.exception.FOPException;
import com.fop.framework.service.trade.TradeInparameter;
import com.fop.framework.util.StringUtil;

/**
 * 描述:输入参数解析
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class NomalInparameterFormat implements InparameterFormat {

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());
  
  /* (non-Javadoc)
   * @see com.fop.framework.service.template.format.InparameterFormat#format(java.lang.String)
   */
  @Override
  public List<TradeInparameter> format(String input) throws FOPException {
    //input: a=123#FieldData,b=$b1#GroupData,c#RepeatData
    //定义解析后的对象
    List<TradeInparameter> listparam = new ArrayList<TradeInparameter>();
    //为空不做后续处理
    if (StringUtil.isStrEmpty(input)){
      return listparam;
    }
    try {
      //第一轮，逗号分解
      String[] firstFmtParams = input.split(",");
      int fcount = firstFmtParams.length; //参数数量
      String singleParam = ""; //单个参数
      String[] paramWithType = null; //带类型的参数
      String[] paramWithValue = null; //带值（或映射名称）的参数
      String paramname = ""; //参数名称
      String paramrealname = ""; //参数实际名称（映射参数名称）
      String paramtype = ""; //参数类型
      for (int j = 0; j < fcount; j++){
        String paramvalue = ""; //参数值
        singleParam = firstFmtParams[j];
        singleParam = singleParam.trim(); //去空格处理
        //第二轮，井号分解
        paramWithType = singleParam.split("#");
        if (paramWithType.length > 1){
          paramtype = paramWithType[1];
          paramtype = paramtype.trim(); //去空格处理
        }

        //第三轮，等号分解
        paramWithValue = paramWithType[0].split("=");
        paramname = paramWithValue[0];
        paramname = paramname.trim(); //去空格处理
        paramrealname = paramname; //默认两个名称一样；
        if (paramWithValue.length > 1){
          paramvalue = paramWithValue[1];
          paramvalue = paramvalue.trim(); //去空格处理
          //第四轮，钱币号识别，识别变量名称 
          int cflag = paramvalue.indexOf("$");
          if (cflag == 0){//带变量识别符
            //取出映射变量名称 
            paramrealname = StringUtil.firstCharDel(paramvalue);
            //不为值的情况下值为空
            paramvalue = null;
          }
        }
        //设置参数对象
        TradeInparameter tparam = new TradeInparameter();
        tparam.setName(paramname);
        tparam.setRealname(paramrealname);
        tparam.setType(paramtype);
        tparam.setValue(paramvalue);
        //添加到参数列表
        listparam.add(tparam);
      }
    } catch (Exception e) {
      logger.error("----framework: error:" + e.getMessage(), e);
      throw new FOPException("FOP000512", "Inparameter解析出错");
    }

    return listparam;
  }

}

/**
 * 文件名		：NomalOutparameterFormat.java
 * 创建日期	：2013-10-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.template.format;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.exception.FOPException;
import com.fop.framework.service.trade.TradeOutparameter;
import com.fop.framework.util.StringUtil;

/**
 * 描述:输出参数解析
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class NomalOutparameterFormat implements OutparameterFormat {

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());

  /* (non-Javadoc)
   * @see com.fop.framework.service.template.format.OutparameterFormat#format(java.lang.String)
   */
  @Override
  public List<TradeOutparameter> format(String output) throws FOPException {
    //定义解析后的对象
    List<TradeOutparameter> listparam = new ArrayList<TradeOutparameter>();
    //为空不做后续处理
    if (StringUtil.isStrEmpty(output)){
      return listparam;
    }
    try {
      //第一轮，逗号分解
      String[] firstFmtParams = output.split(",");
      int fcount = firstFmtParams.length; //参数数量
      String singleParam = ""; //单个参数
      String[] paramWithType = null; //带类型的参数
      String[] paramWithValue = null; //带值（或映射名称）的参数
      String paramname = ""; //参数名称
      String paramrealname = ""; //参数实际名称（映射参数名称）
      String paramvalue = ""; //参数值
      String paramtype = ""; //参数类型
      for (int j = 0; j < fcount; j++){
        singleParam = firstFmtParams[j].trim();
        //第二轮，井号分解
        paramWithType = singleParam.split("#");
        if (paramWithType.length > 1){
          paramtype = paramWithType[1].trim();
        }

        //第三轮，等号分解
        paramWithValue = paramWithType[0].split("=");
        paramname = paramWithValue[0].trim();
        paramrealname = paramname; //默认两个名称一样；
        if (paramWithValue.length > 1){
          paramvalue = paramWithValue[1].trim();
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
        TradeOutparameter tparam = new TradeOutparameter();
        tparam.setName(paramname);
        tparam.setRealname(paramrealname);
        tparam.setType(paramtype);
        tparam.setValue(paramvalue);
        //添加到参数列表
        listparam.add(tparam);
      }
    } catch (Exception e) {
      logger.error("----framework: error:" + e.getMessage(), e);
      throw new FOPException("FOP000513", "Outparameter解析出错");
    }

    return listparam;
  }

}

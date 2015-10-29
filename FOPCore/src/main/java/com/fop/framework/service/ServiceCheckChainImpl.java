/**
 * 文件名		：ServiceCheckChainImpl.java
 * 创建日期	：2013-9-22
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPValidateException;
import com.fop.framework.service.trade.ServiceCheck;
import com.fop.framework.service.validate.IValidate;
import com.fop.framework.service.validate.ValidateResult;


/**
 * 描述:执行输入要素检查接口实现
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ServiceCheckChainImpl implements ServiceCheckChain {

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());

  @SuppressWarnings("rawtypes")
  @Override
  public void process(List<Map<String, ServiceCheck>> listCheckChain,
      FOPContext serviceContext) throws FOPValidateException {

    int csize = listCheckChain.size();
    logger.debug("----framework: CheckChain.size is " + csize + ".");
    //定义验证结果对象
    ValidateResult vresult = new ValidateResult();
    try {
      String strack = ""; //执行轨迹
      String strackflag = "-->";
      for (int i = 0; i < csize; i++){
        //取得单项检查配置
        Map map = listCheckChain.get(i);
        Iterator it = map.entrySet().iterator();   
        it.hasNext();
        Entry entry = (Entry)it.next();   
        String key = String.valueOf(entry.getKey());   //检查项
        ServiceCheck tlineCheck = (ServiceCheck)entry.getValue(); //对应的目标检查实例
        //logger.debug("----framework: key=" + key + ",value=" + tlineCheck);
        String[] paramNames = tlineCheck.getSrc();//设置验证源
        IValidate validateobj = tlineCheck.getVobj();//设置验证类
        if (i == csize - 1){
          strackflag = "";
        }
        strack += key + ":" + validateobj.getClass().getSimpleName() + strackflag;
        validateobj.validate(paramNames, serviceContext, vresult);//执行验证
      }
      //打印执行痕迹
      logger.info("----framework: CheckChain track: " + strack);
      //存储验证结果
      serviceContext.addFieldData(FOPConstants.VALIDATE_RESULT, vresult);
    } catch (Exception e) {
      logger.error("----framework: error:" + e.getMessage(), e);
      throw new FOPValidateException("FOP000500", "校验数据处理出错");
    }
    //判断若验证结果中存有值，则抛出验证异常
    if (vresult.getResults().size() > 0){
      logger.debug(vresult);
      throw new FOPValidateException("FOP000501", "数据检查不通过");
    }
  }
}

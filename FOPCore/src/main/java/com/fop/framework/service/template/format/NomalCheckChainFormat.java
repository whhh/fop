/**
 * 文件名		：NomalCheckChainFormat.java
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
import com.fop.framework.exception.FOPException;
import com.fop.framework.service.trade.ServiceCheck;
import com.fop.framework.service.validate.IValidate;

/**
 * 描述: 输入要素检查链解析
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class NomalCheckChainFormat implements CheckChainFormat{

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());

  /* (non-Javadoc)
   * @see com.fop.framework.service.template.format.CheckChainFormat#format(java.util.List)
   */
  @SuppressWarnings("rawtypes")
  @Override
  public List<Map<String, ServiceCheck>> format(List configCheckChain)
  throws FOPException {
    //定义checkChain对象
    List<Map<String, ServiceCheck>> checkChain = new ArrayList<Map<String, ServiceCheck>>();
    //计算configCheckChain大小
    int csize = configCheckChain.size();
    logger.debug("----framework: CheckChain.size is " + csize + ".");

    Map<String, ServiceCheck> mapCheck = null; //检查项定义
    for (int i = 0; i < csize; i++){
      //取得单项检查配置
      Map map = (Map)configCheckChain.get(i);
      Iterator it = map.entrySet().iterator();    
      it.hasNext();
      Entry entry = (Entry)it.next();   
      Object key = entry.getKey();   //检查源
      Object value = entry.getValue(); //对应的目标检查实例
      logger.debug("----framework: key=" + key + ",value=" + value);
      if (key == null){
        continue; //为null不做设置，继续下一个
      }

      String desc = String.valueOf(map.get(FOPConstants.TEMPLATE_NODE_DESC)); //描述
      desc = desc.trim(); //去空格处理
      logger.debug("----framework: desc=" + desc);
      String strkey = String.valueOf(key);
      strkey = strkey.trim(); //去空格处理
      String[] paramNames = strkey.split(",");  //分解可能存在多个传入的校验对象名称
      IValidate validateobj = (IValidate)value;

      //设置checkChain单个元素
      mapCheck = new HashMap<String, ServiceCheck>();
      ServiceCheck tlineCheck = new ServiceCheck();
      tlineCheck.setIndex(i);
      tlineCheck.setSrc(paramNames);
      tlineCheck.setVobj(validateobj);
      tlineCheck.setDesc(desc);
      mapCheck.put(strkey, tlineCheck);
      checkChain.add(mapCheck);
    }
    return checkChain;
  }

}

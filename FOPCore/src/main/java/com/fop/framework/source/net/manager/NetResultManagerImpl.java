/**
 * 文件名		：NetResultManagerImpl.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.net.manager;

import com.fop.framework.source.BaseContext;
import com.fop.framework.source.net.context.NetConstant;
import com.fop.framework.source.pub.SourceConstant;

/**
 * 描述:其他系统访问结果数据处理实现类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class NetResultManagerImpl implements NetResultManager{
  
  @Override
  public void operate(BaseContext netContext) {
    Object exchangeRes=netContext.get(NetConstant.CONST_exchangeRes);
    //结果数据处理
    Object resData=exchangeRes;
    netContext.setOutData(resData);
  }
  @Override
  public String getDescription() {
    return SourceConstant.Net_Res;
  }
}

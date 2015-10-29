/**
 * 文件名		：ITradeDot.java
 * 创建日期	：2013-9-10
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.trade;

import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;


/**
 * 描述:交易节点接口
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface ITradeDot{

  /**
   * 节点执行方法
   * @param context
   */
   public void process(FOPContext dotContext) throws FOPException;
}

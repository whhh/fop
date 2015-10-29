/**
 * 文件名		：ControlProcess.java
 * 创建日期	：2013-9-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.control;

import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;

/**
 * 描述:控制处理层接口
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface ControlProcess {
  
  /**
   * 控制层处理
   * @param context
   * @throws FOPException
   */
   public void ctrlProcess(FOPContext requestContext) throws FOPException;
}

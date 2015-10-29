/**
 * 文件名		：RecordLog.java
 * 创建日期	：2013-9-16
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.log;

import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;

/**
 * 描述: 记录操作日志接口
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface RecordLog {
  
  /**
   * 记录操作日志
   * @param context
   * @throws FOPException
   */
   public void record(FOPContext context) throws FOPException;
   
}

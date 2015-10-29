/**
 * 文件名		：ControlHandlerChainImpl.java
 * 创建日期	：2013-10-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.control.handler;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;

/**
 * 描述:策略处理链
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ControlHandlerChainImpl implements ControlHandlerChain {

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());

  /**
   * handler集合
   */
  private List<ControlHandler> handlers;

  /* (non-Javadoc)
   * @see com.fop.framework.control.handler.ControlHandlerChain#handlerChain(com.fop.framework.context.FOPContext)
   */
  @Override
  public void handlerChain(FOPContext requestContext) throws FOPException {
    if (handlers == null){
      return; 
    }
    try {
      int hsize = handlers.size();
      logger.debug("----framework: handlers size:" + hsize);
      ControlHandler handler = null;
      for (int i = 0; i < hsize; i++){
        //取得单个拦截处理器
        handler = handlers.get(i);
        handler.handle(requestContext);
      }
    } catch (FOPException e) {
      logger.error("-----framework: error: " + e.getErrCode(), e);
      throw e;
    } catch (Exception e) {
      logger.error("-----framework: error: " + e.getMessage(), e);
      throw new FOPException("FOP000406", "控制策略处理出错");
    }
  }

  /**
   * setter handlers
   * @param handlers
   */
  public void setHandlers(List<ControlHandler> handlers){
    this.handlers = handlers;
  }

}

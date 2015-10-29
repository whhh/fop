/**
 * 文件名		：ControlProcessImpl.java
 * 创建日期	：2013-9-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 * 
 * 修改记录：
 * 1.修改时间：2013-11-13
 *   修改人：xieyg
 *   生成版本：1.01
 *   修改内容：执行服务层代码出现异常的情况下，仍然
 *            需要做处理输出参数、记录操作日志、视图响应处理操作。因此调整为finally方式
 *
 */
package com.fop.framework.control;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.context.FOPContext;
import com.fop.framework.context.ServiceBeanFactoryUtil;
import com.fop.framework.control.handler.ControlHandlerChain;
import com.fop.framework.exception.FOPException;
import com.fop.framework.service.ServiceProcess;
import com.fop.framework.service.log.RecordLog;
import com.fop.framework.service.trade.ServiceBasicInfo;
import com.fop.framework.service.trade.ServiceConfig;
import com.fop.framework.util.StringUtil;

/**
 * 描述: 控制处理层接口实现
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ControlProcessImpl implements ControlProcess{

  /**日志打印对象**/
  private Log logger = LogFactory.getLog(getClass());

  /**拦截器链**/
  private ControlHandlerChain controlHandlerChain;

  /**出入参处理**/
  private ControlServiceParameter ctrlServiceParam;

  /**操作日志处理**/
  private RecordLog recordLog;

  /**视图响应处理**/
  private ControlViewResolver controlViewResolver;

  /**服务装配层处理**/
  private ServiceProcess serviceProcess;

  /* (non-Javadoc)
   * @see com.fop.framework.control.ControlProcess#ctrlProcess(com.fop.framework.context.FOPContext)
   */
  @SuppressWarnings("rawtypes")
  @Override
  public void ctrlProcess(FOPContext requestContext) throws FOPException{
    logger.debug("----framework: control begin");
    try {
      //判断是否上送tradeid
      String tradeid = (String)requestContext.getFieldDataValue(FOPConstants.TRADE_ID);
      if (StringUtil.isStrEmpty(tradeid)){
        logger.warn("----framework: the tradeid is isStrEmpty.");
        throw new FOPException("FOP000400", "未上送交易请求码");
      }

      //----------1.执行拦截器链---------------------
      controlHandlerChain.handlerChain(requestContext);
      logger.debug("----framework: handlerChain execute finish");
      
      String versionNo = (String)requestContext.getFieldDataValue(FOPConstants.REQUEST_HEADS + ".versionNo");
      
      String deviceType = (String)requestContext.getFieldDataValue(FOPConstants.REQUEST_HEADS + ".deviceType");

      //----------2. & 3. 取得解析后的流程对象---------------------
      ServiceConfig serviceConfig = ServiceBeanFactoryUtil.getBean(tradeid, versionNo, deviceType);

      //----------4.处理过滤单支请求交易，输入参数---------------------
      ServiceBasicInfo basicInfo = serviceConfig.getBasicInfo();
      List listinput = basicInfo.getInparameter();
      List listoutput = basicInfo.getOutparameter();
      FOPContext serviceContext = new FOPContext(FOPConstants.CONTEXT_SERVICE);
      //传入requestContext得到serviceContext
      FOPContext tempContext = ctrlServiceParam.inputProcess(requestContext, listinput); 
      if (tempContext != null){
        //有值的情况下才取该对象
        serviceContext = tempContext;
      }
      serviceContext.addFieldData(FOPConstants.TRADE_ID, tradeid);
      logger.debug("----framework: inputProcess execute finish");

      //----------5.执行服务层代码---------------------
      serviceContext.addFieldData(FOPConstants.SERVICE_CONFIG_DATA, serviceConfig);
      try {
        serviceProcess.serviceProcess(serviceContext);
        logger.debug("----framework: serviceProcess execute finish");
      } catch (FOPException eex) {
        //Exception对象留给记日志时使用
        requestContext.addFieldData(FOPConstants.TRADE_EXCEPTION, eex);
        throw eex;
      } catch (Exception eex2) {
        //Exception对象留给记日志时使用
        requestContext.addFieldData(FOPConstants.TRADE_EXCEPTION, eex2);
        logger.error("-----framework: error3: " + eex2.getMessage(), eex2);
        throw new FOPException("FOP000407", "业务流程执行出错");
      } finally {
        //----------6.处理过滤单支请求交易，输出参数---------------------
        ctrlServiceParam.outputProcess(requestContext, serviceContext, listinput, listoutput); 
        logger.debug("----framework: outputProcess execute finish");

        //----------7.记录操作日志-------------
        recordLog.record(requestContext);
        logger.debug("----framework: record execute finish");

        //----------8.视图响应处理----------------
        Object viewresult = null; //视图处理结果
        String viewtype = String.valueOf(serviceContext.getFieldDataValue(FOPConstants.TRADE_VIEW_TYPE));
        Object viewobj = String.valueOf(serviceContext.getFieldDataValue(FOPConstants.TRADE_VIEW_VALUE));
        viewresult = controlViewResolver.viewResolve(viewtype, viewobj);
        //设置视图结果
        requestContext.addFieldData(FOPConstants.TRADE_VIEW_RESULT, viewresult);
        logger.debug("----framework: viewResolve execute finish");
      }
    } catch (FOPException e) {
      logger.error("-----framework: error1: " + e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      logger.error("-----framework: error2: " + e.getMessage(), e);
      throw new FOPException("FOP000401", "控制流程出错");
    } 
    logger.debug("----framework: control end");
  }

  /**
   * @param controlHandlerChain the controlHandlerChain to set
   */
  public void setControlHandlerChain(ControlHandlerChain controlHandlerChain) {
    this.controlHandlerChain = controlHandlerChain;
  }

  /**
   * @param logger the logger to set
   */
  public void setLogger(Log logger) {
    this.logger = logger;
  }

  /**
   * @param ctrlServiceParam the ctrlServiceParam to set
   */
  public void setCtrlServiceParam(ControlServiceParameter ctrlServiceParam) {
    this.ctrlServiceParam = ctrlServiceParam;
  }

  /**
   * @param recordLog the recordLog to set
   */
  public void setRecordLog(RecordLog recordLog) {
    this.recordLog = recordLog;
  }

  /**
   * @param controlViewResolver the controlViewResolver to set
   */
  public void setControlViewResolver(ControlViewResolver controlViewResolver) {
    this.controlViewResolver = controlViewResolver;
  }

  /**
   * @param serviceProcess the serviceProcess to set
   */
  public void setServiceProcess(ServiceProcess serviceProcess) {
    this.serviceProcess = serviceProcess;
  }
}

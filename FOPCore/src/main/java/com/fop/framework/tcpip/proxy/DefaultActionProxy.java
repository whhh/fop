package com.fop.framework.tcpip.proxy;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.context.ApplicationContextUtil;
import com.fop.framework.context.FOPContext;
import com.fop.framework.control.ControlProcess;
import com.fop.framework.control.ControlProcessImpl;
import com.fop.framework.data.GroupData;
import com.fop.framework.data.RepeatData;
import com.fop.framework.exception.FOPException;
import com.fop.framework.service.validate.ValidateBody;
import com.fop.framework.service.validate.ValidateResult;


/**
 * The Default ActionProxy implementation
 *
 */
public class DefaultActionProxy implements ActionProxy {
  /**日志打印对象**/
  private Log logger = LogFactory.getLog(DefaultActionProxy.class);
  
  private ControlProcess controlProcess  = new ControlProcessImpl();

    public DefaultActionProxy(){
      Object obj = ApplicationContextUtil.getBean(ControlProcess.class);
      if(obj != null) {
        controlProcess = (ControlProcess)obj;
      }
    }

    public  FOPContext execute(String tradeId,FOPContext context) throws Exception {
      
      
      //FOPContext context = new FOPContext(FOPConstants.CONTEXT_REQUEST);
     
      
      //定义sessionContext上下文对象，存放HttpSession数据
      FOPContext sessionContext = new FOPContext(FOPConstants.CONTEXT_SESSION);
      //sessionContext做为requestContext的子节点
      
      GroupData headsgroup = new GroupData(FOPConstants.REQUEST_HEADS);
      context.addFOPData(headsgroup);
      context.addChildContext(sessionContext);
      context.addFieldData(FOPConstants.TRADE_ID,tradeId);
      FOPException ex = null;
      try {
        controlProcess.ctrlProcess(context);
      } catch (FOPException e) {
        logger.error("----framework: tradeid=" + tradeId + ", error1:" + e.getMessage(), e);
//        throw e;
        if(e.getErrCode() != null && (e.getErrCode().startsWith("E") 
            || e.getErrCode().startsWith("I") 
            || e.getErrCode().startsWith("W"))) {
          ex = (FOPException) e;
        } else {
          ex = new FOPException("E010104100001", "调用控制处理层出错");
        }
      } catch (Exception e) {
        logger.error("----framework: tradeid=" + tradeId + ", error2:" + e.getMessage(), e);
//        throw new FOPException("E010104100001", "调用控制处理层出错");
        ex = new FOPException("E010104100001", "调用控制处理层出错");
      } 
      
      if(ex != null) {
        ValidateResult result = (ValidateResult)context.getFieldDataValue(FOPConstants.VALIDATE_RESULT);
        if(result != null && result.getResults().size() > 0) {
          ValidateBody valid = result.getResults().get(0);
          ex = new FOPException(valid.getErrCode(), valid.getErrMessage());
        }
        if(context.getFieldDataValue("retStatus") == null) {
          RepeatData ret = new RepeatData("mbsRet");
          GroupData groupData = new GroupData();
          groupData.addFieldData("retCode", ex.getErrCode());
          groupData.addFieldData("retMsg", ex.getErrMessage());
          context.addFieldData("retStatus", "F");
          context.addFOPData(ret);
          ret.add(groupData);
        }
      }
      //FOPData result = (FOPData)context.getFieldDataValue("send");
        return context;
    }

}
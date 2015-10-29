package com.fop.framework.tcpip.proxy;

import com.fop.framework.context.ApplicationContextUtil;
import com.fop.framework.tcpip.invocation.ActionInvocation;

public class ActionProxyFactory {
  
  public final static String TCPINVOCATION = "tcpInvocation";
  
  private static ActionInvocation getFormatConfigContainer() {
    return (ActionInvocation) ApplicationContextUtil.getBean(TCPINVOCATION);
  }

  public static ActionProxy createActionProxy() {
    return createActionProxy(
        new DefaultActionProxy(),
        getFormatConfigContainer());

  }

  public static ActionProxy createActionProxy(
      ActionProxy proxy,
      ActionInvocation handler) {
    ActionProxy actionProxy = (ActionProxy) handler.bind(proxy);
    return actionProxy;
  }
}

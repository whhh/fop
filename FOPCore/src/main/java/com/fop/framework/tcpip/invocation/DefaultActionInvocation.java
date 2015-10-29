package com.fop.framework.tcpip.invocation;


import com.fop.framework.tcpip.proxy.ActionProxy;


import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;



public class DefaultActionInvocation implements ActionInvocation {
  
  private ActionProxy actionProxy;
  
  private List<Interceptor> interceptors = new LinkedList<Interceptor>();
  
    /**
   * @return the interceptors
   */
  public List<Interceptor> getInterceptors() {
    return interceptors;
  }

  /**
   * @param interceptors the interceptors to set
   */
  public void setInterceptors(List<Interceptor> interceptors) {
    this.interceptors = interceptors;
  }

    /** 
     * 动态生成一个代理类对象,并绑定被代理类和代理处理器 
     * 
     * @param business 
     * @return 代理类对象 
     */ 
  
  public DefaultActionInvocation(){
    
  }
  
    public Object bind(ActionProxy actionProxy) { 
        this.actionProxy = actionProxy; 
        return Proxy.newProxyInstance( 
                //被代理类的ClassLoader 
            actionProxy.getClass().getClassLoader(), 
                //要被代理的接口,本方法返回对象会自动声称实现了这些接口 
            actionProxy.getClass().getInterfaces(), 
                //代理处理器对象 
                this); 
    } 

    /** 
     * 代理要调用的方法,并在方法调用前后调用连接器的方法. 
     * 
     * @param proxy  代理类对象 
     * @param method 被代理的接口方法 
     * @param args   被代理接口方法的参数 
     * @return 方法调用返回的结果 
     * @throws Throwable 
     */ 
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable { 
        Object result = null; 
        for(Interceptor interceptor : interceptors){
          interceptor.before();
        }
        result = method.invoke(actionProxy,args);
        for(Interceptor interceptor : interceptors){        
          interceptor.after();
        }
        return result;
    } 
}

/**
 * 文件名		：CoreControllerInterceptor.java
 * 创建日期	：2013-9-10
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.http.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.apache.log4j.MDC;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.context.CacheDataUtil;
import com.fop.framework.data.IUser;
import com.fop.framework.util.StringUtil;

/**
 * 描述: HTTP渠道控制器拦截器
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class CoreControllerInterceptor extends HandlerInterceptorAdapter {

  /**日志打印对象**/
  private static Log logger = LogFactory.getLog(CoreControllerInterceptor.class);

  /**
   * 前拦截，在拦截对象方法执行前执行，返回false表示不再继续往后执行，返回true表示还能继续执行后续的正常流程
   */
  public boolean preHandle(HttpServletRequest request,
      HttpServletResponse response, Object handler) throws Exception {
    long startTime = System.currentTimeMillis();
//    tradeid = String.valueOf(request.getParameter(FOPConstants.TRADE_ID));
    String tradeid = null;
    String url = request.getRequestURL().toString();
    logger.info("url:" + url);
    int index = url.indexOf("?");
    if (index != -1) {
      tradeid = url.substring(0, index);
    } else {
      tradeid = url;
    }
    index = tradeid.lastIndexOf("/");
    tradeid = tradeid.substring(index + 1);
    tradeid = tradeid.substring(0, tradeid.lastIndexOf(".html"));
    
    CacheDataUtil.addThreadCacheData("tradeid", tradeid);//.put("tradeid", tradeid);
    CacheDataUtil.addThreadCacheData("startTime", startTime);

    UserDetails userobj = getUser(request);
    String operId = "";
    if (userobj != null) {
      operId = userobj.getUsername();
    } else {
      IUser user = getUserObject(request);
      if(user != null) {
        operId = user.getUsername();
      }
    }
    
    
    String clientIp = request.getHeader("x-forwarded-for");
    if(StringUtil.isStrEmpty(clientIp)) {
      clientIp = request.getRemoteAddr();
    }
    String serverIp = request.getLocalAddr();
//    operId += (":" + clientIp + "=>" + request.getLocalAddr());
    //存储ID到日志上下文
    MDC.put(FOPConstants.ID_NUM, operId);
    MDC.put(FOPConstants.TRADE_ID, tradeid);
    MDC.put("clientIp", clientIp);
    MDC.put("serverIp", serverIp);
    logger.info("----framework: startTime=" + startTime);
    logger.info("----framework: " + tradeid + ", startTime: " + startTime);
    return true;
  }

  /**
   * 后拦截，在拦截对象方法还没结束前，能改变modelAndView对象内容
   */
  public void postHandle(HttpServletRequest request,
      HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
  }

  /**
   * 后拦截，在拦截对象方法执行后执行
   */
  public void afterCompletion(HttpServletRequest request,
      HttpServletResponse response, Object handler, Exception ex)
  throws Exception {
    long endTime = System.currentTimeMillis();
    
    String tradeid = (String)CacheDataUtil.getThreadCacheDataValue("tradeid");
    Object obj = CacheDataUtil.getThreadCacheDataValue("startTime");
    long startTime = 0;
    if(obj != null) {
      startTime = (Long)obj;
    }
    //打印所使用的执行时间
    logger.info("----framework: endTime=" + endTime);
    logger.info("----framework: " + tradeid + " process used time: " + (endTime - startTime) + "ms");
    //日志操作
    if (MDC.get(FOPConstants.ID_NUM) != null){
      //清除相应对象
      MDC.remove(FOPConstants.ID_NUM);
    }
    if (MDC.get(FOPConstants.TRADE_ID) != null){
      //清除相应对象
      MDC.remove(FOPConstants.TRADE_ID);
    }
    if (MDC.get("clientIp") != null){
      //清除相应对象
      MDC.remove("clientIp");
    }
    if(MDC.get("serverIp") != null) {
      MDC.remove("serverIp");
    }
    CacheDataUtil.clearThreadCacheData();
  }
  
  public static IUser getUserObject(HttpServletRequest request) {
    IUser user = null;
    HttpSession session = request.getSession(false);
    if(session != null) {
      logger.info("----framework: session is not null.");
      Object obj = session.getAttribute("user");
      if(obj != null && obj instanceof IUser) {
        user = (IUser) obj;
      }
    }
    return user;
  }

  /**
   * 从SecurityContextHolder获取用户对象
   * @return
   */
  public static UserDetails getUser(HttpServletRequest request){
    UserDetails userobj = null;
    //获取SecurityContext上下文 
    SecurityContext scontext = SecurityContextHolder.getContext();
    if (scontext == null){
      logger.info("----framework: SecurityContextHolder context is null");
      HttpSession session = request.getSession(false);
      if(session != null) {
        logger.info("----framework: session is not null.");
        Object obj = session.getAttribute("SPRING_SECURITY_CONTEXT");
        if(obj != null && obj instanceof SecurityContext) {
          logger.info("----framework: session context instanceof SecurityContext");
          scontext = (SecurityContext) obj;
        } else {
          obj = session.getAttribute("user");
          if(obj != null && obj instanceof UserDetails) {
            
          }
          logger.info("----framework: session context: " + obj);
          return userobj;
        }
      } else {
        return userobj;
      }
    }
    Authentication sauth = scontext.getAuthentication();
    if (sauth == null){
      return userobj;
    }
    Object principal = sauth.getPrincipal();
    if (principal == null){
      return userobj;
    }
    if (principal instanceof UserDetails) {
      userobj = (UserDetails)principal;
    }
    return userobj;
  }
}

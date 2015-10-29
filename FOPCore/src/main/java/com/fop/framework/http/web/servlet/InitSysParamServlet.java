/**
 * 文件名		：InitSysParamServlet.java
 * 创建日期	：2013-9-11
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.http.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.fop.framework.util.StringUtil;

/**
 * 描述: 初始化参数
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class InitSysParamServlet extends HttpServlet {

  private static final long serialVersionUID = -5306744033794899229L;

  /**
   * Constructor of the object.
   */
  public InitSysParamServlet() {
    super();
  }

  /**
   * 初始化参数启动
   */
  public void init() throws ServletException {
    super.init();
    String file = getInitParameter("log4jfile");  
    String prefix = getServletContext().getRealPath("/"); 

    System.out.println("******************Load log4j config.");
//    System.out.println("******************the prefix is:" + prefix);
    if (StringUtil.isStrEmpty(file)){
      file = "WEB-INF/classes/log4j.properties";
    }
    String strfile = prefix + file;
    if(prefix != null) {
      PropertyConfigurator.configure(strfile);
    }
    Log logger = LogFactory.getLog(InitSysParamServlet.class);
    logger.info("******************Load log4j config finish.");
  }

}

/**
 * 文件名		：FOPLoaderDataChain.java
 * 创建日期	：2013-10-24
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.context;

import com.fop.framework.exception.FOPException;

/**
 * 描述:初始化、结束信息处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface FOPLoaderDataChain {
  
  /**
   * 初始化处理
   * @param context
   * @throws FOPException
   */
  public void initChain();
  
  /**
   * 结束处理
   * @param context
   * @throws FOPException
   */
  public void destroyChain();

}
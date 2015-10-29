/**
 * 文件名		：BaseManager.java
 * 创建日期	：2013-9-27
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source;


/**
 * 资源管理类的基类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public interface BaseManager {
  /**
   * 管理工具执行的入口方法
   * @param baseContext 传入上下文参数 
   */
  public void operate(BaseContext baseContext);
  /**
   * 获取管理工具描述信息
   * @return 返回管理工具描述字符串
   */
  public String getDescription();
}

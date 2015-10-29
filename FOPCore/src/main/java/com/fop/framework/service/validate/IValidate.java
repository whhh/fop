/**
 * 文件名		：IValidate.java
 * 创建日期	：2013-9-17
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.validate;

import com.fop.framework.context.FOPContext;

/**
 * 描述: 数据校验接口
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public interface IValidate {

  /**
   * 数据校验
   * @param context
   * @param result
   */
  public void validate(String[] paramNames, FOPContext context, ValidateResult result);
}

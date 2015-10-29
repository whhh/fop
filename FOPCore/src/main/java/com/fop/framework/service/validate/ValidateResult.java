/**
 * 文件名		：ValidateResult.java
 * 创建日期	：2013-9-17
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.validate;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:验证异常结果
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ValidateResult {

  /**验证结果*/
  private List<ValidateBody> vresults = new ArrayList<ValidateBody>();

  /**
   * 添加验证结果对象
   * @param errorCode
   * @param defaultErrMessage
   */
  public void addValidateErrorCode(String errorCode, String defaultErrMessage){
    vresults.add(new ValidateBody(errorCode, defaultErrMessage));
  }

  /**
   * 获取验证结果
   * @return
   */
  public List<ValidateBody> getResults(){
    return vresults;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return vresults.toString();
  }
}

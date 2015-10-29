/**
 * 文件名		：ServiceCheck.java
 * 创建日期	：2013-9-22
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.trade;

import com.fop.framework.service.validate.IValidate;



/**
 * 描述:检查配置对象
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ServiceCheck {
  /**描述*/
  private String desc;
  
  /**验证类实例*/
  private IValidate vobj;
  
  /**待验证变量标识*/
  private String[] src;
  
  /**所处位置*/
  private int index;

  /**
   * @return the desc
   */
  public String getDesc() {
    return desc;
  }

  /**
   * @param desc the desc to set
   */
  public void setDesc(String desc) {
    this.desc = desc;
  }

  /**
   * @return the vobj
   */
  public IValidate getVobj() {
    return vobj;
  }

  /**
   * @param vobj the vobj to set
   */
  public void setVobj(IValidate vobj) {
    this.vobj = vobj;
  }

  /**
   * @return the src
   */
  public String[] getSrc() {
    return src;
  }

  /**
   * @param src the src to set
   */
  public void setSrc(String[] src) {
    if (src != null){
      //去空格处理后，重新添加到集合
      int leng = src.length;
      String[] srcnew = new String[leng];
      for (int i = 0; i < leng; i++){
        srcnew[i] = src[i].trim();
      }
      this.src = srcnew;
      return;
    }
    this.src = src;
  }

  /**
   * @return the index
   */
  public int getIndex() {
    return index;
  }

  /**
   * @param index the index to set
   */
  public void setIndex(int index) {
    this.index = index;
  }
}

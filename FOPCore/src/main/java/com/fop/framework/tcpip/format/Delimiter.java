/**
 * 文件名		：Delimiter.java
 * 创建日期	：2013-11-21
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.format;

/**
 * 描述:TODO.....
 * 
 * @version 1.00
 * @author user
 * 
 */
public class Delimiter {
  
  private String delim;
  
  public Delimiter(){
    this.delim = "|";
  }
  
  public Delimiter(String delim) {
    this.delim = delim;
  }
  
  public void setDelim(String delim) {
    this.delim = delim;
  }

  /**
   * @return delim
   */
  public String getDelim() {
    return delim;
  }

}

/**
 * 文件名		：CProcess.java
 * 创建日期	：2013-10-10
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package test.com.fop.framework.http.web;

import java.util.Map;

import com.fop.framework.context.FOPContext;
import com.fop.framework.data.FieldData;

/**
 * 描述:设置context信息
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class CProcess {

  public void ctrlProcess(FOPContext context) {
    try {
      //设置信息
      FieldData fdata = new FieldData("instr", "is abc");
      context.addFOPData(fdata);
      //      context.addFieldData("instr", "is abc");
      // context.setDataValue("instr", "is abc");
    } catch (Exception e) {
    } 
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void ctrlProcess2(Map context){
    //设置信息
    context.put("instr", "is abc");
  }
}

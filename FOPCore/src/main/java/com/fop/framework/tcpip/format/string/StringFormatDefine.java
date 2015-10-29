/**
 * 文件名		：StringFormatDefine.java
 * 创建日期	：2013-10-23
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.format.string;


import com.fop.framework.context.FOPContext;
import com.fop.framework.data.FOPData;
import com.fop.framework.data.GroupData;
import com.fop.framework.tcpip.format.BaseFormat;
import com.fop.framework.tcpip.format.FormatConfigContainer;
import com.fop.framework.tcpip.format.FormatContainer;
import com.fop.framework.tcpip.format.IFormat;
import com.fop.framework.tcpip.util.TCPTools;

/**
 * 描述:字符串格式化定义类
 * 
 * @version 1.00
 * @author user
 * 
 */
public class StringFormatDefine extends BaseFormat implements IFormat {
  
  private FormatContainer childContainer = new FormatContainer();
  
  private String headName;
  

  public final static String delimiter = "|";

  
  
  /**
   * 格式化数据
   * 
   * @param data
   *          需要格式化的数据
   */
  public void format(FOPData data) {    
    format(new StringBuffer(), data);
  }
  
  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.format.IFormat#format(java.lang.Object, com.fop.framework.data.FOPData)
   */
  public void format(Object buffer, FOPData data) {
    childContainer.formatElement(buffer, data);
    
  }
  
  public void format(Object buffer, FOPContext context) {
    
    childContainer.formatElement(buffer, context);
    if(null == headName){
      return;
    }
      
      IFormat i = FormatConfigContainer.getFormat(headName);
      StringBuffer data = new StringBuffer();
      i.format(data, context);
      
      StringBuffer body = (StringBuffer)buffer;
      String len = TCPTools.paddingStr(String.valueOf(body.length()), '0', true, 6);
      data.append(len);
      body.insert(0, data.toString());
      

   // context.gegetFOPData("user.name").;
   // context.getFieldDataValue(dataName);
  
  }

  
  /**
   * 反格式化数据
   * 
   * @param s
   *          XML要素
   * @return 返回反格式化后数据的对象
   */
  public FOPData unFormat(String s) {
    FOPData data = new GroupData();
    unFormat(s, data);
    return data;
  }
  
  


  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.format.IFormat#unFormat(java.lang.Object, com.fop.framework.data.FOPData)
   */
  @Override
  public void unFormat(Object buffer, FOPData data) {
    childContainer.unFormatElement(buffer, data);
    
  }
  
  public void unFormat(Object buffer, FOPContext context) {
    if(null != headName){
      IFormat i = FormatConfigContainer.getFormat(headName);
      i.unFormat(buffer, context);
    }
    childContainer.unFormatElement(buffer, context);
    
  }


  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.format.IFormat#addChildFormat(com.fop.framework.tcpip.format.IFormat)
   */
  @Override
  public void addChildFormat(IFormat format) {
    childContainer.addIFormat(format);
  }
  
  
  /**
   * @return the headName
   */
  public String getHeadName() {
    return headName;
  }

  /**
   * @param headName the headName to set
   */
  public void setHeadName(String headName) {
    this.headName = headName;
  }

}

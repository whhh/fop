/**
 * 文件名		：FormatField.java
 * 创建日期	：2011-4-25
 * Copyright (c) 2003-2011 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.format.xml;


import org.dom4j.Element;

import com.fop.framework.context.FOPContext;
import com.fop.framework.data.FOPData;
import com.fop.framework.data.FieldData;
import com.fop.framework.data.GroupData;
import com.fop.framework.tcpip.format.BaseFormat;
import com.fop.framework.tcpip.format.IFormat;

/**
 * 描述:格式化数据定义类<br/>
 * 封装格式化数据. 目前有分组数据, 数组数据和单体数据三种
 * 
 * @version 1.00
 * @author User
 * 
 */
public class FormatField extends BaseFormat implements IFormat {

  private String fixedValue;
  // private String index; //数据下标

  /*
   * (non-Javadoc)
   * 
   * @see com.fop.framework.tcpip.format.IFormat#format(org.dom4j.Element,
   * com.fop.framework.data.FOPData)
   */
  public void format(Object object, FOPData data) {
    String fieldValue = null;
    if(null != fixedValue){
      fieldValue = fixedValue;
    }else{
      FOPData fieldData = ((GroupData) data).getFOPData(getDataName());
      if(null == fieldData){
        return;
      }
      fieldValue =  String.valueOf(((FieldData) fieldData).getValue());

   }
    Element element = ((Element)object).addElement(getFieldName());
    element.addText(fieldValue);
  }
  
  public void format(Object object, FOPContext contenxt) {
    String fieldValue = null;
    if(null != fixedValue){
      fieldValue = fixedValue;
    }else{
      FOPData fData = contenxt.getFOPData(getDataName());
      if(null == fData){
        return;
      }
      FieldData fieldData = (FieldData) fData;
      fieldValue = (String)fieldData.getValue();
    }
    if(fieldValue != null) {
      Element element = ((Element)object).addElement(getFieldName());
      element.addText(fieldValue);
    }
   }

  /*
   * (non-Javadoc)
   * 
   * @see com.fop.framework.tcpip.format.IFormat#unFormat(org.dom4j.Element,
   * com.fop.framework.data.FOPData)
   */
  public void unFormat(Object object, FOPData data) {
    if(null == object){
      return;
    }
    String value = ((Element)object).elementText(getFieldName());
    GroupData g = ((GroupData) data);
//    String dataName = getDataName();
//    if(null == value){
//      FieldData field = new FieldData(dataName);
//      g.addFieldData(field);
//      return;
//    }
    g.addFieldData(getDataName(), value);
  }
  
  public void unFormat(Object element, FOPContext context) {
    if(element == null){
      return;
    }
    String value = ((Element)element).elementText(getFieldName());
    if(null != value ){
      context.addFieldData(getDataName(), value);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.fop.framework.tcpip.format.IFormat#format(com.fop.framework.data.FOPData
   * )
   */
  @Override
  public void format(FOPData data) {
    // TODO Auto-generated method stub
  }
  
  @Override
  public void addChildFormat(IFormat format) {
    // TODO Auto-generated method stub
    
  }
  
  /**
   * @return the defaultValue
   */
  public String getFixedValue() {
    return fixedValue;
  }

  /**
   * @param defaultValue the defaultValue to set
   */
  public void setFixedValue(String fixedValue) {
    this.fixedValue = fixedValue;
  }
}

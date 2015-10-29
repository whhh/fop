/**
 * 文件名		：FormatField.java
 * 创建日期	：2011-4-25
 * Copyright (c) 2003-2011 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.format.string;


import com.fop.framework.context.FOPContext;
import com.fop.framework.data.FOPData;
import com.fop.framework.data.FieldData;
import com.fop.framework.data.GroupData;
import com.fop.framework.tcpip.format.BaseFormat;
import com.fop.framework.tcpip.format.Delimiter;
import com.fop.framework.tcpip.format.IFormat;
import com.fop.framework.tcpip.util.TCPTools;

/**
 * 描述:格式化数据定义类<br/>
 * 封装格式化数据. 目前有分组数据, 数组数据和单体数据三种
 * 
 * @version 1.00
 * @author User
 * 
 */
public class StringFormatField extends BaseFormat implements IFormat {
  

  
  private String delim = "true";



  private String fixedValue;
  
  private String dataLen;
  // private String index; //数据下标



  /*
   * (non-Javadoc)
   * 
   * @see com.fop.framework.tcpip.format.IFormat#format(org.dom4j.Element,
   * com.fop.framework.data.FOPData)
   */
  public void format(Object buffer, FOPData data) {
    //Element element = ((Element)ele).addElement(getFieldName());
    StringBuffer buf = (StringBuffer)buffer;
    String fieldValue = null;
    if(null != fixedValue){
      fieldValue = fixedValue;
    }else{
    FOPData fieldData = ((GroupData) data).getFOPData(getDataName());
    
    if(null == fieldData){
      throw new IndexOutOfBoundsException();
    }
    FieldData fData = (FieldData) fieldData;
    
    fieldValue = (String)fData.getValue();
//    if(null != getDataLength()){
//      int dataLength = Integer.parseInt(getDataLength());
//      if(fieldValue.length() < dataLength){
//        fieldValue = TCPTools.paddingStr(fieldValue, ' ', false, dataLength);
//      }
//    }
    }
    buf.append(fieldValue);
    if(true == Boolean.valueOf(delim)){
      buf.append(StringFormatDefine.delimiter);
    }
  }
  
  
  public void format(Object buffer, FOPContext context) {
    StringBuffer buf = (StringBuffer)buffer;
    String fieldValue = null;
    if(null != fixedValue){
      fieldValue = fixedValue;

    }else{
      Object fieldData = context.getFieldDataValue(getDataName());
      
      if(null == fieldData){
        throw new IndexOutOfBoundsException();
      }
      fieldValue = (String)fieldData;
    }
    
    buf.append(fieldValue);
    if(true == Boolean.valueOf(delim)){
      buf.append(StringFormatDefine.delimiter);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.fop.framework.tcpip.format.IFormat#unFormat(org.dom4j.Element,
   * com.fop.framework.data.FOPData)
   */
  public void unFormat(Object buffer, FOPData data) {
    StringBuffer sb = (StringBuffer)buffer;
    int len ;
    int location;
    if(true == Boolean.valueOf(delim)){
      len = sb.indexOf(StringFormatDefine.delimiter);
      location = len +1;
    }else{
      len = Integer.valueOf(dataLen);
      location = len;
    }
    String value = sb.substring(0,len);
    
    sb = sb.delete(0,location);
    
    ((GroupData) data).addFieldData(getDataName(), value);
  }

  public void unFormat(Object buffer, FOPContext context) {
    StringBuffer sb = (StringBuffer)buffer;
    int len ;
    int location;
    if(true == Boolean.valueOf(delim)){
      len = sb.indexOf(StringFormatDefine.delimiter);
      location = len +1;
    }else{
      len = Integer.valueOf(dataLen);
      location = len;
    }
    String value = sb.substring(0,len);
    
    sb = sb.delete(0,location);
    
    context.addFieldData(getDataName(), value);
    
    
    
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
   * @return the delim
   */
  public String getDelim() {
    return delim;
  }

  /**
   * @param delim the delim to set
   */
  public void setDelim(String delim) {
    this.delim = delim;
  }
  
  
  /**
   * @return the dataLen
   */
  public String getDataLen() {
    return dataLen;
  }

  /**
   * @param dataLen the dataLen to set
   */
  public void setDataLen(String dataLen) {
    this.dataLen = dataLen;
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

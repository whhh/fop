package com.fop.framework.tcpip.format.xml;


import java.util.List;

import org.dom4j.Element;

import com.fop.framework.context.FOPContext;
import com.fop.framework.data.FOPData;
import com.fop.framework.data.GroupData;
import com.fop.framework.data.RepeatData;
import com.fop.framework.tcpip.format.BaseFormat;
import com.fop.framework.tcpip.format.FormatContainer;
import com.fop.framework.tcpip.format.IFormat;

/**
 * 描述:字符串格式化循环类
 * 
 * @version 1.00
 * @author user
 * 
 */
public class FormatRepeat extends BaseFormat implements IFormat {

  /**
   * SDO常量
   */
  public final static String SDO = "SDO";
  
  private FormatContainer childContainer = new FormatContainer();

  
  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.format.IFormat#format(java.lang.Object, com.fop.framework.data.FOPData)
   */
  public void format(Object object, FOPData data) {

    Element ele = ((Element)object).addElement(getFieldName());
    FOPData repeatData = ((GroupData) data).getFOPData(getDataName());    
    RepeatData dataTemp =(RepeatData) repeatData;

    for(FOPData childData : dataTemp){
      Element sdoChild = ele.addElement(SDO);
      childContainer.formatElement(sdoChild, childData);
    }
  }
  
  public void format(Object object, FOPContext contenxt) {
    FOPData repeatData = contenxt.getFOPData(getDataName()); 
    if(null == repeatData){
      return;
    }
    RepeatData dataTemp =(RepeatData) repeatData;
    Element ele = ((Element)object).addElement(getFieldName());
    
    for(FOPData childData : dataTemp){
      Element sdoChild = ele.addElement(SDO);
      childContainer.formatElement(sdoChild, childData);
    }
  }


  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.format.IFormat#unFormat(java.lang.Object, com.fop.framework.data.FOPData)
   */
  @SuppressWarnings("unchecked")
  public void unFormat(Object object, FOPData data) {
    Element ele = ((Element)object).element(getFieldName());
    if(null == ele){
      return;
    }
    RepeatData repeatData = new RepeatData(getDataName());
    ((GroupData) data).addRepeatData(repeatData);

    List<Element> eleList = ele.elements(SDO);    
    for(Element eleTemp : eleList){
      GroupData g = new GroupData();
      repeatData.add(g);
      childContainer.unFormatElement(eleTemp, g);
    }

  }
  
  @SuppressWarnings("unchecked")
  public void unFormat(Object element, FOPContext context) {
    if(element == null){
      return;
    }
    Element ele = ((Element)element).element(getFieldName());
    
    if(null == ele){
      return;
    }
    RepeatData repeatData = new RepeatData(getDataName());
    
    context.addFOPData(repeatData);
    
    List<Element> eleList = ele.elements(SDO);
    
    for(Element eleTemp : eleList){
      GroupData g = new GroupData();
      repeatData.add(g);
      childContainer.unFormatElement(eleTemp, g);
    }
    
    
    
    childContainer.unFormatElement(ele, context);
  }


  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.format.IFormat#format(com.fop.framework.data.FOPData)
   */
  @Override
  public void format(FOPData data) {
    // TODO Auto-generated method stub
    
  }
  
  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.format.IFormat#addChildFormat(com.fop.framework.tcpip.format.IFormat)
   */
  @Override
  public void addChildFormat(IFormat format) {
    childContainer.addIFormat(format);
    
  }

}

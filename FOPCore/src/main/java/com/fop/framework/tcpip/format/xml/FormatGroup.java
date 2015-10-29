package com.fop.framework.tcpip.format.xml;

import org.dom4j.Element;

import com.fop.framework.context.FOPContext;
import com.fop.framework.data.FOPData;
import com.fop.framework.data.GroupData;
import com.fop.framework.tcpip.format.BaseFormat;
import com.fop.framework.tcpip.format.FormatContainer;
import com.fop.framework.tcpip.format.IFormat;

/**
 * 描述:字符串格式化组类
 * 
 * @version 1.00
 * @author user
 * 
 */
public class FormatGroup extends BaseFormat implements IFormat {

  private FormatContainer childContainer = new FormatContainer();



  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.format.IFormat#format(java.lang.Object, com.fop.framework.data.FOPData)
   */
  public void format(Object object, FOPData data) {
    Element ele = ((Element)object).addElement(getFieldName());
    FOPData childData = ((GroupData) data).getFOPData(getDataName());
    
    childContainer.formatElement(ele, childData);
  }
  
  public void format(Object object, FOPContext contenxt) {
    Element ele = ((Element)object).addElement(getFieldName());
    childContainer.formatElement(ele, contenxt);
  }


  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.format.IFormat#unFormat(java.lang.Object, com.fop.framework.data.FOPData)
   */
  public void unFormat(Object object, FOPData data) {
    if(object == null){
      return;
    }
    Element ele = ((Element)object).element(getFieldName());

    GroupData childData = new GroupData(getDataName());
    ((GroupData) data).addGroupData(childData);

    
    childContainer.unFormatElement(ele, childData);
  }
  
  public void unFormat(Object element, FOPContext context) {
    if(element == null){
      return;
    }
    Element ele = ((Element)element).element(getFieldName());
    
    if(null == ele){
      return;
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

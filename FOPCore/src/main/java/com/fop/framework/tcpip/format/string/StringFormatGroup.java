package com.fop.framework.tcpip.format.string;

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
public class StringFormatGroup extends BaseFormat implements IFormat {

  private FormatContainer childContainer = new FormatContainer();


  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.format.IFormat#format(java.lang.Object, com.fop.framework.data.FOPData)
   */
  public void format(Object buffer, FOPData data) {
    //Element ele = ((Element)element).addElement(getFieldName());
    FOPData childData = ((GroupData) data).getFOPData(getDataName());
    
    childContainer.formatElement(buffer, childData);
  }
  
  public void format(Object buffer, FOPContext context) {
    childContainer.formatElement(buffer, context);
  }


  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.format.IFormat#unFormat(java.lang.Object, com.fop.framework.data.FOPData)
   */
  public void unFormat(Object buffer, FOPData data) {
    GroupData childData = new GroupData(getDataName());
    ((GroupData) data).addGroupData(childData);
    //Element ele = ((Element)buffer).element(getFieldName());
    
    childContainer.unFormatElement(buffer, childData);
  }
  
  public void unFormat(Object buffer, FOPContext context) {
    childContainer.unFormatElement(buffer, context);
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

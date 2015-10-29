package com.fop.framework.tcpip.format.xml;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.fop.framework.context.FOPContext;
import com.fop.framework.data.FOPData;
import com.fop.framework.data.GroupData;
import com.fop.framework.tcpip.format.BaseFormat;
import com.fop.framework.tcpip.format.FormatConfigContainer;
import com.fop.framework.tcpip.format.FormatContainer;
import com.fop.framework.tcpip.format.IFormat;

/**
 * 描述:格式化定义类
 * 
 * @version 1.00
 * @author user
 * 
 */
public class FormatDefine extends BaseFormat implements IFormat {


  /**
   * SDOROOT常量
   */
  public final static String SDOROOT = "SDOROOT";

  private Document document = DocumentHelper.createDocument();// 创建XML文件

  private FormatContainer childContainer = new FormatContainer();//节点容器
  
  private String headName;
  

  /**
   * 格式化数据
   * 
   * @param data
   *          需要格式化的数据
   */
  public void format(FOPData data) {
    format(createXml(), data);
  }



  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.format.IFormat#format(java.lang.Object, com.fop.framework.data.FOPData)
   */
  public void format(Object element, FOPData data) {
    childContainer.formatElement(element, data);
  }
  
  public void format(Object element, FOPContext contenxt) {
    if(null != headName){
      IFormat i = FormatConfigContainer.getFormat(headName);
      i.format(element, contenxt);
    }
    childContainer.formatElement(element, contenxt);
  }

  /**
   * 反格式化数据
   * 
   * @param element
   *          XML要素
   * @return 返回反格式化后数据的对象
   */
  public FOPData unFormat(Element element) {
    FOPData data = new GroupData();
    unFormat(element, data);
    return data;
  }

  /**
   * 反格式化数据
   * 
   * @param element
   *          XML要素
   * @param data
   *          存放反格式化后数据的对象
   */
  public void unFormat(Object element, FOPData data) {
    childContainer.unFormatElement(element, data);
  }
  
  public void unFormat(Object element, FOPContext context) {
    if(null != headName){
      IFormat i = FormatConfigContainer.getFormat(headName);
      i.unFormat(element, context);
    }
    childContainer.unFormatElement(element, context);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return document.asXML();
  }

  /**
   * 创建XML对象
   * 
   * @return 返回XML要素对象
   */
  public Element createXml() {
    document.clearContent();
    setFieldName(SDOROOT);
    return document.addElement(SDOROOT);
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

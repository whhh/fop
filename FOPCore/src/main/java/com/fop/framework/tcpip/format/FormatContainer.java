/**
 * 文件名		：FormatContainer.java
 * 创建日期	：2013-10-23
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.format;

import java.util.ArrayList;
import java.util.List;

import com.fop.framework.context.FOPContext;
import com.fop.framework.data.FOPData;

/**
 * 描述:节点容器
 * 
 * @version 1.00
 * @author user
 * 
 */
public class FormatContainer {

  private List<IFormat> elements = new ArrayList<IFormat>();

  /**
   * 遍历节点容器，调用子节点格式化
   * 
   * @param element
   *          格式化类型对象
   * @param data
   *          需要格式化的数据对象
   */
  public void formatElement(Object element, FOPData data) {
    for (IFormat format : elements) {
      format.format(element, data);
    }
  }
  
  /**
   * 遍历节点容器，调用子节点格式化
   * 
   * @param element
   *          格式化类型对象
   * @param context
   *          需要格式化的数据对象
   */
  public void formatElement(Object element, FOPContext context) {
    for (IFormat format : elements) {
      format.format(element, context);
    }
  }

  /**
   * 遍历节点容器，调用子节点反格式化
   * 
   * @param element
   *          反格式化类型对象
   * @param data
   *          需要反格式化的数据对象
   */
  public void unFormatElement(Object element, FOPData data) {
    for (IFormat format : elements) {
      format.unFormat(element, data);
    }
  }
  
  
  public void unFormatElement(Object element, FOPContext context) {
    for (IFormat format : elements) {
      format.unFormat(element, context);
    }
  }

  /**
   * 添加子节点格式化对象
   * 
   * @param format
   *          格式化对象
   */
  public void addIFormat(IFormat format) {
    elements.add(format);
  }

  /**
   * 添加子节点格式化对象
   * 
   * @param format
   *          格式化对象
   */
  public void removeIFormat(IFormat format) {
    elements.add(format);
  }

}

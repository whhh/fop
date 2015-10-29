package com.fop.framework.tcpip.format;


import com.fop.framework.context.FOPContext;
import com.fop.framework.data.FOPData;

/**
 * 描述:格式化接口类，所有格式化类型要素需要实现此接口
 * 
 * @version 1.00
 * @author user
 * 
 */
public interface IFormat {

  /**
   * 格式化数据
   * 
   * @param data
   *          需要格式化的数据对象
   */
  void format(FOPData data);


  /**
   * 格式化数据
   * 
   * @param object
   *          格式化后对象
   * @param data
   *          需要格式化的数据对象
   */
  void format(Object object, FOPData data);
  
  
  
  /**
   * 格式化数据
   * 
   * @param object
   *          格式化后对象
   * @param context
   *          需要格式化的数据对象
   */
  void format(Object object, FOPContext context);

  /**
   * 反格式化数据
   * 
   * @param object
   *          反格式化后对象
   * @param data
   *          需要反格式化的数据对象
   */
  void unFormat(Object object, FOPData data);

  void unFormat(Object object, FOPContext context);
  /**
   * 设置数据名字
   * 
   * @param dataName
   */
  void setDataName(String dataName);

  /**
   * 获取数据名称
   * 
   * @return
   */
  String getDataName();
  
  
  
  /**
   * 添加子格式化类
   * 
   * @param format
   */
  void addChildFormat(IFormat format);

}

/**
 * 文件名		：GroupData.java
 * 创建日期	：2013-8-27
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述:分组数据对象
 * 分组数据对象是逻辑上的分组, 保存的是多个数据的集合
 * 分组数据对象可以包含多个单个数据对象、分组数据对象和重复数据对象
 * 例如, 一个用户的完整信息可以用分组数据对象保存, 其完整的XML描述如下:
 * <group id="user">
 *  <field id="name" value="张三" />
 *  <field id="age" value="20" />
 *  <group id="connectType">
 *    <field id="phone" value="0755-12345678" />
 *    <field id="mobile" value="18011111111" />
 *    <field id="qq" value="20382039" />
 *  </group>
 *  <repeat id="accInfoList">
 *   <group>
 *     <field id="accNo" value="11111111" />
 *     <field id="accName"  value="活期账户" />
 *   </group>
 *   <group>
 *     <field id="accNo"  value="22222222" />
 *     <field id="accName"  value="定期账户" />
 *   </group>
 *  </repeat>
 * </group>
 * 通过名称查找分组数据对象里的某个数据对象或值时, 名称可以是个表达式
 * 名称表达式支持"[]"和".", 其中"[]"是指定重复数据对象的下标, "."用来获取分组数据对象的子节点
 * 以上面XML描述的用户信息为例:
 * getFOPData("user.name")的结果是单个数据对象, 其id为"name"
 * getFOPData("user.accInfoList[1].accNo")的结果是单个数据对象, 其id为"accNo"
 * getFieldData("user.accInfoList[1].accNo")的结果是字符串, 值为"22222222"
 * getFieldData("user.accInfoList[1].accNo")和getFOPData("user.accInfoList[1].accNo").getValue()结果是一致的
 * 
 * @version 1.00
 * @author Dustin
 * 
 */
public class GroupData extends FOPData implements Map<String, FOPData> {
  
  //数据对象集合
  private Map<String, FOPData> dataElements;
  
  //数据对象名称列表
  private List<String> dataNames;
  
  /**
   * 构造函数
   */
  public GroupData() {
    this.dataElements = new HashMap<String, FOPData>();
    this.dataNames = new ArrayList<String>();
  }
  
  /**
   * 构造函数
   * @param name 名称
   */
  public GroupData(String name) {
    super(name);
    this.dataElements = new HashMap<String, FOPData>();
    this.dataNames = new ArrayList<String>();
  }
  
  /**
   * 获取指定名称的数据对象
   * 如果指定名称的数据对象不存在, 则返回NULL
   * @param dataName 指定的名称
   * @return 获取到的数据对象
   */
  public FOPData getFOPData(String dataName) {
    if (dataName == null) {
      return null;
    }
    
    int idx = dataName.indexOf('.');
    
    if (idx != -1) {
      String mbsDataName = dataName.substring(0, idx);
     
      int eidx = mbsDataName.indexOf("[");
      if ((mbsDataName.charAt(idx - 1) == ']') && (eidx != -1)) {
        //重复数据对象
        int pos = Integer.parseInt(mbsDataName.substring(eidx + 1, idx - 1));
        String repeatName = mbsDataName.substring(0, eidx);
        RepeatData repeat = (RepeatData)this.dataElements.get(repeatName);
        
        if (repeat == null) {
          return null;
        }
        
        return repeat.getGroupDataAt(pos).getFOPData(dataName.substring(idx + 1));
      }

      //分组数据对象
      String groupName = mbsDataName;
      GroupData group = (GroupData)this.dataElements.get(groupName);

      if (group == null) {
        return null;
      }
      
      return group.getFOPData(dataName.substring(idx + 1));
    }

    //单体数据对象
    FOPData field = this.dataElements.get(dataName);
    if (field != null) {
      return field;
    }
    
    return null;
  }
  
  /**
   * 添加数据对象
   * 如果已经存在相同名称的数据对象, 则覆盖原来的数据对象
   * @param dataElement 数据对象
   */
  public void addFOPData(FOPData dataElement) {
    this.dataElements.put(dataElement.getName(), dataElement);
    if (!dataNames.contains(dataElement.getName())) {
      this.dataNames.add(dataElement.getName());
    }
  }
  
  /**
   * 添加分组数据对象
   * @param dataElement 分组数据对象
   * @return 被添加的分组数据对象
   */
  public GroupData addGroupData(GroupData dataElement) {
    addFOPData(dataElement);
    return dataElement;
  }
  
  /**
   * 添加重复数据对象
   * @param dataElement 重复数据对象
   * @return 被添加的重复数据对象
   */
  public RepeatData addRepeatData(RepeatData dataElement) {
    addFOPData(dataElement);
    return dataElement;
  }
  
  /**
   * 添加单个数据对象
   * @param dataElement 单个数据对象
   * @return 被添加的单个数据对象
   */
  public FieldData addFieldData(FieldData dataElement) {
    addFOPData(dataElement);
    return dataElement;
  }
  
  /**
   * 添加单个数据对象
   * @param name 单个数据对象的名称
   * @param value 单个数据对象的值
   * @throws InvalidArgumentException 如果名称为空, 则抛出参数错误异常
   */
  public void addFieldData(String name, Object value) {
    if (name == null) {
      throw new InvalidArgumentException("FieldData's name should not be null!");
    }
    
    FieldData field = new FieldData(name, value);
    addFOPData(field);
  }
  
  /**
   * 移除指定名称的数据对象
   * 如果指定名称的数据对象不存在, 则返回NULL
   * @param dataName 指定的数据对象名称
   * @return 被移除的数据对象
   */
  public FOPData removeFOPData(String dataName) {
    this.dataNames.remove(dataName);
    return this.dataElements.remove(dataName);
  }
  
  /**
   * 获取指定名称的单个数据对象的值
   * 如果指定名称的单个数据不存在, 或者指定名称的数据对象不是单个数据, 则返回NULL
   * @param dataName 指定的单个数据对象的名称
   * @return Object 要获取的单个数据对象的值
   */
  public Object getFieldDataValue(String dataName) {
    FOPData element = getFOPData(dataName);
    
    if ((element != null) && (element instanceof FieldData)) {
      return ((FieldData) element).getValue();
    }
    
    return null;
  }
  
  /**
   * 设置指定名称的单个数据对象的值
   * 新设置的值会覆盖旧值
   * @param dataName 指定的单个数据名称
   * @param value 要设置的值
   * @throws ObjectNotFoundException 如果指定名称的单个数据不存在, 或者指定名称的数据对象不是单个数据, 则返回对象未找到异常
   */
  public void setFieldDataValue(String dataName, Object value) {
    FOPData element = getFOPData(dataName);
    
    if ((element != null) && (element instanceof FieldData)) {
      ((FieldData) element).setValue(value);
    } else {
      throw new ObjectNotFoundException("FieldData named[" + dataName + "] is not found!");
    }
  }
  
  /* (non-Javadoc)
   * @see com.chinamworld.mbs.data.FOPData#clone()
   */
  @Override
  public GroupData clone() {
    GroupData group = new GroupData(getName());
    group.setAppend(isAppend());
    
    for (int i = 0; i < this.dataNames.size(); i++) {
      FOPData element = this.dataElements.get(this.dataNames.get(i));

      element = (FOPData)element.clone();
      try {
        group.addFOPData(element);
      } catch (Exception e) {
      }
    }
    
    return group;
  }

  /* (non-Javadoc)
   * @see com.chinamworld.mbs.data.FOPData#reset()
   */
  @Override
  public void reset() {
  }

  /* (non-Javadoc)
   * @see com.chinamworld.mbs.data.FOPData#toString()
   */
  @Override
  public String toString() {
    return toString(0);
  }

  /* (non-Javadoc)
   * @see com.chinamworld.mbs.data.FOPData#toString(int)
   */
  @Override
  public String toString(int tabCount) {
    StringBuffer buf = new StringBuffer();

    for (int i = 0; i < tabCount; i++) {
      buf.append("\t");
    }
    buf.append("<group id=\"");
    buf.append(getName());
    buf.append("\" append=\"" + isAppend() + "\">\n");

    for (int i = 0; i < this.dataNames.size(); i++) {
      String dataName = (String)this.dataNames.get(i);
      FOPData element = (FOPData)this.dataElements.get(dataName);
      buf.append(element.toString(tabCount + 1));
      buf.append("\n");
    }

    for (int i = 0; i < tabCount; i++) {
      buf.append("\t");
    }
    buf.append("</group>");

    return buf.toString();
  }

  /* (non-Javadoc)
   * @see com.chinamworld.mbs.data.FOPData#setAppend(boolean)
   */
  @Override
  public void setAppend(boolean append) {
    super.setAppend(append);
    Iterator<String> nameList = this.dataNames.iterator();
    while (nameList.hasNext()) {
      (this.dataElements.get(nameList.next())).setAppend(append);
    }
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  /* (non-Javadoc)
   * @see java.util.Map#size()
   */
  @Override
  public int size() {
    if (this.dataNames == null) {
      return 0;
    }
    
    return this.dataNames.size();
  }

  /* (non-Javadoc)
   * @see java.util.Map#isEmpty()
   */
  @Override
  public boolean isEmpty() {
    return (this.dataNames == null) || (this.dataNames.size() == 0);
  }

  /* (non-Javadoc)
   * @see java.util.Map#containsKey(java.lang.Object)
   */
  @Override
  public boolean containsKey(Object key) {
    if (this.dataElements == null) {
      return false;
    }
    
    return this.dataElements.containsKey(key);
  }

  /* (non-Javadoc)
   * @see java.util.Map#containsValue(java.lang.Object)
   */
  @Override
  public boolean containsValue(Object value) {
    if (this.dataElements == null) {
      return false;
    }
    
    return this.dataElements.containsValue(value);
  }

  /* (non-Javadoc)
   * @see java.util.Map#get(java.lang.Object)
   */
  @Override
  public FOPData get(Object key) {
    if (this.dataElements == null) {
      return null;
    }
    
    return getFOPData(key.toString());
  }

  /* (non-Javadoc)
   * @see java.util.Map#put(java.lang.Object, java.lang.Object)
   */
  @Override
  public FOPData put(String key, FOPData value) {
    value.setId(key);
    addFOPData(value);
    
    return value;
  }

  /* (non-Javadoc)
   * @see java.util.Map#remove(java.lang.Object)
   */
  @Override
  public FOPData remove(Object key) {
    return removeFOPData(key.toString());
  }

  /* (non-Javadoc)
   * @see java.util.Map#putAll(java.util.Map)
   */
  @Override
  public void putAll(Map m) {
  }

  /* (non-Javadoc)
   * @see java.util.Map#clear()
   */
  @Override
  public void clear() {
    if (this.dataElements != null) {
      this.dataNames.clear();
      this.dataElements.clear();
    }
  }

  /* (non-Javadoc)
   * @see java.util.Map#keySet()
   */
  @Override
  public Set<String> keySet() {
    if (this.dataElements != null) {
      return this.dataElements.keySet();
    }
    
    return null;
  }

  /* (non-Javadoc)
   * @see java.util.Map#values()
   */
  @Override
  public Collection<FOPData> values() {
    if (this.dataElements != null) {
      return this.dataElements.values();
    }
    
    return null;
  }

  /* (non-Javadoc)
   * @see java.util.Map#entrySet()
   */
  @Override
  public Set<Map.Entry<String, FOPData>> entrySet() {
    if (this.dataElements != null) {
      return this.dataElements.entrySet();
    }
    
    return null;
  }
}

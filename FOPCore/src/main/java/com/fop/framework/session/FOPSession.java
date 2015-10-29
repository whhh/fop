/**
 * 文件名		：MBSSession.java
 * 创建日期	：2013-9-6
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.session;

import java.util.Set;

import com.fop.framework.data.FieldData;
import com.fop.framework.data.GroupData;

/**
 * 描述:会话实现类
 * 
 * @version 1.00
 * @author Dustin
 * 
 */
public class FOPSession implements Session {
  
  //新会话标识, true-新会话, false-非新会话, 默认为true
  private boolean isNew = true;
  
  //会话ID
  private String sessionId;
  
  //会话创建时间
  private long createTime;
  
  //会话最后访问时间
  private long lastAccessTime;
  
  //会话数据集合
  private GroupData attributes;
  
  /**
   * 构造函数
   * <p>初始化创建时间, 最后访问时间, 会话数据集合</p>
   */
  public FOPSession() {
    this.createTime = System.currentTimeMillis();
    this.lastAccessTime = this.createTime;
    this.attributes = new GroupData();
  }
  
  /**
   * 构造函数
   * <p>初始化会话ID, 创建时间, 最后访问时间, 会话数据集合</p>
   * @param sessionId 会话ID
   */
  public FOPSession(String sessionId) {
    this.sessionId = sessionId;
    this.createTime = System.currentTimeMillis();
    this.lastAccessTime = this.createTime;
    this.attributes = new GroupData();
  }

  /* (non-Javadoc)
   * @see com.chinamworld.mbs.session.Session#getId()
   */
  @Override
  public String getId() {
    return this.sessionId;
  }

  /* (non-Javadoc)
   * @see com.chinamworld.mbs.session.Session#isNew()
   */
  @Override
  public boolean isNew() {
    return this.isNew;
  }

  /**
   * 设置会话数据
   * 如果指定名称的数据不存在, 添加新的数据
   * 如果指定名称的数据已存在, 修改现有数据的值
   */
  @Override
  public void setAttribute(String name, Object value) {
    try {
      this.attributes.addFieldData(name, value);
    } catch (Exception e) {
      this.attributes.put(name, new FieldData(name, value));
    }
  }

  /**
   * 通过名称获取会话数据的值
   * 如果不存在指定名称的会话数据则返回NULL
   */
  @Override
  public Object getAttribute(String name) {
    try {
      return this.attributes.getFieldDataValue(name);
    } catch (Exception e) {
      return null;
    }
  }

  /* (non-Javadoc)
   * @see com.chinamworld.mbs.session.Session#removeAttribute(java.lang.String)
   */
  @Override
  public void removeAttribute(String name) {
    this.attributes.removeFOPData(name);
  }

  /* (non-Javadoc)
   * @see com.chinamworld.mbs.session.Session#getAttributeNames()
   */
  @Override
  public Set<String> getAttributeNames() {
    return this.attributes.keySet();
  }

  /**
   * @return the sessionId
   */
  public String getSessionId() {
    return sessionId;
  }

  /**
   * @param sessionId the sessionId to set
   */
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  /**
   * @return the createTime
   */
  public long getCreateTime() {
    return createTime;
  }

  /**
   * @param createTime the createTime to set
   */
  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  /**
   * @return the lastAcessTime
   */
  public long getLastAccessTime() {
    return lastAccessTime;
  }

  /**
   * @param lastAcessTime the lastAcessTime to set
   */
  public void setLastAccessTime(long lastAccessTime) {
    this.lastAccessTime = lastAccessTime;
  }

  /**
   * @param isNew the isNew to set
   */
  public void setNew(boolean isNew) {
    this.isNew = isNew;
  }

}

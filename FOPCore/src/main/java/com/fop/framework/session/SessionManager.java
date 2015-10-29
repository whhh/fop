/**
 * 文件名		：SessionManager.java
 * 创建日期	：2013-9-6
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.session;

import java.util.Map;

/**
 * 描述:会话管理接口.
 * 管理会话，提供对会话的增、删、改、查和统计等功能
 * 
 * @version 1.00
 * @author Dustin
 * 
 */
public interface SessionManager {
  
  /**
   * 通过会话ID获取会话
   * 如果ID不存在则返回NULL
   * @param sessionId 会话ID
   * @return 会话
   */
  public abstract Session getSession(String sessionId);
  
  /**
   * 通过ID和新建标识获取会话
   * 首先查看是否存在指定ID的会话，如果存在则返回，如果不存在且新建标识为true，则新建
   * 一个会话并返回，否则返回NULL
   * @param sessionId 会话ID
   * @param newFlag 新建标识 true-新建; false-不新建
   * @return 会话
   */
  public abstract Session getSession(String sessionId, boolean newFlag);
  
  /**
   * 获取当前管理的所有会话
   * @return 会话的集合
   */
  public abstract Map<String, Session> getSessions();

  /**
   * 设置会话
   * 如果会话不存在，则新增此会话，如果会话已存在，则覆盖原有的会话
   * @param session 会话对象
   */
  public abstract void setSession(Session session);

  /**
   * 设置多个会话
   * 一次性设置多个会话，每个会话的设置操作和setSession(Session)的操作一致
   * @param sessions
   */
  public abstract void setSessions(Map<String, Session> sessions);

  /**
   * 通过会话ID删除会话
   * @param sessionId 会话ID
   */
  public abstract void removeSession(String sessionId);

  /**
   * 删除指定的会话对象
   * @param session 需要删除的会话
   */
  public abstract void removeSession(Session session);

  /**
   * 统计当前管理的会话数目
   * @return 会话数目
   */
  public abstract int getSessionCount();

  /**
   * 终止管理
   */
  public abstract void terminate();

}

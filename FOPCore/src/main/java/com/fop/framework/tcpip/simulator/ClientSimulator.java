/**
 * 文件名		：ClientSimulator.java
 * 创建日期	：2013-12-24
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.tcpip.simulator;

import java.io.File;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fop.framework.context.FOPContext;
import com.fop.framework.data.GroupData;
import com.fop.framework.data.RepeatData;

/**
 * 描述:做为客户端请求核心：挡板识别处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class ClientSimulator {

  /** 日志打印对象 **/
  private static Log logger = LogFactory.getLog(ClientSimulator.class);

  /** 挡板配置 */
  public static ClientSimulatorConfig clientSimulatorConfig;

  /**
   * @return the clientSimulatorConfig
   */
  public static ClientSimulatorConfig getClientSimulatorConfig() {
    return clientSimulatorConfig;
  }

  /**
   * @param clientSimulatorConfig
   *          the clientSimulatorConfig to set
   */
  public static void setClientSimulatorConfig(
      ClientSimulatorConfig clientSimulatorConfig) {
    ClientSimulator.clientSimulatorConfig = clientSimulatorConfig;
  }

  /**
   * 挡板识别处理
   * 
   * @param txcode
   *          交易码
   * @param message
   *          上下文
   * @param timeOutValue
   *          超时时间
   * @return 回值：List index0:Boolean true.走挡板 false.走真实核心 index1:Object
   */
  public static Object process(String txcode, Object message, Long timeOutValue) {
    // 定义返回值：List(index0:Boolean, index1:Object)
    // List listflag = new ArrayList();
    FOPContext context = null; // 上下文

    // 从配置文件中读取
    if (clientSimulatorConfig == null) {
      // clientSimulatorConfig =
      // (ClientSimulatorConfig)ApplicationContextUtil.getBean(ClientSimulatorConfig.class);
      return null;
    }
    Map<String, String> mapConfig = clientSimulatorConfig.getConfigs();
    if ("1".equals(mapConfig.get(txcode))) { // 表示匹配为挡板交易
      logger.info("----txcode is: " + txcode + ",simulator flag is: 1");
      try {
        // 取得指定的文件
        String fileName = new File(ClientSimulator.class.getClassLoader()
            .getResource("/").getPath()).getParentFile().getParent();
        fileName = fileName + "/META-INF/simulator/" + txcode + ".properties";
        // 取得上下文
        context = (FOPContext) message;
        // 填充上下文
        getMessage(fileName, txcode, context);
      } catch (Exception e) {
        logger.warn("----ClientSimulator process error1：" + e.getMessage());
      }
      return message;
    } else {
      return null;// listflag.add(false);
    }
    // return listflag;
  }

  /**
   * 根据挡板文件填充上下文
   * 
   * @param fileName
   *          挡板文件
   * @param txCode
   *          交易码
   * @param context
   *          上下文
   */
  private static void getMessage(String fileName, String txCode,
      FOPContext context) {
    // 读取挡板数据文件
    Properties prop = new Properties();
    try {
      prop.load(new FileReader(new File(fileName)));
    } catch (Exception e) {
      logger.warn("----ClientSimulator process error3：" + e.getMessage());
    }

    // 挡板数据填充
    Enumeration<?> keys = prop.keys();
    String key;
    while (keys.hasMoreElements()) {
      key = (String) keys.nextElement();
      int idx = key.indexOf(".");
      if (idx != -1) {
        // 对带小数点的key分解为字符串数组
        String[] datanames = key.split("\\.");
        String dataname = datanames[0]; // 取值变量名
        int dataindex = 0; // 取值变量下标
        if (datanames[0].indexOf("[") != -1) { // 如为abc[0]这样的格式，做进一步分解
          dataname = getPreIndex(datanames[0]);
          dataindex = getCurrentIndex(datanames[0]);
        }
        if (context.getFOPData(dataname) != null) {
          // 首个串做为变量名取值不为null，直接使用RepeatData对象
          RepeatData rp = ((RepeatData) context.getFOPData(dataname));
          if (dataindex >= rp.size()) {
            // 如果索引超出已知大小，继续创建新的GroupData
            rp.addGroupData(new GroupData());
          }
          rp.getGroupDataAt(dataindex).addFieldData(datanames[1],
              prop.getProperty(key));
        } else {
          // 首个串做为变量名取值为null，定义一个新的RepeatData对象
          RepeatData rp = new RepeatData(dataname);
          rp.addGroupData(new GroupData());
          context.addFOPData(rp);
          ((RepeatData) context.getFOPData(dataname)).getGroupDataAt(dataindex)
              .addFieldData(datanames[1], prop.getProperty(key));
        }
      } else {
        // 没有带小数点的变量直接赋值
        context.addFieldData(key, prop.getProperty(key));
      }
      logger.info("key=" + key + ";value=" + prop.getProperty(key));
    }
  }

  /**
   * 将字符串abc[0]的形式，取值abc
   * 
   * @param strSrc
   *          源串
   * @return
   */
  private static String getPreIndex(String strSrc) {
    if (strSrc.indexOf("[") != -1) {
      String[] strNew = strSrc.split("\\[");
      return strNew[0]; // 返回取值后的串
    }
    // 返回源串
    return strSrc;
  }

  /**
   * 将字符串abc[0]的形式，取值0
   * 
   * @param strSrc
   *          源串
   * @return
   */
  private static int getCurrentIndex(String strSrc) {
    int ibegin = strSrc.indexOf("[");
    int iend = strSrc.indexOf("]");
    if ((ibegin != -1) && (iend > ibegin)) {
      String strNew = strSrc.substring(ibegin + 1, iend);
      int icurrentIndex = Integer.parseInt(strNew);
      return icurrentIndex; // 返回当前索引值
    }
    // 返回0
    return 0;
  }

  // public static void main(String[] args){
  // String str = getPreIndex("abc[0]");
  // System.out.println("str==" + str);
  // int index = getCurrentIndex("abc[0]");
  // System.out.println("index==" + index);
  // }
}

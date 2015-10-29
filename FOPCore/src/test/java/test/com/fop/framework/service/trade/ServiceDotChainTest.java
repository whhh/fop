/**
 * 文件名		：ServiceDotChainTest.java
 * 创建日期	：2013-10-23
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package test.com.fop.framework.service.trade;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import test.com.fop.framework.base.BaseApplicationContext;
import test.com.mbs.manage.busi.dao.TXADao;
import test.com.mbs.manage.busi.dao.TXBDao;
import test.com.mbs.manage.busi.dot.TXDot1;
import test.com.mbs.manage.busi.dot.TXDot2;
import test.com.mbs.manage.busi.dot.TXDot3;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.context.ApplicationContextUtil;
import com.fop.framework.context.FOPContext;

/**
 * 描述:测试数据库事务管理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
@SuppressWarnings("all")
public class ServiceDotChainTest {
  /**A表参数**/
  public static final String RMFLAGA = "rmflaga";
  public static final String RMIDA = "rmida";
  public static final String MAPPARAMA = "mapparama";
  /**B表参数**/
  public static final String RMFLAGB = "rmflagb";
  public static final String RMIDB = "rmidb";
  public static final String MAPPARAMB = "mapparamb";
  /**初始值**/
  public static final String INITPARAMVALUE = "FLAG1";
  /**操作对象**/
  private TXDot1 dot1;
  private TXDot2 dot2;
  private TXDot3 dot3;
  private TXADao daoa;
  private TXBDao daob;
  /**Dot上下文**/
  private FOPContext dotContext;
    
  /**
   * 共用资源
   */
  private void initSource(){
    //加载配置
    BaseApplicationContext.getApplicationContext();
    //获取bean
    dot1 = (TXDot1)ApplicationContextUtil.getBean(TXDot1.class);
    dot2 = (TXDot2)ApplicationContextUtil.getBean(TXDot2.class);
    dot3 = (TXDot3)ApplicationContextUtil.getBean(TXDot3.class);
    daoa = (TXADao)ApplicationContextUtil.getBean(TXADao.class);
    daob = (TXBDao)ApplicationContextUtil.getBean(TXBDao.class);

    //定义context
    dotContext = new FOPContext(FOPConstants.CONTEXT_DOT);
    
    //更新A、B表数据为初始值
    //定入输入参数--A表
    Map mapa = new HashMap();
    mapa.put(RMFLAGA, INITPARAMVALUE);
    mapa.put(RMIDA, "A1");
    dotContext.addFieldData(ServiceDotChainTest.MAPPARAMA, mapa);

    //定入输入参数--B表
    Map mapb = new HashMap();
    mapb.put(RMFLAGB, INITPARAMVALUE);
    mapb.put(RMIDB, "B1");
    dotContext.addFieldData(ServiceDotChainTest.MAPPARAMB, mapb);
    
    //调用方法
    try {
      dot1.process(dotContext);
    } catch (Exception e) {
      System.out.println("err0======" + e.getMessage());
    }
  }
  
  /**
   * 1.测试只有一个dot的情况---B表执行出错，A表跟着记录回滚
   */
  //@Test
  public void testTXBError(){
    //加载资源
    initSource();
    
    //定入输入参数--A表
    String rmflaga = "FLAGaaaa";
    String rmida = "A1";
    Map mapa = new HashMap();
    mapa.put(RMFLAGA, rmflaga);
    mapa.put(RMIDA, rmida);
    dotContext.addFieldData(ServiceDotChainTest.RMIDA, rmida);
    dotContext.addFieldData(ServiceDotChainTest.RMFLAGA, rmflaga);
    dotContext.addFieldData(ServiceDotChainTest.MAPPARAMA, mapa);

    //定入输入参数--B表
    String rmflagb = "FLAGbbbbsss888990";  //模拟超出10位长度，执行将报错
    String rmidb = "B1";
    Map mapb = new HashMap();
    mapb.put(RMFLAGB, rmflagb);
    mapb.put(RMIDB, rmidb);
    dotContext.addFieldData(ServiceDotChainTest.RMIDB, rmidb);
    dotContext.addFieldData(ServiceDotChainTest.MAPPARAMB, mapb);
    
    //调用方法
    try {
      dot1.process(dotContext);
    } catch (Exception e) {
      System.out.println("err1======" + e.getMessage());
    }
    
    //断言
    String rmflagResultA = null;
    String rmflagResultB = null;
    try {
      rmflagResultA = daoa.queryA(rmida);
      System.out.println("rmflagResultA======" + rmflagResultA);
      rmflagResultB = daob.queryB(rmidb);
      System.out.println("rmflagResultB======" + rmflagResultB);
    } catch (Exception e) {
      System.out.println("err2======" + e.getMessage());
    }
    String oldrmflaga = INITPARAMVALUE; //A表原记录
    assertEquals(rmflagResultA, oldrmflaga); //判断A表记录是否已被修改，查询结果等于原记录，表示已回滚
    String oldrmflagb = INITPARAMVALUE;
    assertEquals(rmflagResultB, oldrmflagb); //判断B表记录是否已被修改，查询结果等于原记录，表示未修改成功
    
  }
  
  /**
   * 2.测试只有一个dot的情况---A、B表记录都修改成功
   */
  //@Test
  public void testTXABSuccess(){
    //加载资源
    initSource();
    
    //定入输入参数--A表
    String rmflaga = "FLAGaa";
    String rmida = "A1";
    Map mapa = new HashMap();
    mapa.put(RMFLAGA, rmflaga);
    mapa.put(RMIDA, rmida);
    dotContext.addFieldData(ServiceDotChainTest.RMIDA, rmida);
    dotContext.addFieldData(ServiceDotChainTest.RMFLAGA, rmflaga);
    dotContext.addFieldData(ServiceDotChainTest.MAPPARAMA, mapa);

    //定入输入参数--B表
    String rmflagb = "FLAGbb"; 
    String rmidb = "B1";
    Map mapb = new HashMap();
    mapb.put(RMFLAGB, rmflagb);
    mapb.put(RMIDB, rmidb);
    dotContext.addFieldData(ServiceDotChainTest.RMIDB, rmidb);
    dotContext.addFieldData(ServiceDotChainTest.MAPPARAMB, mapb);
    
    //调用方法
    try {
      dot1.process(dotContext);
    } catch (Exception e) {
      System.out.println("err3======" + e.getMessage());
    }
    
    //断言
    String rmflagResultA = null;
    String rmflagResultB = null;
    try {
      rmflagResultA = daoa.queryA(rmida);
      System.out.println("rmflagResultA======" + rmflagResultA);
      rmflagResultB = daob.queryB(rmidb);
      System.out.println("rmflagResultB======" + rmflagResultB);
    } catch (Exception e) {
      System.out.println("err4======" + e.getMessage());
    }
    assertEquals(rmflagResultA, rmflaga); //判断A表记录是否已被修改，查询结果等于原记录，表示已回滚
    assertEquals(rmflagResultB, rmflagb); //判断B表记录是否已被修改，查询结果等于原记录，表示未修改成功
  }

  /**
   * 3.测试两个dot的情况---(dot3执行)B表更新出错，(dot2执行)A表更新没有回滚，而是执行成功。
   */
  //@Test
  public void testTXBErrorForTwoDot(){
    //加载资源
    initSource();
    
    //定入输入参数--A表
    String rmflaga = "FLAGaacc";
    String rmida = "A1";
    Map mapa = new HashMap();
    mapa.put(RMFLAGA, rmflaga);
    mapa.put(RMIDA, rmida);
    dotContext.addFieldData(ServiceDotChainTest.RMIDA, rmida);
    dotContext.addFieldData(ServiceDotChainTest.RMFLAGA, rmflaga);
    dotContext.addFieldData(ServiceDotChainTest.MAPPARAMA, mapa);

    //定入输入参数--B表
    String rmflagb = "FLAGbbccddddesesfegs";  //模拟超出10位长度，执行将报错
    String rmidb = "B1";
    Map mapb = new HashMap();
    mapb.put(RMFLAGB, rmflagb);
    mapb.put(RMIDB, rmidb);
    dotContext.addFieldData(ServiceDotChainTest.RMIDB, rmidb);
    dotContext.addFieldData(ServiceDotChainTest.MAPPARAMB, mapb);
    
    //调用方法
    try {
      dot2.process(dotContext);
      dot3.process(dotContext);
    } catch (Exception e) {
      System.out.println("err5======" + e.getMessage());
    }
    
    //断言
    String rmflagResultA = null;
    String rmflagResultB = null;
    try {
      rmflagResultA = daoa.queryA(rmida);
      System.out.println("rmflagResultA======" + rmflagResultA);
      rmflagResultB = daob.queryB(rmidb);
      System.out.println("rmflagResultB======" + rmflagResultB);
    } catch (Exception e) {
      System.out.println("err6======" + e.getMessage());
    }
    assertEquals(rmflagResultA, rmflaga); //判断A表记录是否已被修改，查询结果等于新记录，表示已成功执行
    String oldrmflagb = INITPARAMVALUE;
    assertEquals(rmflagResultB, oldrmflagb); //判断B表记录是否已被修改，查询结果等于原记录，表示未修改成功
  }
}

/*
CREATE TABLE TEST_XYG_A(
  RMID VARCHAR2(10) PRIMARY KEY,
  RMFLAG VARCHAR2(10)
);

CREATE TABLE TEST_XYG_B(
  RMID VARCHAR2(10) PRIMARY KEY,
  RMFLAG VARCHAR2(10)
);
DELETE FROM TEST_XYG_A;
DELETE FROM TEST_XYG_B;
INSERT INTO TEST_XYG_A(RMID, RMFLAG) VALUES ('A1', 'FLAG1');
INSERT INTO TEST_XYG_B(RMID, RMFLAG) VALUES ('B1', 'FLAG1');
COMMIT;

 */

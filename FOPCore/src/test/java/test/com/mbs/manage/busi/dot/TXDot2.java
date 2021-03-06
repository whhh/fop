/**
 * 文件名		：Dot2.java
 * 创建日期	：2013-10-23
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package test.com.mbs.manage.busi.dot;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import test.com.fop.framework.service.trade.ServiceDotChainTest;
import test.com.mbs.manage.busi.dao.TXADao;
import test.com.mbs.manage.busi.dao.TXBDao;

import com.fop.framework.context.FOPContext;
import com.fop.framework.exception.FOPException;
import com.fop.framework.service.trade.ITradeDot;
import com.fop.framework.service.trade.TradeDot;

/**
 * 描述:业务流程点2
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
@SuppressWarnings("all")
@TradeDot
public class TXDot2 implements ITradeDot {

  @Autowired
  private TXADao adao;

  @Autowired
  private TXBDao bdao;
  
  public void process(FOPContext context) throws FOPException{
    System.out.println("dot2 process...");
    //接收参数--改表A
    Map mapa = (Map)context.getFieldDataValue(ServiceDotChainTest.MAPPARAMA);
    adao.updateA(mapa);
  }
}
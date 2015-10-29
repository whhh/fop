/**
 * 文件名		：FOPLoaderDataChainImpl.java
 * 创建日期	：2013-10-24
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.context;

import java.util.List;

/**
 * 描述:初始化、结束信息处理
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
public class FOPLoaderDataChainImpl implements FOPLoaderDataChain {

  /**
   * 数据处理对象集合
   */
  private List<FOPDataProcess> listprocess;

  /* (non-Javadoc)
   * @see com.fop.framework.context.FOPLoaderDataChain#initChain()
   */
  @Override
  public void initChain() {
    if (listprocess == null){ //为空不做处理
      return;
    }
    int lsize = listprocess.size(); //取得集合大小
    FOPDataProcess dataprocess = null; //数据处理对象
    for (int i = 0; i < lsize; i++){ //遍历进行数据处理
      dataprocess = listprocess.get(i);
      dataprocess.init(); //数据处理
    }
  }

  /* (non-Javadoc)
   * @see com.fop.framework.context.FOPLoaderDataChain#destroyChain()
   */
  @Override
  public void destroyChain() {
    if (listprocess == null){ //为空不做处理
      return;
    }
    int lsize = listprocess.size(); //取得集合大小
    FOPDataProcess dataprocess = null; //数据处理对象
    for (int i = 0; i < lsize; i++){ //遍历进行数据处理
      dataprocess = listprocess.get(i);
      dataprocess.destroy(); //数据处理
    }
  }


  public void setListprocess(List<FOPDataProcess> listprocess){
    this.listprocess = listprocess;
  }

}

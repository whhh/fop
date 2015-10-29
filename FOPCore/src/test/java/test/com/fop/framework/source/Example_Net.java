/**
 * 文件名		：Example_Net.java
 * 创建日期	：2013-9-27
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package test.com.fop.framework.source;

import com.fop.framework.source.SourceAccessUtil;
import com.fop.framework.source.net.data.NetInDataModel;

/**
 * Net调用过程example
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class Example_Net {
  
  public Object example01(){
    String serviceContainer=null;//socket容器名称
    String messageConfigId=null;//报文配置Id
    Object message=null; //报文内容
    int timeOut=-1; //超时时间
    NetInDataModel inData=new NetInDataModel(serviceContainer, messageConfigId, message, timeOut);
    Object res=SourceAccessUtil.operate(inData);
    return res;
  }
  
}

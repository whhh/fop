package com.fop.framework.tcpip.proxy;


import com.fop.framework.context.FOPContext;


public interface ActionProxy {


    
    FOPContext execute(String tradeId,FOPContext context) throws Exception;


    
}

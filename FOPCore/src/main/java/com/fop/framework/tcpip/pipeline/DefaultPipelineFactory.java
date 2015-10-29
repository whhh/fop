package com.fop.framework.tcpip.pipeline;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;

import com.fop.framework.context.ApplicationContextUtil;
import com.fop.framework.tcpip.ServerSocket;
import com.fop.framework.tcpip.handler.ClientHandler;
import com.fop.framework.tcpip.handler.ServerHandler;
import com.fop.framework.tcpip.handler.TCPIPHandler;
import com.fop.framework.tcpip.processor.CommProcessor;
import com.fop.framework.tcpip.processor.CommProcessorImpl;
import com.fop.framework.tcpip.processor.ESBCommProcessorImpl;

//import com.chinamworld.mbs.tcpip.handler.DefaultDeCode;

public  class DefaultPipelineFactory implements ChannelPipelineFactory {
  
  private static final Log logger = LogFactory.getLog(DefaultPipelineFactory.class);//日志记录对象
	
	//private ChannelPipeline channelPipeline;
	
	//private static  Map<String, ChannelHandler> handlers = new LinkedHashMap<String, ChannelHandler>(4);
	
  //private   Map<String, ChannelHandler> handlers = new LinkedHashMap<String, ChannelHandler>(4);

	private CommProcessor commProcessor ;
	
	private String handlerName;
	
	public DefaultPipelineFactory(String handlerName){
	  this(handlerName,new ESBCommProcessorImpl());
//	  handlerName = "com.fop.framework.tcpip.handler.ServerHandler";
//	  commProcessor = new ESBCommProcessorImpl();
	}
	
	 public DefaultPipelineFactory(String handler,CommProcessor commProcessor){
	   this.handlerName = handler;
	   this.commProcessor = commProcessor;
	  }
	
	
//	public void addHandler(String name,ChannelHandler channelHandler){
//	  handlers.put(name, channelHandler);
//	}
	
//	private void intHandler(ChannelPipeline channelPipeline){
//		Iterator<Map.Entry<String, ChannelHandler>> i = handlers.entrySet().iterator();
//		Map.Entry<String, ChannelHandler> entry;
//		while(i.hasNext()){
//			entry = i.next();
//			channelPipeline.addLast(entry.getKey(),entry.getValue());
//		}
//	}
	
	@Override
	public ChannelPipeline getPipeline() throws Exception {
	  logger.debug("客户端连接,开始获取处理管道.....");
		  ChannelPipeline channelPipeline = Channels.pipeline();
		  //ChannelHandler serverHandler =  (ChannelHandler)ApplicationContextUtil.getBean(handler);
		  //ServerHandler serverHandler =(ServerHandler) Class.forName("com.fop.framework.tcpip.handler.ServerHandler").getConstructor(CommProcessor.class).newInstance(commProcessor);
		  //com.fop.framework.tcpip.handler.ServerHandler.class.getConstructor(parameterTypes).newInstance()

		  TCPIPHandler handler =(TCPIPHandler) Class.forName(this.handlerName).newInstance();
		  handler.setCommProcessor(commProcessor);
		  channelPipeline.addLast("TCPIPHandler", handler);
		  //intHandler(channelPipeline);
		  logger.debug("客户端连接,加载处理管道成功.....");
	    return channelPipeline;
	}

}

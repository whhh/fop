package com.fop.framework.tcpip;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.bootstrap.Bootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;

public  class ISocket {


	public boolean keepAlive;
	
	public boolean tcpNoDelay;
	
	
	
	public boolean reuseAddress;
	
	public int soLinger;
	
	public int trafficClass;
	
	public long connectTimeoutMillis;
	

  private Bootstrap bootstrap;
	

  protected  Map<String, Object> socketOptions = new  HashMap<String, Object>();
	
	
	public ISocket(Bootstrap bootstrap){
		this.bootstrap = bootstrap;	
	}
	
	public ISocket(Bootstrap bootstrap,
	    ChannelFactory channelFactory,
	    ChannelPipelineFactory pipelineFactory){
	  
		this.bootstrap = bootstrap;
		bootstrap.setFactory(channelFactory);
		bootstrap.setPipelineFactory(pipelineFactory);
	}
	
	 public ISocket(Bootstrap bootstrap,
	      ChannelFactory channelFactory){
	    
	    this.bootstrap = bootstrap;
	    bootstrap.setFactory(channelFactory);
	  }
	
	/**
	 * 释放该对象依赖的外部资源。如果其他对象正在使用这些外部资源(如线程池),千万不要调用该方法。
	 * 该方法简单委派调用 ChannelFactory.releaseExternalResources()方法。
	 */
	public void releaseExternalResources(){
		bootstrap.releaseExternalResources();
	}
	
	 /**
   * @return the bootstrap
   */
  public Bootstrap getBootstrap() {
    return bootstrap;
  }

  /**
   * @param bootstrap the bootstrap to set
   */
  public void setBootstrap(Bootstrap bootstrap) {
    this.bootstrap = bootstrap;
  }
	
	public void setChannelFactory(ChannelFactory channelFactory){
		bootstrap.setFactory(channelFactory);
	}
	
	public ChannelFactory getChannelFactory(){
		return bootstrap.getFactory();
	}
	
	
	public void setPipelineFactory(ChannelPipelineFactory channlpipelineFactory){
		bootstrap.setPipelineFactory(channlpipelineFactory);
	}
	
	public ChannelPipelineFactory getPipelineFactory(ChannelPipelineFactory channlpipelineFactory){
		return bootstrap.getPipelineFactory();
	}
	
	public void setOptions(Map<String, Object> socketOptions){
		bootstrap.setOptions(socketOptions);	
	}
	
	public void setOption(String key, Object option){
		bootstrap.setOption(key,option);	
	}

}

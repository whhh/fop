/**
 * 文件名		：ClientHandler.java
 * 创建日期	：2013-10-21
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.fop.framework.context.FOPContext;
import com.fop.framework.tcpip.constant.SocketConstant;
import com.fop.framework.tcpip.processor.CommProcessor;

/**
 * 描述:TODO.....
 * 
 * @version 1.00
 * @author user
 * 
 */
public class ClientHandler extends SimpleChannelHandler implements
    MessageHandler {

  private static final Log logger = LogFactory.getLog(ClientHandler.class);// 日志记录对象

  private String formatId;
  
  //1-接受数据成功 2-通讯接收数据格式化失败 
  private volatile int state;
  
  private volatile byte[] bytes;
  
  private volatile long length = -1;
  

  // private ActionHandler action;
  private volatile FOPContext context;


  private volatile Object message;
  
  private CommProcessor commProcessor;
  
  private volatile long time;
  
  /**
   * @return the commProcessor
   */
  public CommProcessor getCommProcessor() {
    return commProcessor;
  }

  /**
   * @param commProcessor the commProcessor to set
   */
  public void setCommProcessor(CommProcessor commProcessor) {
    this.commProcessor = commProcessor;
  }

//  public ClientHandler(CommProcessor commProcessor){
//    this.commProcessor = commProcessor;
//  }

  public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent evt)
      throws Exception {
    logger.info("handleDownstream begin:" + evt);
    if (!(evt instanceof MessageEvent)) {
      ctx.sendDownstream(evt);
      return;
    }

    MessageEvent e = (MessageEvent) evt;
    if (!doEncode(ctx, e)) {
      ctx.sendDownstream(e);
    }
    logger.info("handleDownstream end");
  }

  public boolean doEncode(ChannelHandlerContext ctx, MessageEvent e)
      throws Exception {
    logger.info("doEncode begin");
    Object originalMessage = e.getMessage();
    this.context = (FOPContext)originalMessage;
    Object encodedMessage = commProcessor.encode(originalMessage,formatId);
    logger.info(encodedMessage);
    if (encodedMessage != null) {
      byte[] sendBytes = (byte[])encodedMessage;
      
      ChannelBuffer sendMess = ChannelBuffers.buffer(sendBytes.length);

      sendMess.writeBytes(sendBytes);
      this.time = System.currentTimeMillis();
      logger.info("----Socket sendData time: " + time);
      logger.info("客户端向主机["+e.getRemoteAddress()+"]发送:" + new String(sendBytes));
      Channels.write(ctx, e.getFuture(), sendMess, e.getRemoteAddress());
    }
    logger.info("doEncode end");
    return true;
  }
  

  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
    logger.info("messageReceived begin");
    this.state = SocketConstant.READ_FINISH;
    long readTime = System.currentTimeMillis() - this.time;
    logger.info("----Socket messageReceived time:" + System.currentTimeMillis());
    ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
    byte[] b = new byte[buffer.capacity()];
    buffer.readBytes(b);
    //读取报文长度
    if(length < 0) {
      length = Long.parseLong(new String(b,0,6));
    }
    //一次通讯数据整合
    if(bytes != null){
      //获取先前读取的数据长度和当次的数据长度，从新生成数据
      byte[] b1 = new byte[(bytes.length + b.length)];
      //copy数据到新数据中
      System.arraycopy(bytes, 0, b1, 0, bytes.length);
      //将当次数据赋值到数据中
      System.arraycopy(b, 0, b1, bytes.length, b.length);
      bytes = b1;
    } else {
      bytes = b;
    }
    //打印读取的数据
    logger.info("客户端耗时["+ readTime +"]毫秒从主机"+e.getRemoteAddress()+"接收:" + new String(bytes));
    //判断报文是否读取完整
    if(length > bytes.length - 6){
      logger.info("分流");
      return ;
    }
    try{
      long formatStartTime = System.currentTimeMillis(); 
      commProcessor.decode(context,bytes,formatId);
      long formatTime = System.currentTimeMillis() - formatStartTime;
      logger.info("客户端耗时["+formatTime+"]毫秒完成接收报文格式化：" + ((FOPContext)context).getDataElement().toString());
      //this.state = 1;
    } catch(Exception ec){
      this.state = SocketConstant.FORMAT_EXCEPTION;
      logger.error("客户端接收报文格式化失败！", ec);     
    }
    //this.state = 2;
    logger.info("messageReceived end");
    this.state = SocketConstant.HANDLER_FINISH;
  }


  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
      throws Exception {
    logger.error("Unexpected exception from downstream.", e.getCause());
    //抛出异常直接返回数据，不在等待
    this.state = SocketConstant.FORMAT_EXCEPTION;
    Channel channel = e.getChannel();
    if(null != channel){
      channel.close();
    }
  }

  /**
   * @return the txCode
   */
  public String getFormatId() {
    return formatId;
  }

  // /**
  // * @return the channelGroup
  // */
  // public ChannelGroup getChannelGroup() {
  // return channelGroup;
  // }
  //
  // /**
  // * @param channelGroup the channelGroup to set
  // */
  // public void setChannelGroup(ChannelGroup channelGroup) {
  // this.channelGroup = channelGroup;
  // }

  public Object getMessage() {
    return message;
  }

  public void setMessage(Object message) {
    this.message = message;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.fop.framework.tcpip.handler.MessageHandler#setFormat(java.lang.String)
   */
  @Override
  public void setFormat(String formatId) {
    this.formatId = formatId;

  }
  
  
  /**
   * @return the state
   */
  public int getState() {
    return state;
  }

  /**
   * @param state the state to set
   */
  public void setState(int state) {
    this.state = state;
  }

}

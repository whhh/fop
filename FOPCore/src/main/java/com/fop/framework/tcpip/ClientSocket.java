package com.fop.framework.tcpip;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.oio.OioClientSocketChannelFactory;

import com.fop.framework.tcpip.constant.SocketConstant;
import com.fop.framework.tcpip.handler.MessageHandler;
import com.fop.framework.tcpip.pipeline.DefaultPipelineFactory;
import com.fop.framework.tcpip.simulator.ClientSimulator;

/**
 * 一个用于创建关联到实际通讯实体的传输主接口的工厂；
 * 客户端一般可以设置为OioClientSocketChannelFactory和NioClientSocketChannelFactory。
 * 
 * 要关闭一个被工厂管理的网络应用服务，你必须执行以下步骤:
 * 1、关闭有该工厂创建的所有通道和它们的子通道,通常是使用ChannelGroup.close()；
 * 2、调用releaseExternalResources()。
 */
public class ClientSocket extends ISocket {

  private static final Log logger = LogFactory.getLog(ClientSocket.class);// 日志记录对象
  
  private static final String IPDELIM = ":";
  
  //private String MessageHandler = "clientHandler";

  public  final ChannelGroup allChannels = new DefaultChannelGroup();// 通道管理容器

  //private String socketInfo; // 连接描述

  //private boolean keepAlive; // 长短连接标识 true-长连接 false-短连接

  // private List<SocketListener> socketAcceptListeners; //连接监听列表

 // private long connectTimeOut; // 连接超时时间

  private long readTimeOut; // 连接超时时间

  //private boolean reconnectImmediately; // 重连标识 true-重新连接 false-不重新连接

  private int ThreadPool; // 线程

  private ClientBootstrap clientBootstrap;//创建客户端通道和尝试连接的帮助类

  private final  Map<String, Object> options;

  // private ChannelFactory channelFactory;
  //
  // private ChannelPipelineFactory pipelineFactory;

  private volatile long lastAccess; // 最后连接时间
  
  //主机列表，用于负载均衡设计，不同类型的系统，请另外配置ClientSocket
  private List<String> hostList ;
  
  private List<InetSocketAddress> backupHostList= new LinkedList<InetSocketAddress>();
  
  

  /**
   * @param addressList
   */
  public ClientSocket(
      List<String> hostList,
      long connetTimeOut,
      long readTimeOut) {

    this(hostList,
        connetTimeOut,
        readTimeOut,
        new OioClientSocketChannelFactory(Executors.newFixedThreadPool(10)),
        new DefaultPipelineFactory(SocketConstant.CLIENTHANDLER),
        true,
        0,
        false);
  }

  public ClientSocket(
      List<String> hostList,
      long connetTimeOut,
      long readTimeOut,
      int ThreadPool ) {

    this(hostList,
        connetTimeOut,
        readTimeOut,
        new OioClientSocketChannelFactory(
            Executors.newFixedThreadPool(ThreadPool)),
        new DefaultPipelineFactory(SocketConstant.CLIENTHANDLER),
        true,
        0,
        false);
  }
  
  public ClientSocket(
      List<String> hostList,
      long connetTimeOut,
      long readTimeOut,
      int ThreadPool,
      ChannelPipelineFactory pipelineFactory) {

    this(hostList,
        connetTimeOut,
        readTimeOut,
        new OioClientSocketChannelFactory(
            Executors.newFixedThreadPool(ThreadPool)),
        pipelineFactory,
        true,
        0,
        false);
  }

  public ClientSocket(
      List<String> hostList,
      long connetTimeOut,
      long readTimeOut,
      ExecutorService executorService) {

    this(hostList,
        connetTimeOut,
        readTimeOut,
        new OioClientSocketChannelFactory(executorService),
        new DefaultPipelineFactory(SocketConstant.CLIENTHANDLER),
        true,
        0,
        false);
  }

  /**
   * @param hostList
   * @param channelFactory
   * @param options
   *  options的key请参照SocketConstant类
   */
  public ClientSocket(
      List<String> hostList,
      long connetTimeOut,
      long readTimeOut,
      ChannelFactory channelFactory,
      ChannelPipelineFactory channlpipelineFactory,
      boolean tcpNoDelay,
      int solinger,
      boolean keepAlive) {
    
    super(new ClientBootstrap());
    this.options = new HashMap<String, Object>();
    this.hostList = hostList;
    init();
    setChannelFactory(channelFactory);
    setPipelineFactory(channlpipelineFactory);
    // 启用TCP_NODELAY
    options.put(SocketConstant.TCPNODELAY, tcpNoDelay);
    // 启动Nagle算法，提高网络吞吐量，牺牲实时性
    options.put(SocketConstant.SOLINGER, solinger);
    // 启用指定连接超时值为10000毫秒
    options.put(SocketConstant.CONNECTTIMEOUTMILLIS, 10000);
    // 设置为短连接
    options.put(SocketConstant.KEEPALIVE, keepAlive);
    // localAddress
    setOptions(options);

    lastAccess = 0L;
    this.readTimeOut = readTimeOut;
    this.clientBootstrap = (ClientBootstrap)getBootstrap();
  }
  
  
  private void init(){
    initHost();
  }

  
  
  private void initHost(){
    if (hostList.size() < 1) {
      throw new NullPointerException("客户端连接地址为空");
    }
    
    String[] defaultHost = hostList.get(0).split(IPDELIM);
    
    //setOption("remoteAddress", new InetSocketAddress(defaultHost[0], Integer.parseInt(defaultHost[1])));
    options.put("remoteAddress", new InetSocketAddress(defaultHost[0], Integer.parseInt(defaultHost[1])));
    hostList.remove(0);
    
    for(String hostTemp : hostList){
      
      String[] host = hostTemp.split(IPDELIM);
      
      backupHostList.add(new InetSocketAddress(host[0], Integer.parseInt(host[1])));
      
    }
    logger.info("Host主机地址初始成功");
  }
  
  

//  public String getRandomClientAddress() {
//    int size = hostList.size();
//    int index = new Random().nextInt(size);
//    return hostList.get(index);
//  }

  // TODO 注意连接多个相同服务器的时候
  public ChannelFuture connet() {
    logger.info("connet");
    lastAccess = System.currentTimeMillis();
    logger.info("----Socket connet startTime:" + lastAccess);
    ChannelFuture future = null;
    future = clientBootstrap.connect();
    long endTime = System.currentTimeMillis();
    logger.info("----Socket connet endTime:" + endTime);
    logger.info("----Socket connet used times: " + (endTime - lastAccess) + "ms");
    return future;

  }
  


  private class ReadListener implements ChannelFutureListener {

    private Object message;

    private long timeOutValue;
    
    private int state;

    private String formatId;

    public ReadListener(String formatId, Object message, long timeOutValue) {
      this.message = message;
      this.timeOutValue = timeOutValue;
      this.formatId = formatId;
    }

    public int getState(){
      return state;
    }

    public Object getMessage() {
      return message;
    }

    @Override
    public void operationComplete(ChannelFuture cf) throws Exception {
      logger.info("operationComplete begin");
      Channel channel = cf.getChannel();
      if (!cf.isSuccess()) {
        Throwable th = cf.getCause();
        this.state = SocketConstant.FORMAT_EXCEPTION;
        throw new Exception(th);
        // TODO 分类异常
        // if (th != null) {
        // Class<?> c = th.getClass();
        // if (c == java.net.BindException.class)
        // errorCode = CxnetConstants.BIND_EXCEPTION;
        // else if (c == java.net.ConnectException.class)
        // errorCode = CxnetConstants.CONNECT_EXCEPTION;
        // else if (c == java.net.MalformedURLException.class)
        // errorCode = CxnetConstants.MAILFORMEDURL_EXCEPTION;
        // else if (c == java.net.NoRouteToHostException.class)
        // errorCode = CxnetConstants.NOROUTETOHOST_EXCEPTION;
        // else if (c == java.net.PortUnreachableException.class)
        // errorCode = CxnetConstants.PORTUNREACHABLE_EXCEPTION;
        // else if (c == java.net.ProtocolException.class)
        // errorCode = CxnetConstants.PROTOCOL_EXCEPTION;
        // else if (c == java.net.SocketException.class)
        // errorCode = CxnetConstants.SOCKET_EXCEPTION;
        // else if (c == java.net.SocketTimeoutException.class)
        // errorCode = CxnetConstants.SOCKETTIMEOUT_EXCEPTION;
        // else if (c == java.net.UnknownHostException.class)
        // errorCode = CxnetConstants.UNKNOWNHOST_EXCEPTION;
        // else if (c == java.net.UnknownServiceException.class)
        // errorCode = CxnetConstants.UNKNOWNSERVICE_EXCEPTION;
        // else if (c == java.net.URISyntaxException.class)
        // errorCode = CxnetConstants.URISYNTAX_EXCEPTION;
        // th.printStackTrace();
        // System.out.println("CxClient::start() A 2 errCode=" + errorCode);
      }
      logger.debug("客户端通讯连接成功");
      //Channel channel = cf.getChannel();
      allChannels.add(channel);
      MessageHandler handler = (MessageHandler) channel.getPipeline().get(
          SocketConstant.TCPIPHANDLER);

      // 设置格式化ID

      handler.setFormat(formatId);
      
      channel.write(message);
      boolean readState = readHandler(handler, timeOutValue);
      this.state = SocketConstant.READ_TIMEOUT;
      if(true == readState){
        this.state = handler.getState();
      }
      if(null != channel){
         channel.close();
      }
      logger.info("operationComplete end");
    }
      //TODO 处理长短连接
  }

  // 发送并等待接收信息
  public Object sendAndWait(
      String id,
      Object message, 
      Long timeOutValue) throws Exception {
    logger.info("sendAndWait begin");
    //----start----添加挡板识别处理程序  add by xieyg for 20131224
    try {
      Object simulator = ClientSimulator.process(id, message, timeOutValue);
      if (simulator != null){ //如果为挡板程序
        logger.info("返回挡板数据");
//        return listflag.get(1);  //返回挡板处理结果
        return simulator;  //返回挡板处理结果
      }
    } catch (Exception e) {
      logger.warn("----ClientSimulator process error2：" + e.getMessage());
    }
    
    ChannelFuture future = null;
    ReadListener readLis = null;
    
      future = connet();
      readLis = new ReadListener(id, message, timeOutValue);
      future.addListener(readLis);
      while (true) {
        if (0 != readLis.getState()) {
          break;
        }
      }

    if (SocketConstant.READ_TIMEOUT == readLis.getState()) {
      //TODO 异常处理
      throw new Exception("客户端接收报文超时！");
    }
    if(SocketConstant.FORMAT_EXCEPTION == readLis.getState()){
      throw new Exception("客户端接收报文格式化失败！");
    }
    logger.info("sendAndWait end");
    return readLis.getMessage();
  }

  public boolean readHandler(MessageHandler handler, long readtimeOut) {
    logger.info("readHandler");
    if(1 > readtimeOut ){
      readtimeOut = this.readTimeOut;
    }
    //Object msg = null;
    long begin = System.currentTimeMillis();
    long waitTime = -1L;
    // 获取超时时间
    long timeOutValue = readtimeOut;
    if (timeOutValue < 0L) {
      timeOutValue = getReadTimeOut();
    }
    while (true) {
      if( 0 == handler.getState()){
        waitTime = timeOutValue - (System.currentTimeMillis() - begin);
        if (waitTime <= 0L) {
          logger.error("接收数据超时[最长时间=" + timeOutValue + "毫秒].");
          return false;
        }
      }else{
        if( SocketConstant.READ_FINISH != handler.getState()){
          return true;
        }
      }
    }
  }

  /**
   * 断开连接
   * 
   */

  public void close() {
    allChannels.close().awaitUninterruptibly();
    clientBootstrap.releaseExternalResources();
  }

  /**
   * 停止连接
   * 
   */
  public void terminate() {
    close();
  }

  /**
   * @return the keepAlive
   */
  public boolean isKeepAlive() {
    return keepAlive;
  }

  /**
   * @param keepAlive
   *          the keepAlive to set
   */
  public void setKeepAlive(boolean keepAlive) {
    this.keepAlive = keepAlive;
  }


  /**
   * @return the threadPool
   */
  public int getThreadPool() {
    return ThreadPool;
  }

  /**
   * @param threadPool
   *          the threadPool to set
   */
  public void setThreadPool(int threadPool) {
    ThreadPool = threadPool;
  }

  /**
   * @param lastAccess
   *          the lastAccess to set
   */
  public long getLastAccess() {
    return lastAccess;
  }

  /**
   * @return the readTimeOut
   */
  public long getReadTimeOut() {
    return readTimeOut;
  }

  /**
   * @param readTimeOut
   *          the readTimeOut to set
   */
  public void setReadTimeOut(long readTimeOut) {
    this.readTimeOut = readTimeOut;
  }
  
  public static void main(String[] args){
    
  }
  
//  public void test() throws Exception{
//    ClientBootstrap bootstrap = new ClientBootstrap(
//        new OioClientSocketChannelFactory(Executors.newCachedThreadPool()));
//
//// Configure the pipeline factory.
//bootstrap.setPipelineFactory(new MyPipelineFactory());
//int time = 100000;
//bootstrap.setOption("connectTimeoutMillis", time);
//// Start the connection attempt.
//
//
//
//ChannelFuture future = bootstrap.connect(new InetSocketAddress("11.8.126.18",30094));
//// Wait until the connection attempt succeeds or fails.
//Channel channel = future.awaitUninterruptibly().getChannel();
//if (!future.isSuccess()) {
//    future.getCause().printStackTrace();
//    bootstrap.releaseExternalResources();
//    return;
//}
//  }

}

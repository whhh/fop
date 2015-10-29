package com.fop.framework.tcpip;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelException;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.oio.OioServerSocketChannelFactory;

import com.fop.framework.tcpip.constant.SocketConstant;
import com.fop.framework.tcpip.pipeline.DefaultPipelineFactory;
import com.fop.framework.tcpip.processor.CommProcessor;

/**
 * 
 * @author hyb 服务端
 * 
 */
public class ServerSocket extends ISocket {

  private static final Log logger = LogFactory.getLog(ServerSocket.class);// 日志记录对象

  public final static ChannelGroup allChannels = new DefaultChannelGroup();

  private final ServerBootstrap serverBootstrap;

  public static int DEFAULT_BOSS_SIZE = 2; // 默认等待队列容量

  public static int DEFAULT_WORK_SIZE = 10; // 默认线程池容量
  
  private Channel serverChannel = null;

  // private final static Map<String, Object> m = new HashMap<String, Object>();
  //
  // private ChannelFactory channelFactory;
  //
  // private ChannelPipelineFactory pipelineFactory;

  // private Executor bossExecutor;
  //
  // private Executor workerExecutor;
  private int port;// 连接端口

  private final Map<String, Object> options;

  private final static String CHILD = "child.";

  // // 父channel的属性集
  // b.setOption("localAddress", new InetSocketAddress(8080));
  // b.setOption("reuseAddress", true);
  //
  // // 子channel的属性集
  // b.setOption("child.tcpNoDelay", true);
  // b.setOption("child.receiveBufferSize", 1048576);

  public ServerSocket(int port, long connectTimeout) {
    this(port, connectTimeout,
        new OioServerSocketChannelFactory(
            Executors.newFixedThreadPool(DEFAULT_BOSS_SIZE),// 与主线程相关，监听多个端口相关
            Executors.newFixedThreadPool(DEFAULT_WORK_SIZE)),// 与接收线程相关
        new DefaultPipelineFactory(SocketConstant.SERVERHANDLER), true, -1,
        false);
  }

  public ServerSocket(int port, long connectTimeout, int bossSize, int workSize) {
    this(port, connectTimeout,
        new OioServerSocketChannelFactory(
            Executors.newFixedThreadPool(bossSize),// 与主线程相关，监听多个端口相关
            Executors.newFixedThreadPool(workSize)),// 与接收线程相关
        new DefaultPipelineFactory(SocketConstant.SERVERHANDLER), true, 10,
        false);
  }

  public ServerSocket(int port, long connectTimeout, int bossSize,
      int workSize, CommProcessor commProcessor) {
    this(
        port,
        connectTimeout,
        new OioServerSocketChannelFactory(
            Executors.newFixedThreadPool(bossSize),// 与主线程相关，监听多个端口相关
            Executors.newFixedThreadPool(workSize)),// 与接收线程相关
        new DefaultPipelineFactory(SocketConstant.SERVERHANDLER, commProcessor),
        true, 10, false);
  }

  public ServerSocket(int port, long connectTimeout, Executor bossEx,
      Executor workEx) {
    this(port, connectTimeout,
        new OioServerSocketChannelFactory(bossEx,// 与主线程相关，监听多个端口相关
            workEx),// 与接收线程相关
        new DefaultPipelineFactory(SocketConstant.SERVERHANDLER), true, -1,
        false);
  }

  public ServerSocket(int port, long connectTimeout,
      ChannelFactory channelFactory,
      ChannelPipelineFactory channlpipelineFactory, boolean tcpNoDelay,
      int soLinger, boolean keepAlive) {

    super(new ServerBootstrap());
    options = new HashMap<String, Object>();

    this.port = port;

    setChannelFactory(channelFactory);
    setPipelineFactory(channlpipelineFactory);
    // 指定连接超时值为connectTimeout毫秒
    options.put(CHILD + SocketConstant.CONNECTTIMEOUTMILLIS, connectTimeout);
    // 启动Nagle算法，提高网络吞吐量，牺牲实时性
    options.put(CHILD + SocketConstant.TCPNODELAY, true);
    // 启用/禁用具有指定逗留时间（以秒为单位）
    options.put(CHILD + SocketConstant.SOLINGER, -1);
    // 设置为短连接
    options.put(CHILD + SocketConstant.KEEPALIVE, false);
    setOptions(options);
    serverBootstrap = (ServerBootstrap) getBootstrap();
    init();
  }

  public void init() {
    bind();
  }

  private void bind() {
    if (1 > this.port) {
      throw new NullPointerException("port");
    }
    try {
      serverChannel = serverBootstrap.bind(new InetSocketAddress(port));
      allChannels.add(serverChannel);
      logger.info("在本地端口" + port + ",创建socket监听服务成功");
    } catch (ChannelException e) {
      logger.error("在本地端口" + port + ",创建socket监听服务失败", e);
      return;
    }
  }

  @Override
  protected void finalize() throws Throwable {
    if(serverChannel != null) {
      try {
        serverChannel.unbind();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    super.finalize();
  }
}

// public void createBootstrap() {
//
// Bootstrap boot = new ServerBootstrap();
// boot = new ClientBootstrap();
//
//
// ChannelFactory factory = new
// OioServerSocketChannelFactory(Executors.newCachedThreadPool(),Executors.newCachedThreadPool());
//
// boot.setFactory(factory);
//
//
// ChannelPipelineFactory pipelineFactory = new ChannelPipelineFactoryImpl();
//
// boot.setPipelineFactory(pipelineFactory);
//
//
// //启用/禁用 Nagle 算法
// boot.setOption("child.tcpNoDelay", true);
//
// boot.setOption("child.soLinger", true);
// //保持连接
// boot.setOption("child.keepAlive", true);
// //重新使用
//
// boot.setOption("child.reuseAddress", true);
//
//
//
// //连接超时
//
// boot.setOption("child.connectTimeoutMillis", "7000");
//
// boot.setOption("localAddress", new InetSocketAddress(8080));
//
// boot.setOption("remoteAddress", new InetSocketAddress("example.com", 8080));
//
//
//
//
//
//
//
// new OioClientSocketChannelFactory(Executors.newCachedThreadPool())
// ClientBootstrap m_bootstrap = new ClientBootstrap(new
// OioClientSocketChannelFactory(Executors.newCachedThreadPool()));
//
// final TCPIPService client = this;
//
// m_bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
//
// @Override
// public ChannelPipeline getPipeline() throws Exception {
// ChannelPipeline pip = Channels.pipeline();
//
// pip.addLast("timeout", new ReadTimeoutHandler(new HashedWheelTimer(), 300));
// pip.addLast("decoder", new CxDecoder());
// pip.addLast("handler", new CxHandler(m_listener, client));
//
// return pip;
// }
// });
// m_bootstrap.setOption("tcpNoDelay", true);
// m_bootstrap.setOption("keepAlive", true);
// m_bootstrap.setOption("reuseAddress", true);
// m_bootstrap.setOption("connectTimeoutMillis", "7000");
// }
//


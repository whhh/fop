package com.fop.framework.tcpip.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.fop.framework.constant.FOPConstants;
import com.fop.framework.context.FOPContext;
import com.fop.framework.data.GroupData;
import com.fop.framework.data.RepeatData;
import com.fop.framework.tcpip.ServerSocket;
import com.fop.framework.tcpip.processor.CommProcessor;
import com.fop.framework.tcpip.proxy.ActionProxy;
import com.fop.framework.tcpip.proxy.ActionProxyFactory;

public class ServerHandler extends SimpleChannelUpstreamHandler implements
    TCPIPHandler {

  private static final Log logger = LogFactory.getLog(ServerHandler.class);// 日志记录对象

  // public static final ChannelGroup channels = new DefaultChannelGroup();

  // private final ChannelBuffer messageBuffer = dynamicBuffer();

  private CommProcessor commProcessor;

  // private ActionProxy action;

  private static ActionProxy action = ActionProxyFactory.createActionProxy();

  public ServerHandler() {
  }

  public ServerHandler(CommProcessor commProcessor) {
    this.commProcessor = commProcessor;
  }

  public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
      throws Exception {
    ServerSocket.allChannels.add(e.getChannel());
  }

  public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e)
      throws Exception {

    ServerSocket.allChannels.remove(e.getChannel());
  }

  // 增加处理超时检查
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
      throws Exception {
    // ChannelBuffer messageBuffer
    ChannelBuffer message = (ChannelBuffer) e.getMessage();
    // ChannelBuffer messageBuffer = dynamicBuffer();
    // message.writeBytes(message);

    // while (messageBuffer.readable()) {
    // System.out.println(messageBuffer.readByte());
    // }

    byte[] segment = new byte[message.capacity()];
    // message.readBytes(segment);
    message.readBytes(segment);
    logger.info("服务端从 " + e.getRemoteAddress() + "接收：" + new String(segment));
    // commProcessor.wrapMessagePackage(segment);
    ChannelBuffer messageBuffer = null;
    try {
      FOPContext fc = new FOPContext();
      String formatId = null;
      try {
        Object object = commProcessor.decode(fc, segment, null);
        GroupData headsgroup = new GroupData(FOPConstants.REQUEST_HEADS);
        headsgroup.addFieldData("serverIp", e.getChannel().getLocalAddress());
        headsgroup.addFieldData("clientIp", e.getChannel().getRemoteAddress());
        fc.addFOPData(headsgroup);
        String serviceScene = (String) fc.getFieldDataValue("_esbServiceScene");
        if(serviceScene == null) {
          serviceScene = "";
        }
        formatId = (String) object;
        String tradeId = formatId;
        action.execute(tradeId , fc);
      } catch (Exception ex) {
        logger.info("执行交易异常", ex);
        fc.addFieldData("retStatus", "F");
        RepeatData ret = new RepeatData("mbsRet");
        GroupData groupData = new GroupData();
        groupData.addFieldData("retCode", "E010104000001");
        groupData.addFieldData("retMsg", "执行交易异常");
        fc.addFOPData(ret);
        ret.add(groupData);
      }
      // ActionProxy action;
      // FOPData data = action.execute(tranCode,data);
      // String result = commProcessor.encode(data,null);
      // String result = "222222222222222222";

      // IFormat sendFormat = FormatConfigContainer.getFormat(send + txCode);
      // sendFormat.format(result);
      byte[] result = (byte[]) commProcessor.encode(fc, formatId);
      logger.info("服务端向" + e.getRemoteAddress() + "响应：" + new String(result));
      messageBuffer = ChannelBuffers.buffer(result.length);

      messageBuffer.writeBytes(result);

      Channels.write(ctx, e.getFuture(), messageBuffer);

    } catch (Exception ec) {
      String errorMessage = "数据格式错误";
      messageBuffer = ChannelBuffers.buffer(errorMessage.length());
      messageBuffer.writeBytes(errorMessage.getBytes());
      Channels.write(ctx, e.getFuture(), messageBuffer);
      logger.error("error:", ec);
    } finally {

      e.getChannel().close();
    }

    // byte[] head = new byte[6];
    // System.arraycopy(head, 0, message, 0, head.length);
    // mess
    //
    // setMessage(b);
    // message = b;
    // IFormat format = FormatConfigContainer.getFormat(receive + txCode);
    // format.unformat(ele, context)
    // message=fmt.format(b);
  }

  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
      throws Exception {
    logger.error("Unexpected exception from downstream." + e.getCause());
    e.getChannel().close();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.fop.framework.tcpip.handler.TCPIPHandler#getCommProcessor()
   */
  @Override
  public CommProcessor getCommProcessor() {
    // TODO Auto-generated method stub
    return commProcessor;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.fop.framework.tcpip.handler.TCPIPHandler#setCommProcessor()
   */
  @Override
  public void setCommProcessor(CommProcessor commProcessor) {
    this.commProcessor = commProcessor;

  }
  
  public void channelBound(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    ServerSocket.allChannels.add(e.getChannel());
  }

}

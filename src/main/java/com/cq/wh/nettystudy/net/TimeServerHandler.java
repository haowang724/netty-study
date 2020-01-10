package com.cq.wh.nettystudy.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Auther: wh
 * @Date: 2019/12/30 15:04
 * @Description:
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

  @Override
  public  void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
//      ByteBuf buf = (ByteBuf)msg;
//      byte[] req = new byte[buf.readableBytes()];
//      buf.readBytes(req);
//      String body = new String(req,"UTF-8");
      String body = (String)msg;
      System.out.println("The time server recive order :"+body);
      String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?new java.util.Date(System.currentTimeMillis()).toString():"BAD ORDER";
      currentTime = currentTime+System.getProperty("line.separator");
      ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
      ctx.writeAndFlush(resp);
  };

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
       ctx.flush();
  };

    @Override
  public  void exceptionCaught(ChannelHandlerContext ctx, Throwable var2) throws Exception{
      ctx.close();
  };
}

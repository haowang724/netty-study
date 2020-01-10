package com.cq.wh.nettystudy.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

/**
 * @Auther: wh
 * @Date: 2019/12/30 15:38
 * @Description:
 */
public class TimeCilentHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = Logger.getLogger(TimeServerHandler.class.getName());

//    private final ByteBuf firstMessage;

    private int counter;

    private byte[] req;

    public TimeCilentHandler(){
        req = ("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
//        firstMessage = Unpooled.buffer(req.length);
//        firstMessage.writeBytes(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for(int i=0;i<100;i++){
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf)msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
        String body = (String)msg;
        System.out.println("Now is : "+body+"; this counter is :"+ ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

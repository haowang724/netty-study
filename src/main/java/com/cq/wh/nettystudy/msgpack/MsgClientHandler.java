package com.cq.wh.nettystudy.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Auther: wh
 * @Date: 2020/1/12 14:17
 * @Description:
 */
public class MsgClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo[] userInfos = getUserInfos(1000);
        for(UserInfo info : userInfos){
            ctx.writeAndFlush(info);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client receive the msgback message : "+msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       cause.printStackTrace();
       ctx.close();
    }

    private UserInfo[] getUserInfos(int number){
        UserInfo[] userInfos = new UserInfo[number];
        for(int i=0;i<number;i++){
          UserInfo userInfo = new UserInfo();
          userInfo.setName("ABCDEFG------>"+i);
          userInfo.setAge(i);
          userInfos[i] = userInfo;
        }
        return userInfos;
    }
}

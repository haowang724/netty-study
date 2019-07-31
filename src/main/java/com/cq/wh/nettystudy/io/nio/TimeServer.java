package com.cq.wh.nettystudy.io.nio;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Auther: wh
 * @Date: 2019/7/22 10:11
 * @Description:
 */
public class TimeServer {

    public static void main(String[] args) {
       MutiplexerTimeServer mutiplexerTimeServer = new MutiplexerTimeServer(19999);
       new Thread(mutiplexerTimeServer,"timeserver'").start();

    }
}

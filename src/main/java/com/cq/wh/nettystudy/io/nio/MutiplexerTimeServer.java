package com.cq.wh.nettystudy.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @Auther: wh
 * @Date: 2019/7/22 11:26
 * @Description:
 */
public class MutiplexerTimeServer implements Runnable {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    /**
     * 初始化多路复用器，绑定监听端口
     * @param port
     */
    public MutiplexerTimeServer(int port){
        try {
            //打开多路复用器
            selector = Selector.open();
            //打开serverSocketChannel
            serverSocketChannel = ServerSocketChannel.open();
            //绑定地址端口
            serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1",port));
            //设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //注册到多路复用器上去
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("This time server is start in port : "+port);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void stop(){
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop){
            try {
                 selector.select(1000);
                 Set<SelectionKey> selectionKeys = selector.selectedKeys();
                 Iterator<SelectionKey> iterator = selectionKeys.iterator();
                 SelectionKey key = null;
                 while(iterator.hasNext()){
                     key = iterator.next();
                     iterator.remove();
                     try {
                         handleKey(key);
                     } catch (Exception e){
                         e.printStackTrace();
                         if(key != null){
                             key.cancel();
                             if(key.channel() !=null ){
                                 key.channel().close();
                             }
                         }
                     }
                 }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //多路复用器关闭后，注册在上面的channel和pipe等资源都会被自动去注册并关闭，所以不需要重复去关闭资源
        if(selector != null){
             try {
                 selector.close();
             } catch (Exception e){
                 e.printStackTrace();
             }
        }

    }

    private void handleKey(SelectionKey key) throws IOException{
            if(key.isValid()){
                if(key.isAcceptable()){
                    ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    //注册新的监听到多路复用器上
                    sc.register(selector,SelectionKey.OP_READ);
                }
                if(key.isReadable()){
                    SocketChannel sc = (SocketChannel)key.channel();;
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int readBtyes = sc.read(buffer);
                    if(readBtyes>0){
                        buffer.flip();
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);
                        String body = new String(bytes,"UTF-8");
                        System.out.println("The time server receive order :"+body);
                        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)? new Date(System.currentTimeMillis()).toString():"BAD ORDER";
                        System.out.println(currentTime);
                        doWrite(sc,currentTime);
                    } else if(readBtyes<0) {
                        //关闭链路
                        key.cancel();
                        sc.close();
                    } else{
                        //读到0字节 忽略
                    }

                }
            }
    }

    private void doWrite(SocketChannel channel,String res){
        try {
           if(res!=null && res.trim().length()>0){
               byte[] bytes = res.getBytes();
               ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
               buffer.put(bytes);
               buffer.flip();
               channel.write(buffer);
           }

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}

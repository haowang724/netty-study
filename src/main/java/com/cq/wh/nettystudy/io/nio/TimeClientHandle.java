package com.cq.wh.nettystudy.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Auther: wh
 * @Date: 2019/7/23 16:22
 * @Description:
 */
public class TimeClientHandle implements Runnable {

    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;


    public TimeClientHandle(String host,int port){
        this.host = host == null ? "127.0.0.1":host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        doConnect();
        while(!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> keySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keySet.iterator();
                SelectionKey key = null;
                while(iterator.hasNext()){
                   key = iterator.next();
                   iterator.remove();
                    try {
                        handleInput(key);
                    } catch (IOException e) {
                        if(key!=null){
                            key.cancel();
                            if(key.channel()!=null){
                                key.channel().close();
                            }
                        }
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private  void handleInput(SelectionKey key) throws IOException{
        if(key.isValid()){
            SocketChannel sc = (SocketChannel) key.channel();
            if(key.isConnectable()){
                if(sc.finishConnect()){
                    sc.register(selector,SelectionKey.OP_READ);
                    doWrite(sc);
                }
            } else {
                System.exit(1);
            }

        }
    }

    private void doConnect(){
        try {
            if(socketChannel.connect(new InetSocketAddress(this.host,this.port))){
                socketChannel.register(selector, SelectionKey.OP_READ);
                doWrite(socketChannel);
            } else {
                socketChannel.register(selector,SelectionKey.OP_CONNECT);
            }
        } catch (Exception e){

        }

    }

    private void doWrite(SocketChannel sc){
        try {
            byte[] req = "QUERY TIME ORDER".getBytes();
            ByteBuffer writerBuffer = ByteBuffer.allocate(req.length);
            writerBuffer.put(req);
            writerBuffer.flip();
            sc.write(writerBuffer);
            if(writerBuffer.hasRemaining()){
                System.out.println("Send order 2 server succeed");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}

package com.cq.wh.nettystudy.io.bio;

import com.cq.wh.nettystudy.io.fakeAsynIo.TimeServerHandlerExecutePool;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Auther: wh
 * @Date: 2019/7/15 10:53
 * @Description: BIO方式的服务端
 */
public class TimeServer {
    public static void main(String[] args) {

        ServerSocket server = null;

        try {
            server = new ServerSocket(19999);
            System.out.println("The Server is start in port:"+19999);
            Socket socket = null;
            while(true){
                socket = server.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } catch (Exception e){
            if(server!=null){
                try {
                    server.close();
                    server = null;
                } catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        }

    }
}

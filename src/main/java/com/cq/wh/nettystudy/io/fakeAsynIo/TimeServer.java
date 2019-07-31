package com.cq.wh.nettystudy.io.fakeAsynIo;

import com.cq.wh.nettystudy.io.fakeAsynIo.TimeServerHandler;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Auther: wh
 * @Date: 2019/7/15 10:53
 * @Description: 伪异步方式的服务端（线程池处理请求）
 */
public class TimeServer {
    public static void main(String[] args) {

        ServerSocket server = null;

        try {
            server = new ServerSocket(19999);
            System.out.println("The Server is start in port:"+19999);
            Socket socket = null;
            TimeServerHandlerExecutePool pool = new TimeServerHandlerExecutePool(50,1000);
            while(true){
                socket = server.accept();
                pool.execute(new TimeServerHandler(socket));
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

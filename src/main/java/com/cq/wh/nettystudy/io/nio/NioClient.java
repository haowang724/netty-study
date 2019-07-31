package com.cq.wh.nettystudy.io.nio;

/**
 * @Auther: wh
 * @Date: 2019/7/23 16:21
 * @Description:
 */
public class NioClient {
    public static void main(String[] args) {
        new Thread(new TimeClientHandle("127.0.0.1",19999)).start();
    }
}

package com.cq.wh.nettystudy.io.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Auther: wh
 * @Date: 2019/7/15 15:55
 * @Description:
 */
public class TimeClient {

    public static void main(String[] args) {

        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            socket = new Socket("127.0.0.1",19999);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println("QUERY TIME ORDER");
            System.out.println("Send order 2 server succeed");
            String resp = in.readLine();
            System.out.println("Now is :"+resp);
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if (in != null){
                try {
                    in.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (out != null){
                try {
                    out.close();
                    out = null;
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (socket != null){
                try {
                    socket.close();
                    socket = null;
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

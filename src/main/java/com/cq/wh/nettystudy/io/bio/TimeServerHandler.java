package com.cq.wh.nettystudy.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * @Auther: wh
 * @Date: 2019/7/15 10:20
 * @Description:
 */
public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {

        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(),true);
            String currentTime = null;
            String body = null;
            while (true){
                body = in.readLine();
                if(body==null){
                    break;
                }
                System.out.println("The time server receive order :"+body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)? new Date(System.currentTimeMillis()).toString():"BAD ORDER";
                System.out.println(currentTime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
            if (this.socket != null){
                try {
                    this.socket.close();
                    this.socket = null;
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }
}

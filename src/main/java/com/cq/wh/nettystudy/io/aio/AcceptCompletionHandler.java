package com.cq.wh.nettystudy.io.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @Auther: wh
 * @Date: 2019/8/2 10:44
 * @Description:
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {
    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
        attachment.asynchronousServerSocketChannel.accept();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
       // result.read(buffer,buffer,mew );
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
        attachment.latch.countDown();
    }
}

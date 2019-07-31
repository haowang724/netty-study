package com.cq.wh.nettystudy.io.fakeAsynIo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: wh
 * @Date: 2019/7/22 09:56
 * @Description: 服务处理线程池
 */
public class TimeServerHandlerExecutePool {

    private ExecutorService executorService;

    public TimeServerHandlerExecutePool(int maxPoolSize,int queueSize){
        executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),maxPoolSize,120L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task){
        executorService.execute(task);
    }

}


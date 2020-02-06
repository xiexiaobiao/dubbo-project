package com.biao.mall.common.util;

import org.apache.tomcat.jni.Thread;

import java.util.concurrent.*;

/**
 * @Classname ThreadPoolUtil
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-11 09:43
 * @Version 1.0
 **/
public class ThreadPoolUtil {

    public ThreadPoolUtil(){

    }

    public static ExecutorService getSingleThreadExecutor(){
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        return singleThreadExecutor;
    }

    public static ExecutorService getCachedThreadPool(){
        ExecutorService  cachedThreadPool = Executors.newCachedThreadPool();
        return cachedThreadPool;
    }

    public static ExecutorService getFixedThreadPool(){
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        return fixedThreadPool;
    }

    public static ExecutorService getScheduledThreadPool(){
        ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        return scheduledThreadPool;
    }
/*
* 获得一个自定义的线程池对象*/
    public static ThreadPoolExecutor getThreadPoolExecutor(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,10,1000L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5),//也可以是其他阻塞队列：LinkedBlockingDeque、SynchronousQueue
                Executors.defaultThreadFactory(),//这里使用最简单的静态工具类的实现，也可以自定义threadFactory
                new ThreadPoolExecutor.AbortPolicy());//拒绝策略还有：DiscardPolicy、DiscardOldestPolicy、CallerRunsPolicy
        return threadPoolExecutor;
    }

    public static Future addTaskToThreadPool(Runnable task){
        Future future = ThreadPoolUtil.getThreadPoolExecutor().submit(task);
        return future;
    }

    public static void shutdownThreadPool(){
        ThreadPoolUtil.getThreadPoolExecutor().shutdown();
    }
}

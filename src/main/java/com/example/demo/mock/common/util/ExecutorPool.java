package com.example.demo.mock.common.util;

import cn.hutool.core.thread.NamedThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
* 线程池
* */
public class ExecutorPool {
    public static ExecutorService socketExecutor = new ThreadPoolExecutor(
            0,25,60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new NamedThreadFactory("socket-",false),
            new ThreadPoolExecutor.AbortPolicy()
    );
}

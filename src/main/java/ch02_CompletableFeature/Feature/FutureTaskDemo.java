package ch02_CompletableFeature.Feature;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author XuHan
 * @date 2023/12/4 15:52
 * 默认笔记 ： https://blog.csdn.net/qq_31745863/article/details/129463565
 */
public class FutureTaskDemo {
    private static ThreadPoolExecutor THREAD_POOL;
    static {
        // 默认线程池参数
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 5;
        int maximumPoolSize = corePoolSize;
        int keepAliveTime = 0;
        TimeUnit minutes = TimeUnit.MILLISECONDS;
        // 定义一个默认的线程池
        BlockingQueue<Runnable> sendContentWorkQueue = new LinkedBlockingQueue<>(300);
        ThreadFactory sendContentThreadFactory = new ThreadFactoryBuilder().setNameFormat("CONTENT_THREAD_POOL-%d").build();
        THREAD_POOL = new ThreadPoolExecutor(3,
                3,
                keepAliveTime,
                minutes,
                sendContentWorkQueue,
                sendContentThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 用一个线程来调用feature
        FutureTask futureTask = new FutureTask<>(new MyThread());
        Thread t1 = new Thread(futureTask, "t1");
        t1.start();
        System.out.println(futureTask.get());
    }

}

class MyThread implements Callable {
    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread()+"---"+"进入callable子线程");
        return Thread.currentThread()+"---"+"hello callable";
    }
}

package ch02_CompletableFeature.c02_CompletableFeature.c01;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author XuHan
 * @date 2023/12/4 17:01
 */
public class CF02_supplyAsync {
    private static ThreadPoolExecutor THREAD_POOL;
    static {
        // 默认线程池参数
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 5;
        int maximumPoolSize = corePoolSize;
        int keepAliveTime = 0;
        TimeUnit minutes = TimeUnit.MILLISECONDS;
        // 定义一个默认的线程池
        BlockingQueue<Runnable> sendContentWorkQueue = new LinkedBlockingQueue<>(300);
        ThreadFactory sendContentThreadFactory = new ThreadFactoryBuilder().setNameFormat("THREAD_POOL-%d").build();
        THREAD_POOL = new ThreadPoolExecutor(3,
                3,
                keepAliveTime,
                minutes,
                sendContentWorkQueue,
                sendContentThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + "running");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "over";
        },THREAD_POOL);// 注意这边用了自己定义的线程池
        System.out.println(Thread.currentThread() + "running");
        System.out.println(completableFuture.get());
        THREAD_POOL.shutdown();
    }
}

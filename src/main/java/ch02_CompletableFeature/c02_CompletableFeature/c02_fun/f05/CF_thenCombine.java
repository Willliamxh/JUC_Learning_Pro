package ch02_CompletableFeature.c02_CompletableFeature.c02_fun.f05;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author XuHan
 * @date 2023/12/6 16:20
 */
public class CF_thenCombine {
    private static ThreadPoolExecutor THREAD_POOL;
    static {
        // 默认线程池参数
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 5;
        int maximumPoolSize = corePoolSize;
        int keepAliveTime = 0;
        TimeUnit minutes = TimeUnit.MILLISECONDS;
        // 定义一个默认的线程池
        BlockingQueue<Runnable> sendContentWorkQueue = new LinkedBlockingQueue<>(300);
        ThreadFactory sendContentThreadFactory = new ThreadFactoryBuilder().setNameFormat("XUHAN_THREAD_POOL-%d").build();
        THREAD_POOL = new ThreadPoolExecutor(3,
                3,
                keepAliveTime,
                minutes,
                sendContentWorkQueue,
                sendContentThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture.supplyAsync(()->{
            // 这边暂停一下，让combine用这个线程（）
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in 1");
            return 10;
        },THREAD_POOL)
                .thenCombine(CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in 2");
            return 20;
        }),(x,y)->{
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in 3");
            System.out.println("x=="+x);
            System.out.println("y=="+y);
            return x+y;
        });

        Thread.sleep(5000);
        THREAD_POOL.shutdown();
    }

}


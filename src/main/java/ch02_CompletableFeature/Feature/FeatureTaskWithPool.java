package ch02_CompletableFeature.Feature;

import ch02_CompletableFeature.LogHelper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author XuHan
 * @date 2023/12/4 16:06
 */
public class FeatureTaskWithPool {
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
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

        long s = System.currentTimeMillis();

        FutureTask<String> task1 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                LogHelper.printLog("task1 over");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "task1 over";
        });
        FutureTask<String> task2 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                LogHelper.printLog("task2 over");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "task2 over";
        });
        FutureTask<String> task3 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                LogHelper.printLog("task3 over");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "task3 over";
        });
        Future<?> submit1 = THREAD_POOL.submit(task1);
        Future<?> submit2 = THREAD_POOL.submit(task2);
        Future<?> submit3 = THREAD_POOL.submit(task3);
        System.out.println(task1.get());
        System.out.println(task2.get());
        System.out.println(task3.get());
        long e = System.currentTimeMillis();
        System.out.println("执行时间:"+(e-s));
        THREAD_POOL.shutdown();
    }
}

package ch02_CompletableFeature.c02_CompletableFeature.c01_base;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author XuHan
 * @date 2023/12/4 17:12
 */
public class CF03_CommonUse {
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

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // future1();
        //自定义线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try {
            CompletableFuture.supplyAsync(() ->{
                System.out.println(Thread.currentThread()+"...come in");
                int result = ThreadLocalRandom.current().nextInt(10);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread()+"1秒钟后出结果:"+result);
                if(result > 5){
                    int i = 10/0; //制造异常
                }
                return result;
                                        //v表示result,e表示异常,CompletableFuture通过whenComplete来减少阻塞和轮询（自动回调）
            },THREAD_POOL).whenComplete((v,e) ->{
                if(e == null){//判断有没有异常
                    System.out.println(Thread.currentThread()+"计算完成,更新系统update value:"+v);
                }
            }).exceptionally(e ->{
                e.printStackTrace();
                System.out.println(Thread.currentThread()+"异常情况:"+e.getCause()+"\t"+e.getMessage());
                return null;
            });
            System.out.println(Thread.currentThread()+"线程先去忙其它任务");
        }catch (Exception e){
            e.printStackTrace();
        }finally {//关闭线程池
            THREAD_POOL.shutdown();
        }
    }
    private static void future1() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + "...come in");
            int result = ThreadLocalRandom.current().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "2秒钟后出结果:" + result);
            return result;
        });
        System.out.println(Thread.currentThread()+"线程先去忙其他任务!大约2s");
        Thread.sleep(2000);
        // 这边阻塞获取结果
        System.out.println(completableFuture.get());

        System.out.println("-----执行耗时： " + (System.currentTimeMillis() - startTime) + "ms  ------");
        // 这样总共只需要2s左右 默认用ForkJoinPool在执行
    }

}

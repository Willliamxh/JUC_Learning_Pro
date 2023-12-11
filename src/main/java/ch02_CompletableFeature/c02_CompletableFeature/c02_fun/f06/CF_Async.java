package ch02_CompletableFeature.c02_CompletableFeature.c02_fun.f06;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author XuHan
 * @date 2023/12/11 10:51
 */
public class CF_Async {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        // 1.第一个任务传入一个自定义的线程池，则thenRun就默认使用的是上一个线程的线程池
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("1号任务"+"\t"+Thread.currentThread().getName());
            return "abcd";
        },threadPool).thenRun(()->{
            try {TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("2号任务"+"\t"+Thread.currentThread().getName());
        }).thenRun(()->{
            try {TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("3号任务"+"\t"+Thread.currentThread().getName());
        }).thenRun(()->{
            try {TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("4号任务"+"\t"+Thread.currentThread().getName());
        });
        threadPool.shutdown();

    }
}
/**
 * 结果
 1号任务	pool-1-thread-1
 2号任务	pool-1-thread-1
 3号任务	pool-1-thread-1
 4号任务	pool-1-thread-1
 */
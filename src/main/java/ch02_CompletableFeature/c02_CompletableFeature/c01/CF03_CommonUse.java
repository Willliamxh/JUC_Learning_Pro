package ch02_CompletableFeature.c02_CompletableFeature.c01;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author XuHan
 * @date 2023/12/4 17:12
 */
public class CF03_CommonUse {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        future1();

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

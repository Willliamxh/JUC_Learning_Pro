package ch02_CompletableFeature.c02_CompletableFeature.c01;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author XuHan
 * @date 2023/12/4 16:54
 */
public class CF01_runAsync {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // runAsync不需要返回参数
        CompletableFuture<Void> completableFuture= CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            //停顿几秒线程
            try {
                System.out.println(Thread.currentThread()+"running");
                SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(Thread.currentThread()+"running");
        System.out.println(completableFuture.get());

    }
}

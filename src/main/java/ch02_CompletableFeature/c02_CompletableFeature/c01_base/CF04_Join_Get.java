package ch02_CompletableFeature.c02_CompletableFeature.c01_base;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author XuHan
 * @date 2023/12/4 17:40
 */
public class CF04_Join_Get {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 抛出异常
        // get();
        // 没有抛出异常
        join();


    }

    private static void join() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            return "hello 12345";
        });
        System.out.println(completableFuture.join());
    }

    private static void get() throws InterruptedException, ExecutionException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            return "hello 12345";
        });
        System.out.println(completableFuture.get());
    }
}

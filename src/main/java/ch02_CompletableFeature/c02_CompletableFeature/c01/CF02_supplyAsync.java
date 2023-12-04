package ch02_CompletableFeature.c02_CompletableFeature.c01;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author XuHan
 * @date 2023/12/4 17:01
 */
public class CF02_supplyAsync {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + "running");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "over";
        });
        System.out.println(Thread.currentThread() + "running");
        System.out.println(completableFuture.get());
    }
}

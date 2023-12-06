package ch02_CompletableFeature.c02_CompletableFeature.c02_fun.f03;

import java.util.concurrent.CompletableFuture;

/**
 * @author XuHan
 * @date 2023/12/6 16:00
 */
public class CF_thenRun {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(()->{
            return 1;
        }).thenApply(f->{
            return f+1;
        }).thenRun(()->{
            System.out.println("哈哈哈哈，我不需要你");
        });
    }
}

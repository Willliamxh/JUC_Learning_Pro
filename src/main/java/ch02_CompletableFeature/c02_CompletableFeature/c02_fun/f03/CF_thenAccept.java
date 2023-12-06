package ch02_CompletableFeature.c02_CompletableFeature.c02_fun.f03;

import java.util.concurrent.CompletableFuture;

/**
 * @author XuHan
 * @date 2023/12/6 15:57
 */
public class CF_thenAccept {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(f -> {
            return f + 2;
        }).thenApply(f -> {
            return f + 3;
        }).thenApply(f -> {
            return f + 4;
        }).thenAccept(r ->
                // 最后不需要返回值
                System.out.println(r));

    }
}

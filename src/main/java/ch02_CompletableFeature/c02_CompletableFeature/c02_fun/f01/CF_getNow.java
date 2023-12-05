package ch02_CompletableFeature.c02_CompletableFeature.c02_fun.f01;

import java.util.concurrent.*;

/**
 * @author XuHan
 * @date 2023/12/5 10:39
 */
public class CF_getNow {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 20, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                // 暂停3s
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 1;
        },threadPoolExecutor);

        // 尝试主线程暂停4s等待结果
        try { TimeUnit.SECONDS.sleep(4); } catch (InterruptedException e) { e.printStackTrace(); }
        Integer now = completableFuture.getNow(-999);
        // 如果等待就是结果 如果不等就是-999
        System.out.println(now);
        threadPoolExecutor.shutdown();

    }
}

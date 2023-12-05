package ch02_CompletableFeature.c02_CompletableFeature.c02_fun.f01;

import java.util.concurrent.*;

/**
 * @author XuHan
 * @date 2023/12/5 10:39
 */
public class CF_complete {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
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
        // try { TimeUnit.SECONDS.sleep(4); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println(completableFuture.complete(-44)+"\t"+completableFuture.get());

        threadPoolExecutor.shutdown();

    }
}

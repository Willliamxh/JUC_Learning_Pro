package ch02_CompletableFeature.Feature;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author XuHan
 * @date 2023/12/4 15:52
 * 默认笔记 ： https://blog.csdn.net/qq_31745863/article/details/129463565
 */
public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();

        // 用一个线程来调用feature
        FutureTask futureTask = new FutureTask<>(new MyThread());
        Thread t1 = new Thread(futureTask, "t1");
        t1.start();
        // 假设在这边主线程忙别的事情
        Thread.sleep(2000L);
        // 然后这边异步等待另外个线程的结果
        System.out.println(Thread.currentThread()+"---"+futureTask.get());
        // 最终执行耗时只需要2s 因为主线程干主线程的，异步线程干异步的。
        System.out.println("-----执行耗时： " + (System.currentTimeMillis() - startTime) + "ms  ------");
    }

}

class MyThread implements Callable {
    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread()+"---"+"进入callable子线程");
        Thread.sleep(2000L);
        return "hello callable";
    }
}

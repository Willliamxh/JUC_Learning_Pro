package ch02_CompletableFeature.c01_Feature.disadvantage;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author XuHan
 * @date 2023/12/4 16:36
 */
public class FutureAPI_IsDone {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> futureTask = new FutureTask<>(() ->{
            System.out.println(Thread.currentThread().getName()+"\t...come in");
            TimeUnit.SECONDS.sleep(5);
            return "task over";
        });
        Thread t1 = new Thread(futureTask,"t1");
        t1.start();
        System.out.println(Thread.currentThread().getName()+"/t...忙其他任务了");
        while(true){
            if(futureTask.isDone()){
                System.out.println(futureTask.get());
                break;
            }else{
                //暂停毫秒
                try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("正在处理中，不要再催了，越催越慢 ，再催熄火");
            }
        }
    }
}

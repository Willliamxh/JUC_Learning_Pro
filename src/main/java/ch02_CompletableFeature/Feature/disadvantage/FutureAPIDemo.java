package ch02_CompletableFeature.Feature.disadvantage;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author XuHan
 * @date 2023/12/4 16:23
 */
public class FutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> futureTask = new FutureTask<>(() ->{
            System.out.println(Thread.currentThread().getName()+"\t...come in");
            TimeUnit.SECONDS.sleep(5);
            return "task over";
        });
        Thread t1 = new Thread(futureTask,"t1");
        t1.start();
        System.out.println(Thread.currentThread().getName()+"/t...忙其他任务了");
//        System.out.println(futureTask.get()); //get方法等待
        System.out.println(futureTask.get(3,TimeUnit.SECONDS));//get方法过时不候 抛出timeout异常
    }

}

/**
 *1 get容易导致阻塞，一般建议放在程序后面，一旦调用不见不散，非要等到结果才会离开，不管你是否计算完成，容易程序堵塞。
 *2 假如我不愿意等待很长时间，我希望过时不候，可以自动离开.
 * 当我们在主线程做其他事情之前调用get方法,那么主线程会被阻塞,主线程会一直等到子线程任务执行完毕,get方法拿到返回值为止,那么这样和单线程没有任何区别甚至更慢
 */
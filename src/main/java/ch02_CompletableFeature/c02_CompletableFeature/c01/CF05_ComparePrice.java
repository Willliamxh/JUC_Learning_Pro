package ch02_CompletableFeature.c02_CompletableFeature.c01;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author XuHan
 * @date 2023/12/4 17:46
 */
public class CF05_ComparePrice {
    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("pdd"),
            new NetMall("taobao"),
            new NetMall("dangdangwang"),
            new NetMall("tmall"),
            new NetMall("xuhan"),
            new NetMall("xuhan2"),
            new NetMall("xuhan3"),
            new NetMall("xuhan34")
    );

    private static ThreadPoolExecutor THREAD_POOL;
    static {
        // 默认线程池参数
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 5;
        int maximumPoolSize = corePoolSize;
        int keepAliveTime = 0;
        TimeUnit minutes = TimeUnit.MILLISECONDS;
        // 定义一个默认的线程池
        BlockingQueue<Runnable> sendContentWorkQueue = new LinkedBlockingQueue<>(300);
        ThreadFactory sendContentThreadFactory = new ThreadFactoryBuilder().setNameFormat("THREAD_POOL-%d").build();
        THREAD_POOL = new ThreadPoolExecutor(3,
                3,
                keepAliveTime,
                minutes,
                sendContentWorkQueue,
                sendContentThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    //同步 ，step by step

    /**
     * List<NetMall>  ---->   List<String>
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPriceByStep(List<NetMall> list,String productName)
    {
        return list
                .parallelStream()
                .map(netMall -> getFormat(productName, " in %s price is %.2f", netMall))
                .collect(Collectors.toList());
    }

    private static String getFormat(String productName, String x, NetMall netMall) {
        return String.format(Thread.currentThread()+productName + x, netMall.getMallName(), netMall.calcPrice(productName));
    }
    //异步 ，多箭齐发

    /**
     * List<NetMall>  ---->List<CompletableFuture<String>> --->   List<String>
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPriceByASync(List<NetMall> list,String productName)
    {
        // 许晗测试
        // List<String> strings = list.stream()
        //         .map(netMall -> {
        //             CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> getFormat(productName, " is %s price is %.2f", netMall));
        //             return completableFuture;
        //         })
        //         .collect(Collectors.toList())
        //         .stream()
        //         .map(stringCompletableFuture -> {
        //             try {
        //                 return stringCompletableFuture.get();
        //             } catch (InterruptedException | ExecutionException e) {
        //                 throw new RuntimeException(e);
        //             }
        //         })
        //         .collect(Collectors.toList());
        return list
                .stream()
                .map(netMall -> CompletableFuture.supplyAsync(() -> getFormat(productName, " is %s price is %.2f", netMall),THREAD_POOL))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }


    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> list1 = getPriceByStep(list, "mysql");
        for (String element : list1) {
            System.out.println(element);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("----costTime: "+(endTime - startTime) +" 毫秒");

        System.out.println();

        long startTime2 = System.currentTimeMillis();
        List<String> list2 = getPriceByASync(list, "mysql");
        for (String element : list2) {
            System.out.println(element);
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("----costTime: "+(endTime2 - startTime2) +" 毫秒");

    }
}


class NetMall
{
    private String mallName;

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public NetMall(String mallName)
    {
        this.mallName = mallName;
    }

    public double calcPrice(String productName)
    {
        //检索需要1秒钟
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}

/**
 实战精讲-比价网站case:
 1 需求说明
 1.1 同一款产品，同时搜索出同款产品在各大电商平台的售价；
 1.2 同一款产品，同时搜索出本产品在同一个电商平台下，各个入驻卖家售价是多少

 2 输出返回：
 出来结果希望是同款产品的在不同地方的价格清单列表， 返回一个List<String>
 《mysql》in jd price is 88.05
 《mysql》in dang dang price is 86.11
 《mysql》in tao bao price is 90.43

 3 解决方案，比对同一个商品在各个平台上的价格，要求获得一个清单列表
 1   stepbystep   ， 按部就班， 查完京东查淘宝， 查完淘宝查天猫......
 2   all in       ，万箭齐发，一口气多线程异步任务同时查询。。。

 */

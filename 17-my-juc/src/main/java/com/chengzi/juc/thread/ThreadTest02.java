package com.chengzi.juc.thread;

import java.util.concurrent.*;

public class ThreadTest02 {

    public static ExecutorService service = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /**
         * 1、corePoolSize：              核心线程数，一直存在除非（allowCoreThreadTimeOut）创建好以后就准备就绪
         * 2、maximumPoolSize：           最大的线程数，控制资源
         * 3、keepAliveTime：             存活是将，如果当前的线程数量大于core数量，释放空闲的线程，只要线程空闲时间大于keepAliveTime
         * 4、TimeUnit：                  时间单位
         * 5、BlockingQueue：             阻塞队列，如果任务有很多，就会将目前多的任务放在队列里面，只要有线程空闲
         *                                就会去队列里取出新的任务继续执行
         * 6、ThreadFactory ：            线程的创建工厂
         * 6、RejectedExecutionHandler：  如果队列满了，按照我们指定的拒绝策略拒绝执行任务
         *
         *
         *  工作顺序：
         *  1）、线程池创建，准备好core数量的核心线程，准备接收任务
         *      1.1 core满了，就将再进来的任务放入阻塞队列中，空闲的core就会自己去阻塞队列里获取任务执行
         *      1.2 阻塞队列满了，就直接开启新线程执行，最大只能开到max指定的数量
         *      1.3 max满了就用RejectedExecutionHandler拒绝任务
         *      1.4 max都执行完成，有很多空闲在指定时间keepAliveTime后，释放max-core这
         *
         *
         * 2）拒绝策略：
         *      2.1 DiscardOldestPolicy ：丢弃最老的任务
         *      2.2 CallerRunsPolicy：直接调用run方法，不启动线程，同步调用
         *      2.3 AbortPolicy：直接丢弃
         *      2.4 DiscardPolicy：直接丢弃不抛一场
         *
         * 3）常见的4种线程池
         *      3.1 newCachedThreadPool  创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。所有都可回收
         *      3.2 newFixedThreadPool  创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。所有都不可回收
         *      3.3 newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
         *      3.4 newSingleThreadExecutor创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
         * 面试:
         * 一个线程池 core 7; max 20 ，queue:50，100 并发进来怎么分配的;
         * 先有 7 个能直接得到执行，接下来 50 个进入队列排队，在多开 13 个继续执行。现在 70 个 被安排上了。剩下 30 个默认拒绝策略。
         *
         */
        new ThreadPoolExecutor(5,
                200,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

    }

}


package com.chengzi.juc.thread;

import java.util.concurrent.*;

/**
 *
 * 异步编排
 *
 */
public class ThreadTest03 {

    public static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("main start....");


        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 5;
            System.out.println("运行结果" + i);
        },executorService);


        /**
         * 1、runXxxx 都是没有返回结果的，supplyXxx 都是可以获取返回结果的
         *
         * 2、可以传入自定义的线程池，否则就用默认的线程池;
         */
        CompletableFuture<Integer> fu = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 5;
            System.out.println("运行结果" + i);
            return i;
        }, executorService);
        Integer integer = fu.get();
        System.out.println(integer);
        System.out.println("main end....");


        /***
         *
         *whenComplete 可以处理正常和异常的计算结果，exceptionally 处理异常情况。 whenComplete 和 whenCompleteAsync 的区别:
         * whenComplete:是执行当前任务的线程执行继续执行 whenComplete 的任务。
         * whenCompleteAsync:是执行把 whenCompleteAsync 这个任务继续提交给线程池 来进行执行
         *  方法不以 Async 结尾，意味着 Action 使用相同的线程执行，而 Async 可能会使用其他线程
         * 执行(如果是使用相同的线程池，也可能会被同一个线程选中执行)
         *
         *
         */
        CompletableFuture<Integer> fu2  = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 5;
            System.out.println("运行结果" + i);
            return i;
        }, executorService);
        fu2.whenComplete((Object result,Throwable exception)->{
            System.out.println("结果:"+result+";异常是："+exception);
        }).exceptionally(throwable -> {
            //感知到异常。返回默认值
            return 10;
        });
        System.out.println(integer);
        System.out.println("main end....");

        /**
         * 方法执行完成后的处理
         */
        CompletableFuture<Integer> fu3  = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 5;
            System.out.println("运行结果" + i);
            return i;
        }, executorService);
        fu3.handle((res,thr)->{
            if (res != null) {
                return res;
            }
            if (thr != null) {
                return 0;
            }
            return 0;
        });
    }

}


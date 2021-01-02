package com.chengzi.juc.thread;

import java.sql.SQLOutput;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

/**
 *
 * 串行化
 *
 *
 */
public class ThreadTest04 {

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


        /**
         *  穿行化
         */

        CompletableFuture<Integer> fu4  = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 5;
            System.out.println("运行结果" + i);
            return i;
        }, executorService);

        fu4.thenRun(()->{
            System.out.println("不能接收到上一步执行结果，无返回值");
        });

        fu4.thenAccept((Object result) ->{
            System.out.println("能接收到上一步执行结果，无返回值");
            System.out.println("上一步执行结果是" + result);
        });

        CompletableFuture<String> stringCompletableFuture =
                fu4.thenApplyAsync((Object result) -> {

            System.out.println("能接收到上一步执行结果，有返回值");
            System.out.println("上一步执行结果是" + result);
            return "123";

        });
        System.out.println(stringCompletableFuture.get());

        /**
         * 非lamda可以这样写，为什么写成lambda就可以只用写输入参数
         */
        //TODO
        fu4.thenApplyAsync(new Function<Integer, String>() {


            @Override
            public String apply(Integer integer) {
                return "123";
            }
        });


        /***
         *
         *  两个任务必须都完成，触发该任务。
         * thenCombine:             组合两个 future，获取两个 future 的返回结果，并返回当前任务的返回值
         * thenAcceptBoth:          组合两个 future，获取两个 future 任务的返回结果，然后处理任务，没有 返回值。
         * runAfterBoth:            组合两个 future，不需要获取 future 的结果，只需两个 future 处理完任务后，处理该任务。
         */
        CompletableFuture<Integer> future01 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1线程：" + Thread.currentThread().getId());
            int i = 10 / 5;
            System.out.println("任务1结束");
            return i;
        }, executorService);
        CompletableFuture<String> future02 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2线程：" + Thread.currentThread().getId());
            System.out.println("任务2结束");
            return "hello";
        }, executorService);

        future01.runAfterBothAsync(future02,()->{
                System.out.println("任务3开始：" + Thread.currentThread().getId());
        },executorService);

        future01.thenAcceptBothAsync(future02,(res1,res2)->{
            System.out.println("任务3开始：" + Thread.currentThread().getId());
            System.out.println(res1 + res2);
        },executorService);


        CompletableFuture<String> stringCompletableFuture1 = future01.thenCombineAsync(future02, (res1, res2) -> {
            return res1 + res2;
        }, executorService);
        System.out.println(stringCompletableFuture1.get());


        /**
         *
         * 当两个任务中，任意一个 future 任务完成的时候，执行任务。
         * applyToEither:       两个任务有一个执行完成，获取它的返回值，处理任务并有新的返回值。
         * acceptEither:        两个任务有一个执行完成，获取它的返回值，处理任务，没有新的返回值。
         * runAfterEither:      两个任务有一个执行完成，不需要获取 future 的结果，处理任务，也没有返 回值。
         */
        CompletableFuture<Object> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1线程：" + Thread.currentThread().getId());
            int i = 10 / 5;
            System.out.println("任务1结束");
            return i;
        }, executorService);
        CompletableFuture<Object> future4 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2线程：" + Thread.currentThread().getId());
            System.out.println("任务2结束");
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }, executorService);

        future3.runAfterEitherAsync(future4,()->{
            System.out.println("任务3开始：" + Thread.currentThread().getId());
        },executorService);

        //必须要求返回值类型一样
        future3.applyToEitherAsync(future4,(res)->{
            System.out.println("任务3开始：" + Thread.currentThread().getId());
            System.out.println(res);
            return ""+res;
        },executorService);


        CompletableFuture<String> stringCompletableFuture2 = future01.thenCombineAsync(future02, (res1, res2) -> {
            return res1 + res2;
        }, executorService);
        System.out.println(stringCompletableFuture2.get());

        /**
         * 多任务组合
         */

        CompletableFuture<String> img = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询到商品的图片信息");
            return "hello.jpg";
        },executorService);
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {

            try {
                Thread.sleep(3000);
                System.out.println("查询到商品的属性信息");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello.jpg";
        },executorService);
        CompletableFuture<String> desc = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询到商品的描述信息");
            return "hello.jpg";
        },executorService);
        //传统的方法阻塞,查询方法多很麻烦
//        img.get();
//        future.get();
//        desc.get();

        CompletableFuture<Void> allresult = CompletableFuture.allOf(img, future, desc);
        allresult.get();//等待所有运行完
//        allresult.join();
        /**
         * 如果不使用线程池提交 查询到商品的属性信息 不会在控制台显示，
         * 使用了线程池，会在控制台显示，方法是无限期存活的 一直等待 因为有线程池，控制台会显示
         * 但是控制台会先显示main end，所以要在main end之前获取到结果是不可行的
         * 所以需要allresult.get()
         */




    }

}


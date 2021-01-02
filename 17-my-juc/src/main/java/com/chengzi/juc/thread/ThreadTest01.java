package com.chengzi.juc.thread;

import java.util.concurrent.*;

public class ThreadTest01 {

    public static ExecutorService service = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("main start....");

        //1、
        Thread01 thread01 = new Thread01();
        thread01.start();

        //2、
        Thread thread = new Thread(new Runnable01());
        thread.start();

        //3、
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable01());
        new Thread(futureTask).start();
        //阻塞等待所有线程执行完
        Integer integer = futureTask.get();

        System.out.println("main end....");


        //以后的业务代码里，以上三种启动线程的方式都不用，将所有的异步任务交给线程池执行
        new Thread(()-> System.out.println("123")).start();

        //4、
        //execute没有返回值，submit有返回值
        service.execute(new Runnable01());

        /**
         *
         * 1、2都不能得到返回值，3可以获取的到返回值
         * 1、2、3都不能控制资源
         * 4可以控制资源、系统性能稳定
         */


    }

    public  static class Thread01 extends  Thread{
        @Override
        public void run() {
            System.out.println("当前线程：" +Thread.currentThread().getId());
            int i = 10  / 5 ;
            System.out.println("运行结果" + i);
        }
    }
    public static class Runnable01 implements Runnable{

        @Override
        public void run() {
            System.out.println("当前线程：" +Thread.currentThread().getId());
            int i = 10  / 5 ;
            System.out.println("运行结果" + i);
        }
    }

    public static class Callable01 implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            System.out.println("当前线程：" +Thread.currentThread().getId());
            int i = 10  / 5 ;
            System.out.println("运行结果" + i);

            return i;
        }
    }
}


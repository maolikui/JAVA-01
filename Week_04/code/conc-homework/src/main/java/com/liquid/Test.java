package com.liquid;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 测试类
 *
 * @author Liquid
 */
public class Test {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        // int result = sum(); //这是得到的返回值
        // MyRunnable<Integer> runnable = new MyRunnable<>();
        // Thread thread = new Thread(runnable);
        // thread.start();
        //方法一:主线程超时等待
        // Thread.sleep(1000);

        //方法二:join方法，主线程等待任务线程执行结束再执行
        // thread.join();

        //方法三:wait/notify 机制，join的底层其实也是该机制
        // MyRunnable2<Integer> runnable2 = new MyRunnable2<>();
        // 方法七利用主线程中断
        // runnable2.setT(Thread.currentThread());
        // Thread thread2 = new Thread(runnable2);
        // thread2.start();
        // synchronized (thread2) {
        //     //此处标记为任务线程是否执行完成
        //     while (runnable2.isFlag()) {
        //         try {
        //             thread2.wait();
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // }

        // 方法四: 根据当前线程线程组中线程的数量判断任务线程是否执行完成
        // while (Thread.activeCount() > 2) {
        //     Thread.yield();
        // }

        // 方法五：封装FutureTask(线程和线程池两种方式)
        // MyCallable callable = new MyCallable();
        // FutureTask<Integer> futureTask = new FutureTask<>(callable);
        // new Thread(futureTask).start();
        // Integer res = futureTask.get();

        //方法六：LockSupport的park/unpark
        // MyRunnable3<Integer> runnable = new MyRunnable3<>();
        // runnable.setT(Thread.currentThread());
        // Thread thread = new Thread(runnable);
        // thread.start();
        // LockSupport.park();

        //方法七：线程中断通知(任务线程可以采用主线程中断解除等待状态)
        //将方法三中的notifyAll和方法六中的unpark，替换为主线程interrupt

        //方法八：ReentrantLock/Condition
        // final ReentrantLock lock = new ReentrantLock();
        // final Condition notCompleted = lock.newCondition();
        // MyRunnable4<Integer> runnable = new MyRunnable4<>();
        // runnable.setLock(lock);
        // runnable.setNotCompleted(notCompleted);
        // Thread thread = new Thread(runnable);
        // thread.start();
        //
        // lock.lock();
        // try {
        //     while (runnable.isFlag()) {
        //         notCompleted.await();
        //     }
        // } finally {
        //     lock.unlock();
        // }

        //方法九：Semaphore
        // final Semaphore notCompleted = new Semaphore(0);
        // MyRunnable5<Integer> runnable = new MyRunnable5<>();
        // runnable.setNotCompleted(notCompleted);
        // Thread thread = new Thread(runnable);
        // thread.start();
        // notCompleted.acquire();

        //方法十：CompletableFuture
        // CompletableFuture<Integer> res = CompletableFuture.supplyAsync(() -> {
        //     return sum();
        // });

        //方法十一：Future
        // ExecutorService executor = Executors.newCachedThreadPool();
        // Future<Integer> result = executor.submit(new MyCallable());
        // executor.shutdown();
        // try {
        //     System.out.println("result:" + result.get());
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // } catch (ExecutionException e) {
        //     e.printStackTrace();
        // }

        //方法十二：CountDownLatch
        // CountDownLatch countDownLatch = new CountDownLatch(1);
        // MyRunnable6<Integer> runnable = new MyRunnable6<>();
        // runnable.setCountDownLatch(countDownLatch);
        // Thread thread = new Thread(runnable);
        // thread.start();
        // countDownLatch.await();

        //方法十三：CyclicBarrier
        // CyclicBarrier barrier = new CyclicBarrier(2);
        //         // MyRunnable7<Integer> runnable = new MyRunnable7<>();
        //         // runnable.setCyclicBarrier(barrier);
        //         // Thread thread = new Thread(runnable);
        //         // thread.start();
        //         // barrier.await();

        //方法十四：Exchanger
        // Exchanger<Result<Integer>> exchanger = new Exchanger<>();
        // MyRunnable8 runnable = new MyRunnable8();
        // runnable.setExchanger(exchanger);
        // Thread thread = new Thread(runnable);
        // thread.start();
        // Result<Integer> res = exchanger.exchange(new Result<>());

        //方法十五：volatile
        // MyRunnable9 runnable = new MyRunnable9();
        // Thread thread = new Thread(runnable);
        // thread.start();
        // //相当于轮询
        // while (runnable.getSignal().get() == 1) {
        // }

        //方法十六：pipe
        PipedWriter writer = new PipedWriter();
        PipedReader reader = new PipedReader();
        MyRunnable10 runnable = new MyRunnable10(writer);
        writer.connect(reader);
        Thread thread = new Thread(runnable);
        thread.start();

        char[] buf = new char[2048];
        String res = "";
        try {
            int len = reader.read(buf);
            res = new String(buf, 0, len);
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // System.out.println(res.get());
        // Result res = runnable.getRes();
        // 确保  拿到result 并输出
        // System.out.println("异步计算结果为：" + res.getRes());
        System.out.println("异步计算结果为：" + res);


        // 然后退出main线程
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}

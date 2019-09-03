package com.n33.guava.learn.utilities;

import java.util.concurrent.TimeUnit;

/**
 * log
 *
 * @author N33
 * @date 2019/9/2
 */
public class ElapsedExample {


    public static void main(String[] args) throws InterruptedException {
        process("123456789");


    }

    private static void process(String orderNo) throws InterruptedException {
        System.out.printf("start process the order %s\n", orderNo);
        long startNano = System.nanoTime();
        TimeUnit.SECONDS.sleep(1);

        System.out.printf("The orderNo %s process successful and elapsed %d\n",orderNo,(System.nanoTime() - startNano));

    }
}

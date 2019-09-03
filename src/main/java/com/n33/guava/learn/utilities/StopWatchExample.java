package com.n33.guava.learn.utilities;


import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class StopWatchExample {

    private final static Logger LOGGER = LoggerFactory.getLogger(StopWatchExample.class);


    public static void main(String[] args) throws InterruptedException {
        process("123456789");


    }

    private static void process(String orderNo) throws InterruptedException {
        LOGGER.info("start process the order [{}]", orderNo);
        Stopwatch stopwatch = Stopwatch.createStarted();
        TimeUnit.SECONDS.sleep(1);


        LOGGER.info("The orderNo [{}] process successful and elapsed [{}]", orderNo, stopwatch.stop().elapsed());

    }
}

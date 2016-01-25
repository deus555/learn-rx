package com.deus;

import rx.Observable;
import rx.functions.Action1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Interval {
    public static void main(String[] args) throws InterruptedException {
        withLatch();
        withBlocking();
    }

    private static void withLatch() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(5);

        Observable
                .interval(1, TimeUnit.SECONDS)
                .subscribe(counter -> {
                    latch.countDown();
                    System.out.println("Got with latch: " + counter);
                });

        latch.await();
    }

    private static void withBlocking() throws InterruptedException {
        Observable
                .interval(1, TimeUnit.SECONDS)
                .take(5)
                .toBlocking()
                .forEach(counter -> {
                    System.out.println("Got with blocking: " + counter);
                });
    }

}

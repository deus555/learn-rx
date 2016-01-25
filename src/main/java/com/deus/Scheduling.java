package com.deus;

import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.concurrent.CountDownLatch;

public class Scheduling {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch1 = new CountDownLatch(5);
        CountDownLatch latch2 = new CountDownLatch(5);

        Observable
                .range(1, 5)
                .map(integer -> {
                    System.out.println("1 Map: (" + Thread.currentThread().getName() + ")");
                    return integer + 2;
                })
                .subscribeOn(Schedulers.computation())
                .subscribe(integer -> {
                            System.out.println("1 Got: " + integer + " (" + Thread.currentThread().getName() + ")");
                            latch1.countDown();
                        }
                );

        Observable
                .range(1, 5)
                .map(integer -> {
                    System.out.println("2 Map: (" + Thread.currentThread().getName() + ")");
                    return integer + 2;
                })
                .observeOn(Schedulers.computation())
                .subscribe(integer -> {
                            System.out.println("2 Got: " + integer + " (" + Thread.currentThread().getName() + ")");
                            latch2.countDown();
                        }
                );

        latch1.await();
        latch2.await();
    }

}

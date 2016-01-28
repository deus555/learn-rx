package com.deus;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * Created by SergeyL on 28.01.2016.
 */
public class Tmp {

    public static void main(String[] args) {
        new ForkJoinPool(4)
                .submit(() -> IntStream.range(1, 20)
                        .parallel()
                .map(Tmp::longOperation)
                .sum()).join();
    }

    private static int longOperation(int i) {
        System.out.printf("Thread %d, i = %d%n", Thread.currentThread().getId(), i);
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Thread %d, i = %d%n", Thread.currentThread().getId(), i);
        return i;
    }

}

package com.deus;

import rx.Observable;
import rx.Subscriber;

public class WithError {
    public static void main(String[] args) {
        Observable
                .just(1, 2, 3)
                .doOnNext(integer -> {
                    if (integer.equals(2)) {
                        throw new RuntimeException("I don't like 2");
                    }
                })
                .subscribe(
                        value -> System.out.println("Got: " + value),
                        throwable -> System.err.println("Whoops: " + throwable.getMessage()),
                        () -> System.out.println("Completed Observable.")
                );
    }
}

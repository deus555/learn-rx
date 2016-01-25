package com.deus;

import rx.Observable;
import rx.Subscriber;

public class Take {
    public static void main(String[] args) {
        Observable
                .just("The", "Dave", "Brubeck", "Quartet", "Time", "Out")
                .take(5)
                .subscribe(
                        name -> System.out.println("Got: " + name),
                        throwable -> System.err.println("Whoops: " + throwable.getMessage()),
                        () -> System.out.println("Completed Observable.")
                );
    }
}

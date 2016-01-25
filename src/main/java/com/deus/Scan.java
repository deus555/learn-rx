package com.deus;

import rx.Observable;

public class Scan {
    public static void main(String[] args) {
        Observable
                .just(1, 2, 3, 4, 5)
                .scan((sum, value) -> sum + value)
                .subscribe(integer -> {
                    System.out.println("Sum: " + integer);
                });
    }

}

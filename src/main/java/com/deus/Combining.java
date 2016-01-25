package com.deus;

import rx.Observable;

public class Combining {
    public static void main(String[] args) {
        Observable<Integer> evens = Observable.just(2, 4, 6, 8, 10);
        Observable<Integer> odds = Observable.just(1, 3, 5, 7);

        Observable
                .merge(evens, odds)
                .toList()
                .subscribe(System.out::println);

        Observable
                .zip(evens, odds, (v1, v2) -> v1 + " + " + v2 + " is: " + (v1 + v2))
                .toList()
                .subscribe(System.out::println);
    }

}

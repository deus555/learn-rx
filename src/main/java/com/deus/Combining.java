package com.deus;

import rx.Observable;

import java.util.concurrent.TimeUnit;

public class Combining {
    public static void main(String[] args) {
        Observable<Integer> evens = Observable.just(2, 4, 6, 8, 10).delay(1, TimeUnit.SECONDS);
        Observable<Integer> odds = Observable.just(1, 3, 5, 7).delay(1, TimeUnit.SECONDS);

        Observable
                .merge(evens, odds)
                .toList()
                .toBlocking()
                .subscribe(System.out::println);

        Observable
                .concat(evens, odds)
                .toList()
                .toBlocking()
                .subscribe(System.out::println);

        Observable
                .zip(evens, odds, (v1, v2) -> v1 + " + " + v2 + " is: " + (v1 + v2))
                .toList()
                .toBlocking()
                .subscribe(System.out::println);

        Observable.combineLatest(evens, odds, (v1, v2) -> v1 + v2)
                .toList()
                .toBlocking()
                .subscribe(System.out::println);

        Observable
                .just(2, 3)
                .startWith(1)
                .toList()
                .subscribe(System.out::println);

    }

}

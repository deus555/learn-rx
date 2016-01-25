package com.deus;

import rx.Observable;

public class Filtering {
    public static void main(String[] args) {
        Observable
                .just(1, 2, 3, 4, 5)
                .filter(i -> i > 2)
                .toList()
                .subscribe(System.out::println);

        Observable
                .just(1, 2, 3, 4, 5)
                .first(i -> i > 2)
                .subscribe(System.out::println);

        Observable
                .just(1, 2, 3, 4, 5)
                .last(i -> i < 4)
                .subscribe(System.out::println);

        Observable
                .just(1, 2, 3, 4, 5)
                .take(3)
                .toList()
                .subscribe(System.out::println);

        Observable
                .just(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)
                .distinct()
                .toList()
                .subscribe(System.out::println);
    }

}

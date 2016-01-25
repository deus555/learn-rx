package com.deus;

import rx.Observable;

public class GroupBy {
    public static void main(String[] args) {
        Observable
                .just(1, 2, 3, 4, 5)
                .groupBy(integer -> integer % 2 == 0)
                .subscribe(grouped -> {
                    grouped
                            .toList()
                            .subscribe(integers -> {
                                System.out.println(integers + " (Even: " + grouped.getKey() + ")");
                            });
                });
    }

}

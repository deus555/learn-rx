package com.deus.crmpoc;

import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class UserUpdateStrategy {

    public static void main(String[] args) {
        Collection<UserUpdateData> userUpdateData = new ArrayList<>();

        Observable
                .from(userUpdateData)
                .map(UserUpdateStrategy::updateUser)
                .groupBy(UserUpdateResult::isSuccess)
                .retryWhen(attempts -> {
                    int retryCount = 5;
                    return attempts
                            .zipWith(Observable.range(1, retryCount), (n, i) -> i)
                            .flatMap(i -> {
                                System.out.println("delay retry by 1 second");
                                return Observable.timer(1, TimeUnit.SECONDS);
                            });
                })
                .subscribeOn(Schedulers.computation())
                .subscribe(grouped -> {
                    grouped
                            .toList()
                            .subscribe(integers -> {
                                System.out.println(integers + " (Even: " + grouped.getKey() + ")");
                            });
                });
    }

    private static UserUpdateResult updateUser(UserUpdateData data) {
        return new UserUpdateResult();
    }
}

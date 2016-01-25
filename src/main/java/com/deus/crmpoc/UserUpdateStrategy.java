package com.deus.crmpoc;

import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Collection;

public class UserUpdateStrategy {

    public static void main(String[] args) {
        Collection<UserUpdateData> userUpdateData = new ArrayList<>();

        Observable
                .from(userUpdateData)
                .map(UserUpdateStrategy::updateUser)
                .groupBy(UserUpdateResult::isSuccess)
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

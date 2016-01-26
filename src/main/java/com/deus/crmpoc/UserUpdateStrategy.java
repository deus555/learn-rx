package com.deus.crmpoc;

import rx.Notification;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;

public class UserUpdateStrategy {

    public static void main(String[] args) {
        Collection<UserUpdateData> userUpdateData = asList(
                new UserUpdateData(),
                new UserUpdateData(),
                new UserUpdateData(),
                new UserUpdateData(),
                new UserUpdateData(),
                new UserUpdateData()
        );

        Observable
                .from(userUpdateData)
                .subscribeOn(Schedulers.computation())
                .map(updateLogic)
                .retryWhen(retryLogic)
                .buffer(2)
                .doOnEach(progressNotification)
                .flatMap(Observable::from)
                .doOnCompleted(() -> System.out.println("Completed event"))
                .toList()
                .toBlocking()
                .subscribe(System.out::println);

    }

    private static UserUpdateResult updateUser(UserUpdateData data) {
        return new UserUpdateResult(new Random().nextInt(2) + 1 == 1);
    }

    private static final Func1<Observable<? extends Throwable>, Observable<?>> retryLogic = attempts -> {
        int retryCount = 5;
        return attempts
                .zipWith(Observable.range(1, retryCount), (n, i) -> i)
                .flatMap(i -> {
                    System.out.println("delay retry by 1 second");
                    return Observable.timer(1, TimeUnit.SECONDS);
                });
    };
    private static final Func1<UserUpdateData, UserUpdateResult> updateLogic = user -> {
        System.out.println("Map: (" + Thread.currentThread().getName() + ")");
        return updateUser(user);
    };
    private static final Action1<Notification<? super List<UserUpdateResult>>> progressNotification = item -> System.out.println("Progress event" + item.getKind());

}

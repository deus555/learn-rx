package com.deus.crmpoc;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.BlockingObservable;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

        Func1<Observable<? extends Throwable>, Observable<?>> retryLogic = attempts -> {
            int retryCount = 5;
            return attempts
                    .zipWith(Observable.range(1, retryCount), (n, i) -> i)
                    .flatMap(i -> {
                        System.out.println("delay retry by 1 second");
                        return Observable.timer(1, TimeUnit.SECONDS);
                    });
        };

        Func1<UserUpdateData, UserUpdateResult> mapLogic = user -> {
            System.out.println("Map: (" + Thread.currentThread().getName() + ")");
            return updateUser(user);
        };

        final AtomicInteger count = new AtomicInteger(0);

        Action1<UserUpdateResult> updateResultLogic = result -> {
            int c = count.getAndIncrement();

            if (c % 2 == 0) {
                System.out.println("Progress event");
            }

            System.out.println(result);
        };

        Observable
                .from(userUpdateData)
                .observeOn(Schedulers.computation())
                .map(mapLogic)
                .retryWhen(retryLogic)
                .doOnNext(result -> System.out.println("onNext update: " + result))
                .doOnCompleted(() -> System.out.println("Completed event"))
                .toBlocking()
                .subscribe(updateResultLogic);

    }

    private static UserUpdateResult updateUser(UserUpdateData data) {
        return new UserUpdateResult(new Random().nextInt(2) + 1 == 1);
    }
}

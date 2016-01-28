package com.deus.crmpoc;

import rx.Notification;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static java.util.Arrays.asList;

public class UserUpdateStrategy {

    private static final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(4);
    private static final Scheduler scheduler = Schedulers.from(threadPoolExecutor);

    private static final UserServiceMock userServiceMock = new UserServiceMock();

    public static void main(String[] args) throws InterruptedException {

        Observable
                .range(1, 20)
                .map(UserUpdateData::new)
                .subscribeOn(scheduler)
                .flatMap(UserUpdateStrategy::updateUser)
                //.retryWhen(retryLogic)
                .buffer(5)
                .doOnEach(item -> System.out.println("Progress event" + item.getKind()))
                .flatMap(Observable::from)
                .doOnCompleted(() -> System.out.println("Completed event"))
                .toList()
                .toBlocking()
                .subscribe(System.out::println);

        threadPoolExecutor.shutdown();
    }

    public static Observable<UserUpdateResult> updateUser(UserUpdateData data) {
        return Observable
                .just(data)
                .subscribeOn(scheduler)
                .map(userServiceMock::updateUser)
                .retryWhen(attempts -> {
                    int retryCount = 3;

                    return attempts
                            .zipWith(Observable.range(1, retryCount), (n, i) -> i)
                            .flatMap(i -> {
                                System.out.println("delay retry by 1 second ");

                                return Observable.timer(1, TimeUnit.SECONDS);
                            });
                });
    }
}

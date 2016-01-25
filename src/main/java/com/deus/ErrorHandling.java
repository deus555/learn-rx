package com.deus;

import rx.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ErrorHandling {
    public static void main(String[] args) {
        Observable
                .just("Apples", "Bananas")
                .doOnNext(s -> {
                    throw new RuntimeException("I don't like: " + s);
                })
                .onErrorReturn(throwable -> {
                    System.err.println("Oops: " + throwable.getMessage());
                    return "Default";
                })
                .subscribe(System.out::println);

        Observable
                .just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .doOnNext(integer -> {
                    if (new Random().nextInt(10) + 1 == 5) {
                        System.out.println("Oops...");
                        throw new RuntimeException("Boo!");
                    }
                })
                .retry() //(3)
                .distinct()
                .toList()
                .subscribe(System.out::println);

        Observable
                .range(1, 10)
                .doOnNext(integer -> {
                    if (new Random().nextInt(10) + 1 == 5) {
                        System.out.println("Oops...");
                        throw new RuntimeException("Boo!");
                    }
                })
                .retryWhen(attempts ->
                        attempts
                                .zipWith(Observable.range(1, 5), (n, i) -> i)
                                .flatMap(i -> {
                                    System.out.println("delay retry by " + i + " second(s)");
                                    return Observable.timer(i, TimeUnit.SECONDS);
                                }))
                .distinct()
                .toBlocking()
                .subscribe(System.out::println);

 /*       bucket
                .get("id")
                .timeout(500, TimeUnit.MILLISECONDS)
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends JsonDocument>>() {
                    @Override
                    public Observable<? extends JsonDocument> call(Throwable throwable) {
                        if (throwable instanceof TimeoutException) {
                            return bucket.getFromReplica("id", ReplicaMode.ALL);
                        }
                        return Observable.error(throwable);
                    }
                });*/
    }

}

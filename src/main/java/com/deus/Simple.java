package com.deus;

import rx.Observable;

public class Simple {
    public static void main(String[] args) {
        Observable
                .just("doc1", "doc2", "doc3")
                .flatMap(Observable::just)
                .subscribe(
                        document -> System.out.println("Got: " + document)
                );
    }

}

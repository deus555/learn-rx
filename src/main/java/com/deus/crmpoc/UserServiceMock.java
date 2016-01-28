package com.deus.crmpoc;

import java.util.Random;

public class UserServiceMock {
    public UserUpdateResult updateUser(UserUpdateData userData) {

        if (new Random().nextInt(5) + 1 == 5) {
            System.out.println("Oops..." + userData);
            throw new RuntimeException("Boo! " + userData);
        }

        System.out.println(Thread.currentThread().getName() + " updated user " + userData);
        return new UserUpdateResult(new Random().nextInt(2) + 1 == 1, userData);
    }
}

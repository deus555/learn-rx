package com.deus.crmpoc;

public class UserUpdateData {
    private int userId;

    public UserUpdateData(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserUpdateData{" +
                "userId=" + userId +
                '}';
    }
}

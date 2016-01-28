package com.deus.crmpoc;

public class UserUpdateResult {
    private boolean isSuccess;
    private UserUpdateData user;

    public UserUpdateResult(boolean isSuccess, UserUpdateData user) {
        this.user = user;
        this.isSuccess = isSuccess;

    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public String toString() {
        return "UserUpdateResult{" +
                "isSuccess=" + isSuccess +
                ", user=" + user +
                '}';
    }
}

package com.deus.crmpoc;

public class UserUpdateResult {
    private boolean isSuccess;

    public UserUpdateResult(boolean isSuccess) {
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
                '}';
    }
}

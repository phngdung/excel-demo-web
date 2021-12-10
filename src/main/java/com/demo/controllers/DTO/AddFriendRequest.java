package com.demo.controllers.DTO;

public class AddFriendRequest {
    long userId;
    long boyId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBoyId() {
        return boyId;
    }

    public void setBoyId(long boyId) {
        this.boyId = boyId;
    }
}

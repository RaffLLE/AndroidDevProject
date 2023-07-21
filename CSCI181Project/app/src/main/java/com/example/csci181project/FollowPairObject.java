package com.example.csci181project;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FollowPairObject extends RealmObject {

    @PrimaryKey
    private String followerUuid;

    private String followedUuid;


    public String getFollowerUuid() {return followerUuid; }
    public void setFollowerUuid(String uuid) {this.followerUuid = uuid; }

    public String getFollowedUuid() {return followedUuid; }
    public void setFollowedUuid(String uuid) {
        this.followedUuid = uuid;
    }


    @Override
    public String toString() {
        return "User{" +
                "followerUuid='" + followerUuid + '\'' +
                ", followedUuid='" + followedUuid + '\'' +
                '}';
    }
}

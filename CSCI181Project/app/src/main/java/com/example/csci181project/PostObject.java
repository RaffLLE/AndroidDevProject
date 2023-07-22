package com.example.csci181project;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PostObject extends RealmObject {

    @PrimaryKey
    private String postUuid;

    private String userUuid;

    private String datePosted;

    private String text;

    private Boolean isPrivate;

    public String getPostUuid() {return postUuid; }
    public void setPostUuid(String postUuid) {this.postUuid = postUuid; }

    public String getUserUuid() {return userUuid; }
    public void setUserUuid(String userUuid) {this.userUuid = userUuid; }

    public String getText() {return text; }
    public void setText(String text) {this.text = text; }

    public String getDatePosted() {return datePosted; }
    public void setDatePosted(String name) {
        this.datePosted = name;
    }

    public Boolean getIsPrivate() {return isPrivate; }
    public void setIsPrivate(Boolean isPrivate) {this.isPrivate = isPrivate; }

    @Override
    public String toString() {
        return "Post{" +
                "postUuid='" + postUuid + '\'' +
                ", userUuid='" + userUuid + '\'' +
                ", dateposted=" + datePosted +
                '}';
    }
}

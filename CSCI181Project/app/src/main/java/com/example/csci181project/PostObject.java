package com.example.csci181project;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserObject extends RealmObject {

    @PrimaryKey
    private String uuid;

    private String name;

    private String password;

    private String bio;

    public String getUuid() {return uuid; }
    public void setUuid(String uuid) {this.uuid = uuid; }

    public String getName() {return name; }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {return password; }
    public void setPassword(String password) {this.password = password; }

    public String getBio() {return password; }
    public void setBio(String bio) {this.bio = bio; }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", password=" + password + '\'' +
                ", bio=" + bio +
                '}';
    }
}

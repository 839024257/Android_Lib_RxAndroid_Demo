package com.hades.android_lib_rxandroid_demo;

/**
 * Created by hades on 7/26/2016.
 */
class User {
    String name;
    String time;

    public User() {
    }

    public User(String name, String time) {
        this.name = name;
        this.time = time;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

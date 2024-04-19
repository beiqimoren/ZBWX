package com.example.zbwx;

import android.app.Application;

public class MyApplication extends Application {
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    String username;

    public static final String my_url ="http://192.168.1.5:8000/";

    @Override
    public void onCreate() {
        super.onCreate();
        setUsername("用户名");
    }
}

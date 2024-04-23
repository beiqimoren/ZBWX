package com.example.zbwx;

import android.app.Application;

public class MyApplication extends Application {

    String username;
    int user_ID;

    public static final String my_url ="http://192.168.1.5:8000/";

    @Override
    public void onCreate() {
        super.onCreate();
        setUsername("用户名");
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getUserID() {
        return user_ID;
    }
    public void setUserID(int user_ID) {
        this.user_ID = user_ID;
    }
}

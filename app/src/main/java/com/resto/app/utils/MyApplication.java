package com.resto.app.utils;

import android.app.Application;

public class MyApplication extends Application {
    private static Integer UserId;
    private static String token;
    private static String user_type;

    public MyApplication() {
    }

    public Integer getUserId() {
        return UserId;
    }

    public static void setUserId(Integer userId) {
        UserId = userId;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        token = token;
    }

    public static String getUser_type() {
        return user_type;
    }

    public static void setUser_type(String user_type) {
        user_type = user_type;
    }
}

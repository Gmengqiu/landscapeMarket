package com.jalen.mapapp;

import android.app.Application;

import cn.bmob.v3.Bmob;


/**
 * 全局应用类
 */
public class MyApp extends Application {

    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Bmob.initialize(this, "f14842f01a02decbfc740144f0c469cd");
    }
}

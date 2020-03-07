package com.jalen.mapapp.util;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.jalen.mapapp.MyApp;


public final class CocoLocalBroadcastUtil {

    /**
     * 发送请求返回广播
     *
     * @param intent
     * @param code
     */
    public static void sendLocalBroadcast(Intent intent, String extra, int code) {
        intent.putExtra(extra, code);
        LocalBroadcastManager.getInstance(MyApp.getInstance()).sendBroadcast(intent);
    }

    /**
     * 发送请求返回广播
     *
     * @param intent
     */
    public static void sendLocalBroadcast(Intent intent) {
        LocalBroadcastManager.getInstance(MyApp.getInstance()).sendBroadcast(intent);
    }

    /**
     * 发送请求返回广播
     *
     * @param actionList
     * @param Action
     * @param code
     */
    public static void sendLocalBroadcast(String Action, int code, String... actionList) {
        Intent intent = new Intent();
        for (String s : actionList) {
            intent.setAction(s);
        }
        intent.putExtra(Action, code);
        LocalBroadcastManager.getInstance(MyApp.getInstance()).sendBroadcast(intent);
    }

    /**
     * 注册请求返回的广播
     *
     * @param mBroadcastReceiver
     * @param filterAction
     */
    public static void registerLocalBroadCast(BroadcastReceiver mBroadcastReceiver, String... filterAction) {
        IntentFilter filter = new IntentFilter();
        for (String action : filterAction) {
            filter.addAction(action);
        }
        LocalBroadcastManager.getInstance(MyApp.getInstance()).registerReceiver(mBroadcastReceiver, filter);
    }

    /**
     * 注册请求返回的广播
     *
     * @param mBroadcastReceiver
     * @param filter
     */
    public static void registerLocalBroadCast(BroadcastReceiver mBroadcastReceiver,
                                              IntentFilter filter) {
        LocalBroadcastManager.getInstance(MyApp.getInstance()).registerReceiver(
                mBroadcastReceiver, filter);
    }

    /**
     * 解注册广播
     *
     * @param mBroadcastReceiver
     */
    public static void unregisterLocalBroadCast(BroadcastReceiver mBroadcastReceiver) {
        LocalBroadcastManager.getInstance(MyApp.getInstance()).unregisterReceiver(mBroadcastReceiver);
    }

}

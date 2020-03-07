package com.jalen.mapapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.jalen.mapapp.MyApp;


/**
 * SharedPreferences 操作的工具类
 */
public class SharedPreferencesUtils {

    private static SharedPreferences sp;

    public static SharedPreferences getInstance() {
        if (sp == null) {
            sp = MyApp.getInstance().getSharedPreferences(
                    AppConstants.SP, Context.MODE_PRIVATE);
        }
        return sp;
    }

    public static String getString(String spName) {
        return getInstance().getString(spName, AppConstants.EMPTY);
    }

    public static void saveString(String spName, String value) {
        getInstance().edit().putString(spName, value).commit();
    }

    public static int getInt(String spName) {
        return getInstance().getInt(spName, 0);
    }

    public static int getInt(String spName, int defaultValue) {
        return getInstance().getInt(spName, defaultValue);
    }

    public static void saveInt(String spName, int value) {
        getInstance().edit().putInt(spName, value).commit();
    }

    public static boolean getBoolean(String spName) {
        return getInstance().getBoolean(spName, false);
    }

    public static void saveBoolean(String spName, boolean value) {
        getInstance().edit().putBoolean(spName, value).commit();
    }

    //旧代码需要,整合时可以参照修改为其他方法
    public static boolean getBooleanByDefault(String spName, boolean deafu) {
        return getInstance().getBoolean(spName, deafu);
    }

    /**
     * 获取长整型类型
     *
     * @param spName
     * @return
     */
    public static long getLong(String spName) {
        return getInstance().getLong(spName, -1l);
    }

    /**
     * 获取长整型类型
     *
     * @param spName
     * @param defaultValue 默认值
     * @return
     */
    public static long getLong(String spName, long defaultValue) {
        return getInstance().getLong(spName, defaultValue);
    }

    /**
     * 保存长整型类型
     *
     * @param spName
     * @param value
     */
    public static void saveLong(String spName, Long value) {
        getInstance().edit().putLong(spName, value).commit();
    }

}

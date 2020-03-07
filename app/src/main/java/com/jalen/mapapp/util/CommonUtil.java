package com.jalen.mapapp.util;

import com.jalen.mapapp.bean.UserBean;

/**
 * 通用工具类
 */
public class CommonUtil {

    /**
     * 登录时 保存数据处理
     *
     * @param userBean
     */
    public static void login(UserBean userBean) {
        SharedPreferencesUtils.saveString(AppConstants.USER_NAME, userBean.name);
        SharedPreferencesUtils.saveString(AppConstants.USER_PWD, userBean.pwd);
        SharedPreferencesUtils.saveString(AppConstants.USER_SEX, userBean.sex);
        SharedPreferencesUtils.saveString(AppConstants.USER_ADDRESS, userBean.address);
        SharedPreferencesUtils.saveString(AppConstants.USER_PHONE, userBean.phone);
        SharedPreferencesUtils.saveString(AppConstants.USER_HEAD, userBean.headId);
    }

    /**
     * 退出操作处理
     */
    public static void logout() {
        SharedPreferencesUtils.saveString(AppConstants.USER_NAME, "");
        SharedPreferencesUtils.saveString(AppConstants.USER_PWD, "");
        SharedPreferencesUtils.saveString(AppConstants.USER_SEX, "");
        SharedPreferencesUtils.saveString(AppConstants.USER_ADDRESS, "");
        SharedPreferencesUtils.saveString(AppConstants.USER_PHONE, "");
        SharedPreferencesUtils.saveString(AppConstants.USER_HEAD, "");
    }


    /**
     * 获取当前用户名
     *
     * @return
     */
    public static String getCurrentUser() {
        return SharedPreferencesUtils.getString(AppConstants.USER_NAME);
    }
}

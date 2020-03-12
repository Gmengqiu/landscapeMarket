package com.jalen.mapapp.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

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
        SharedPreferencesUtils.saveString(AppConstants.USER_ID, userBean.getObjectId());
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
        SharedPreferencesUtils.saveString(AppConstants.USER_ID, "");
    }


    /**
     * 获取当前用户名
     *
     * @return
     */
    public static String getCurrentUser() {
        return SharedPreferencesUtils.getString(AppConstants.USER_NAME);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}

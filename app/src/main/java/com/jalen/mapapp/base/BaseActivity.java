package com.jalen.mapapp.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.jalen.mapapp.util.CocoLocalBroadcastUtil;


/**
 * 所有Activity界面的基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    private InputMethodManager inputMethodManager;
    protected Context context;
    private IntentFilter filter;
    private final BroadcastReceiver m_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dealLocalBroadcast(context, intent);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        try {
            initView();
            initData();
            initEvent();
            filter = new IntentFilter();
            wrapLocalBroadcastFilter(filter);
            CocoLocalBroadcastUtil.registerLocalBroadCast(m_receiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 加载视图
     */
    public abstract void initView();

    /**
     * 加载数据
     */
    public abstract void initData();

    /**
     * 设置时间监听相关
     */
    public abstract void initEvent();


    public void showMsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showMsgs(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showMsg(msg);
            }
        });
    }

    public void log(String msg) {
        Log.d("TEST", msg);
    }


    /**
     * Add actions and categories here by subclass.
     *
     * @param filter IntentFilter
     */
    protected void wrapLocalBroadcastFilter(IntentFilter filter) {
    }

    /**
     * Deal local broadcast events by subclass.
     *
     * @param context Context
     * @param intent  Intent
     */
    protected void dealLocalBroadcast(Context context, Intent intent) {
    }

    protected void unregisterLocalBroadCast() {
        CocoLocalBroadcastUtil.unregisterLocalBroadCast(m_receiver);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterLocalBroadCast();
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput() {
        View view = getCurrentFocus();
        if (view == null) {
            view = getWindow().getDecorView();
        }
        getInputMethodManager().hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private InputMethodManager getInputMethodManager() {
        if (inputMethodManager == null) {
            inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        return inputMethodManager;
    }

}

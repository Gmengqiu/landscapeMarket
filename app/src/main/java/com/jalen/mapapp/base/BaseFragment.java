package com.jalen.mapapp.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jalen.mapapp.util.CocoLocalBroadcastUtil;

/**
 * 所有Fragment界面的基类
 */
public abstract class BaseFragment extends Fragment {

    public Activity mContext;


    private IntentFilter filter;
    private final BroadcastReceiver m_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dealLocalBroadcast(context, intent);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initBaseView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        filter = new IntentFilter();
        wrapLocalBroadcastFilter(filter);
        CocoLocalBroadcastUtil.registerLocalBroadCast(m_receiver, filter);
    }

    public abstract int getLayoutId();

    public abstract void initBaseView(View view);


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterLocalBroadCast();
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


    /**
     * 展示Toast
     */
    protected void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}

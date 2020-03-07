package com.jalen.mapapp.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jalen.mapapp.R;
import com.jalen.mapapp.base.BaseFragment;
import com.jalen.mapapp.ui.LoginActivity;
import com.jalen.mapapp.util.AppConstants;
import com.jalen.mapapp.util.CommonUtil;
import com.jalen.mapapp.util.SharedPreferencesUtils;

import java.util.Locale;

public class MyFragment extends BaseFragment {

    private TextView tvName, tvPhone, tvAddress, tvExit;
    private ImageView iv;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initBaseView(View view) {
        tvName = view.findViewById(R.id.tvName);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvExit = view.findViewById(R.id.tvExit);
        iv = view.findViewById(R.id.iv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String name = SharedPreferencesUtils.getString(AppConstants.USER_NAME);
        String address = SharedPreferencesUtils.getString(AppConstants.USER_ADDRESS);
        String phone = SharedPreferencesUtils.getString(AppConstants.USER_PHONE);
        String userHead = SharedPreferencesUtils.getString(AppConstants.USER_HEAD);
        if (!TextUtils.isEmpty(name)) {
            tvName.setText(name);
            tvAddress.setText(address);
            tvPhone.setText(phone);
            int mipmap = getResources().getIdentifier(userHead, "mipmap", getActivity().getPackageName());
            iv.setImageResource(mipmap);
        }
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit();
            }
        });
    }

    private void exit() {
        new AlertDialog.Builder(getActivity())
                .setMessage(String.format(Locale.getDefault(), "是否确认退出当前账号?", "提示"))
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CommonUtil.logout();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                })
                .setNegativeButton("取消", null)
                .setCancelable(false)
                .show();


    }
}
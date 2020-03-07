package com.jalen.mapapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jalen.mapapp.R;
import com.jalen.mapapp.base.BaseFragment;
import com.jalen.mapapp.ui.LoginActivity;
import com.jalen.mapapp.util.AppConstants;
import com.jalen.mapapp.util.CommonUtil;
import com.jalen.mapapp.util.SharedPreferencesUtils;

public class MyFragment extends BaseFragment {

    private TextView tvName, tvPhone, tvAddress, tvExit;


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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String name = SharedPreferencesUtils.getString(AppConstants.USER_NAME);
        String address = SharedPreferencesUtils.getString(AppConstants.USER_ADDRESS);
        String phone = SharedPreferencesUtils.getString(AppConstants.USER_PHONE);
        if (!TextUtils.isEmpty(name)) {
            tvName.setText(name);
            tvAddress.setText(address);
            tvPhone.setText(phone);
        }
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit();
            }
        });
    }

    private void exit() {
        CommonUtil.logout();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}
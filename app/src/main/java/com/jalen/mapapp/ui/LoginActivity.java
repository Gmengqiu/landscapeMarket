package com.jalen.mapapp.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jalen.mapapp.R;
import com.jalen.mapapp.base.BaseActivity;
import com.jalen.mapapp.bean.CommentBean;
import com.jalen.mapapp.bean.LandmarkBean;
import com.jalen.mapapp.bean.UserBean;
import com.jalen.mapapp.util.AppConstants;
import com.jalen.mapapp.util.CommonUtil;
import com.jalen.mapapp.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;


/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etName, etPwd;

    @Override
    public void initView() {
        setContentView(R.layout.act_login);
        etName = findViewById(R.id.et_name);
        etPwd = findViewById(R.id.et_pwd);
    }

    @Override
    public void initData() {
        String name = SharedPreferencesUtils.getString(AppConstants.USER_NAME);
        if (!TextUtils.isEmpty(name)) {
            String pwd = SharedPreferencesUtils.getString(AppConstants.USER_PWD);
            etName.setText(name);
            etPwd.setText(pwd);
            go2Main();
        }
    }

    @Override
    public void initEvent() {
        findViewById(R.id.tv_login).setOnClickListener(this);
        findViewById(R.id.tvRight).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_login) {
            login();
        } else if (v.getId() == R.id.tvRight) {//跳转到注册页面
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }

    private void login() {
        final String name = etName.getText().toString().trim();
        final String pwd = etPwd.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            showMsg("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            showMsg("密码不能为空");
            return;
        }

        if (name.length() < 3 || name.length() > 16) {
            showMsg("用户名必须是3到16位");
            return;
        }
        if (pwd.length() < 6 || pwd.length() > 16) {
            showMsg("密码必须是6到16位");
            return;
        }
        BmobQuery<UserBean> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<UserBean>() {
            @Override
            public void done(List<UserBean> categories, BmobException e) {
                if (e == null && categories.size() > 0) {
                    showMsg("登录成功");
                    UserBean userBean = categories.get(0);
                    //保存在SP文件中
                    CommonUtil.login(userBean);
                    go2Main();
                } else {
                    showMsg("请检查您的用户名和密码");
                }
            }
        });
    }

    private void go2Main() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void simulateUserData() {
        UserBean userBean = new UserBean();
        userBean.address = "北京市海淀区中关村至尊大厦顶楼008室";
        userBean.name = "测试账号";
        userBean.phone = "13888888888";
        userBean.sex = "男";
        userBean.pwd = "123456";
        userBean.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    showMsg("保存用户数据成功");
                } else {
                    showMsg("创建数据失败：" + e.getMessage());
                }
            }
        });
    }

    private void simulateBaseData() {
        String[] address_list = getResources().getStringArray(R.array.address_list);
        String[] desc_list = getResources().getStringArray(R.array.desc_list);
        String[] img_list = getResources().getStringArray(R.array.img_list);
        String[] latitude_list = getResources().getStringArray(R.array.latitude_list);
        String[] longitude_list = getResources().getStringArray(R.array.longitude_list);
        String[] name_list = getResources().getStringArray(R.array.name_list);
        String[] note_list = getResources().getStringArray(R.array.note_list);
        String[] tel_list = getResources().getStringArray(R.array.tel_list);

        List<LandmarkBean> landmarkBeanList = new ArrayList<LandmarkBean>();
        for (int i = 0; i < address_list.length; i++) {
            LandmarkBean landmarkBean = new LandmarkBean();
            landmarkBean.address = address_list[i];
            landmarkBean.describe = desc_list[i];
            landmarkBean.imgUrl = img_list[i];
            landmarkBean.latitude = latitude_list[i];
            landmarkBean.longitude = longitude_list[i];
            landmarkBean.title = name_list[i];
            landmarkBean.note = note_list[i];
            landmarkBean.tel = tel_list[i];
            landmarkBeanList.add(landmarkBean);

            landmarkBean.save(new SaveListener<String>() {
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        showMsg("保存风景数据成功");
                    } else {
                        showMsg("创建数据失败：" + e.getMessage());
                    }
                }
            });
        }

    }
}

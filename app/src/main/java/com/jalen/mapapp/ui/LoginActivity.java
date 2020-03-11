package com.jalen.mapapp.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jalen.mapapp.R;
import com.jalen.mapapp.base.BaseActivity;
import com.jalen.mapapp.bean.UserBean;
import com.jalen.mapapp.util.AppConstants;
import com.jalen.mapapp.util.CommonUtil;
import com.jalen.mapapp.util.SharedPreferencesUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


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
            startActivityForResult(intent, 106);
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
                    updateLoginUser(name, categories);
                } else {
                    showMsg("请检查您的用户名和密码");
                }
            }
        });
    }

    private void updateLoginUser(String name, List<UserBean> categories) {
        boolean hasUser = false;
        UserBean userBeanResult = null;
        for (int i = 0; i < categories.size(); i++) {
            UserBean userBean = categories.get(i);
            if (userBean.name.equals(name)) {
                hasUser = true;
                userBeanResult = userBean;
                break;
            }
        }
        if (hasUser) {
            showMsg("登录成功");
            //保存在SP文件中
            CommonUtil.login(userBeanResult);
            go2Main();
        } else {
            showMsg("暂无该用户记录");
        }

    }


    private void go2Main() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 106) {
            if (null != data) {
                final UserBean userBean = (UserBean) data.getSerializableExtra("user");
                etName.setText(userBean.name);
                etPwd.setText(userBean.pwd);
                //延迟1分钟自动登录
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CommonUtil.login(userBean);
                        go2Main();
                    }
                }, 1000);

            }
        }
    }
}

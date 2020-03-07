package com.jalen.mapapp.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.jalen.mapapp.R;
import com.jalen.mapapp.base.BaseActivity;
import com.jalen.mapapp.bean.UserBean;

import java.util.Random;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * 注册界面
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    //界面输入控件
    private EditText etName, etPwd, etAddress, etPhone;
    //性别选择控件
    private RadioGroup rgSex;
    private String sex = "男";

    @Override
    public void initView() {
        setContentView(R.layout.act_register);
        etName = (EditText) findViewById(R.id.et_name);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        rgSex = (RadioGroup) findViewById(R.id.rgSex);
        etAddress = (EditText) findViewById(R.id.et_address);
        etPhone = (EditText) findViewById(R.id.et_phone);
    }

    @Override
    public void initData() {

    }

    /**
     * 设置监听事件
     */
    @Override
    public void initEvent() {
        findViewById(R.id.tvRegister).setOnClickListener(this);
        findViewById(R.id.ivBack).setOnClickListener(this);
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.rbBoy) {
                    sex = "男";
                } else {
                    sex = "女";
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRegister:
                register();
                break;
            case R.id.ivBack:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 注册用户
     */
    private void register() {
        String name = etName.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        String[] headList = getResources().getStringArray(R.array.head_list);
        String headId = headList[new Random().nextInt(headList.length)];

        if (TextUtils.isEmpty(name)) {
            showMsg("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            showMsg("密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            showMsg("地址不能为空");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showMsg("手机号不能为空");
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
        if (address.length() < 10) {
            showMsg("地址不能少于10个字");
            return;
        }

        if (phone.length() != 11) {
            showMsg("手机号必须是11位");
            return;
        }
        UserBean userBean = new UserBean();
        userBean.name = name;
        userBean.address = address;
        userBean.pwd = pwd;
        userBean.phone = phone;
        userBean.sex = sex;
        userBean.headId = headId;
        final Intent intent = new Intent();
        intent.putExtra("user", userBean);
        userBean.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    showMsg("注册成功");
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    showMsg("创建数据失败：" + e.getMessage());
                }
            }
        });
    }
}

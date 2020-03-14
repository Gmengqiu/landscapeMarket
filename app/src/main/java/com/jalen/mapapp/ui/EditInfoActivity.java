package com.jalen.mapapp.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jalen.mapapp.R;
import com.jalen.mapapp.base.BaseActivity;
import com.jalen.mapapp.bean.UserBean;
import com.jalen.mapapp.util.AppConstants;
import com.jalen.mapapp.util.CommonUtil;
import com.jalen.mapapp.util.SharedPreferencesUtils;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * 更新用户资料界面
 */
public class EditInfoActivity extends BaseActivity implements View.OnClickListener {

    //界面输入控件
    private EditText etPwd, etAddress, etPhone;
    private TextView tvName;
    //性别选择控件
    private RadioGroup rgSex;
    private RadioButton rbBoy, rbGirl;
    private String sex = "男";

    //用户数据
    private UserBean userBean = null;


    @Override
    public void initView() {
        setContentView(R.layout.act_edit_info);
        tvName = (TextView) findViewById(R.id.tv_name);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etAddress = (EditText) findViewById(R.id.et_address);
        etPhone = (EditText) findViewById(R.id.et_phone);
        rgSex = (RadioGroup) findViewById(R.id.rgSex);
        rbBoy = (RadioButton) findViewById(R.id.rbBoy);
        rbGirl = (RadioButton) findViewById(R.id.rbGirl);
    }

    /**
     * 设置基本数据
     */
    private void loadBaseData() {
        tvName.setText(userBean.name);
        etAddress.setText(userBean.address);
        etPwd.setText(userBean.pwd);
        etPhone.setText(userBean.phone);
        if (userBean.sex.equals("男")) {
            rbBoy.setChecked(true);
        } else {
            rbGirl.setChecked(true);
        }
    }

    @Override
    public void initData() {
        //从bmob后台查询用户数据
        String objectId = SharedPreferencesUtils.getString(AppConstants.USER_ID);
        //ObjectId在不为空的情况下开始进行查询数据
        BmobQuery<UserBean> bmobQuery = new BmobQuery<UserBean>();
        bmobQuery.getObject(objectId, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean userBeanData, BmobException e) {
                if (e == null) {
                    userBean = userBeanData;
                    loadBaseData();
                } else {
                    showMsg("查询失败：" + e.getMessage());
                }
            }
        });
    }


    /**
     * 设置监听事件
     */
    @Override
    public void initEvent() {
        findViewById(R.id.tvSave).setOnClickListener(this);
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
            case R.id.tvSave:
                hideSoftInput();
                updateInfo();
                break;
            case R.id.ivBack:
                hideSoftInput();
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 更新用户资料
     */
    private void updateInfo() {
        String pwd = etPwd.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

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

        userBean.address = address;
        userBean.pwd = pwd;
        userBean.phone = phone;
        userBean.sex = sex;
        userBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    showMsg("修改成功");
                    CommonUtil.login(userBean);
                    setResult(RESULT_OK);
                    finish();
                } else {
                    showMsg(e.getMessage());
                }
            }
        });
    }


}

package com.jalen.mapapp.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.jalen.mapapp.R;
import com.jalen.mapapp.base.BaseActivity;
import com.jalen.mapapp.ui.fragment.HomeFragment;
import com.jalen.mapapp.ui.fragment.MyFragment;


public class MainActivity extends BaseActivity {


    BottomNavigationView navView;

    public Fragment curFragment = null;
    private FragmentManager fragmentManager = null;
    private HomeFragment homeFragment = null;
    private MyFragment myFragment = null;


    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initData() {
        navView = findViewById(R.id.navView);
        fragmentManager = getSupportFragmentManager();
        gotoHomeFragment();
    }

    @Override
    public void initEvent() {
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.navigation_home) {
                    gotoHomeFragment();
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_notifications) {
                    gotoMyFragment();
                    return true;
                }
                return false;
            }
        });
    }

    private void gotoHomeFragment() {
        String tag = HomeFragment.class.getSimpleName();
        if (null == homeFragment) {
            homeFragment = new HomeFragment();
        }
        switchContent(R.id.container, curFragment, homeFragment, tag);
    }

    private void gotoMyFragment() {
        String tag = MyFragment.class.getSimpleName();
        if (null == myFragment) {
            myFragment = new MyFragment();
        }
        switchContent(R.id.container, curFragment, myFragment, tag);
    }

    public void switchContent(int id, Fragment from, Fragment to, String tag) {
        if (from != to && null != fragmentManager) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                if (null == from) {
                    transaction.add(id, to, tag).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(from).add(id, to, tag).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                }
            } else {
                if (null == from) {
                    transaction.show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                } else {
                    transaction.hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                }
            }
            curFragment = to;
        }
    }
}

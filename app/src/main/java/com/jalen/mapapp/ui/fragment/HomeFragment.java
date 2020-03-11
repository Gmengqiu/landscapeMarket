package com.jalen.mapapp.ui.fragment;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jalen.mapapp.R;
import com.jalen.mapapp.adapter.MainDataAdapter;
import com.jalen.mapapp.base.BaseFragment;
import com.jalen.mapapp.bean.LandmarkBean;
import com.jalen.mapapp.ui.LandmarkDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener {


    private ListView listView;
    private MainDataAdapter adapter;
    private EditText etQuery;
    private List<LandmarkBean> allData = null;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initBaseView(View view) {
        listView = view.findViewById(R.id.lv_show);
        etQuery = view.findViewById(R.id.et_query);
        listView.setOnItemClickListener(this);
        getAllData();
        //新增文本变化监听处理
        etQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                queryData();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    /**
     * 获取所有的数据
     */
    private void getAllData() {
        //启用模糊查询
        BmobQuery<LandmarkBean> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<LandmarkBean>() {
            @Override
            public void done(List<LandmarkBean> landmarkBeans, BmobException e) {
                if (e == null && landmarkBeans.size() > 0) {
                    allData = landmarkBeans;
                    updateAdapter(landmarkBeans);
                } else {
                    Toast.makeText(getContext(), "查询出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 更新适配器显示的数据
     *
     * @param landmarkBeans
     */
    private void updateAdapter(List<LandmarkBean> landmarkBeans) {
        if (null != landmarkBeans && landmarkBeans.size() > 0) {
            adapter = new MainDataAdapter(getContext(), landmarkBeans);//创建适配器
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.GONE);
        }
    }


    //查询数据
    private void queryData() {
        final String keyword = etQuery.getText().toString().trim();
        List<LandmarkBean> landmarkBeans = new ArrayList<>();
        //将landmarkBeans进行过滤处理
        for (int i = 0; i < allData.size(); i++) {
            LandmarkBean landmarkBean = allData.get(i);
            boolean aBoolean = landmarkBean.title.contains(keyword) || landmarkBean.note.contains(keyword);//判断这个bean中有没有包含该关键字
            if (aBoolean) {
                landmarkBeans.add(landmarkBean);
            }
        }
        updateAdapter(landmarkBeans);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //进入详情界面
        Intent intent = new Intent(getActivity(), LandmarkDetailsActivity.class);
        String ObjectId = adapter.getItem(position).getObjectId();//将ObjectId传递过去，详情页面查询ObjectId获取数据
        intent.putExtra("ObjectId", ObjectId);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}
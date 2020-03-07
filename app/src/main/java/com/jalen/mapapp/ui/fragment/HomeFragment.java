package com.jalen.mapapp.ui.fragment;

import android.content.Intent;
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initBaseView(View view) {
        listView = view.findViewById(R.id.lv_show);
        etQuery = view.findViewById(R.id.et_query);
        listView.setOnItemClickListener(this);

        BmobQuery<LandmarkBean> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<LandmarkBean>() {
            @Override
            public void done(List<LandmarkBean> landmarkBeans, BmobException e) {
                if (e == null && landmarkBeans.size() > 0) {
                    //获取数据之后绑定数据
                    adapter = new MainDataAdapter(getContext(), landmarkBeans);//创建适配器
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "查询出错", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //顶部查询处理
        etQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //如果actionId是搜索的id，则进行下一步的操作
                    queryData();

                }
                return false;
            }
        });
    }


    //查询数据
    private void queryData() {
        final String keyword = etQuery.getText().toString().trim();
        final List<LandmarkBean> queryData = new ArrayList<>();
        //启用模糊查询
        BmobQuery<LandmarkBean> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<LandmarkBean>() {
            @Override
            public void done(List<LandmarkBean> landmarkBeans, BmobException e) {
                if (e == null && landmarkBeans.size() > 0) {
                    //将landmarkBeans进行过滤处理
                    for (int i = 0; i < landmarkBeans.size(); i++) {
                        boolean aBoolean = landmarkBeans.get(i).title.contains(keyword) || landmarkBeans.get(i).note.contains(keyword);//判断这个bean中有没有包含该关键字
                        if (aBoolean) {
                            queryData.add(landmarkBeans.get(i));
                        }
                    }
                    if (queryData.size() > 0) {
                        //获取数据之后绑定数据
                        Toast.makeText(getContext(), "查询成功", Toast.LENGTH_SHORT).show();
                        adapter = new MainDataAdapter(getContext(), queryData);//创建适配器
                        listView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getContext(), "没有查询到您要的数据", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "查询出错", Toast.LENGTH_SHORT).show();
                }
            }
        });


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
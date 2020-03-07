package com.jalen.mapapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jalen.mapapp.R;
import com.jalen.mapapp.adapter.CommentAdapter;
import com.jalen.mapapp.base.BaseActivity;
import com.jalen.mapapp.bean.CommentBean;
import com.jalen.mapapp.bean.LandmarkBean;
import com.jalen.mapapp.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 风景或者餐厅列表
 */
public class LandmarkDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack, ivDetails;
    private TextView tvTitle, tvIntroduce, tvAddComment;
    private TextView tvAddress, tvTel;
    private ListView lvComment;
    private EditText etYouComment;
    private CommentAdapter commentAdapter;
    private int position;
    private LandmarkBean landmarkBeanDetails;


    @Override
    public void initView() {
        setContentView(R.layout.act_landmark_details);
        ivBack = findViewById(R.id.ivBack);
        ivDetails = findViewById(R.id.iv_details);
        tvTitle = findViewById(R.id.tv_title);
        tvIntroduce = findViewById(R.id.tv_introduce);
        tvAddress = findViewById(R.id.tv_address);
        tvTel = findViewById(R.id.tv_tel);
        tvAddComment = findViewById(R.id.tv_add_comment);
        lvComment = findViewById(R.id.lv_comment);
        etYouComment = findViewById(R.id.et_you_comment);
    }

    @Override
    public void initData() {
        //查询数据
        //上个页面带入数据过来
        Intent intent = getIntent();
        String objectId = intent.getStringExtra("ObjectId");
        position = intent.getIntExtra("position", 0);
        if (TextUtils.isEmpty(objectId)) {
            showMsg("传递过来的ObjectId为空");
            return;
        }
        //ObjectId在不为空的情况下开始进行查询数据
        BmobQuery<LandmarkBean> bmobQuery = new BmobQuery<LandmarkBean>();
        bmobQuery.getObject(objectId, new QueryListener<LandmarkBean>() {
            @Override
            public void done(LandmarkBean landmarkBean, BmobException e) {
                if (e == null) {
                    landmarkBeanDetails = landmarkBean;
                    showMsg("查询成功");
                    //绑定数据显示页面
                    showPage(landmarkBean);
                } else {
                    showMsg("查询失败：" + e.getMessage());
                }
            }
        });


    }

    /**
     * 绑定数据
     *
     * @param landmarkBean
     */
    private void showPage(LandmarkBean landmarkBean) {
        tvTitle.setText(landmarkBean.title);
        Glide.with(context).load(landmarkBean.imgUrl).into(ivDetails);
        tvIntroduce.setText(landmarkBean.describe);
        tvAddress.setText("地址:" + landmarkBean.address);
        tvTel.setText("电话:" + landmarkBean.tel);
        if (null != landmarkBean.commentBeanList && landmarkBean.commentBeanList.size() > 0) {
            commentAdapter = new CommentAdapter(this, landmarkBean.commentBeanList);
            lvComment.setAdapter(commentAdapter);
            setListViewHeightBasedOnChildren(lvComment);
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


    @Override
    public void initEvent() {
        ivBack.setOnClickListener(this);
        tvAddress.setOnClickListener(this);//跳转百度翻地图
        tvAddComment.setOnClickListener(this);//添加评论
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tv_address://跳转到地图
                gotoMap();
                break;
            case R.id.tv_add_comment://添加评论
                addComment();
                break;
            default:
                break;
        }
    }


    private void gotoMap() {
        //先检查有没有百度地图
        boolean aBoolean = isAvilible(this, "com.baidu.BaiduMap");
        if (!aBoolean) {
            showMsg("没有安装百度app");
            return;
        }
        Intent i1 = new Intent();
        // 展示地图
        i1.setData(Uri.parse("baidumap://map/direction?destination=" + landmarkBeanDetails.address + "&mode=driving"));
        startActivity(i1);
    }


    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    private boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }


    /**
     * 添加评论
     */
    private void addComment() {
        String commentDetail = etYouComment.getText().toString().trim();
        //获取当前用户
        String commentator = CommonUtil.getCurrentUser();
        CommentBean commentBean = new CommentBean();
        commentBean.commentator = commentator;
        commentBean.CommentDetail = commentDetail;
        List<CommentBean> commentBeanList = new ArrayList<>();
        commentBeanList.add(commentBean);

        List<CommentBean> commentBeanList1 = landmarkBeanDetails.commentBeanList;
        if (commentBeanList1 == null) {
            commentBeanList1 = new ArrayList<>();
            landmarkBeanDetails.commentBeanList = commentBeanList1;
        }
        commentBeanList1.addAll(commentBeanList);
        //更新数据
        commentBean.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    landmarkBeanDetails.update(landmarkBeanDetails.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                showMsg("添加成功");
                                etYouComment.setText("");
                                //刷新当前该页面
                                refreshPage();
                            } else {
                                showMsg("添加失败");
                            }
                        }
                    });
                } else {
                    showMsg("添加失败");
                }
            }
        });
    }

    /**
     * 刷新当前该页面
     */
    private void refreshPage() {
        //ObjectId在不为空的情况下开始进行查询数据
        BmobQuery<LandmarkBean> bmobQuery = new BmobQuery<LandmarkBean>();
        bmobQuery.getObject(landmarkBeanDetails.getObjectId(), new QueryListener<LandmarkBean>() {
            @Override
            public void done(LandmarkBean landmarkBean, BmobException e) {
                if (e == null) {
                    landmarkBeanDetails = landmarkBean;
                    //绑定数据显示页面
                    showPage(landmarkBean);
                } else {
                    showMsg("暂无数据：" + e.getMessage());
                }
            }
        });
    }
}

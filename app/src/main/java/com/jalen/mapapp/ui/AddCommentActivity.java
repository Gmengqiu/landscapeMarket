package com.jalen.mapapp.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jalen.mapapp.R;
import com.jalen.mapapp.base.BaseActivity;
import com.jalen.mapapp.bean.CommentBean;
import com.jalen.mapapp.bean.LandmarkBean;
import com.jalen.mapapp.util.AppConstants;
import com.jalen.mapapp.util.CommonUtil;
import com.jalen.mapapp.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 新增评论
 */
public class AddCommentActivity extends BaseActivity {

    private ImageView ivBack;
    private EditText etYouComment;
    private TextView tvAddComment;

    private LandmarkBean landmarkBeanDetails;


    public static void go2AddCommentActivity(BaseActivity context, LandmarkBean landmarkBeanDetails) {
        Intent intent = new Intent(context, AddCommentActivity.class);
        intent.putExtra("landmarkBeanDetails", landmarkBeanDetails);
        context.startActivityForResult(intent, 106);
    }


    @Override
    public void initView() {
        setContentView(R.layout.act_add_comment);
        ivBack = findViewById(R.id.ivBack);
        etYouComment = findViewById(R.id.et_you_comment);
        tvAddComment = findViewById(R.id.tv_add_comment);
    }

    @Override
    public void initData() {
        landmarkBeanDetails = (LandmarkBean) getIntent().getSerializableExtra("landmarkBeanDetails");
    }

    @Override
    public void initEvent() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment();
            }
        });
    }

    /**
     * 添加评论
     */
    private void addComment() {
        String userHead = SharedPreferencesUtils.getString(AppConstants.USER_HEAD);
        String commentDetail = etYouComment.getText().toString().trim();
        if (TextUtils.isEmpty(commentDetail)) {
            showMsg("评论内容不能为空");
        } else {
//获取当前用户
            String commentator = CommonUtil.getCurrentUser();
            CommentBean commentBean = new CommentBean();
            commentBean.commentator = commentator;
            commentBean.CommentDetail = commentDetail;
            commentBean.headId = userHead;
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
                                    hideSoftInput();
                                    //刷新当前该页面
                                    setResult(RESULT_OK);
                                    finish();
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
    }
}

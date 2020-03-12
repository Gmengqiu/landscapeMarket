package com.jalen.mapapp.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jalen.mapapp.R;
import com.jalen.mapapp.adapter.ReplyAdapter;
import com.jalen.mapapp.base.BaseActivity;
import com.jalen.mapapp.bean.CommentBean;
import com.jalen.mapapp.bean.ReplyBean;
import com.jalen.mapapp.util.AppConstants;
import com.jalen.mapapp.util.CommonUtil;
import com.jalen.mapapp.util.SharedPreferencesUtils;
import com.jalen.mapapp.weight.MyListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 评论详情界面
 */
public class CommentDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack;
    private TextView tvContent, tvAddComment, tvTime, tvName;
    private ImageView iv;
    private MyListView lvComment;
    private EditText etYouComment;
    private ReplyAdapter replyAdapter;
    private CommentBean commentBean;


    @Override
    public void initView() {
        setContentView(R.layout.act_comment_details);
        tvTime = findViewById(R.id.tv_time);
        tvName = findViewById(R.id.tvName);
        iv = findViewById(R.id.iv);
        ivBack = findViewById(R.id.ivBack);
        tvContent = findViewById(R.id.tvContent);
        tvAddComment = findViewById(R.id.tv_add_comment);
        lvComment = findViewById(R.id.lv_comment);
        etYouComment = findViewById(R.id.et_you_comment);
    }

    @Override
    public void initData() {
        commentBean = (CommentBean) getIntent().getSerializableExtra("commentBean");
        getCommentDetail();

    }

    private void getCommentDetail() {
        BmobQuery<CommentBean> bmobQuery = new BmobQuery<CommentBean>();
        bmobQuery.getObject(commentBean.getObjectId(), new QueryListener<CommentBean>() {
            @Override
            public void done(CommentBean landmarkBean, BmobException e) {
                if (e == null) {
                    commentBean = landmarkBean;
                    refreshPage();
                } else {
                    showMsg("查询失败：" + e.getMessage());
                }
            }
        });
    }


    @Override
    public void initEvent() {
        ivBack.setOnClickListener(this);
        tvAddComment.setOnClickListener(this);//添加评论
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tv_add_comment://添加评论
                addComment();
                break;
            default:
                break;
        }
    }


    /**
     * 添加评论
     */
    private void addComment() {
        String userHead = SharedPreferencesUtils.getString(AppConstants.USER_HEAD);
        String commentDetail = etYouComment.getText().toString().trim();
        if (TextUtils.isEmpty(commentDetail)) {
            showMsg("回帖不能为空");
        } else {
            //获取当前用户
            String commentator = CommonUtil.getCurrentUser();
            ReplyBean replyBean = new ReplyBean();
            replyBean.commentator = commentator;
            replyBean.replyDetail = commentDetail;
            replyBean.headId = userHead;
            replyBean.commentId = commentBean.getObjectId();
            //更新数据
            replyBean.save(new SaveListener<String>() {
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        updateCurrentPage();
                    } else {
                        showMsg("添加失败");
                    }
                }
            });
        }
    }


    private void updateCurrentPage() {
        //ObjectId在不为空的情况下开始进行查询数据
        BmobQuery<ReplyBean> bmobQuery = new BmobQuery<ReplyBean>();
        bmobQuery.findObjects(new FindListener<ReplyBean>() {
            @Override
            public void done(List<ReplyBean> list, BmobException e) {
                if (e == null && list.size() > 0) {
                    List<ReplyBean> result = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        ReplyBean replyBean = list.get(i);
                        if (replyBean.commentId.equals(commentBean.getObjectId())) {
                            result.add(replyBean);
                        }
                    }
                    commentBean.replyBeanList = result;
                    refreshPage();
                    commentBean.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                showMsg("新增成功");
                                etYouComment.setText("");
                                hideSoftInput();
                                refreshPage();
                            } else {
                                showMsg("更新失败");
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 刷新当前该页面
     */
    private void refreshPage() {
        tvContent.setText(commentBean.CommentDetail);
        tvName.setText(commentBean.commentator);
        tvTime.setText(commentBean.getCreatedAt());
        int mipmap = context.getResources().getIdentifier(commentBean.headId, "mipmap", context.getPackageName());
        iv.setImageResource(mipmap);
        List<ReplyBean> replyBeanList = commentBean.replyBeanList;
        if (null != replyBeanList && replyBeanList.size() > 0) {
            replyAdapter = new ReplyAdapter(this, replyBeanList);
            lvComment.setAdapter(replyAdapter);
            CommonUtil.setListViewHeightBasedOnChildren(lvComment);
        }
    }

}

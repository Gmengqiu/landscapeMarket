package com.jalen.mapapp.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 评论数据
 */
public class CommentBean extends BmobObject {


    /**
     * 内容
     */
    public String CommentDetail;
    /**
     * 评论人
     */
    public String commentator;//评论人
    /**
     * 头像
     */
    public String headId;


    /**
     * 回复列表
     */
    public List<ReplyBean> replyBeanList;

}

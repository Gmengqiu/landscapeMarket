package com.jalen.mapapp.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 评论数据
 */
public class ReplyBean extends BmobObject implements Serializable {
    /**
     * 内容
     */
    public String replyDetail;

    /**
     * 主评论id
     */
    public String commentId;

    /**
     * 回复人
     */
    public String commentator;
    /**
     * 头像
     */
    public String headId;


}

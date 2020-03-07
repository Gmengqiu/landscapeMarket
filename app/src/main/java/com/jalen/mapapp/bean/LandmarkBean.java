package com.jalen.mapapp.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 风景或者餐厅
 */
public class LandmarkBean extends BmobObject {

    /**
     * 类型  餐厅 或 风景
     */
    public String note;
    /**
     * 标题
     */
    public String title;
    /**
     * 电话
     */
    public String tel;
    /**
     * 详细地址
     */
    public String address;
    /**
     * 描述 简介
     */
    public String describe;
    /**
     * 精度
     */
    public String latitude;
    /**
     * 纬度
     */
    public String longitude;

    /**
     * 图片地址
     */
    public String imgUrl;
    /**
     * 评论列表
     */
    public List<CommentBean> commentBeanList;

}

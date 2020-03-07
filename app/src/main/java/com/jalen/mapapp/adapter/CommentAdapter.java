package com.jalen.mapapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jalen.mapapp.R;
import com.jalen.mapapp.bean.CommentBean;


import java.util.ArrayList;
import java.util.List;

/**
 * 评论适配器
 */
public class CommentAdapter extends BaseAdapter {

    Context context;
    List<CommentBean> list = new ArrayList<>();

    public CommentAdapter(Context context, List<CommentBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CommentBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.item_comment, null);
            viewHolder = new ViewHolder();
            viewHolder.time = convertView.findViewById(R.id.tv_time);
            viewHolder.comment = convertView.findViewById(R.id.tv_comment);
            viewHolder.tvName = convertView.findViewById(R.id.tvName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CommentBean commentBean = list.get(position);
        if (null != commentBean) {
            viewHolder.time.setText(commentBean.getCreatedAt());
            viewHolder.comment.setText(commentBean.CommentDetail);
            viewHolder.tvName.setText(commentBean.commentator);
        }
        return convertView;
    }

    static class ViewHolder {
        public TextView time;
        public TextView comment;
        public TextView tvName;
    }
}

package com.jalen.mapapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jalen.mapapp.R;
import com.jalen.mapapp.bean.CommentBean;
import com.jalen.mapapp.bean.ReplyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 回复 适配器
 */
public class ReplyAdapter extends BaseAdapter {

    Context context;
    List<ReplyBean> list = new ArrayList<>();

    public ReplyAdapter(Context context, List<ReplyBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ReplyBean getItem(int position) {
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
            viewHolder.iv = convertView.findViewById(R.id.iv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ReplyBean commentBean = list.get(position);
        if (null != commentBean) {
            try {
                int mipmap = context.getResources().getIdentifier(commentBean.headId, "mipmap", context.getPackageName());
                viewHolder.iv.setImageResource(mipmap);
                viewHolder.time.setText(commentBean.getCreatedAt());
                viewHolder.tvName.setText(commentBean.commentator);
                viewHolder.comment.setText(commentBean.replyDetail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

    static class ViewHolder {
        public TextView time;
        public TextView comment;
        public TextView tvName;
        public ImageView iv;
    }
}

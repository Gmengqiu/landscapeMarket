package com.jalen.mapapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jalen.mapapp.R;
import com.jalen.mapapp.bean.LandmarkBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 风景或者餐厅适配器
 */
public class MainDataAdapter extends BaseAdapter {

    Context context;
    List<LandmarkBean> list = new ArrayList<>();

    public MainDataAdapter(Context context, List<LandmarkBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public LandmarkBean getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.item_home_show, null);
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.tv_title);
            viewHolder.imageView = convertView.findViewById(R.id.iv);
            viewHolder.tvTel = convertView.findViewById(R.id.tvTel);
            viewHolder.address = convertView.findViewById(R.id.tv_address);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LandmarkBean landmarkBean = list.get(position);
        viewHolder.title.setText(landmarkBean.title);
        viewHolder.tvTel.setText(landmarkBean.tel);
        viewHolder.address.setText(landmarkBean.address);
        Glide.with(context).load(landmarkBean.imgUrl).into(viewHolder.imageView);
        return convertView;
    }


    static class ViewHolder {
        public TextView title;
        public ImageView imageView;
        public TextView tvTel;
        public TextView address;
    }
}

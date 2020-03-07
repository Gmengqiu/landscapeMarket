package com.jalen.mapapp.weight;

import android.content.Context;
import android.widget.ListView;

/**
 * @Date 2020/3/5 下午9:24
 * @Author mengqiu.gao
 * @Email:1835133814@qq.com
 * @Description
 */
public class MyListView extends ListView {
    public MyListView(android.content.Context context,android.util.AttributeSet attrs){
        super(context, attrs);
    }
    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}

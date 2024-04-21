package com.example.zbwx.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zbwx.R;

import java.util.List;

public class ZClistViewAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;//得到一个LayoutInfalter对象用来导入布局
    List<ZClistviewitem> list;//得到一个List<App>集合用来导入数据

    //构造函数
    public ZClistViewAdapter(Context context, List<ZClistviewitem> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            //绑定Item布局
            convertView = layoutInflater.inflate(R.layout.lv_zc_item, null, false);
            //自定义内部类，对象holder用来存储文字和图片控件
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.item_title);
            holder.text = (TextView) convertView.findViewById(R.id.item_text);
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_image);
            //将holder放入当前视图中
            convertView.setTag(holder);
        } else {
            //复用holder
            holder = (ViewHolder) convertView.getTag();
        }
        //取出app对象
        ZClistviewitem zClistviewitem = list.get(position);
        holder.title.setText(zClistviewitem.getTitle());
        holder.text.setText(zClistviewitem.getText());
        holder.imageView.setBackgroundResource(zClistviewitem.getId());
        Log.v("hc", "getView结束：position=" + position +
                ",name=" + zClistviewitem.getTitle() +
                ",convertView=" + convertView
        );//可以通过hc标签查看logcat
        return convertView;
    }

    //内部类
    static class ViewHolder {
        TextView title,text;
        ImageView imageView;
    }

}

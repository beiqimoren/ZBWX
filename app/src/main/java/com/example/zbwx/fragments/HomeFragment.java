package com.example.zbwx.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zbwx.IntroduceActivity;
import com.example.zbwx.LoginActivity;
import com.example.zbwx.MainActivity;
import com.example.zbwx.R;
import com.example.zbwx.model.ZClistViewAdapter;
import com.example.zbwx.model.ZClistviewitem;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    //定义控件
     ListView lv_zc;
     ImageView msg,yuan1,yuan2,yuan3,yuan4;
     LinearLayout fang1,fang2;
     TextView zc_more;
     List<ZClistviewitem> listdata_zc;


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //指定布局
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        //获得控件
        this.msg = view.findViewById(R.id.msg);
        this.yuan1 = view.findViewById(R.id.yuan1);
        this.yuan2 = view.findViewById(R.id.yuan2);
        this.yuan3 = view.findViewById(R.id.yuan3);
        this.yuan4 = view.findViewById(R.id.yuan4);
        this.zc_more=view.findViewById(R.id.zc_more);
        this.lv_zc = view.findViewById(R.id.lv_zc);
        this.fang1 = view.findViewById(R.id.fang1);
        this.fang2 = view.findViewById(R.id.fang2);
        //填充listview 数据
        ZClistviewitem zClistviewitem1=new ZClistviewitem("装备维修管理规定","2023年3月1日版",R.drawable.zcitem1);
        ZClistviewitem zClistviewitem2=new ZClistviewitem("装备管理条例","2023年3月1日版",R.drawable.zcitem1);
        ZClistviewitem zClistviewitem3=new ZClistviewitem("共用信息系统装备维修保障指南","2023年3月1日版",R.drawable.zcitem1);
        ZClistviewitem zClistviewitem4=new ZClistviewitem("一体化维修保障规范","2023年3月1日版",R.drawable.zcitem1);

        listdata_zc=new ArrayList<>();
        listdata_zc.add(zClistviewitem1);
        listdata_zc.add(zClistviewitem2);
        listdata_zc.add(zClistviewitem3);
        listdata_zc.add(zClistviewitem4);
        //加载数据到listview适配器
        ZClistViewAdapter zClistViewAdapter = new ZClistViewAdapter(this.getContext(),listdata_zc);
        this.lv_zc.setAdapter(zClistViewAdapter);
        //第1个圆形按钮的监听事件
        yuan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), IntroduceActivity.class);
                startActivity(intent);
                //Toast.makeText(view.getContext(), "体系介绍",Toast.LENGTH_SHORT).show();
            }
        });
        //第2个圆形按钮的监听事件
        yuan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "机构注册",Toast.LENGTH_SHORT).show();

            }
        });
        //第3个圆形按钮的监听事件
        yuan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "业务咨询",Toast.LENGTH_SHORT).show();

            }
        });
        //第4个圆形按钮的监听事件
        yuan4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "能力培训",Toast.LENGTH_SHORT).show();

            }
        });
        //第1个方形按钮的监听事件
        fang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "装备报修",Toast.LENGTH_SHORT).show();

            }
        });
        //第2个方形按钮的监听事件
        fang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "技术支援",Toast.LENGTH_SHORT).show();

            }
        });
        //装备法规-更多的监听事件
        zc_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "更多法规",Toast.LENGTH_SHORT).show();

            }
        });
        //liseview的点击事件
        lv_zc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = view.findViewById(R.id.item_title);
                String title = tv.getText().toString();
                Toast.makeText(adapterView.getContext(), title,Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }


}
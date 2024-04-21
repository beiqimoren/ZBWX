package com.example.zbwx.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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




        return view;
    }


}
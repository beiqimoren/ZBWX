package com.example.zbwx.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.zbwx.R;
import com.example.zbwx.model.ZClistViewAdapter;
import com.example.zbwx.model.ZClistviewitem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    ImageView imageView;
    ListView lv_study;
    List<ZClistviewitem> list_study;

    public StudyFragment() {
        // Required empty public constructor
    }


    public static StudyFragment newInstance(String param1, String param2) {
        StudyFragment fragment = new StudyFragment();
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
        View view=inflater.inflate(R.layout.fragment_study, container, false);
        lv_study=view.findViewById(R.id.lv_study);

        //添加数据
        ZClistviewitem studylistviewitem1=new ZClistviewitem("装备维修管理规定","2023年3月1日版",R.drawable.zcitem1);
        ZClistviewitem studylistviewitem2=new ZClistviewitem("装备管理条例","2023年3月1日版",R.drawable.zcitem1);
        ZClistviewitem studylistviewitem3=new ZClistviewitem("共用信息系统装备维修保障指南","2023年3月1日版",R.drawable.zcitem1);
        ZClistviewitem studylistviewitem4=new ZClistviewitem("一体化维修保障规范","2023年3月1日版",R.drawable.zcitem1);

        list_study=new ArrayList<>();
        list_study.add(studylistviewitem4);
        list_study.add(studylistviewitem3);
        list_study.add(studylistviewitem2);
        list_study.add(studylistviewitem1);
        //加载数据到listview适配器
        ZClistViewAdapter studylistViewAdapter = new ZClistViewAdapter(this.getContext(),list_study);
        this.lv_study.setAdapter(studylistViewAdapter);

        //
        lv_study.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //这里是点击事件
            }
        });

        return view;
    }
}
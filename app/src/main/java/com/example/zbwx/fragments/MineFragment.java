package com.example.zbwx.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zbwx.LoginActivity;
import com.example.zbwx.MessageActivity;
import com.example.zbwx.MyApplication;
import com.example.zbwx.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    TextView username;
    private Button btn_msg,btn_ask,btn_contactus,btn_suggest,btn_lonout;
    MyApplication myApplication;

    public MineFragment() {
        // Required empty public constructor
    }
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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
        View view=inflater.inflate(R.layout.fragment_mine, container, false);
        init(view);//初始化控件
        //我的消息
        btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), MessageActivity.class);
                startActivity(intent);
            }
        });
        //我的咨询
        btn_ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //联系我们
        btn_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //反馈建议
        btn_suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //退出登录
        btn_lonout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                myApplication.setUserID(0);
//                myApplication.setUsername("");
                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                intent.putExtra("is_autolonin", false);
                startActivity(intent);
            }
        });
        return view;
    }
    //初始控件
    private void init(View view){
        username =view.findViewById(R.id.user_name);
        btn_msg = view.findViewById(R.id.my_msg);
        btn_ask = view.findViewById(R.id.myask);
        btn_contactus = view.findViewById(R.id.contactus);
        btn_suggest = view.findViewById(R.id.suggest);
        btn_lonout = view.findViewById(R.id.logout);
        myApplication=(MyApplication) requireActivity().getApplication();
        username.setText(myApplication.getUsername());
    }
}
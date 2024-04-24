package com.example.zbwx.fragments;

import static com.example.zbwx.MyApplication.my_url;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zbwx.LoginActivity;
import com.example.zbwx.MyApplication;
import com.example.zbwx.R;
import com.example.zbwx.RepairActivity;
import com.example.zbwx.SignupActivity;
import com.example.zbwx.SupportActivity;
import com.example.zbwx.model.RepairTable;
import com.example.zbwx.model.Utility;
import com.example.zbwx.model.ZClistViewAdapter;
import com.example.zbwx.model.ZClistviewitem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class RepairFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ImageView add_repair,add_support,repair_more,support_more;
    ListView lv_repair,lv_support;
    private final OkHttpClient okHttpClient = new OkHttpClient();
    List<RepairTable> repairTableList;
    //设置handler,监听服务器返回消息，并执行操作
    Handler baoxiu_handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Toast.makeText(getContext(),"网络连接失败",Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    try {
                        JSONArray jsonArray= new JSONArray(msg.obj.toString());
                        JSONArray_to_RepairTables(jsonArray);
                        show_repairTableList();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    Toast.makeText(getContext(),"该用户目前没有单据！",Toast.LENGTH_LONG).show();
                    break;
                default:break;

            }
        }
    };
    public RepairFragment() {
        // Required empty public constructor
    }
    public static RepairFragment newInstance(String param1, String param2) {
        RepairFragment fragment = new RepairFragment();
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
        View view=inflater.inflate(R.layout.fragment_repair, container, false);
        init_view(view);//初始化各种控件
        repair_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(my_url + "select_repair_byuserID/")).newBuilder();
                MyApplication app_baoxiu=(MyApplication) requireActivity().getApplication();
                urlBuilder.addQueryParameter("userID", app_baoxiu.getUserIDtoString());
                String url = urlBuilder.build().toString();  // 构建完整的URL
                Request request = new Request.Builder().url(url).get().build(); //构建请求
                okHttpClient.newCall(request).enqueue(new Callback() {   //设置响应
                    @Override    //响应失败进这里
                    public void onFailure(@NonNull Call call, IOException e) {
                        Log.e("---body", "onResponse");
                        baoxiu_handler.sendEmptyMessage(0);
                    }

                    @Override     //响应成功进这里
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String response_string = response.body().string();
                        if(!response_string.equals("没有数据")){
                            try {
                                JSONObject jsonObject = new JSONObject(response_string);
                                String jsonArray_string = jsonObject.getString("repairTable");
                                JSONArray jsonArray = new JSONArray(jsonArray_string);
                                Message msg=new Message();
                                msg.what=1;
                                msg.obj=jsonArray.toString();
                                baoxiu_handler.sendMessage(msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else
                            baoxiu_handler.sendEmptyMessage(2);//没有数据

                    }
                });
            }
        });
        support_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
    private void init_view(View view){
        add_repair=view.findViewById(R.id.add_repair);
        add_support=view.findViewById(R.id.add_support);
        repair_more=view.findViewById(R.id.more_repair);
        support_more=view.findViewById(R.id.more_support);
        lv_repair = view.findViewById(R.id.list_repair);
        lv_support = view.findViewById(R.id.list_support);
        repairTableList= new ArrayList<>();
        add_repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RepairActivity.class);
                startActivity(intent);
            }
        });
        add_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SupportActivity.class);
                startActivity(intent);
            }
        });
    }
    private void show_repairTableList() {
        List<ZClistviewitem> itemlist=new ArrayList<>();
        for (int i = 0; i < repairTableList.size(); i++){
            RepairTable table=repairTableList.get(i);
            ZClistviewitem item = new ZClistviewitem(table.type+table.equipment+"---"+table.state,table.fault,R.drawable.zcitem1);
            itemlist.add(item);
        }
        ZClistViewAdapter adapter = new ZClistViewAdapter(getActivity(),itemlist);
        this.lv_repair.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(lv_repair);
    }
    private void JSONArray_to_RepairTables(JSONArray jsonArray){
        repairTableList.clear();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                RepairTable repairTable=new RepairTable();
                repairTable.table_ID=jsonArray.getJSONObject(i).getInt("id");
                repairTable.user_ID=jsonArray.getJSONObject(i).getInt("userID");
                repairTable.equipment=jsonArray.getJSONObject(i).getString("equipment");
                repairTable.type=jsonArray.getJSONObject(i).getString("type");
                repairTable.province=jsonArray.getJSONObject(i).getString("province");
                repairTable.city=jsonArray.getJSONObject(i).getString("city");
                repairTable.address=jsonArray.getJSONObject(i).getString("address");
                repairTable.fault=jsonArray.getJSONObject(i).getString("fault");
                repairTable.contacts=jsonArray.getJSONObject(i).getString("contact");
                repairTable.phone=jsonArray.getJSONObject(i).getString("phone");
                repairTable.unit=jsonArray.getJSONObject(i).getString("unit");
                repairTable.notes=jsonArray.getJSONObject(i).getString("notes");
                repairTable.state=jsonArray.getJSONObject(i).getString("state");
                repairTableList.add(repairTable);
                Log.d("repairTableList",repairTableList.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("repairTableList",repairTableList.toString());
    }
}
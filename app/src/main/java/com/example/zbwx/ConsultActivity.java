package com.example.zbwx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zbwx.model.Consult;
import com.example.zbwx.model.ConsultMsg;
import com.example.zbwx.model.MsglistViewAdapter;
import com.example.zbwx.model.MyHttpClient;
import com.example.zbwx.model.MyMsg;
import com.example.zbwx.model.Utility;
import com.example.zbwx.model.ZClistviewitem;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultActivity extends AppCompatActivity {

    private ImageView back_image;
    private ListView lv_consult;
    MyApplication myapp;
    List<Consult> consultlist;
    MyHttpClient myHttpClient;
    //设置handler,监听服务器返回消息，并执行操作
    Handler consult_handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0: Toast.makeText(ConsultActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();break;
                case 1:
                    try {
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        if(jsonObject.getString("myconsult")=="没有数据")
                            Toast.makeText(ConsultActivity.this,"暂时没有消息！",Toast.LENGTH_LONG).show();
                        else {
                            JSONArray jsonArray= new JSONArray(jsonObject.getString("myconsult"));
                            JSONArray_to_ConsultTables(jsonArray);
                            show_consultTableList();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        back_image=findViewById(R.id.back_image);
        lv_consult=findViewById(R.id.consultlist);
        consultlist=new ArrayList<>();
        //维修单列表点击事件
        lv_consult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ConsultActivity.this,ConsultMsgActivity.class);
                //intent.putExtra("selcted_consultitem",new Gson().toJson(consultlist.get(i)));
                intent.putExtra("selcted_consultitem",consultlist.get(i).consultID);
                startActivity(intent);
            }
        });
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        myHttpClient=new MyHttpClient();
        myapp=(MyApplication) getApplication();
        myHttpClient.SendToServer(consult_handler,"getconsult/",myapp.getUserIDtoString());
    }
    //显示消息
    private void show_consultTableList() {
        List<ZClistviewitem> itemlist=new ArrayList<>();
        for (int i = 0; i < consultlist.size(); i++){
            Consult table=consultlist.get(i);
            ZClistviewitem item = new ZClistviewitem(table.title,table.firstcontent,R.drawable.zcitem1);
            itemlist.add(item);
        }
        MsglistViewAdapter adapter = new MsglistViewAdapter(this,itemlist);
        this.lv_consult.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(lv_consult);
    }
    //JSON数组转为消息集合
    private void JSONArray_to_ConsultTables(JSONArray jsonArray){
        consultlist.clear();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                Consult consult_temp=new Consult();
                consult_temp.adminID=jsonArray.getJSONObject(i).getInt("adminID");
                consult_temp.userID=jsonArray.getJSONObject(i).getInt("userID");
                consult_temp.consultID=jsonArray.getJSONObject(i).getInt("id");
                consult_temp.title=jsonArray.getJSONObject(i).getString("title");
                consult_temp.firstcontent=jsonArray.getJSONObject(i).getString("contentlist");
                consultlist.add(consult_temp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
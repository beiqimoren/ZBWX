package com.example.zbwx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zbwx.model.MsglistViewAdapter;
import com.example.zbwx.model.MyHttpClient;
import com.example.zbwx.model.MyMsg;
import com.example.zbwx.model.RepairTable;
import com.example.zbwx.model.Utility;
import com.example.zbwx.model.ZClistViewAdapter;
import com.example.zbwx.model.ZClistviewitem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private ImageView back_image;
    private ListView lv_msg;
    MyApplication app_baoxiu;
    List<MyMsg> msglist;
    MyHttpClient myHttpClient;
    //设置handler,监听服务器返回消息，并执行操作
    Handler mymsg_handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0: Toast.makeText(MessageActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();break;
                case 1:
                    try {
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        if(jsonObject.getString("mymsg")=="没有数据")
                            Toast.makeText(MessageActivity.this,"暂时没有消息！",Toast.LENGTH_LONG).show();
                        else {
                            JSONArray jsonArray= new JSONArray(jsonObject.getString("mymsg"));
                            JSONArray_to_MsgTables(jsonArray);
                            show_msgTableList();
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
        setContentView(R.layout.activity_message);
        back_image=findViewById(R.id.back_image);
        lv_msg=findViewById(R.id.msglist);
        msglist=new ArrayList<>();
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        myHttpClient=new MyHttpClient();
        app_baoxiu=(MyApplication) getApplication();
        myHttpClient.SendToServer(mymsg_handler,"getmsg/",app_baoxiu.getUserIDtoString());

    }
    //显示消息
    private void show_msgTableList() {
        List<ZClistviewitem> itemlist=new ArrayList<>();
        for (int i = 0; i < msglist.size(); i++){
            MyMsg table=msglist.get(i);
            ZClistviewitem item = new ZClistviewitem(table.title,table.body+"---"+table.datetime,R.drawable.zcitem1);
            itemlist.add(item);
        }
        MsglistViewAdapter adapter = new MsglistViewAdapter(this,itemlist);
        this.lv_msg.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(lv_msg);
    }
    //JSON数组转为消息集合
    private void JSONArray_to_MsgTables(JSONArray jsonArray){
        msglist.clear();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                MyMsg msg_temp=new MyMsg();
                msg_temp.sender=jsonArray.getJSONObject(i).getInt("adminID");
                msg_temp.receiver=jsonArray.getJSONObject(i).getInt("userID");
                msg_temp.datetime=jsonArray.getJSONObject(i).getString("date");
                msg_temp.title=jsonArray.getJSONObject(i).getString("title");
                msg_temp.body=jsonArray.getJSONObject(i).getString("content");
                msg_temp.state=jsonArray.getJSONObject(i).getString("state");
                msglist.add(msg_temp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
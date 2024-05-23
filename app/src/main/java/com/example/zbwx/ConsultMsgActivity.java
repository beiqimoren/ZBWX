package com.example.zbwx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zbwx.model.Consult;
import com.example.zbwx.model.ConsultMsg;
import com.example.zbwx.model.MyHttpClient;
import com.example.zbwx.model.Utility;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ConsultMsgActivity extends AppCompatActivity {

    MyApplication myapp;
    private Consult myconsult;
    private ListView lsv_consultmsg;
    private EditText consultmsg;
    private Button send_consultmsg;
    private ImageView back_image;

    private int TYPE_COUNT = 2;
    private int LEFT = 0;
    private int RIGHT = 1;
    private LayoutInflater inflater;
    private MyAdapter adapter;
    MyHttpClient myHttpClient;
    //设置handler,监听服务器返回消息，并执行操作
    Handler consultmsg_handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(ConsultMsgActivity.this, "网络连接失败", Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    try {
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        if (jsonObject.getString("myconsultbyID") == "没有数据")
                            Toast.makeText(ConsultMsgActivity.this, "暂时没有消息！", Toast.LENGTH_LONG).show();
                        else {
                            JSONArray jsonArray = new JSONArray(jsonObject.getString("myconsultbyID"));
                            JSONArray_to_myconsult(jsonArray);
                            show_consultmsg();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 7:
                    if (msg.obj.equals("成功")) {
                        myHttpClient.SendToServer(consultmsg_handler, "getconsultbyID/", String.valueOf(myconsult.consultID));
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_msg);
        init();//初始化
        //加载数据
        myHttpClient.SendToServer(consultmsg_handler, "getconsultbyID/", String.valueOf(myconsult.consultID));
        //发送按钮监听
        send_consultmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(TextUtils.isEmpty(consultmsg.getText().toString()))){
                    ConsultMsg msg = new ConsultMsg();
                    msg.type = 0;
                    msg.content = consultmsg.getText().toString();
                    msg.time_stamp = System.currentTimeMillis();
                    myconsult.contentlist.add(msg);
                    myHttpClient = new MyHttpClient();
                    //收集数据打包成JSON
                    try {
                        JSONObject json = new JSONObject();
                        JSONArray contentlistjson = new JSONArray();
                        for (int i = 0; i < myconsult.contentlist.size(); i++) {
                            JSONObject content_json = new JSONObject();
                            content_json.put("time_stamp", myconsult.contentlist.get(i).time_stamp);
                            content_json.put("type", myconsult.contentlist.get(i).type);
                            content_json.put("content", myconsult.contentlist.get(i).content);
                            contentlistjson.put(content_json);
                        }
                        json.put("userID", myconsult.userID);
                        json.put("adminID", myconsult.adminID);
                        json.put("consultID", myconsult.consultID);
                        json.put("title", myconsult.title);
                        json.put("contentlist", contentlistjson);
                        //发送信息
                        myHttpClient.SendToServer(consultmsg_handler, 7, "updateconsult/", json);
                        consultmsg.setText("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(ConsultMsgActivity.this,"输入为空！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //初始化
    private void init() {
        myconsult=new Consult();
        //获取传进来的信息
        Intent intent = getIntent();
        if (intent != null) {
            myconsult.consultID=intent.getIntExtra("selcted_consultitem",0);
        }
        back_image=findViewById(R.id.back_image);
        lsv_consultmsg = findViewById(R.id.consulmsgtlist);
        consultmsg = findViewById(R.id.consultmsg);
        myHttpClient = new MyHttpClient();
        send_consultmsg = findViewById(R.id.send_consultmsg);
        inflater = LayoutInflater.from(this);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new MyAdapter();



    }

    //更新显示
    private void show_consultmsg() {
        adapter.notifyDataSetChanged();
        lsv_consultmsg.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(lsv_consultmsg);
    }

    //json解析
    private void JSONArray_to_myconsult(JSONArray jsonArray) {
        myconsult.contentlist.clear();
        try {
            myconsult.adminID = jsonArray.getJSONObject(0).getInt("adminID");
            myconsult.userID = jsonArray.getJSONObject(0).getInt("userID");
            myconsult.consultID = jsonArray.getJSONObject(0).getInt("id");
            myconsult.title = jsonArray.getJSONObject(0).getString("title");
            JSONArray content_temp = new JSONArray(jsonArray.getJSONObject(0).getString("contentlist"));
            for (int j = 0; j < content_temp.length(); j++) {
                ConsultMsg conmsg = new ConsultMsg();
                conmsg.time_stamp = content_temp.getJSONObject(j).getLong("time_stamp");
                conmsg.type = content_temp.getJSONObject(j).getInt("type");
                conmsg.content = content_temp.getJSONObject(j).getString("content");
                myconsult.contentlist.add(conmsg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return myconsult.contentlist.size();
        }

        @Override
        public Object getItem(int position) {
            return myconsult.contentlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            if (myconsult.contentlist.get(position).type == LEFT) {
                return LEFT;
            }
            return RIGHT;
        }

        @Override
        public int getViewTypeCount() {
            return TYPE_COUNT;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            ConsultMsg p = myconsult.contentlist.get(position);
            if (getItemViewType(position) == LEFT) {
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.consultmsg_left, null);
                    holder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
                    holder.tv_age = (TextView) convertView.findViewById(R.id.tv_age);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.tv_username.setText(p.content);
                holder.tv_age.setText(String.valueOf(p.type));
            } else {
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.consultmsg_right, null);
                    holder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
                    holder.tv_age = (TextView) convertView.findViewById(R.id.tv_age);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.tv_username.setText(p.content);
                holder.tv_age.setText(String.valueOf(p.type));
            }

            return convertView;
        }

        class ViewHolder {
            TextView tv_username;
            TextView tv_age;
        }
    }

}
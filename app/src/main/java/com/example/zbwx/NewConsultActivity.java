package com.example.zbwx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zbwx.model.Consult;
import com.example.zbwx.model.MyHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewConsultActivity extends AppCompatActivity {

    ImageView back;
    EditText title,content;
    Button submit;
    Consult consult;
    MyApplication myapp;
    MyHttpClient myHttpClient;
    //设置handler,监听服务器返回消息，并执行操作
    Handler newconsult_handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what==1&&msg.obj.equals("成功")){
                Toast.makeText(NewConsultActivity.this,"提交成功！",Toast.LENGTH_LONG).show();
                finish();
            }else {
                Toast.makeText(NewConsultActivity.this, "提交失败！", Toast.LENGTH_SHORT).show();
                //如果handler通知不为1，则登录失败
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_coult);
        back=findViewById(R.id.back_image);
        title=findViewById(R.id.coult_title);
        content=findViewById(R.id.coult_content);
        myHttpClient=new MyHttpClient();
        myapp=(MyApplication) getApplication();
        submit=findViewById(R.id.coult_submit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //收集数据打包成JSON
                JSONObject content_json = new JSONObject();
                JSONObject json = new JSONObject();
                try {
                    content_json.put("time_stamp", System.currentTimeMillis());
                    content_json.put("type", 0);
                    content_json.put("content",content.getText());
                    //JSONArray content=new JSONArray();
                    //content.put(content_json);
                    json.put("userID", myapp.getUserID());
                    json.put("adminID", 0);
                    json.put("title", title.getText());
                    //json.put("contentlist",content.toString());
                    json.put("content",content.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MyHttpClient client = new MyHttpClient(newconsult_handler, "addconsult/",json);
            }
        });

    }
}
package com.example.zbwx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.zbwx.model.Consult;
import com.example.zbwx.model.ConsultMsg;
import com.google.gson.Gson;

public class ConsultMsgActivity extends AppCompatActivity {

    private Consult myconsult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_msg);
        //判断是用于显示，还是编辑提交
        Intent intent =getIntent();
        String JsonData=intent.getStringExtra("selcted_consultitem");
        if(JsonData!=null){
            myconsult=new Gson().fromJson(JsonData,Consult.class);
        }

    }
}
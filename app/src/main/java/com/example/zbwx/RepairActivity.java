package com.example.zbwx;

import static com.example.zbwx.MyApplication.my_url;
import static okhttp3.MediaType.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zbwx.fragments.RepairFragment;
import com.example.zbwx.model.Citys;
import com.example.zbwx.model.MyHttpClient;
import com.example.zbwx.model.RepairTable;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RepairActivity extends AppCompatActivity {

    ImageView imageView;
    MyApplication myApplication;

    Spinner spinner1, spinner2;
    private String province, city;
    int position1 = 0;

    //声明控件
    EditText equipment,type,address,fault,contact,phone,unit,notes;
    Button submit;

    //设置handler,监听服务器返回消息，并执行操作
    Handler repair_handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what==1&&msg.obj.equals("成功")){
                Toast.makeText(RepairActivity.this,"提交成功！",Toast.LENGTH_LONG).show();
                finish();
            }else {
                Toast.makeText(RepairActivity.this, "提交失败！", Toast.LENGTH_SHORT).show();
                //如果handler通知不为1，则登录失败
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);
        init();//初始化控件
        //提交数据
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //收集数据打包成JSON
                JSONObject json = new JSONObject();
                try {
                    json.put("userID", myApplication.getUserID());
                    json.put("equipment", equipment.getText().toString());
                    json.put("type", type.getText().toString());
                    json.put("province", province);
                    json.put("city", city);
                    json.put("address", address.getText().toString());
                    json.put("fault", fault.getText().toString());
                    json.put("contact", contact.getText().toString());
                    json.put("phone", phone.getText().toString());
                    json.put("unit", unit.getText().toString());
                    json.put("notes", notes.getText().toString());
                    json.put("state", "待审核");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MyHttpClient client = new MyHttpClient(repair_handler, "addrepairtable/", json);
            }
        });
        //判断是用于显示，还是编辑提交
        Intent intent =getIntent();
        String JsonData=intent.getStringExtra("selcted_repairitem");
        if(JsonData!=null){
            RepairTable repairTable=new Gson().fromJson(JsonData,RepairTable.class);
            ShowRepairTable(repairTable);
        }

    }
    //初始化控件
    private void init(){
        imageView = findViewById(R.id.back_image);
        submit = findViewById(R.id.submit);
        equipment = findViewById(R.id.equipment);
        type = findViewById(R.id.type);
        address = findViewById(R.id.address);
        fault = findViewById(R.id.falut);
        contact = findViewById(R.id.contact);
        phone = findViewById(R.id.phone);
        unit = findViewById(R.id.unit);
        notes = findViewById(R.id.notes);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        myApplication = (MyApplication)getApplication();

        spinner1 = findViewById(R.id.sp1);
        spinner2 = findViewById(R.id.sp2);
        // 省份Spinner适配器
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new Citys().getProvince());
        spinner1.setAdapter(provinceAdapter);
        // Spinner选项选择监听器
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position1 = position;
                province = new Citys().getProvince(position);
                String[] cities = new Citys().getCityData(position); // 获取省份对应的城市数组
                ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(RepairActivity.this, android.R.layout.simple_spinner_dropdown_item, cities);
                spinner2.setAdapter(cityAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = new Citys().getCityData(position1)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //显示表内信息
    private void ShowRepairTable(RepairTable repairTable){
        equipment.setText(repairTable.equipment);
        equipment.setTextColor(Color.BLACK);
        equipment.setEnabled(false);

        type.setText(repairTable.type);
        type.setTextColor(Color.BLACK);
        type.setEnabled(false);

        spinner1.setEnabled(false);
        spinner2.setEnabled(false);
        address.setText(repairTable.province+repairTable.city+repairTable.address);
        address.setTextColor(Color.BLACK);
        address.setEnabled(false);

        fault.setText(repairTable.fault);
        fault.setTextColor(Color.BLACK);
        fault.setEnabled(false);

        contact.setText(repairTable.contacts);
        contact.setTextColor(Color.BLACK);
        contact.setEnabled(false);

        phone.setText(repairTable.phone);
        phone.setTextColor(Color.BLACK);
        phone.setEnabled(false);

        unit.setText(repairTable.unit);
        unit.setTextColor(Color.BLACK);
        unit.setEnabled(false);

        notes.setText(repairTable.notes);
        notes.setTextColor(Color.BLACK);
        notes.setEnabled(false);

        submit.setEnabled(false);
    }


}
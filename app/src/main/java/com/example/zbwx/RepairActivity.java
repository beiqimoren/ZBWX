package com.example.zbwx;

import static com.example.zbwx.MyApplication.my_url;
import static okhttp3.MediaType.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.zbwx.model.Citys;
import com.example.zbwx.model.RepairTable;

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
                RepairActivity.this.finish();
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
        //加载预先界面
        imageView = findViewById(R.id.back_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        //获取数据
        submit = findViewById(R.id.submit);
        equipment = findViewById(R.id.equipment);
        type = findViewById(R.id.type);
        address = findViewById(R.id.address);
        fault = findViewById(R.id.falut);
        contact = findViewById(R.id.contact);
        phone = findViewById(R.id.phone);
        unit = findViewById(R.id.unit);
        notes = findViewById(R.id.notes);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //收集数据打包成JSON
                MyApplication myApplication = (MyApplication)getApplication();
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
//                try {
//                    json.put("userID", 12);
//                    json.put("equipment", "华为传输");
//                    json.put("type", "8800");
//                    json.put("province", "重庆市");
//                    json.put("city", "渝中区");
//                    json.put("address", "长江一路");
//                    json.put("fault", "10G光板故障灯常亮，自环未消失");
//                    json.put("contact", "王忠攀");
//                    json.put("phone", "15723070964");
//                    json.put("unit", "重庆站");
//                    json.put("notes", "测试1");
//                    json.put("state", "待审核");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                //设置http客户端post 发送JSON数据
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(String.valueOf(json),MediaType.parse("application/json; charset=utf-8"));
                Request request=new Request.Builder()
                        .url(Objects.requireNonNull(HttpUrl.parse(my_url + "addrepairtable/")))
                        .addHeader("key","value")
                        .post(requestBody)
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d("Repairsend", "维修表发送失败");
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Log.d("Repairsend", Objects.requireNonNull(response.body()).string());
                        String body = Objects.requireNonNull(response.body()).string();
                        Message msg =new Message();
                        msg.what=1;
                        msg.obj=body;
                        repair_handler.sendMessage(msg);
                    }
                });
//                RepairTable repairTable=new RepairTable();
//                repairTable.user_ID=myApplication.getUserID();
//                repairTable.equipment=equipment.getText().toString();
//                repairTable.type=type.getText().toString();
//                repairTable.province=province;
//                repairTable.city=city;
//                repairTable.address=address.getText().toString();
//                repairTable.fault=fault.getText().toString();
//                repairTable.contacts=contact.getText().toString();
//                repairTable.phone=phone.getText().toString();
//                repairTable.unit=unit.getText().toString();
//                repairTable.notes=notes.getText().toString();
//                repairTable.state="待审核";

            }
        });






    }


}
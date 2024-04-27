package com.example.zbwx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.ChineseCalendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zbwx.model.Citys;
import com.example.zbwx.model.MyHttpClient;
import com.example.zbwx.model.RepairTable;
import com.example.zbwx.model.SupportTable;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Calendar;

public class SupportActivity extends AppCompatActivity {

    ImageView imageView;
    EditText date, address, thing, contact, phone, unit, notes;
    Spinner sp_province, sp_city;
    Button submit;
    MyApplication myApplication;
    private String province, city;
    int position1 = 0;
    //设置handler,监听服务器返回消息，并执行操作
    Handler support_handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Toast.makeText(SupportActivity.this, "提交失败！", Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    if (msg.obj.equals("成功")){
                        Toast.makeText(SupportActivity.this, "提交成功！", Toast.LENGTH_LONG).show();
                        SupportActivity.this.finish();
                    }else
                        Toast.makeText(SupportActivity.this, "提交失败！", Toast.LENGTH_SHORT).show();
                    break;
                default:break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        init();//初始化控件
        //点击提交数据
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject json = new JSONObject();
                try {
                    json.put("userID", myApplication.getUserID());
                    json.put("date", date.getText().toString());
                    json.put("province", province);
                    json.put("city", city);
                    json.put("address", address.getText().toString());
                    json.put("thing", thing.getText().toString());
                    json.put("contact", contact.getText().toString());
                    json.put("phone", phone.getText().toString());
                    json.put("unit", unit.getText().toString());
                    json.put("notes", notes.getText().toString());
                    json.put("state", "待审核");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MyHttpClient client = new MyHttpClient(support_handler, "addsupporttable/", json);
            }
        });
        //判断是用于显示，还是编辑提交
        Intent intent =getIntent();
        String JsonData=intent.getStringExtra("selcted_supportitem");
        if(JsonData!=null){
            SupportTable supportTable=new Gson().fromJson(JsonData,SupportTable.class);
            ShowSupportTable(supportTable);
        }
    }

    //初始化控件
    private void init() {
        imageView = findViewById(R.id.back_image);
        date = findViewById(R.id.date_edit);
        sp_province = findViewById(R.id.sp1);
        sp_city = findViewById(R.id.sp2);
        address = findViewById(R.id.address);
        thing = findViewById(R.id.thing);
        contact = findViewById(R.id.contact);
        phone = findViewById(R.id.phone);
        unit = findViewById(R.id.unit);
        notes = findViewById(R.id.notes);
        submit = findViewById(R.id.submit);
        myApplication = (MyApplication) getApplication();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //处理日期选择控件
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(SupportActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                        date.setText(year + "年" + (month + 1) + "月" + dayofMonth + "日");
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        // 省份Spinner适配器
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new Citys().getProvince());
        sp_province.setAdapter(provinceAdapter);
        // 城市Spinner适配器
        sp_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position1 = position;
                province = new Citys().getProvince(position);
                String[] cities = new Citys().getCityData(position); // 获取省份对应的城市数组
                ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(SupportActivity.this, android.R.layout.simple_spinner_dropdown_item, cities);
                sp_city.setAdapter(cityAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = new Citys().getCityData(position1)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void ShowSupportTable(SupportTable supportTable){
        date.setText(supportTable.date);
        date.setTextColor(Color.BLACK);
        date.setEnabled(false);

        sp_province.setEnabled(false);
        sp_city.setEnabled(false);

        address.setText(supportTable.address);
        address.setTextColor(Color.BLACK);
        address.setEnabled(false);

        thing.setText(supportTable.thing);
        thing.setTextColor(Color.BLACK);
        thing.setEnabled(false);

        contact.setText(supportTable.contact);
        contact.setTextColor(Color.BLACK);
        contact.setEnabled(false);

        phone.setText(supportTable.phone);
        phone.setTextColor(Color.BLACK);
        phone.setEnabled(false);

        unit.setText(supportTable.unit);
        unit.setTextColor(Color.BLACK);
        unit.setEnabled(false);

        notes.setText(supportTable.notes);
        notes.setTextColor(Color.BLACK);
        notes.setEnabled(false);

        submit.setEnabled(false);
    }
}
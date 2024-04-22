package com.example.zbwx;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.icu.util.ChineseCalendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.zbwx.model.Citys;

import java.time.LocalDateTime;
import java.util.Calendar;

public class SupportActivity extends AppCompatActivity {

    ImageView imageView;
    EditText editText;
    Spinner spinner1, spinner2;

    private String province, city;
    int position1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        imageView = findViewById(R.id.back_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //处理日期选择控件
        editText = findViewById(R.id.date_edit);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(SupportActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                        editText.setText(year + "年" + (month + 1) + "月" + dayofMonth + "日");
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });


        spinner1 = findViewById(R.id.sp1);
        spinner2 = findViewById(R.id.sp2);
        // 省份Spinner适配器
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new Citys().getProvince());
        spinner1.setAdapter(provinceAdapter);
        // 城市Spinner适配器

        // Spinner选项选择监听器
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position1 = position;
                province = new Citys().getProvince(position);
                String[] cities = new Citys().getCityData(position); // 获取省份对应的城市数组
                ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(SupportActivity.this, android.R.layout.simple_spinner_dropdown_item, cities);
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

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // 获取日期对话框设定的年月份
        String desc = String.format("您选择的日期是%d年%d月%d日",
                year, monthOfYear + 1, dayOfMonth);
        //tv_date.setText(desc);
    }

}
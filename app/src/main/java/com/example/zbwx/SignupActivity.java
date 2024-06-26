package com.example.zbwx;

import static com.example.zbwx.MyApplication.my_url;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zbwx.model.Citys;
import com.example.zbwx.model.MyHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {

    EditText _username, _password,_password2,unit;
    Spinner spinner1, spinner2;
    private String province, city;
    int position1 = 0;
    Button _signup;
    //设置handler,监听服务器返回消息，并执行操作
    Handler signup_handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    _signup.setEnabled(true);
                    Toast.makeText(getApplication(), "无法连接服务器！", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    try {
                        JSONObject json = new JSONObject(msg.obj.toString());
                        switch (json.getString("state")) {
                            case "该账号已注册":
                                _signup.setEnabled(true);
                                Toast.makeText(getApplication(), "该账号已注册！", Toast.LENGTH_SHORT).show();
                                break;
                            case "注册成功":
                                Toast.makeText(getApplication(), "注册成功！请前往登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case "注册失败":
                                _signup.setEnabled(true);
                                Toast.makeText(getApplication(), "注册失败！", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
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
        setContentView(R.layout.activity_signup);
        init();//初始化控件
        _signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MyHttpClient client = new MyHttpClient(signup_handler,
//                        "sigup/", _username.getText().toString(), _password.getText().toString());
                //收集数据打包成JSON
                JSONObject json = new JSONObject();
                String p1=_password.getText().toString();
                String p2=_password2.getText().toString();
                if(p1.equals(p2)){
                    try {
                        json.put("username", _username.getText().toString());
                        json.put("password",  _password.getText().toString());
                        json.put("province", province);
                        json.put("city", city);
                        json.put("unit", unit.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MyHttpClient client = new MyHttpClient(signup_handler, "sigup/", json);
                }else {
                    Toast.makeText(SignupActivity.this,"密码不一致，请重新输入！",Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    //初始化控件
    private void init() {
        _username = findViewById(R.id.signup_username);
        _password = findViewById(R.id.signup_password);
        _password2 = findViewById(R.id.signup_password2);
        _signup = findViewById(R.id.btn_signup);
        unit = findViewById(R.id.signup_unit);
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
                ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(SignupActivity.this, android.R.layout.simple_spinner_dropdown_item, cities);
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
}
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    EditText _username, _password;
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
                MyHttpClient client = new MyHttpClient(signup_handler,
                        "sigup/", _username.getText().toString(), _password.getText().toString());
            }
        });
    }

    //初始化控件
    private void init() {
        _username = findViewById(R.id.signup_username);
        _password = findViewById(R.id.signup_password);
        _signup = findViewById(R.id.btn_signup);
    }
}
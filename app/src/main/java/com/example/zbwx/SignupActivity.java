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

    EditText _username,_password;
    Button _signup;
    private final OkHttpClient okHttpClient = new OkHttpClient();
    //设置handler,监听服务器返回消息，并执行操作
    Handler signup_handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    _signup.setEnabled(true);
                    Toast.makeText(getApplication(), "无法连接服务器！", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getApplication(), "注册成功！请前往登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    _signup.setEnabled(true);
                    Toast.makeText(getApplication(), "该用户已注册，请前往登录", Toast.LENGTH_SHORT).show();
                    break;
                default:break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        _username=findViewById(R.id.signup_username);
        _password=findViewById(R.id.signup_password);
        _signup=findViewById(R.id.btn_signup);
        _signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = _username.getText().toString();  //获取用户名
                String password = _password.getText().toString();  //获取密码
                //发送网络请求   构建查询参数
                HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(my_url + "sigup/")).newBuilder();
                urlBuilder.addQueryParameter("username", username);
                urlBuilder.addQueryParameter("password", password);
                String url = urlBuilder.build().toString();  // 构建完整的URL
                Request request = new Request.Builder().url(url).get().build(); //构建请求
                okHttpClient.newCall(request).enqueue(new Callback() {   //设置响应
                    @Override    //响应失败进这里
                    public void onFailure(@NonNull Call call, IOException e) {
                        Log.e("---body", "onResponse");
                        signup_handler.sendEmptyMessage(0);
                    }
                    @Override     //响应成功进这里
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String response_string = response.body().string();
                        try {
                            JSONObject jsonObject=new JSONObject(response_string);
                            String state = jsonObject.getString("state");
                            switch (state) {
                                case "注册成功":
                                    signup_handler.sendEmptyMessage(1);//Handler 发送消息，通知主进程响应成功
                                    break;
                                case "该账号已注册":
                                    signup_handler.sendEmptyMessage(2);
                                    break;
                                default:
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
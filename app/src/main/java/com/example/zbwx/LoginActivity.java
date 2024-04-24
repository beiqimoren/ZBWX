package com.example.zbwx;

import static com.example.zbwx.MyApplication.my_url;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private SharedPreferences sp;
    private EditText _emailText;
    private EditText _passwordText;
    private Button _loginButton;
    private TextView _signupLink;
    private CheckBox _rmpass;
    private CheckBox _aulogin;
    //定义转圈进度条
//    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
//            com.google.android.material.R.style.Base_Theme_Material3_Dark_Dialog);

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private String username, password;
    //设置handler,监听服务器返回消息，并执行操作
    Handler login_handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    _loginButton.setEnabled(true);
                    Toast.makeText(getApplication(), "连接服务器失败！", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    SharedPreferences.Editor editor = sp.edit();//记住用户名
                    editor.putInt("USER_ID", (int) msg.obj);
                    editor.putString("USER_NAME", username);
                    editor.putString("PASSWORD", password);
                    editor.apply();
                    MyApplication myApplication = (MyApplication) getApplication();
                    myApplication.setUsername(sp.getString("USER_NAME", ""));
                    myApplication.setUserID(sp.getInt("USER_ID", 0));
                    Toast.makeText(getApplication(), "登录成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    _loginButton.setEnabled(true);
                    _passwordText.setText("");
                    Toast.makeText(getApplication(), "密码错误！", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    _loginButton.setEnabled(true);
                    _passwordText.setText("");
                    Toast.makeText(getApplication(), "用户不存在！", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取控件
        _emailText = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);
        _loginButton = findViewById(R.id.btn_login);
        _signupLink = findViewById(R.id.link_sigup);
        _rmpass = findViewById(R.id.rm_pass);
        _aulogin = findViewById(R.id.au_login);
        //获取SharedPreferences实例
        sp = this.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        //注册动作
        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        //点击登录       发送登录验证请求
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_login_requst();
            }
        });
        //判断记住密码和自动登录
        _emailText.setText(sp.getString("USER_NAME", ""));
        _passwordText.setText(sp.getString("PASSWORD", ""));
        send_login_requst();     //发送登录请求
    }

    //发送验证请求
    public void send_login_requst() {
        if (!validate()) {  //检查数据合法性
            onLoginFailed();
            return;
        }
        _loginButton.setEnabled(false);  //登录按钮变灰色
        username = _emailText.getText().toString();  //获取用户名
        password = _passwordText.getText().toString();  //获取密码
        //发送网络请求   构建查询参数
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(my_url + "login/")).newBuilder();
        urlBuilder.addQueryParameter("username", username);
        urlBuilder.addQueryParameter("password", password);
        String url = urlBuilder.build().toString();  // 构建完整的URL
        Request request = new Request.Builder().url(url).get().build(); //构建请求
        okHttpClient.newCall(request).enqueue(new Callback() {   //设置响应
            @Override    //响应失败进这里
            public void onFailure(@NonNull Call call, IOException e) {
                Log.e("---body", "onResponse");
                login_handler.sendEmptyMessage(0);
            }

            @Override     //响应成功进这里
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String response_string = response.body().string();
                Message msg = new Message();
                try {
                    JSONObject jsonObject=new JSONObject(response_string);
                    int userID = jsonObject.getInt("userID");
                    String state = jsonObject.getString("state");
                    switch (state) {
                        case "成功":
                            msg.what=1;
                            msg.obj=userID;
                            login_handler.sendMessage(msg);
                            break;
                        case "密码错误":
                            login_handler.sendEmptyMessage(2);
                            break;
                        case "用户不存在":
                            login_handler.sendEmptyMessage(3);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "用户名或密码错误！", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }
    //输入格式检查
    public boolean validate() {
        boolean valid = true;
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        if (email.isEmpty() || !Patterns.PHONE.matcher(email).matches()) {
            _emailText.setError("enter a valid phone number");
            valid = false;
        } else _emailText.setError(null);
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else _passwordText.setError(null);
        return valid;
    }

}
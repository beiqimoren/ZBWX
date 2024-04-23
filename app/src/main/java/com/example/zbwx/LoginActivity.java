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

    private static final String TAG="LoginActivity";
    private static final int REQUEST_SIGNUP=0;
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

    private String username,password;
    //设置handler,监听服务器返回消息，并执行操作
    Handler login_handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                JSONObject msgobj = new JSONObject(msg.obj.toString());
                String state = msgobj.getString("state");
                int userID = msgobj.getInt("userID");
                if(msg.what==1&& state.equals("成功")){
                    SharedPreferences.Editor editor = sp.edit();//记住用户名
                    editor.putInt("USER_ID", userID);
                    editor.putString("USER_NAME", username);
                    editor.putString("PASSWORD", password);
                    editor.apply();
                    MyApplication myApplication= (MyApplication) getApplication();
                    myApplication.setUsername(sp.getString("USER_NAME",""));
                    myApplication.setUserID(sp.getInt("USER_ID",0));

                    //login_succesd();  //如果handler通知为1，则登录成功
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }else
                    login_failed();  //如果handler通知不为1，则登录失败
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         //获取控件
        _emailText=findViewById(R.id.input_email);
        _passwordText=findViewById(R.id.input_password);
        _loginButton=findViewById(R.id.btn_login);
        _signupLink=findViewById(R.id.link_sigup);
        _rmpass=findViewById(R.id.rm_pass);
        _aulogin=findViewById(R.id.au_login);
        //获取SharedPreferences实例
        sp=this.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
//        if(sp.getBoolean("ISCHECK", false)) {
//            _rmpass.setChecked(true);
//            _emailText.setText(sp.getString("USER_NAME", ""));
//            _passwordText.setText(sp.getString("PASSWORD", ""));
//            if(sp.getBoolean("AUTO_ISCHECK", false)) {
//                //设置默认是自动登录状态
//                _aulogin.setChecked(true);
//                send_login_requst();     //发送登录请求
//                //Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                //startActivity(intent);
//                //this.finish();
//            }
//        }
        //注册动作
        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                //startActivityForResult(intent,REQUEST_SIGNUP);
            }
        });
        //点击登录       发送登录验证请求
        _loginButton.setOnClickListener(new View.OnClickListener(){
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
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Authenticating...");
//        progressDialog.show();    //模拟登录转圈
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
                    Message msg =new Message();
                    msg.what=1;
                    msg.obj=response.body().string();
                    login_handler.sendMessage(msg);//Handler 发送消息，通知主进程响应成功
            }
        });
    }
    //验证失败后执行下列操作
    public void login_failed(){
//        progressDialog.dismiss();
        _loginButton.setEnabled(true);
        _emailText.setText("");
        _passwordText.setText("");
        Toast.makeText(getApplication(), "用户名或密码错误", Toast.LENGTH_SHORT).show();

    }
    //验证成功后执行下列操作
    public void login_succesd(){
        if(_rmpass.isChecked()) {
            SharedPreferences.Editor editor = sp.edit();//记住用户名
            editor.putString("USER_NAME", username);
            editor.putString("PASSWORD", password);
            editor.apply();
            sp.edit().putBoolean("ISCHECK", true).apply();
        }else sp.edit().putBoolean("ISCHECK", false).apply();
        if (this._aulogin.isChecked())
            sp.edit().putBoolean("AUTO_ISCHECK", true).apply();
        else sp.edit().putBoolean("AUTO_ISCHECK", false).apply();
//        progressDialog.dismiss();
          this._loginButton.setEnabled(true);
          //finish();
        //延迟2秒后finish当前activity
//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onLoginSuccess or onLoginFailed
//                        progressDialog.dismiss();
//                        onLoginSuccess();
//                        // onLoginFailed();
//
//                    }
//                }, 2000);
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
package com.example.zbwx;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyHttpClient {

    private final String my_url="http://192.168.1.5:8000/";
    private boolean login_issuccessful;

    public void set_login_issuccessful(boolean m){
        this.login_issuccessful = m;
    }
    public boolean get_login_issuccessful(){
        return this.login_issuccessful;
    }

//    public boolean login_check(String user_name,String password){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    OkHttpClient client=new OkHttpClient();
//                    RequestBody requestBody = new FormBody.Builder()
//                            .add("username",user_name)
//                            .add("password",password)
//                            .build();
//                    Request request = new Request.Builder()
//                            .url(my_url)
//                            .post(requestBody)
//                            .build();
//                    Response response=null;
//                    response =client.newCall(request).execute();
//                    if(response.isSuccessful()){
//                        MyHttpClient.this.login_issuccessful=true;
//                        Log.d("kwwl","response.code()=="+response.code());
//                        Log.d("kwwl","response.message()=="+response.message());
//                        Log.d("kwwl","res=="+response.body().string());
//                    }else {
//                        MyHttpClient.this.login_issuccessful=false;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        //return MyHttpClient.this.login_issuccessful;
//        return true;
//    }

//    public boolean login_check(String user_name,String password){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient client = new OkHttpClient();
//
//                // 构建查询参数
//                HttpUrl.Builder urlBuilder = HttpUrl.parse(my_url+"login/").newBuilder();
//                urlBuilder.addQueryParameter("username", user_name);
//                urlBuilder.addQueryParameter("password", password);
//
//                // 构建完整的URL
//                String url = urlBuilder.build().toString();
//
//                // 构建请求
//                Request request = new Request.Builder()
//                        .url(url)
//                        .get()
//                        .build();
//
//                try {
//                    // 发送请求
//                    Response response = client.newCall(request).execute();
//                    boolean ok = response.isSuccessful();
//                    Log.d("kwwl","response=="+response);
//
//                    // 处理响应
//                    if (response.isSuccessful()) {
//                        String responseBody = Objects.requireNonNull(response.body()).string();
//                        // 使用responseBody
//                        if(responseBody.equals("登录成功！")){
//                            MyHttpClient.this.login_issuccessful=true;
//                        }
//                        Log.d("kwwl","responseBody=="+responseBody);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        return MyHttpClient.this.login_issuccessful;
//        //return true;
//    }
    public void login_check(String user_name,String password){
        OkHttpClient okHttpClient = new OkHttpClient();
        // 构建查询参数
        HttpUrl.Builder urlBuilder = HttpUrl.parse(my_url+"login/").newBuilder();
        urlBuilder.addQueryParameter("username", user_name);
        urlBuilder.addQueryParameter("password", password);
        // 构建完整的URL
        //String url = urlBuilder.build().toString();
        String url ="http://192.168.1.5:8000/login/?username=15723070964&password=78156";
        // 构建请求
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        //设置响应
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("---body","onResponse");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                Log.e("---body","onResponse"+body);


            }
        });
        //return this.login_issuccessful;
    }
}

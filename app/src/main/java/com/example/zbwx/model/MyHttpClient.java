package com.example.zbwx.model;


import static com.example.zbwx.MyApplication.my_url;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

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

    private static final String my_url="http://192.168.1.5:8000/";
    OkHttpClient okHttpClient;
    public MyHttpClient(){}
    //get方式发送请求，带1个参数的情况
    public MyHttpClient(Handler handler,String command,String param){
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(my_url + command)).newBuilder();
        urlBuilder.addQueryParameter("param", param);
        String url = urlBuilder.build().toString();  // 构建完整的URL
        Request request = new Request.Builder().url(url).get().build(); //构建请求
        okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {   //设置响应
            @Override    //响应失败进这里
            public void onFailure(@NonNull Call call, IOException e) {
                handler.sendEmptyMessage(0);
            }
            @Override     //响应成功进这里
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Message msg = new Message();
                msg.what=1;
                msg.obj=response.body().string();
                handler.sendMessage(msg);
            }
        });
    }
    //get方式发送请求，带2个参数的情况
    public MyHttpClient(Handler handler,String command,String param,String param1){
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(my_url + command)).newBuilder();
        urlBuilder.addQueryParameter("param", param);
        urlBuilder.addQueryParameter("param1", param1);
        String url = urlBuilder.build().toString();  // 构建完整的URL
        Request request = new Request.Builder().url(url).get().build(); //构建请求
        okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {   //设置响应
            @Override    //响应失败进这里
            public void onFailure(@NonNull Call call, IOException e) {
                Log.e("---body", "onResponse");
                handler.sendEmptyMessage(0);
            }

            @Override     //响应成功进这里
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Message msg = new Message();
                msg.what=1;
                msg.obj=response.body().string();
                handler.sendMessage(msg);
            }
        });
    }
    //get方式发送请求，带3个参数的情况
    public MyHttpClient(Handler handler,String command,String param,String param1,String param2){
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(my_url + command)).newBuilder();
        urlBuilder.addQueryParameter("param", param);
        urlBuilder.addQueryParameter("param1", param1);
        urlBuilder.addQueryParameter("param2", param2);
        String url = urlBuilder.build().toString();  // 构建完整的URL
        Request request = new Request.Builder().url(url).get().build(); //构建请求
        okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {   //设置响应
            @Override    //响应失败进这里
            public void onFailure(@NonNull Call call, IOException e) {
                Log.e("---body", "onResponse");
                handler.sendEmptyMessage(0);
            }

            @Override     //响应成功进这里
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Message msg = new Message();
                msg.what=1;
                msg.obj=response.body().string();
                handler.sendMessage(msg);
            }
        });
    }



}

package com.example.zbwx.model;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyHttpClient {

    private static final String my_url="http://192.168.216.215:8000/";
    OkHttpClient okHttpClient;
    public MyHttpClient(){}
    ////get方式发送请求，不带参数的情况
    public MyHttpClient(Handler handler,String command){
        Request request = new Request.Builder().url(Objects.requireNonNull(HttpUrl.parse(my_url + command))).get().build(); //构建请求
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
    public MyHttpClient(Handler handler,int msgwhat,String command){
        Request request = new Request.Builder().url(Objects.requireNonNull(HttpUrl.parse(my_url + command))).get().build(); //构建请求
        okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {   //设置响应
            @Override    //响应失败进这里
            public void onFailure(@NonNull Call call, IOException e) {
                handler.sendEmptyMessage(0);
            }
            @Override     //响应成功进这里
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Message msg = new Message();
                msg.what=msgwhat;
                msg.obj=response.body().string();
                handler.sendMessage(msg);
            }
        });
    }
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
    public MyHttpClient(Handler handler,int msgwhat,String command,String param){
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
                msg.what=msgwhat;
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
    public MyHttpClient(Handler handler,int msgwhat,String command,String param,String param1){
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
                msg.what=msgwhat;
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
    public MyHttpClient(Handler handler,int msgwhat,String command,String param,String param1,String param2){
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
                msg.what=msgwhat;
                msg.obj=response.body().string();
                handler.sendMessage(msg);
            }
        });
    }

    //POST方式
    public MyHttpClient(Handler handler,String command,JSONObject json){
        RequestBody requestBody = RequestBody.create(String.valueOf(json), MediaType.parse("application/json; charset=utf-8"));
        Request request=new Request.Builder()
                .url(Objects.requireNonNull(HttpUrl.parse(my_url + command)))
                .addHeader("key","value")
                .post(requestBody)
                .build();
        okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Message msg =new Message();
                msg.what=1;
                msg.obj=response.body().string();
                handler.sendMessage(msg);
            }
        });
    }
    public MyHttpClient(Handler handler,int msgwhat,String command,JSONObject json){
        RequestBody requestBody = RequestBody.create(String.valueOf(json), MediaType.parse(command+"json; charset=utf-8"));
        Request request=new Request.Builder()
                .url(Objects.requireNonNull(HttpUrl.parse(my_url + command)))
                .addHeader("key","value")
                .post(requestBody)
                .build();
        okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Message msg =new Message();
                msg.what=msgwhat;
                msg.obj=response.body().string();
                handler.sendMessage(msg);
            }
        });
    }

////////////////////////////////////////////////////////////////////////

    public void SendToServer(Handler handler,String command){
        Request request = new Request.Builder().url(Objects.requireNonNull(HttpUrl.parse(my_url + command))).get().build(); //构建请求
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
    public void SendToServer(Handler handler, int msgwhat, String command){
        Request request = new Request.Builder().url(Objects.requireNonNull(HttpUrl.parse(my_url + command))).get().build(); //构建请求
        okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {   //设置响应
            @Override    //响应失败进这里
            public void onFailure(@NonNull Call call, IOException e) {
                handler.sendEmptyMessage(0);
            }
            @Override     //响应成功进这里
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Message msg = new Message();
                msg.what=msgwhat;
                msg.obj=response.body().string();
                handler.sendMessage(msg);
            }
        });
    }
    //get方式发送请求，带1个参数的情况
    public void SendToServer(Handler handler,String command,String param){
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
    public void SendToServer(Handler handler,int msgwhat,String command,String param){
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
                msg.what=msgwhat;
                msg.obj=response.body().string();
                handler.sendMessage(msg);
            }
        });
    }
    //get方式发送请求，带2个参数的情况
    public void SendToServer(Handler handler,String command,String param,String param1){
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
    public void SendToServer(Handler handler,int msgwhat,String command,String param,String param1){
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
                msg.what=msgwhat;
                msg.obj=response.body().string();
                handler.sendMessage(msg);
            }
        });
    }
    //get方式发送请求，带3个参数的情况
    public void SendToServer(Handler handler,String command,String param,String param1,String param2){
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
    public void SendToServer(Handler handler,int msgwhat,String command,String param,String param1,String param2){
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
                msg.what=msgwhat;
                msg.obj=response.body().string();
                handler.sendMessage(msg);
            }
        });
    }

    //POST方式
    public void SendToServer(Handler handler,String command,JSONObject json){
        RequestBody requestBody = RequestBody.create(String.valueOf(json), MediaType.parse("application/json; charset=utf-8"));
        Request request=new Request.Builder()
                .url(Objects.requireNonNull(HttpUrl.parse(my_url + command)))
                .addHeader("key","value")
                .post(requestBody)
                .build();
        okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Message msg =new Message();
                msg.what=1;
                msg.obj=response.body().string();
                handler.sendMessage(msg);
            }
        });
    }
    public void SendToServer(Handler handler,int msgwhat,String command,JSONObject json){
        RequestBody requestBody = RequestBody.create(String.valueOf(json), MediaType.parse(command+"json; charset=utf-8"));
        Request request=new Request.Builder()
                .url(Objects.requireNonNull(HttpUrl.parse(my_url + command)))
                .addHeader("key","value")
                .post(requestBody)
                .build();
        okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Message msg =new Message();
                msg.what=msgwhat;
                msg.obj=response.body().string();
                handler.sendMessage(msg);
            }
        });
    }


}

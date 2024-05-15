package com.example.zbwx.model;

public class MyMsg {
    public int sender;  //发送者
    public int receiver;  //接收者
    public String datetime;  //消息时间
    public String title;  //标题
    public String body;   //消息内容
    public String state;    //消息状态

    public MyMsg(){
        this.sender=0;
        this.receiver=0;
        this.datetime="";
        this.title="";
        this.body="";
        this.state="";
    }


}

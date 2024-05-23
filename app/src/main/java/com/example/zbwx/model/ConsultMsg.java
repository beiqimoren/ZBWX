package com.example.zbwx.model;

public class ConsultMsg {
    public long time_stamp;   //时间戳
    public int type;   //0:客户端发送的，1：管理员发送的
    public String content;

    public ConsultMsg(){
        this.time_stamp=0;
        this.type=0;
        this.content="";
    }

}

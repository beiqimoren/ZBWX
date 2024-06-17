package com.example.zbwx.model;

public class Cmsg {
    public int id;   //id
    public int consultID;   //咨询主题的id
    public int type;   //0:客户端发送的，1：管理员发送的
    public String content;

    public Cmsg(){
        this.id=0;
        this.consultID=0;
        this.type=0;
        this.content="";
    }

}

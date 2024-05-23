package com.example.zbwx.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Consult {
    public int userID;
    public int adminID;
    public int consultID; //数据库列头自增ID
    public String title;
    public List<ConsultMsg> contentlist;//json数组字符串格式，内容为，datatime:xx type：xx  content:xx

    public Consult(){
        this.userID=0;
        this.adminID=0;
        this.consultID=0;
        this.title="";
        this.contentlist=new ArrayList<>();
    }

}

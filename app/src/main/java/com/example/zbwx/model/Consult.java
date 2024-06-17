package com.example.zbwx.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Consult {
    public int userID;
    public int adminID;
    public int consultID; //数据库列头自增ID
    public String title;
    public String firstcontent;

    public Consult(){
        this.userID=0;
        this.adminID=0;
        this.consultID=0;
        this.title="";
        this.firstcontent="";
    }

}

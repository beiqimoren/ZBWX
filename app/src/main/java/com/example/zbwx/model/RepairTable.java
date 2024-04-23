package com.example.zbwx.model;

import com.example.zbwx.MyApplication;

public class RepairTable {
    public int table_ID;  //表单ID
    public int user_ID;  //当前账户ID
    public String equipment;//装备名
    public String type;//装备型号
    public String province;//省份
    public String city;//城市
    public String address;//详细地址
    public String fault;//故障描述
    public String contacts;//联系人
    public String phone;//电话
    public String unit;//单位
    public String state;//表单状态
    public String notes;//备注


    //构造函数
    public RepairTable(){
        this.table_ID=0;
        this.user_ID=0;
        equipment="";
        type="";
        province="";
        city="";
        address="";
        fault="";
        contacts="";
        phone="";
        unit="";
        state="";
        notes="";
    }



}

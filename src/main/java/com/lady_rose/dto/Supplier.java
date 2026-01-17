package com.lady_rose.dto;

import com.lady_rose.util.CrudUtil;

import java.sql.SQLException;
import java.time.LocalDate;

public class Supplier {
    private String s_ID;
    private String name;
    private String item;
    private String conStart_Date;
    private String conEnd_Date;

    public Supplier(String string, String resultSetString, String setString, String s, String string1, String resultSetString1){
    }

    public Supplier(String s_ID,String name,String item,String conStart_Date,String conEnd_Date){
        this.s_ID=s_ID;
        this.name=name;
        this.item=item;
        this.conStart_Date=conStart_Date;
        this.conEnd_Date=conEnd_Date;
    }

    public String getS_ID() {
        return s_ID;
    }
    public void setS_ID(String s_ID) {
        this.s_ID = s_ID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getItemList() {
        return item;
    }
    public void setItemList(String itemList) {
        this.item = item;
    }

    public String getConStart_Date() {
        return conStart_Date;
    }

    public void setConStart_Date(String conStart_Date) {
        this.conStart_Date = conStart_Date;
    }

    public String getConEnd_Date() {
        return conEnd_Date;
    }

    public void setConEnd_Date(String conEnd_Date) {
        this.conEnd_Date = conEnd_Date;
    }
}

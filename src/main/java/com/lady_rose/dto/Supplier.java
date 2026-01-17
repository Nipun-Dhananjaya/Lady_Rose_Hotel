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

    public static boolean addSupplier(String ID, String name, String address, String email, String contact, String item, LocalDate conStart_Date, LocalDate conEnd_Date){
        try{
            boolean isAffected = CrudUtil.execute("INSERT INTO Supplier VALUES(?,?,?,?,?,?,?,?,?,?,?,?);", ID, name,
                    address, email, contact, item,conStart_Date,conEnd_Date);
            if (isAffected){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateSupplier(String ID, String name, String address, String email, String contact, String item, LocalDate conStart_Date, LocalDate conEnd_Date) {
        try {
            boolean isAffected =CrudUtil.execute("UPDATE supplier SET sup_name=?,sup_address=?,sup_email=?,sup_contact=?,sup_item=?,conStart_date=?,conEnd_date=? WHERE sup_id=?;", name,
                     address, email, contact, conStart_Date, conEnd_Date,ID);
            if (isAffected){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
}

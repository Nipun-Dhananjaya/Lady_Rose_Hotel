package com.lady_rose.dto;

import java.util.ArrayList;

public class Customer extends Person{
    private String category;
    public static ArrayList<String> contact = new ArrayList<>();

    public Customer(String string, String resultSetString, String setString, String s, String string1, String resultSetString1, String setString1, String s1, String string2, String resultSetString2, String setString2, String s2){

    }

    public Customer(String pId,String name,String phoneNumber,String emailAddress,String address,String gender,String nic,String category){
        super(pId, name, phoneNumber, emailAddress, address, gender, nic);
        this.category=category;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

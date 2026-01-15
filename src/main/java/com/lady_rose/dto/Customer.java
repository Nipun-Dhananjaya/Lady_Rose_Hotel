package com.lady_rose.dto;

public class Customer extends Person{
    private String category;

    public Customer(){

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

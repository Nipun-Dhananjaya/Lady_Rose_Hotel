package com.lady_rose.dto;

public class Packages {
    private String packId;
    private String type;
    private double price;

    public Packages(){

    }
    public Packages(String packId,String type,double price){
        this.packId=packId;
        this.type=type;
        this.price=price;
    }

    public String getPackId() {
        return packId;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public void setPackId(String packId) {
        this.packId = packId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.lady_rose.dto;

public class Restaurant_Item {
    private String item_ID;
    private String name;
    private double itemUnitPrice;

    public Restaurant_Item(){
    }

    public Restaurant_Item(String item_ID,String name,double itemUnitPrice){
        this.item_ID=item_ID;
        this.name=name;
        this.itemUnitPrice=itemUnitPrice;
    }

    public String getItem_ID() {
        return item_ID;
    }

    public void setItem_ID(String item_ID) {
        this.item_ID = item_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(double itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }
}

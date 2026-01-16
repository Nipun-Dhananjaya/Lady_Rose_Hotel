package com.lady_rose.dto;

public class Item {
    private String item_ID;
    private String name;
    private double qty;
    private double qtyOnHand;

    public Item(){
    }

    public Item(String item_ID, String name, double qty, double qtyOnHand){
        this.item_ID=item_ID;
        this.name=name;
        this.qty=qty;
        this.qtyOnHand=qtyOnHand;
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

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(double qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }
}

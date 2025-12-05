package com.lady_rose.model;

public class Store {
    private String item_ID;
    private String name;
    private double qty;
    private double itemUnitPrice;
    private String stockedDate;
    private String orderedDate;
    private String preOrderDate;
    private double preOrderQty;

    public Store(){
    }

    public Store(String item_ID,String name,double qty,double itemUnitPrice,String stockedDate,String orderedDate,String preOrderDate,double preOrderQty){
        this.item_ID=item_ID;
        this.name=name;
        this.qty=qty;
        this.itemUnitPrice=itemUnitPrice;
        this.stockedDate=stockedDate;
        this.orderedDate=orderedDate;
        this.preOrderDate=preOrderDate;
        this.preOrderQty=preOrderQty;
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

    public double getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(double itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    public String getStockedDate() {
        return stockedDate;
    }

    public void setStockedDate(String stockedDate) {
        this.stockedDate = stockedDate;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public String getPreOrderDate() {
        return preOrderDate;
    }

    public void setPreOrderDate(String preOrderDate) {
        this.preOrderDate = preOrderDate;
    }

    public double getPreOrderQty() {
        return preOrderQty;
    }

    public void setPreOrderQty(double preOrderQty) {
        this.preOrderQty = preOrderQty;
    }
}

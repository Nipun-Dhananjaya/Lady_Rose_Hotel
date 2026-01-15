package com.lady_rose.model;

import com.lady_rose.util.CrudUtil;

import java.sql.SQLException;
import java.time.LocalDate;

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

    public static boolean addRestaurantItem(String item_ID,String name,double itemUnitPrice){
        try {
            boolean isAffected = CrudUtil.execute("INSERT INTO Restaurant Item VALUES(?,?,?,?,?,?,?,?,?,?,?,?);", item_ID, name,
                    itemUnitPrice);
            if (isAffected){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateRestaurantItem(String item_ID,String name,double itemUnitPrice) {
        try {
            boolean isAffected =CrudUtil.execute("UPDATE Restaurant Item SET item_name=?,item_unit_price=? WHERE item_id=?;", name,
                    itemUnitPrice,item_ID);
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

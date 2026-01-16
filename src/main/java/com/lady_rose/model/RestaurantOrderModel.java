package com.lady_rose.model;

import com.lady_rose.util.CrudUtil;

import java.sql.SQLException;
import java.time.LocalDate;

public class RestaurantOrderModel {
    public static boolean addRestaurantOrder(String orderID, double quantity, String items, LocalDate billedDate, String billedTime){
        try {
            boolean isAffected = CrudUtil.execute("INSERT INTO RestaurantOrder VALUES(?,?,?,?,?,?,?,?,?,?,?,?);", orderID, quantity,items,
                    billedDate, billedTime );
            if (isAffected){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateRestaurantOrder(String orderID, double quantity, String items, LocalDate billedDate, String billedTime) {
        try {
            boolean isAffected =CrudUtil.execute("UPDATE Restaurant Order SET order_qty=?,order_item=?,billed_date=?,billed_time=? WHERE order_id=?;",
                    orderID,quantity,items,billedDate,billedTime);
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

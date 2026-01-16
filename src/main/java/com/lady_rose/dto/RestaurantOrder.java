package com.lady_rose.dto;


import com.lady_rose.util.CrudUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

        public class RestaurantOrder {
        private String orderID;
        private double quantity;
        private String billedDateAndTime;
        private ArrayList<String> itemsNoList;
        private ArrayList<Double> itemQtyList;

        // Constructor
        public RestaurantOrder(){}
        public RestaurantOrder(String orderID, double quantity, String billedDateAndTime,
                               ArrayList<String> itemsNoList, ArrayList<Double> itemQtyList) {
            this.orderID = orderID;
            this.quantity = quantity;
            this.billedDateAndTime = billedDateAndTime;
            this.itemsNoList = itemsNoList;
            this.itemQtyList = itemQtyList;
        }

        // Getters
        public String getOrderID() {
            return orderID;
        }

        public double getQuantity() {
            return quantity;
        }

        public String getBilledDateAndTime() {
            return billedDateAndTime;
        }

        public ArrayList<String> getItemsNoList() {
            return itemsNoList;
        }

        public ArrayList<Double> getItemQtyList() {
            return itemQtyList;
        }

        // Setters
        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }

        public void setBilledDateAndTime(String billedDateAndTime) {
            this.billedDateAndTime = billedDateAndTime;
        }

        public void setItemsNoList(ArrayList<String> itemsNoList) {
            this.itemsNoList = itemsNoList;
        }

        public void setItemQtyList(ArrayList<Double> itemQtyList) {
            this.itemQtyList = itemQtyList;
        }

        public static boolean addRestaurantOrder(String orderID, double quantity, String items, LocalDate billedDate, String billedTime){
            try {
                boolean isAffected = CrudUtil.execute("INSERT INTO RestaurantOrder VALUES(?,?,?,?,?,?,?,?,?,?,?,?);", orderID, quantity,items,
                        billedDate, billedTime);
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



package com.lady_rose.dto;


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
    }



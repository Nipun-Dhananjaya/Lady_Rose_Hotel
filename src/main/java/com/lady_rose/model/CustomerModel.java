package com.lady_rose.model;

import com.lady_rose.dto.Customer;
import com.lady_rose.dto.Employee;
import com.lady_rose.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {
    public static int stringLength=5;
    public static String generateID() {
        ResultSet result=null;
        String[] idParts;
        String id="C-00000";
        try {
            result= CrudUtil.execute("SELECT ID FROM Customer ORDER BY ID DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            idParts=id.split("-");
            System.out.println(idParts.length);
            int number=Integer.parseInt(idParts[1]);
            String num=setNextIdValue(++number);
            return "C-"+num;
        } catch (SQLException e) {
            return "C-00000";
        }
    }

    private static String setNextIdValue(int number) {
        String returnVal="";
        int length=String.valueOf(number).length();
        if(length<stringLength){
            int difference=stringLength-length;
            for (int i = 0; i < difference; i++) {
                returnVal+="0";
            }
            returnVal+=String.valueOf(number);
            return returnVal;
        }
        return String.valueOf(number);
    }

    public static boolean addCustomer(String id, String name, String nic, String address, String email, String contact, String gender, String category) {
        try {
            boolean isAffected = CrudUtil.execute("INSERT INTO Customer VALUES(?,?,?,?,?,?,?,?);", id, nic, name,
                    gender,address, email, contact,category);
            if (isAffected){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateCustomer(String id, String name, String nic, String address, String email, String contact, String gender, String category) {
        try {
            boolean isAffected = CrudUtil.execute(
                    "UPDATE Customer SET Name=?, NIC=?, Address=?, Email=?, Contact=?, Gender=?, Category=? WHERE ID=?;",
                    name, nic, address, email, contact, gender, category, id
            );
            return isAffected;
        } catch (SQLException e) {
            return false;
        }
    }


    public static List<Customer> searchCustomer(String Id) throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM Customer WHERE cus_id=?;",Id);
        List<Customer> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Customer(
                    resultSet.getString(1), // pId → cus_id
                    resultSet.getString(3), // name
                    resultSet.getString(7), // phoneNumber → contact
                    resultSet.getString(6), // emailAddress → email
                    resultSet.getString(5), // address
                    resultSet.getString(4), // gender
                    resultSet.getString(2), // nic
                    resultSet.getString(8)  // category
            ));
        }
        return data;
    }

    public static List<Customer> getAll() throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM Customer ORDER BY ID;");
        List<Customer> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Customer(
                    resultSet.getString(1), // pId → cus_id
                    resultSet.getString(3), // name
                    resultSet.getString(7), // phoneNumber → contact
                    resultSet.getString(6), // emailAddress → email
                    resultSet.getString(5), // address
                    resultSet.getString(4), // gender
                    resultSet.getString(2), // nic
                    resultSet.getString(8)  // category
            ));
        }
        return data;
    }
}

package com.lady_rose.model;

import com.lady_rose.dto.Item;
import com.lady_rose.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.lady_rose.model.EmployeeModel.stringLength;

public class ItemModel {
    public static List<Item> getAll() throws SQLException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM store ORDER BY Item_ID;");
        List<Item> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            ));
        }
        return data;
    }

    public static String generateID() {
        ResultSet result=null;
        String[] idParts;
        String id="Item-00000";
        try {
            result= CrudUtil.execute("SELECT Item_ID FROM store ORDER BY Item_ID DESC LIMIT 1;");
            if(result.next()) {
                id=result.getString(1);
            }
            idParts=id.split("-");
            int number=Integer.parseInt(idParts[1]);
            String num=setNextIdValue(++number);
            return "Item-"+num;
        } catch (SQLException e) {
            return "Item-00000";
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

    public static boolean addItem(String itemCode, String description,double qty) {
        try {
            boolean isAffected =CrudUtil.execute("INSERT INTO store VALUES(?,?,?);", itemCode,description,qty);
            if (isAffected){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateItem(String itmCode, String description,double qty) {
        try {
            boolean isAffected =CrudUtil.execute("UPDATE store SET Name=?,qtyOnHand=? WHERE Item_ID=?;", description,qty, itmCode);
            if (isAffected){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeItem(String itemCode) {
        try {
            boolean isAffected =CrudUtil.execute("DELETE FROM store WHERE item_ID=?;", itemCode);
            if (isAffected){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Item> searchItem(String itemCode) throws SQLException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM store WHERE item_ID=?;",itemCode);
        List<Item> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            ));
        }
        return data;
    }
}
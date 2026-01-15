package com.lady_rose.model;

import com.lady_rose.dto.Item;
import com.lady_rose.dto.Restaurant_Item;
import com.lady_rose.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.lady_rose.model.EmployeeModel.stringLength;

public class RestaurantItemModel {
    public static List<Restaurant_Item> getAll() throws SQLException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM item ORDER BY item_code;");
        List<Restaurant_Item> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Restaurant_Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return data;
    }

    public static String generateID() {
        ResultSet result=null;
        String[] idParts;
        String id="Item-00000";
        try {
            result= CrudUtil.execute("SELECT item_code FROM item ORDER BY item_code DESC LIMIT 1;");
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

    public static boolean addRestaurantItem(String itemCode, String description) {
        try {
            if (!(name.matches("^[a-zA-Z][ ]*$") | (!email.matches("^(.+)@(\\S+) $")) | (nic.matches()))){

            }
            boolean isAffected =CrudUtil.execute("INSERT INTO item VALUES(?,?);", itemCode,description);
            if (isAffected){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateRestaurantItem(String itmCode, String description) {
        try {
            boolean isAffected =CrudUtil.execute("UPDATE item SET description=? WHERE item_code=?;", description, itmCode);
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

    public static boolean removeRestaurantItem(String itemCode) {
        try {
            boolean isAffected = CrudUtil.execute("DELETE FROM item WHERE item_code=?;", itemCode);
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
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM item WHERE item_code=?;",itemCode);
        List<Item> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }
        return data;
    }
}

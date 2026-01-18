package com.lady_rose.model;

import com.lady_rose.dto.Supplier;
import com.lady_rose.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public static boolean addSupplier(String ID, String name, String address, String email, String contact, String item, LocalDate conStart_Date, LocalDate conEnd_Date){
        try{
            boolean isAffected = CrudUtil.execute("INSERT INTO supplier VALUES(?,?,?,?,?,?,?,?);", ID, name,
                    address, email, contact, item,conStart_Date,conEnd_Date);
            if (isAffected){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateSupplier(String ID, String name, String address, String email, String contact, String item, LocalDate conStart_Date, LocalDate conEnd_Date) {
        try {
            boolean isAffected =CrudUtil.execute("UPDATE supplier SET Name=?,Address=?,Email=?,Contact=?,Item=?,C_Start_date=?,C_End_date=? WHERE ID=?;", name,
                    address, email, contact, conStart_Date, conEnd_Date,ID);
            if (isAffected){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }


    public static List<Supplier> searchSupplier(String S_Id) throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM supplier WHERE ID=?;",S_Id);
        List<Supplier> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return data;
    }

    public static List<Supplier> getAll() throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM supplier ORDER BY ID;");
        List<Supplier> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)

            ));
        }
        return data;
    }
}

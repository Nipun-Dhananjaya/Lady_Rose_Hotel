package com.lady_rose.model;

import com.lady_rose.dto.Employee;
import com.lady_rose.dto.Room;
import com.lady_rose.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomModel {
    public static boolean addRoom(String R_no, Object category, String bedCount, Object roomView){
        try{
            boolean isAffected = CrudUtil.execute("INSERT INTO Room VALUES(?,?,?,?,?,?,?,?,?,?,?,?);", R_no,category,bedCount,roomView);
            if (isAffected){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateRoom(String R_no, Object category, String bedCount, Object roomView) {
        try {
            boolean isAffected =CrudUtil.execute("UPDATE room SET Obj_category=?,r_bedCount=?,Obj_view=? WHERE r_no=?;",bedCount,roomView , category,R_no);
            if (isAffected){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    /*public static List<Room> searchRoom(String R_No) throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM room WHERE emp_id=?;",R_No);
        List<Room> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Room(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            ));
        }
        return data;
    }*/

    /*public static List<Room> getAll() throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM room ORDER BY R_no;");
        List<Room> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Room(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            ));
        }
        return data;
    }*/
}

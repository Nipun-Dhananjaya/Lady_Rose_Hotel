package com.lady_rose.dto;

import com.lady_rose.util.CrudUtil;
import jdk.jfr.Category;

import java.sql.SQLException;
import java.time.LocalDate;

public class Room {
    private String R_No;
    private String Category;
    private String pckgeList;
    private int bedCount;

    public Room(){
    }

    public Room(String R_No,String Category,String packageList,int bedCount){
        this.R_No=R_No;
        this.Category=Category;
        this.pckgeList=pckgeList;
        this.bedCount=bedCount;
    }

    public String getR_No(){
        return R_No;
    }

    public void setR_No(String R_No){
        this.R_No=R_No;
    }

    public String getCategory(){
        return Category;
    }

    public void setCategory(String Category){
        this.Category=Category;
    }

    public String getPckgeList(){
        return pckgeList;
    }

    public void setPckgeList(String pckgeList){
        this.pckgeList=pckgeList;
    }

    public int getBedCount (){
        return bedCount;
    }

    public void setBedCount(int bedCount){
        this.bedCount=bedCount;
    }

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

}

package com.lady_rose.model;

public class Room {
    private String R_No;
    private String Category;
    private String[] pckgeList;
    private int bedCount;

    public Room(){
    }

    public Room(String R_No,String Category,String[] packageList,int bedCount){
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

    public String[] getPckgeList(){
        return pckgeList;
    }

    public void setPckgeList(String[] pckgeList){
        this.pckgeList=pckgeList;
    }

    public int getBedCount (){
        return bedCount;
    }

    public void setBedCount(int bedCount){
        this.bedCount=bedCount;
    }

}

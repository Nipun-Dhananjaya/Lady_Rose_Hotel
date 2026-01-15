package com.lady_rose.dto;

import java.util.ArrayList;

public class RoomPackage extends Packages{
    private int bedCount;
    private boolean acOrnonAc;
    private String view;
    private boolean wifi;
    private ArrayList <String> meal;

    public RoomPackage(){

    }

    public RoomPackage(String packId,String type,double price,int bedCount,boolean acOrnonAc,String view,boolean wifi,ArrayList <String> meal){
        super(packId,type,price);
        this.acOrnonAc=acOrnonAc;
        this.bedCount=bedCount;
        this.wifi=wifi;
        this.view=view;
        this.meal=meal;
    }

    public int getBedCount() {
        return bedCount;
    }
    public boolean getAcOrnonAc(){
        return acOrnonAc;
    }

    public String getView() {
        return view;
    }
    public boolean getWifi(){
        return wifi;
    }
    public ArrayList<String> getMeal() {
        return meal;
    }

    public void setAcOrnonAc(boolean acOrnonAc) {
        this.acOrnonAc = acOrnonAc;
    }

    public void setMeal(ArrayList<String> meal) {
        this.meal = meal;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setBedCount(int bedCount) {
        this.bedCount = bedCount;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }
}
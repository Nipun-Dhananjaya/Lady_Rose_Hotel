package com.lady_rose.model;

import com.lady_rose.controller.ChangeSpaPacksFormController;
import com.lady_rose.db.DBConnection;
import com.lady_rose.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChangeSpaPackModel {
    public static void setArrayList() throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            ResultSet resultSet = CrudUtil.execute("SELECT packName FROM spaPackage;");
            ArrayList<String> tempList=new ArrayList<>();
            while (resultSet.next()) {
                tempList.add(resultSet.getString(1));
            }
            for (int i = 0; i < tempList.size(); i++) {
                if (!(ChangeSpaPacksFormController.spaPacks.contains(tempList.get(i)))){
                    ChangeSpaPacksFormController.spaPacks.add(tempList.get(i));
                }else{
                    continue;
                }
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public static String getPackageId(String pack) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            ResultSet resultSet = CrudUtil.execute("SELECT pack_Id FROM spaPackage WHERE packName=?;",pack);
            ArrayList<String> tempList=new ArrayList<>();
            while (resultSet.next()) {
                tempList.add(resultSet.getString(1));
            }
            DBConnection.getInstance().getConnection().commit();
            if(!tempList.isEmpty()){
                return tempList.get(0);
            }else{
                return "No Packs Available";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
            return "No Packs Available";
        }
    }

    public static boolean changePakage(String packageId,String packageNewPrice) throws SQLException {
        boolean isUpdated=CrudUtil.execute("UPDATE spaPackage SET price=? WHERE pack_No=?;",packageNewPrice,packageId);

        if (isUpdated){
            return true;
        }else{
            return false;
        }
    }
}

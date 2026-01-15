package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.model.Employee;
import com.lady_rose.model.Restaurant_Item;
import com.lady_rose.regex.RegExPattern;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

public class RestaurantItemManageFormController {
    public AnchorPane itemRoot;
    public TextField idTxt;
    public TextField nameTxt;
    public TableView itmTbl;
    public Button addBtn;
    public Button updateBtn;
    public Button removeBtn;
    public TextField unitPriceTxt;

    public void idSearchOnAction(ActionEvent actionEvent) {
    }

    public void addItemOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected= Restaurant_Item.addRestaurantItem(idTxt.getId(),nameTxt.getText(), Double.parseDouble(unitPriceTxt.getText()));
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Employer Added!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
            } else {
                new Alert(Alert.AlertType.WARNING, "Re-Check Submitted Details!").showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    private boolean isCorrectPattern(){
        if(RegExPattern.getIdPattern().matcher(idTxt.getId()).matches() && RegExPattern.getNamePattern().matcher(nameTxt.getText()).matches() && RegExPattern.getDoublePattern().matcher(unitPriceTxt.getText()).matches()){
            return true;
        }
        return false;
    }

    public void updateItemOnAction(ActionEvent actionEvent) throws SQLException {
            try {
                DBConnection.getInstance().getConnection().setAutoCommit(false);
                boolean isAffected=false;
                if (isCorrectPattern()){
                    isAffected= Restaurant_Item.addRestaurantItem(idTxt.getId(),nameTxt.getText(), Double.parseDouble(unitPriceTxt.getText()));
                }
                if (isAffected) {
                    new Alert(Alert.AlertType.INFORMATION, "Restaurant Item Updated!").showAndWait();
                    DBConnection.getInstance().getConnection().commit();
                    idTxt.setDisable(false);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Re-Check Submitted Details!").showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                DBConnection.getInstance().getConnection().rollback();
            }finally{
                DBConnection.getInstance().getConnection().setAutoCommit(true);
            }
        }


    public void removeItemOnAction(ActionEvent actionEvent) {
    }
}

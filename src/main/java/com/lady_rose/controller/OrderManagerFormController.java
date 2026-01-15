package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.model.Employee;
import com.lady_rose.model.RestaurantOrder;
import com.lady_rose.regex.RegExPattern;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.sql.SQLException;

public class OrderManagerFormController {
    public Pane orderPane;
    public TextField orderIdTxt;
    public TextField itemTxt;
    public TextField quantityTxt;
    public DatePicker billedDateTxt;
    public TextField billedTimeTxt;
    public TableView orderTbl;
    public Button addBtn;
    public Button updateBtn;

    public void idSearchOnAction(ActionEvent actionEvent) {
    }

    public void addOrderOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected= RestaurantOrder.addRestaurantOrder(orderIdTxt.getId(), Double.parseDouble(quantityTxt.getText()),itemTxt.getText(),billedDateTxt.getValue(),billedTimeTxt.getText());
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Restaurant Order Added!").showAndWait();
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
        if(RegExPattern.getIdPattern().matcher(orderIdTxt.getId()).matches() && RegExPattern.getDoublePattern().matcher(quantityTxt.getText()).matches() && RegExPattern.getNamePattern().matcher(itemTxt.getText()).matches()){
            return true;
        }
        return false;
    }

    public void updateDetailsOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected= RestaurantOrder.updateRestaurantOrder(orderIdTxt.getId(), Double.parseDouble(quantityTxt.getText()),itemTxt.getText(),billedDateTxt.getValue(),billedTimeTxt.getText());
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Restaurant Order Updated!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                orderIdTxt.setDisable(false);
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
}

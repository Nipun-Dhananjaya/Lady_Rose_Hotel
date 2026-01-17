package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.model.ChangeRoomPackModel;
import com.lady_rose.regex.RegExPattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.ArrayList;

public class ChangeRoomPacksFormController {
    public AnchorPane changeRoomPackRoot;
    public TextField txtNewPrice;
    public Button changePriceBtn;
    public ComboBox packagesCmb;
    public TextField txtSleepCount;

    public static ArrayList<String> roomPrices = new ArrayList<>();

    public void initialize() throws SQLException {
        txtNewPrice.setDisable(true);
        txtSleepCount.setDisable(true);
        ChangeRoomPackModel.setArrayList();
        ObservableList<String> roomPack = FXCollections.observableList(roomPrices);
        packagesCmb.setItems(roomPack);
    }
    public void changePriceOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()) {
                isAffected = ChangeRoomPackModel.changePakage(String.valueOf(packagesCmb.getSelectionModel().getSelectedItem()),txtNewPrice.getText());
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Package Updated!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                resetPage();
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

    private void resetPage() throws SQLException {
        roomPrices.clear();
        ChangeRoomPackModel.setArrayList();
        ObservableList<String> roomPack = FXCollections.observableList(roomPrices);
        packagesCmb.setItems(roomPack);
        txtSleepCount.setText("");
        txtNewPrice.setText("");
    }

    public void selectPriceOnAction(ActionEvent actionEvent) throws SQLException {
        txtNewPrice.setDisable(false);
        String price= String.valueOf(packagesCmb.getSelectionModel().getSelectedItem());
        txtSleepCount.setText(ChangeRoomPackModel.getSleepCount(price));
        if (txtSleepCount.getText().equals("No Rooms Available")){
            txtNewPrice.setDisable(true);
        }
    }
    private boolean isCorrectPattern(){
        if (RegExPattern.getDoublePattern().matcher(txtNewPrice.getText()).matches()){
            return true;
        }
        return false;
    }
}

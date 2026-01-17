package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.model.ChangeRoomPackModel;
import com.lady_rose.model.ChangeSpaPackModel;
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

public class ChangeSpaPacksFormController {
    public AnchorPane changeSpaPackRoot;
    public TextField txtNewPrice;
    public Button changePriceBtn;
    public ComboBox packagesCmb;
    public TextField txtPackId;

    public static ArrayList<String> spaPacks = new ArrayList<>();

    public void initialize() throws SQLException {
        txtNewPrice.setDisable(true);
        ChangeSpaPackModel.setArrayList();
        ObservableList<String> spaPack = FXCollections.observableList(spaPacks);
        packagesCmb.setItems(spaPack);
    }
    public void changePriceOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()) {
                isAffected = ChangeSpaPackModel.changePakage(String.valueOf(packagesCmb.getSelectionModel().getSelectedItem()),txtNewPrice.getText());
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
        spaPacks.clear();
        ChangeSpaPackModel.setArrayList();
        ObservableList<String> spaPack = FXCollections.observableList(spaPacks);
        packagesCmb.setItems(spaPack);
        txtNewPrice.setText("");
    }

    public void selectPriceOnAction(ActionEvent actionEvent) throws SQLException {
        txtNewPrice.setDisable(false);
        String pack= String.valueOf(packagesCmb.getSelectionModel().getSelectedItem());
        txtPackId.setText(ChangeSpaPackModel.getPackageId(pack));
        if (txtPackId.getText().equals("No Packs Available")){
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

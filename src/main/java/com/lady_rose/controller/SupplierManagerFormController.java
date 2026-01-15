package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.model.Employee;
import com.lady_rose.model.Supplier;
import com.lady_rose.regex.RegExPattern;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.sql.SQLException;

public class SupplierManagerFormController {
    public Pane ancrpane;
    public TextField supplierTxt;
    public TextField snameTxt;
    public DatePicker sDteTxt;
    public DatePicker eDtTxt;
    public TextField sitemTxt;
    public TableView supplierTable;
    public TextField saddrsTxt;
    public TextField smilTxt;
    public TextField scontctTxt;
    public Button addBtn;
    public Button updtebtn;

    public void idSearchOnAction(ActionEvent actionEvent) {
    }

    public void addSupplierOnAction(ActionEvent actionEvent) throws SQLException {
        try{
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected= Supplier.addSupplier(supplierTxt.getId(),snameTxt.getText(),saddrsTxt.getText(),smilTxt.getText(),scontctTxt.getText(),sitemTxt.getText(),sDteTxt.getValue(),eDtTxt.getValue());
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Added!").showAndWait();
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
        if(RegExPattern.getIdPattern().matcher(supplierTxt.getId()).matches() && RegExPattern.getNamePattern().matcher(snameTxt.getText()).matches() && RegExPattern.getAddressPattern().matcher(saddrsTxt.getText()).matches() && RegExPattern.getEmailPattern().matcher(smilTxt.getText()).matches()){
            return true;
        }
        return false;
    }

    public void updateSupplierOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected= Supplier.updateSupplier(supplierTxt.getId(),snameTxt.getText(),saddrsTxt.getText(),smilTxt.getText(),scontctTxt.getText(),sitemTxt.getText(),sDteTxt.getValue(),eDtTxt.getValue());
            }

            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Updated!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                supplierTxt.setDisable(false);
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

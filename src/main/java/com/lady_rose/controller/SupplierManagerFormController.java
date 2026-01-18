package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.dto.Supplier;
import com.lady_rose.model.SupplierModel;
import com.lady_rose.regex.RegExPattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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
    public TableColumn columnID;
    public TableColumn columnName;
    public TableColumn columnAddress;
    public TableColumn columnEmail;
    public TableColumn columnContact;
    public TableColumn columnItem;
    public TableColumn columnC_Start_date;
    public TableColumn columnC_End_date;

    public void initialize() throws SQLException {
        setCellValueFactory();
        getAllSuppliers();
    }

    private void getAllSuppliers() throws SQLException {
        ObservableList<Supplier> obList = FXCollections.observableArrayList();
        List<Supplier> supList = SupplierModel.getAll();

        for (Supplier supplier : supList) {
            obList.add(new Supplier(
                    supplier.getS_ID(),
                    supplier.getName(),
                    supplier.getAddress(),
                    supplier.getEmail(),
                    supplier.getContact(),
                    supplier.getItemList(),
                    supplier.getConStart_Date(),
                    supplier.getConEnd_Date()
            ));
        }
        supplierTable.setItems(obList);
    }
    void setCellValueFactory() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("s_ID"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        columnItem.setCellValueFactory(new PropertyValueFactory<>("item"));
        columnC_Start_date.setCellValueFactory(new PropertyValueFactory<>("conStart_Date"));
        columnC_End_date.setCellValueFactory(new PropertyValueFactory<>("conEnd_Date"));
    }

    public void idSearchOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            List<Supplier> supList = SupplierModel.searchSupplier(supplierTxt.getText());
            if (!supList.isEmpty()){
                for (Supplier supplier : supList) {
                    snameTxt.setText(supplier.getName());
                    saddrsTxt.setText(supplier.getAddress());
                    smilTxt.setText(supplier.getEmail());
                    scontctTxt.setText(supplier.getContact());
                    sitemTxt.setText(supplier.getItemList());
                    sDteTxt.setValue(LocalDate.parse(supplier.getConStart_Date()));
                    eDtTxt.setValue(LocalDate.parse(supplier.getConEnd_Date()));
                }
                supplierTxt.setDisable(true);
            }else{
                new Alert(Alert.AlertType.WARNING, "Supplier ID Not Found!").showAndWait();
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void addSupplierOnAction(ActionEvent actionEvent) throws SQLException {
        try{
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected= SupplierModel.addSupplier(supplierTxt.getId(),snameTxt.getText(),saddrsTxt.getText(),smilTxt.getText(),scontctTxt.getText(),sitemTxt.getText(),sDteTxt.getValue(),eDtTxt.getValue());
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
                isAffected= SupplierModel.updateSupplier(supplierTxt.getId(),snameTxt.getText(),saddrsTxt.getText(),smilTxt.getText(),scontctTxt.getText(),sitemTxt.getText(),sDteTxt.getValue(),eDtTxt.getValue());
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

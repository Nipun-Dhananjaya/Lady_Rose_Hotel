package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.model.Employee;
import com.lady_rose.regex.RegExPattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

public class EmployerManageFormController {
    public AnchorPane employerRoot;
    public TextField idTxt;
    public TextField nameTxt;
    public TextField nicTxt;
    public TextField emailTxt;
    public TextField addressTxt;
    public RadioButton maleRdBtn;
    public ToggleGroup gender;
    public RadioButton femaleRdBtn;
    public TableView empTbl;
    public Button addBtn;
    public TextField jobRolTxt;
    public DatePicker endDtPkr;
    public DatePicker strtDtPck;
    public DatePicker dobDtPck;
    public TextField empSalary;
    public Button updateBtn;
    public TextField contactTxt;
    public Button addContactBtn;
    public ComboBox contactCmbBx;
    static String con;

    public void idSearchOnAction(ActionEvent actionEvent) {
    }

    public void addEmployerOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected= Employee.addEmployee(Employee.generateID(), nameTxt.getText(), nicTxt.getText(),
                        addressTxt.getText(), emailTxt.getText(), String.join(" , ", Employee.contact),dobDtPck.getValue(),
                        maleRdBtn.isSelected() ? "MALE" : "FEMALE", jobRolTxt.getText(),empSalary.getText(),strtDtPck.getValue(),endDtPkr.getValue());
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
    public void addContactOnAction(ActionEvent actionEvent) {
        if (contactCmbBx.getItems().contains(con)) {
            Employee.contact.remove(con);
        }
        if (RegExPattern.getMobilePattern().matcher(contactTxt.getText()).matches()){
            boolean isAlreadyHas = Employee.addContact(contactTxt.getText());
            if (!isAlreadyHas) {
                new Alert(Alert.AlertType.WARNING, "Contact Already Added!").showAndWait();
            } else {
                ObservableList<String> cont = FXCollections.observableList(Employee.contact);
                contactCmbBx.setItems(cont);
                contactCmbBx.setValue("Contact List");
                contactTxt.setText("");
            }
        }
        else{
            new Alert(Alert.AlertType.WARNING, "Invalid Contact Number!").showAndWait();
        }
    }

    private boolean isCorrectPattern() {
        if (RegExPattern.getNamePattern().matcher(nameTxt.getText()).matches()  && (RegExPattern.getIdPattern().matcher(nicTxt.getText()).matches() ||RegExPattern.getOldIDPattern().matcher(nicTxt.getText()).matches() ) && RegExPattern.getAddressPattern().matcher(addressTxt.getText()).matches() && RegExPattern.getDoublePattern().matcher(empSalary.getText()).matches() && RegExPattern.getEmailPattern().matcher(emailTxt.getText()).matches()){
            return true;
        }
        return false;
    }

    public void updateDetailsOnAction(ActionEvent actionEvent) {
    }

    public void setTxtBxValueOnAction(ActionEvent actionEvent) {
    }
}

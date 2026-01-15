package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.dto.Employee;
import com.lady_rose.model.EmployeeModel;
import com.lady_rose.regex.RegExPattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
    public TableColumn columnId;
    public TableColumn columnName;
    public TableColumn columnNic;
    public TableColumn columnAddress;
    public TableColumn columnEmail;
    public TableColumn columnContact;
    public TableColumn columnDob;
    public TableColumn columnGender;
    public TableColumn columnJobrole;
    public TableColumn columnSalary;
    public TableColumn columnStartDate;
    public TableColumn columnResignedDate;


    public void initialize() throws SQLException {
        setCellValueFactory();
        getAllEmployers();
    }

    private void getAllEmployers() throws SQLException {
        ObservableList<Employee> obList = FXCollections.observableArrayList();
        List<Employee> empList = EmployeeModel.getAll();

        for (Employee employer : empList) {
            obList.add(new Employee(
                    employer.getPId(),
                    employer.getName(),
                    employer.getNic(),
                    employer.getAddress(),
                    employer.getEmailAddress(),
                    employer.getPhoneNumber(),
                    employer.getDob(),
                    employer.getGender(),
                    employer.getPosition(),
                    String.valueOf(employer.getSalary()),
                    employer.getEnteredDate(),
                    employer.getResignedDate()
            ));
        }
        empTbl.setItems(obList);
    }
    void setCellValueFactory() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        columnNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        columnDob.setCellValueFactory(new PropertyValueFactory<>("Dob"));
        columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        columnJobrole.setCellValueFactory(new PropertyValueFactory<>("jobRole"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<>("monthlySalary"));
        columnStartDate.setCellValueFactory(new PropertyValueFactory<>("enteredDate"));
        columnResignedDate.setCellValueFactory(new PropertyValueFactory<>("resignedDate"));
    }
    public void idSearchOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            Employee.contact.clear();
            List<Employee> empList = EmployeeModel.searchEmployee(idTxt.getText());
            if (!empList.isEmpty()){
                for (Employee employer : empList) {
                    nameTxt.setText(employer.getName());
                    nicTxt.setText(employer.getNic());
                    emailTxt.setText(employer.getEmailAddress());
                    addressTxt.setText(employer.getAddress());
                    contactCmbBx.setItems(getContactObList(employer.getPhoneNumber()));
                    jobRolTxt.setText(employer.getPosition());
                    dobDtPck.setValue(LocalDate.parse(employer.getDob()));
                    strtDtPck.setValue(LocalDate.parse(employer.getEnteredDate()));
                    System.out.println(employer.getResignedDate());
                    if (employer.getResignedDate()!=null) {
                        endDtPkr.setValue(LocalDate.parse(employer.getResignedDate()));
                    }
                    empSalary.setText(String.valueOf(employer.getSalary()));
                    if (employer.getGender().equals("MALE")){
                        maleRdBtn.setSelected(true);
                    }else{
                        femaleRdBtn.setSelected(true);
                    }
                }
                idTxt.setDisable(true);
            }else{
                new Alert(Alert.AlertType.WARNING, "Employer ID Not Found!").showAndWait();
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }
    private ObservableList getContactObList(String custContact) {
        String[] contacts=custContact.split(" , ");
        List<String> conList = Arrays.asList(contacts);
        ObservableList<String> cont = FXCollections.observableList(conList);
        int length=contacts.length;
        while (length>0){
            Employee.contact.add(contacts[length-1]);
            length-=1;
        }
        return cont;
    }

    public void addEmployerOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected= EmployeeModel.addEmployee(EmployeeModel.generateID(), nameTxt.getText(), nicTxt.getText(),
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
            boolean isAlreadyHas = EmployeeModel.addContact(contactTxt.getText());
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
        if (RegExPattern.getNamePattern().matcher(nameTxt.getText()).matches()  &&
                (RegExPattern.getIdPattern().matcher(nicTxt.getText()).matches() ||
                        RegExPattern.getOldIDPattern().matcher(nicTxt.getText()).matches() ) &&
                RegExPattern.getAddressPattern().matcher(addressTxt.getText()).matches() &&
                RegExPattern.getDoublePattern().matcher(empSalary.getText()).matches() &&
                RegExPattern.getEmailPattern().matcher(emailTxt.getText()).matches()){
            return true;
        }
        return false;
    }

    public void updateDetailsOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected= EmployeeModel.updateEmployer(idTxt.getText(), nameTxt.getText(), nicTxt.getText(),
                        addressTxt.getText(), emailTxt.getText(), String.join(" , ", Employee.contact),dobDtPck.getValue(),
                        maleRdBtn.isSelected() ? "MALE" : "FEMALE", jobRolTxt.getText(),empSalary.getText(),strtDtPck.getValue(),endDtPkr.getValue());
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Employer Updated!").showAndWait();
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

    public void setTxtBxValueOnAction(ActionEvent actionEvent) {
        contactTxt.setText(String.valueOf(contactCmbBx.getSelectionModel().getSelectedItem()));
        con = contactTxt.getText();
    }
}

package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.dto.Customer;
import com.lady_rose.model.CustomerModel;
import com.lady_rose.regex.RegExPattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public class CustomerFormController {
    public AnchorPane customerRoot;
    public TextField customerIdTxt;
    public TextField customerNameTxt;
    public TextField nicTxt;
    public TextField addressTxt;
    public TextField emailTxt;
    public TextField contactTxt;
    public RadioButton maleRdBtn;
    public ToggleGroup genderGroup;
    public RadioButton femaleRdBtn;
    public ComboBox<String> categoryChoiceBox;
    public TableView<Customer> customerTbl;
    public Button addBtn;
    public Button clearBtn;
    public Button updateBtn;
    public Button addContactBtn;

    public TableColumn columnId;
    public TableColumn columnName;
    public TableColumn columnNic;
    public TableColumn columnAddress;
    public TableColumn columnEmail;
    public TableColumn columnContact;
    public TableColumn columnGender;
    public TableColumn columnCategory;


    // Initialize categories (you can load from DB later)
    private void initializeCategories() {
        categoryChoiceBox.getItems().addAll("Regular", "VIP", "Corporate", "Guest");
        categoryChoiceBox.setValue("Regular");
    }

    public void initialize() throws SQLException {
        // Link radio buttons
        ToggleGroup genderGroup = new ToggleGroup();
        maleRdBtn.setToggleGroup(genderGroup);
        femaleRdBtn.setToggleGroup(genderGroup);

        setCellValueFactory();
        getAllEmployers();
    }

    private void getAllEmployers() throws SQLException {
        ObservableList<Customer> obList = FXCollections.observableArrayList();
        List<Customer> cusList = CustomerModel.getAll();

        for (Customer customer : cusList) {
            obList.add(new Customer(
                    customer.getPId(),
                    customer.getName(),
                    customer.getNic(),
                    customer.getAddress(),
                    customer.getEmailAddress(),
                    customer.getPhoneNumber(),
                    customer.getGender(),
                    customer.getCategory()

            ));
        }
        customerTbl.setItems(obList);
    }
    void setCellValueFactory() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("pId"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        columnContact.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    public void idSearchOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            Customer.contact.clear();
            List<Customer> cusList = CustomerModel.searchCustomer(customerIdTxt.getText());
            if (!cusList.isEmpty()){
                for (Customer customer : cusList) {
                    customerNameTxt.setText(customer.getName());
                    nicTxt.setText(customer.getNic());
                    emailTxt.setText(customer.getEmailAddress());
                    addressTxt.setText(customer.getAddress());
                    categoryChoiceBox.setItems(getContactObList(customer.getPhoneNumber()));

                    if (customer.getGender().equals("MALE")){
                        maleRdBtn.setSelected(true);
                    }else{
                        femaleRdBtn.setSelected(true);
                    }
                }
                customerIdTxt.setDisable(true);
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
            Customer.contact.add(contacts[length-1]);
            length-=1;
        }
        return cont;
    }

    public void addCustomerOnAction(ActionEvent actionEvent) {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);

            if (!isCorrectPattern()) {
                new Alert(Alert.AlertType.WARNING, "Please check input patterns!").showAndWait();
                return;
            }

            String id = customerIdTxt.getText().isEmpty() ? CustomerModel.generateID() : customerIdTxt.getText();
            String name = customerNameTxt.getText();
            String nic = nicTxt.getText();
            String address = addressTxt.getText();
            String email = emailTxt.getText();
            String gender = maleRdBtn.isSelected() ? "MALE" : "FEMALE";
            String category = categoryChoiceBox.getValue();

            // Handle contact(s) - assuming single contact for now
            String contact = contactTxt.getText().trim();

            boolean isAdded = CustomerModel.addCustomer(id, name, nic, address, email, contact, gender, category);

            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Added Successfully!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                clearFields();
              //  loadCustomersToTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Failed to add customer. Check details.").showAndWait();
                DBConnection.getInstance().getConnection().rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DBConnection.getInstance().getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).showAndWait();
        } finally {
            try {
                DBConnection.getInstance().getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateCustomerOnAction(ActionEvent actionEvent) {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);

            if (!isCorrectPattern()) {
                new Alert(Alert.AlertType.WARNING, "Please check input patterns!").showAndWait();
                return;
            }

            String id = customerIdTxt.getText();
            if (id == null || id.trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Customer ID required to update!").showAndWait();
                return;
            }

            String name = customerNameTxt.getText();
            String nic = nicTxt.getText();
            String address = addressTxt.getText();
            String email = emailTxt.getText();
            String gender = maleRdBtn.isSelected() ? "MALE" : "FEMALE";
            String category = categoryChoiceBox.getValue();
            String contact = contactTxt.getText().trim();

            boolean isUpdated = CustomerModel.updateCustomer(id, name, nic, address, email, contact, gender, category);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Updated Successfully!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                clearFields();
             //   loadCustomersToTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Update failed. Customer may not exist.").showAndWait();
                DBConnection.getInstance().getConnection().rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DBConnection.getInstance().getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).showAndWait();
        } finally {
            try {
                DBConnection.getInstance().getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearCustomerOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        customerIdTxt.clear();
        customerNameTxt.clear();
        nicTxt.clear();
        addressTxt.clear();
        emailTxt.clear();
        contactTxt.clear();
        maleRdBtn.setSelected(true); // default
        categoryChoiceBox.setValue("Regular");
        customerTbl.getSelectionModel().clearSelection();
    }

    private boolean isCorrectPattern() {
        if (RegExPattern.getNamePattern().matcher(customerNameTxt.getText()).matches()
                && (RegExPattern.getIdPattern().matcher(nicTxt.getText()).matches() || RegExPattern.getOldIDPattern().matcher(nicTxt.getText()).matches())
                && RegExPattern.getAddressPattern().matcher(addressTxt.getText()).matches()
                && RegExPattern.getEmailPattern().matcher(emailTxt.getText()).matches()
                && RegExPattern.getMobilePattern().matcher(contactTxt.getText()).matches()) {
            return true;
        }
        return false;
    }



    // Optional: Add contact button (if you plan to allow multiple contacts per customer)
    public void addContactOnAction(ActionEvent actionEvent) {
        String contact = contactTxt.getText().trim();
        if (contact.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Enter a contact number first!").showAndWait();
            return;
        }

        if (!RegExPattern.getMobilePattern().matcher(contact).matches()) {
            new Alert(Alert.AlertType.WARNING, "Invalid contact number format!").showAndWait();
            return;
        }


        new Alert(Alert.AlertType.INFORMATION, "Contact added: " + contact).showAndWait();

    }
}





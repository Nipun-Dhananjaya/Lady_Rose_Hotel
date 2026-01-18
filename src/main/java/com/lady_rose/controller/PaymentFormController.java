package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.dto.Payment;
import com.lady_rose.dto.TM.PaymentTM;
import com.lady_rose.model.PaymentModel;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import com.lady_rose.dto.RoomBookings;

import java.sql.SQLException;
import java.time.LocalDate;

public class PaymentFormController {
    public AnchorPane paymentRoot;
    public TextField paymentIdTxt;
    public TextField customerIdTxt;
    public TextField customerNameTxt;
    public TextField bookingIdTxt;
    public TextField amountTxt;
    public DatePicker paymentDateDtpkr;

    public RadioButton cashRdBtn;
    public RadioButton cardRdBtn;
    public ToggleGroup methodGroup;

    public RadioButton paidRdBtn;
    public RadioButton pendingRdBtn;
    public ToggleGroup statusGroup;

    public Button payBtn;
    public Button clearBtn;
    public Button updateBtn;

    public TableView paymentTbl;

    public TableColumn colPaymentId;
    public TableColumn colCustomerId;
    public TableColumn colCustomerName;
    public TableColumn colBookingId;
    public TableColumn colPaymentDate;
    public TableColumn colMethod;
    public TableColumn colAmount;
    public TableColumn colStatus;
    public TextField payIdTxt;
    public TextField custNameTxt;
    public TextField custIdTxt;
    public DatePicker payDateDtPckr;

    private String bookingType; // "ROOM" or "SPA"

    public void setBookingType(String type) {
        this.bookingType = type;
    }

    public void initialize() throws SQLException {
        methodGroup = new ToggleGroup();
        cashRdBtn.setToggleGroup(methodGroup);
        cardRdBtn.setToggleGroup(methodGroup);

        statusGroup = new ToggleGroup();
        paidRdBtn.setToggleGroup(statusGroup);
        pendingRdBtn.setToggleGroup(statusGroup);

        cashRdBtn.setSelected(true);
        paidRdBtn.setSelected(true);
        try {
            loadPaymentTable();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load payments!").showAndWait();
        }
        setCellValueFactory();
    }

    private void loadPaymentTable() throws SQLException {
        ObservableList<PaymentTM> list = FXCollections.observableArrayList();
        list.addAll(PaymentModel.getAllRoomBookings());
        paymentTbl.setItems(list);
        /*if ("ROOM".equals(bookingType)) {
        } else if ("SPA".equals(bookingType)) {
            ObservableList<SpaBookings> list = FXCollections.observableArrayList();
            list.addAll(PaymentModel.getAllSpaBookings());
            paymentTbl.setItems(list);
        }*/
    }
    public void idSearchOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            String bookingId = bookingIdTxt.getText().trim();
            if (bookingId.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter Booking ID!").showAndWait();
                return;
            }
            PaymentTM rb = PaymentModel.getRoomBookingById(bookingId);
            if (rb != null) {
                customerIdTxt.setText(rb.getCustId());

                amountTxt.setText(rb.getAmount());

                // Set payment method
                if ("Cash".equals(rb.getPaymentMethod())) {
                    cashRdBtn.setSelected(true);
                } else {
                    cardRdBtn.setSelected(true);
                }

                // Set status
                if ("Paid".equals(rb.getPaymentStatus())) {
                    paidRdBtn.setSelected(true);
                } else {
                    pendingRdBtn.setSelected(true);
                }

                // Set date
                if (rb.getPaymentDate() != null && !rb.getPaymentDate().isEmpty()) {
                    paymentDateDtpkr.setValue(LocalDate.parse(rb.getPaymentDate()));
                }

                bookingIdTxt.setDisable(true); // lock ID
            } else {
                new Alert(Alert.AlertType.WARNING, "Room Booking not found!").showAndWait();
            }

            /*if ("ROOM".equals(bookingType)) {
            }
            else if ("SPA".equals(bookingType)) {
                SpaBookings sb = PaymentModel.getSpaBookingById(bookingId);
                if (sb != null) {
                    customerIdTxt.setText(sb.getCustomerId());

                    amountTxt.setText(sb.getAmount());

                    if ("Cash".equals(sb.getPaymentMethod())) {
                        cashRdBtn.setSelected(true);
                    } else {
                        cardRdBtn.setSelected(true);
                    }

                    if ("Paid".equals(sb.getPaymentStatus())) {
                        paidRdBtn.setSelected(true);
                    } else {
                        pendingRdBtn.setSelected(true);
                    }

                    if (sb.getPaymentDate() != null && !sb.getPaymentDate().isEmpty()) {
                        paymentDateDtpkr.setValue(LocalDate.parse(sb.getPaymentDate()));
                    }

                    bookingIdTxt.setDisable(true);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Spa Booking not found!").showAndWait();
                }
            }*/
            DBConnection.getInstance().getConnection().commit();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Search failed!").showAndWait();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void payOnAction(ActionEvent actionEvent) throws SQLException {
        updatePayment("PAY");
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException {
        updatePayment("UPDATE");
    }

    private void updatePayment(String actionType) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);

            if (!validate()) return;

            String bookingId = bookingIdTxt.getText();
            String amount = amountTxt.getText();
            LocalDate date = paymentDateDtpkr.getValue();
            String paymentDate = (date != null) ? date.toString() : "";
            String method = cashRdBtn.isSelected() ? "Cash" : "Card";
            String status = paidRdBtn.isSelected() ? "Paid" : "Pending";

            boolean success = PaymentModel.updateRoomBookingPayment(bookingId,PaymentModel.generateID(), amount, paymentDate, method, status);;

            /*if ("ROOM".equals(bookingType)) {
                } else if ("SPA".equals(bookingType)) {
                success = PaymentModel.updateSpaBookingPayment(bookingId, amount, paymentDate, method, status);
            }*/

            if (success) {
                String message;
                if ("PAY".equals(actionType)) {
                    message = "Payment recorded successfully!";
                } else {
                    message = "Payment updated successfully!";
                }
                new Alert(Alert.AlertType.INFORMATION, message).showAndWait();
                DBConnection.getInstance().getConnection().commit();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Failed to save payment!").showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        } finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }

    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private boolean validate() {
        if (bookingIdTxt.getText().isEmpty() ||
                customerIdTxt.getText().isEmpty() ||
                amountTxt.getText().isEmpty() ||
                paymentDateDtpkr.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please fill all required fields!").showAndWait();
            return false;
        }
        return true;
    }

    private void clearFields() {
        paymentIdTxt.clear();
        bookingIdTxt.clear();
        customerIdTxt.clear();
        customerNameTxt.clear();
        amountTxt.clear();
        paymentDateDtpkr.setValue(null);
        cashRdBtn.setSelected(true);
        paidRdBtn.setSelected(true);
    }

    private void rollbackAndAlert() {
        try {
            DBConnection.getInstance().getConnection().rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        new Alert(Alert.AlertType.ERROR, "Database error!").showAndWait();
    }

    private void setAutoCommitTrue() {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void setCellValueFactory() {
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        /*if ("ROOM".equals(bookingType)) {



        } else if ("SPA".equals(bookingType)) {

            colPaymentId.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
            colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
            colMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
            colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        }*/
    }
}

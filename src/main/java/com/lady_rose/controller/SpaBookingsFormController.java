package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.model.Spa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

public class SpaDetailsFormController {

    @FXML
    public AnchorPane spaRoot;

    @FXML
    public TextField txtBookingId;

    @FXML
    public DatePicker dpBookedDate;

    @FXML
    public DatePicker dpStartDate;

    @FXML
    public DatePicker dpEndDate;

    @FXML
    public Button btnSave;

    @FXML
    public Button btnUpdate;

    // ================== SAVE ==================
    public void saveOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);

            boolean isAdded = Spa.addSpaDetails(
                    txtBookingId.getText(),
                    dpBookedDate.getValue(),
                    dpStartDate.getValue(),
                    dpEndDate.getValue()
            );

            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Spa Booking Saved Successfully!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Failed to Save Booking!").showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        } finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    // ================== UPDATE ==================
    public void updateOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);

            boolean isUpdated = Spa.updateSpaDetails(
                    txtBookingId.getText(),
                    dpBookedDate.getValue(),
                    dpStartDate.getValue(),
                    dpEndDate.getValue()
            );

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Spa Booking Updated Successfully!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Update Failed!").showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        } finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    // ================== CLEAR ==================
    private void clearFields() {
        txtBookingId.clear();
        dpBookedDate.setValue(null);
        dpStartDate.setValue(null);
        dpEndDate.setValue(null);
    }
}


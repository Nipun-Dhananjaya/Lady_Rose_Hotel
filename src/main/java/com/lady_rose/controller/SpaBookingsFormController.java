package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.dto.SpaBooking;
import com.lady_rose.model.SpaDetailsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SpaDetailsFormController {

    public TextField txtBookingId;
    public DatePicker dpBookingDate;
    public DatePicker dpBookedDate;
    public ComboBox<String> cmbPackage;
    public Button btnSave;
    public Button btnUpdate;
    public TableView<SpaBooking> spaTbl;

    public TableColumn columnbookingid;
    public TableColumn columnbookingdate;
    public TableColumn columnbookeddate;
    public TableColumn columnpackage;

    public void initialize() throws SQLException {
        setCellValueFactory();
        loadPackages();
        getAllBookings();
    }

    private void setCellValueFactory() {
        columnbookingid.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        columnbookingdate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        columnbookeddate.setCellValueFactory(new PropertyValueFactory<>("bookedDate"));
        columnpackage.setCellValueFactory(new PropertyValueFactory<>("spaPackage"));
    }

    private void loadPackages() {
        cmbPackage.setItems(FXCollections.observableArrayList(
                "Relax Package",
                "Full Body Massage",
                "Facial Treatment",
                "Couple Spa",
                "Luxury Spa"
        ));
    }

    private void getAllBookings() throws SQLException {
        ObservableList<SpaBooking> obList = FXCollections.observableArrayList();
        List<SpaBooking> bookingList = SpaDetailsModel.getAll();

        for (SpaBooking booking : bookingList) {
            obList.add(new SpaBooking(
                    booking.getBookingId(),
                    booking.getBookingDate(),
                    booking.getBookedDate(),
                    booking.getSpaPackage()
            ));
        }
        spaTbl.setItems(obList);
    }

    public void bookingIdSearchOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);

            SpaBooking booking = SpaDetailsModel.searchBooking(txtBookingId.getText());

            if (booking != null) {
                dpBookingDate.setValue(LocalDate.parse(booking.getBookingDate()));
                dpBookedDate.setValue(LocalDate.parse(booking.getBookedDate()));
                cmbPackage.setValue(booking.getSpaPackage());
                txtBookingId.setDisable(true);
            } else {
                new Alert(Alert.AlertType.WARNING, "Booking ID Not Found!").showAndWait();
            }

            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        } finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);

            boolean isAffected = SpaDetailsModel.addBooking(
                    SpaDetailsModel.generateID(),
                    dpBookingDate.getValue(),
                    dpBookedDate.getValue(),
                    cmbPackage.getValue()
            );

            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Spa Booking Saved!").showAndWait();
                getAllBookings();
                DBConnection.getInstance().getConnection().commit();
            } else {
                new Alert(Alert.AlertType.WARNING, "Re-check Booking Details!").showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        } finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);

            boolean isAffected = SpaDetailsModel.updateBooking(
                    txtBookingId.getText(),
                    dpBookingDate.getValue(),
                    dpBookedDate.getValue(),
                    cmbPackage.getValue()
            );

            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Spa Booking Updated!").showAndWait();
                getAllBookings();
                txtBookingId.setDisable(false);
                DBConnection.getInstance().getConnection().commit();
            } else {
                new Alert(Alert.AlertType.WARNING, "Re-check Booking Details!").showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        } finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }
}
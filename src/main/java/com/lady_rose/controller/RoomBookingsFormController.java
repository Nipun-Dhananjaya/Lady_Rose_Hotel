package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.dto.RoomBookings;
import com.lady_rose.model.RoomBookingsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RoomBookingsFormController {

    public AnchorPane roomBookingRoot;

    public TextField txtBookingId;
    public DatePicker dpBookedDate;
    public DatePicker dpStartDate;
    public DatePicker dpEndDate;

    public Button btnSave;
    public Button btnUpdate;

    public TableView<RoomBookings> bookingTbl;
    public TableColumn<RoomBookings, String> columnbookingid;
    public TableColumn<RoomBookings, LocalDate> columnbookeddate;
    public TableColumn<RoomBookings, LocalDate> columnstartdate;
    public TableColumn<RoomBookings, LocalDate> columnenddate;

    public void initialize() throws SQLException {
        setCellValueFactory();
        getAllBookings();
    }

    private void setCellValueFactory() {
        columnbookingid.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        columnbookeddate.setCellValueFactory(new PropertyValueFactory<>("bookedDate"));
        columnstartdate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        columnenddate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    }

    private void getAllBookings() throws SQLException {
        ObservableList<RoomBookings> obList = FXCollections.observableArrayList();
        List<RoomBookings> list = RoomBookingsModel.getAll();
        obList.addAll(list);
        bookingTbl.setItems(obList);
    }

    public void saveBookingOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);

            boolean isSaved = RoomBookingsModel.saveBooking(
                    txtBookingId.getText(),
                    dpBookedDate.getValue(),
                    dpStartDate.getValue(),
                    dpEndDate.getValue()
            );

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Booking Saved!").show();
                DBConnection.getInstance().getConnection().commit();
                getAllBookings();
            }
        } catch (SQLException e) {
            DBConnection.getInstance().getConnection().rollback();
            throw e;
        } finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void updateBookingOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);

            boolean isUpdated = RoomBookingsModel.updateBooking(
                    txtBookingId.getText(),
                    dpBookedDate.getValue(),
                    dpStartDate.getValue(),
                    dpEndDate.getValue()
            );

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Booking Updated!").show();
                DBConnection.getInstance().getConnection().commit();
                getAllBookings();
            }
        } catch (SQLException e) {
            DBConnection.getInstance().getConnection().rollback();
            throw e;
        } finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }
}
package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.dto.RoomBookings;
import com.lady_rose.dto.TM.RoomBookingTM;
import com.lady_rose.model.PaymentModel;
import com.lady_rose.model.RoomBookingsModel;
import com.lady_rose.regex.RegExPattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomBookingsFormController {
    public AnchorPane bookRoot;
    public TextField bookingIdTxt;
    public TextField custIdTxt;
    public TextField nameTxt;
    public TextField startTimeTxt;
    public Button bookBtn;
    public DatePicker startDateDtPckr;
    public TextField endTimeTxt;
    public DatePicker endDateDtPckr;
    public ComboBox roomTypeCmbBx;
    public ComboBox roomNumCmbBx;
    public Button cancelBookBtn;
    public Button updateBtn;
    public TableView bookingTbl;
    public static ArrayList<String> roomTypes = new ArrayList<>();
    public TextField sleepsTxt;
    public TableColumn columnBookingId;
    public TableColumn columnCustomerId;
    public TableColumn columnCustName;
    public TableColumn columnRoomNo;
    public TableColumn columnBookFrom;
    public TableColumn columnCost;
    public TableColumn columnDrtion;
    public TableColumn columnAvailability;
    public TableColumn columnBookedOn;

    public void initialize() throws SQLException {
        roomTypes.clear();
        roomTypes.add("Deluxe Room");
        roomTypes.add("Standard Room");

        ObservableList<String> roomType = FXCollections.observableList(roomTypes);
        roomTypeCmbBx.setItems(roomType);
        //RoomBookingsModel.updateStatus();
        setCellValueFactory();
        getAllBookings();
    }

    private void getAllBookings() throws SQLException {
        ObservableList<RoomBookingTM> obList = FXCollections.observableArrayList();
        List<RoomBookingTM> bookList = RoomBookingsModel.getAll();

        for (RoomBookingTM booking : bookList) {
            obList.add(new RoomBookingTM(
                    booking.getBookingId(),
                    booking.getCustId(),
                    booking.getCustName(),
                    booking.getRoomNo(),
                    booking.getRoomType(),
                    booking.getBookFrom(),
                    booking.getDuration(),
                    booking.getCost(),
                    booking.getBookedOn(),
                    booking.getAvailability()
            ));
        }
        bookingTbl.setItems(obList);
    }
    void setCellValueFactory() {
        columnBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        columnCustomerId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        columnCustName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        columnRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        columnBookFrom.setCellValueFactory(new PropertyValueFactory<>("bookFrom"));
        columnDrtion.setCellValueFactory(new PropertyValueFactory<>("duration"));
        columnCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        columnBookedOn.setCellValueFactory(new PropertyValueFactory<>("bookedOn"));
        columnAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
    }
    public void idSearchOnAction(ActionEvent actionEvent) throws SQLException, ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            List<RoomBookingTM> bookList = RoomBookingsModel.searchBooking(bookingIdTxt.getText());
            if (!bookList.isEmpty()){
                for (RoomBookingTM booking : bookList) {
                    bookingIdTxt.setText(booking.getBookingId());
                    custIdTxt.setText(booking.getCustId());
                    roomNumCmbBx.setValue(booking.getRoomNo());
                    String[] datTime=booking.getBookFrom().split(" ");
                    startDateDtPckr.setValue(LocalDate.parse(booking.getBookFrom(),formatter));
                    startTimeTxt.setText(datTime[1]);
                    LocalDateTime oldDateTime = LocalDateTime.parse(booking.getBookFrom(), formatter);
                    endDateDtPckr.setValue(LocalDate.from(oldDateTime.plusHours(Long.parseLong(booking.getDuration()))));
                    endTimeTxt.setText(String.valueOf(LocalTime.from(oldDateTime.plusHours(Long.parseLong(booking.getDuration()))))+":00");
                    bookingIdTxt.setDisable(true);
                    custIdTxt.setDisable(true);
                }
            }else{
                new Alert(Alert.AlertType.WARNING, "Booking Cancelled Or Not Found!").showAndWait();
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void searchCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            String name=RoomBookingsModel.searchCustomer(custIdTxt.getText());
            if (name!=null){
                nameTxt.setText(name);
            }
            else{
                new Alert(Alert.AlertType.WARNING, "Customer ID not Found!").showAndWait();
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void bookRoomOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startDate = LocalDateTime.of(startDateDtPckr.getValue(), LocalTime.parse(startTimeTxt.getText()));
            LocalDateTime endDate = LocalDateTime.of(endDateDtPckr.getValue(), LocalTime.parse(endTimeTxt.getText()));
            long duration = ChronoUnit.HOURS.between(startDate,endDate);
            String bookingId = RoomBookingsModel.generateID();
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected=RoomBookingsModel.isAdded(bookingId, custIdTxt.getText(), startDate,endDate, duration,String.valueOf(roomNumCmbBx.getValue()), now,"Active");
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Booking Successful!").showAndWait();
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

    public void cancelBookRoomOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            Optional<ButtonType> comfirm=new Alert(Alert.AlertType.CONFIRMATION, "Do you want to cancel the booking?").showAndWait();
            if (comfirm.isPresent()){
                String bookingId = bookingIdTxt.getText();
                String paymentId = RoomBookingsModel.getPaymentId(bookingId);
                boolean isAffected=false;
                if (isCorrectPattern()){
                    System.out.println("correct");
                    isAffected=RoomBookingsModel.cancelBooking(bookingId,"Cancelled",String.valueOf(roomNumCmbBx.getValue()));
                }
                if (isAffected) {
                    new Alert(Alert.AlertType.INFORMATION, "Booking Cancelled!").showAndWait();
                    DBConnection.getInstance().getConnection().commit();
                    resetPage();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Something went wrong!").showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void updateDetailsOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime startDate = LocalDateTime.of(startDateDtPckr.getValue(), LocalTime.parse(startTimeTxt.getText()));
            LocalDateTime endDate = LocalDateTime.of(endDateDtPckr.getValue(), LocalTime.parse(endTimeTxt.getText()));
            long duration = ChronoUnit.HOURS.between(startDate,endDate);
            String bookingId = bookingIdTxt.getText();
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected=RoomBookingsModel.updateBooking(bookingId, startDate,endDate, duration,"Active");
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Booking Updated!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                bookingIdTxt.setDisable(false);
                custIdTxt.setDisable(false);
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
        bookingIdTxt.setText("");
        custIdTxt.setText("");
        nameTxt.setText("");
        startTimeTxt.setText("");
        endTimeTxt.setText("");
        roomNumCmbBx.setValue("Room No");
        roomTypeCmbBx.setValue("Room Type");
        setCellValueFactory();
        getAllBookings();
    }

    public void setSelectedRoomTypeNoOnAction(ActionEvent actionEvent) {
        roomNumCmbBx.setItems(null);
        if (roomTypeCmbBx.getSelectionModel().getSelectedItem().equals("Deluxe Room")){
            RoomBookingsModel.setRoomNumbers("Deluxe Room");
            ObservableList<String> roomNo = FXCollections.observableList(RoomBookingsModel.deluxeRoomNo);
            roomNumCmbBx.setItems(roomNo);
        }else{
            RoomBookingsModel.setRoomNumbers("Standard Room");
            ObservableList<String> roomNo = FXCollections.observableList(RoomBookingsModel.standardRoomNo);
            roomNumCmbBx.setItems(roomNo);
        }
    }

    public void setSleepCountOnAction(ActionEvent actionEvent) {
        sleepsTxt.setText("");
        sleepsTxt.setText(RoomBookingsModel.setSleepCount(String.valueOf(roomNumCmbBx.getSelectionModel().getSelectedItem())));
    }
    private boolean isCorrectPattern(){
        if (RegExPattern.getNamePattern().matcher(nameTxt.getText()).matches()  && startTimeTxt.getText().matches("^(2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])$") && endTimeTxt.getText().matches("^(2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])$")){
            return true;
        }
        return false;
    }
}
package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.dto.Employee;
import com.lady_rose.dto.Room;
import com.lady_rose.model.EmployeeModel;
import com.lady_rose.model.RoomModel;
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

public class RoomManagerFormController {
    public Pane anchorPane;
    public TextField roomNoTxt;
    public TableView roomTable;
    public TextField bedCountTxt;
    public Button addBtn;
    public Button updateBtn;
    public ComboBox roomCtgyCmbBx;
    public ComboBox roomViewCmbBx;
    public TableColumn columnRoomNo;
    public TableColumn columnCategory;
    public TableColumn columnBedCount;
    public TableColumn columnView;
    public Button addbtn;
    public Button updtbtn;

    public void initialize() throws SQLException {
        setCellValueFactory();
        getAllRoom();
    }

    private void getAllRoom() throws SQLException {
        ObservableList<Room> obList = FXCollections.observableArrayList();
        List<Room> roomList = RoomModel.getAll();

        for (Room room : roomList) {
            obList.add(new Room(
                    room.getR_No(),
                    room.getCategory(),
                    room.getView(),
                    room.getBedCount()
                    ));
        }
        roomTable.setItems(obList);
    }
    void setCellValueFactory() {
        columnRoomNo.setCellValueFactory(new PropertyValueFactory<>("R_No"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        columnView.setCellValueFactory(new PropertyValueFactory<>("view"));
        columnBedCount.setCellValueFactory(new PropertyValueFactory<>("bedCount"));
    }

    public void idSearchOnAction(ActionEvent actionEvent) throws SQLException{
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            List<Room> roomList = RoomModel.searchRoom(roomNoTxt.getText());
            ObservableList<String> CategoryObList = FXCollections.observableArrayList();
            CategoryObList.add("Single Room");
            CategoryObList.add("Double Room");
            CategoryObList.add("Deluxe Room");
            CategoryObList.add("Family Room");
            CategoryObList.add("Suite");
            ObservableList<String> ViewObList = FXCollections.observableArrayList();
            ViewObList.add("Beach view");
            ViewObList.add("Garden view");
            ViewObList.add("Pool view");
            ViewObList.add("City view");
            ViewObList.add("Sea view");
            if (!roomList.isEmpty()){
                for (Room room : roomList) {
                    roomCtgyCmbBx.setItems(CategoryObList);
                    bedCountTxt.setText(String.valueOf(room.getBedCount()));
                    roomViewCmbBx.setItems(ViewObList);
                }
                roomNoTxt.setDisable(true);
            }else{
                new Alert(Alert.AlertType.WARNING, "Employee ID Not Found!").showAndWait();
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void addRoomOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected = false;
            if (isCorrectPattern()) {
                isAffected = RoomModel.addRoom(roomNoTxt.getText(),roomCtgyCmbBx.getSelectionModel().getSelectedItem(),bedCountTxt.getText(),roomViewCmbBx.getSelectionModel().getSelectedItem());
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Room Added!").showAndWait();
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
        if(RegExPattern.getIdPattern().matcher(roomNoTxt.getText()).matches() && RegExPattern.getIntPattern().matcher(bedCountTxt.getText()).matches()){
            return true;
        }
        return false;
    }

    public void updateRoomOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected= RoomModel.updateRoom(roomNoTxt.getText(),roomCtgyCmbBx.getSelectionModel().getSelectedItem(),bedCountTxt.getText(),roomViewCmbBx.getSelectionModel().getSelectedItem());
            }
            if (isAffected) {
                new Alert(Alert.AlertType.INFORMATION, "Room Updated!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                roomNoTxt.setDisable(false);
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



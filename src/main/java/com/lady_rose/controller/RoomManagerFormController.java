package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.regex.RegExPattern;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.sql.SQLException;

public class RoomManagerFormController {
    public Pane anchorPane;
    public TextField roomNoTxt;
    public TableView roomTable;
    public TextField bedCountTxt;
    public Button addBtn;
    public Button updateBtn;
    public ComboBox roomCtgyCmbBx;
    public ComboBox roomViewCmbBx;

    public void idSearchOnAction(ActionEvent actionEvent) {
    }

    public void addRoomOnAction(ActionEvent actionEvent) throws SQLException {
        /*try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected = false;
            if (isCorrectPattern()) {
                isAffected = Room.addRoom(roomNoTxt.getText(),roomCtgyCmbBx.getSelectionModel().getSelectedItem(),bedCountTxt.getText(),roomViewCmbBx.getSelectionModel().getSelectedItem());
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
        }*/
    }

    private boolean isCorrectPattern(){
        if(RegExPattern.getIdPattern().matcher(roomNoTxt.getText()).matches() && RegExPattern.getIntPattern().matcher(bedCountTxt.getText()).matches()){
            return true;
        }
        return false;
    }

    public void updateRoomOnAction(ActionEvent actionEvent) throws SQLException {
        /*try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAffected=false;
            if (isCorrectPattern()){
                isAffected= Room.updateRoom(roomNoTxt.getText(),roomCtgyCmbBx.getSelectionModel().getSelectedItem(),bedCountTxt.getText(),roomViewCmbBx.getSelectionModel().getSelectedItem());
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
        }*/
    }

    public void setTxtBxCatgyValueOnAction(ActionEvent actionEvent) {
    }

    public void setTxtBxViewValueOnAction(ActionEvent actionEvent) {
    }




}



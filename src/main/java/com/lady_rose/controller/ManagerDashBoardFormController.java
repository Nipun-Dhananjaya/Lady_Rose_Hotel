package com.lady_rose.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;


public class ManagerDashBoardFormController {
    public AnchorPane homeRoot;
    public AnchorPane root;
    public JFXButton employerBtn;
    public JFXButton roomsBtn;
    public JFXButton suppliersBtn;
    public JFXButton ordersBtn;
    public JFXButton profileBtn;
    public Label dateLbl;
    public Label timeLbl;
    public JFXButton itemsBtn;
    public JFXButton settingsBtn;
    public JFXButton restaurantItemsBtn;

    public void initialize() throws IOException {
        homeRoot.getChildren().clear();
        homeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("/view/employeeManageForm.fxml")));
        loadDateAndTime();
    }
    private void loadDateAndTime() {
        dateLbl.setText(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        Timeline clock=new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime=LocalTime.now();
            timeLbl.setText(currentTime.getHour()+" : "+ currentTime.getMinute()+" : "+ currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    public void showEmployeeManageOnAction(ActionEvent actionEvent) throws IOException {
        homeRoot.getChildren().clear();
        homeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("/view/employeeManageForm.fxml")));
    }

    public void showRoomManageOnAction(ActionEvent actionEvent) throws IOException {
        homeRoot.getChildren().clear();
        homeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("/view/roomManagerForm.fxml")));
    }

    public void showSupplierManageOnAction(ActionEvent actionEvent) throws IOException {
        homeRoot.getChildren().clear();
        homeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("/view/supplierManagerForm.fxml")));
    }

    public void showOrderManageOnAction(ActionEvent actionEvent) throws IOException {
        homeRoot.getChildren().clear();
        homeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("/view/orderManagerForm.fxml")));
    }

    public void loadHomeOnAction(ActionEvent actionEvent) throws IOException {
    }

    public void loadProfileSelectionOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/selectProfileForm.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Profile Selection");
        stage.centerOnScreen();
    }

    public void showItemManageOnAction(ActionEvent actionEvent) throws IOException {
        homeRoot.getChildren().clear();
        homeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("/view/itemManageForm.fxml")));
    }

    public void showSettingsFormOnAction(ActionEvent actionEvent) throws IOException {
        homeRoot.getChildren().clear();
        homeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("/view/settingsForm.fxml")));
    }

    public void showRestaurantItemManageOnAction(ActionEvent actionEvent) throws IOException {
        homeRoot.getChildren().clear();
        homeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("/view/restaurantItemManageForm.fxml")));
    }
}

package com.lady_rose.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ReceptionistDashBoardFormController {
    public AnchorPane root;
    public JFXButton customerBtn;
    public JFXButton roomBookingBtn;
    public JFXButton paymentBtn;
    public AnchorPane homeRoot;
    public Label dateLbl;
    public Label timeLbl;
    public JFXButton spaBookingBtn;
    public JFXButton homeBtn;
    public JFXButton profileBtn;

    public void initialize() throws IOException {
        homeRoot.getChildren().clear();
        homeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("/view/customerForm.fxml")));
    }
    public void showPaymentManageOnAction(ActionEvent actionEvent) throws IOException {
        homeRoot.getChildren().clear();
        homeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("/view/paymentForm.fxml")));
    }

    public void showCustomerManageOnAction(ActionEvent actionEvent) throws IOException {
        homeRoot.getChildren().clear();
        homeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("/view/customerForm.fxml")));
    }

    public void showBookingManageOnAction(ActionEvent actionEvent) throws IOException {
        homeRoot.getChildren().clear();
        homeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("/view/roomBookingsForm.fxml")));
    }

    public void loadProfileSelectionOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/selectProfileForm.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Profile Selection");
        stage.centerOnScreen();
    }

    public void loadHomeOnAction(ActionEvent actionEvent) {
    }
}

package com.lady_rose.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {
    public Button lgnBtn;
    public Pane root;

    public void login() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        if (stage.getTitle().equals("Receptionist Login Form")) {
            setUI("Receptionist Dashboard", "/view/receptionistDashBoardForm.fxml");
        } else {
            setUI("Manager Dashboard", "/view/managerDashBoardForm.fxml");
        }
    }

    private void setUI(String title, String path) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(anchorPane));
        stage.setTitle(title);
        stage.centerOnScreen();
    }
}

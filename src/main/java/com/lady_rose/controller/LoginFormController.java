package com.lady_rose.controller;

import com.lady_rose.model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {
    public Button lgnBtn;
    public Pane root;
    public TextField usernameTxt;
    public TextField passwordTxt;

    public void loginOnAction() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        if (stage.getTitle().equals("Receptionist Login Form")) {
            if (UserModel.IsCorrect(usernameTxt.getText(), passwordTxt.getText(),"Receptionist")) {
                setUI("Receptionist Dashboard", "/view/receptionistDashboardForm.fxml");
            }else{
                new Alert(Alert.AlertType.WARNING, "Username or password is incorrect!").show();
            }
        } else {
            if (UserModel.IsCorrect(usernameTxt.getText(), passwordTxt.getText(),"Manager")) {
                setUI("Manager Dashboard", "/view/managerDashBoardForm.fxml");
            }else{
                new Alert(Alert.AlertType.WARNING, "Username or password is incorrect!").show();
            }
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

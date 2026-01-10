package com.lady_rose.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;

public class SelectProfileFormController {
    public AnchorPane root;
    public Button receptionistBtn;
    public Button managerBtn;
    public Hyperlink wbLnk;

    public void loadBrowserOnAction(ActionEvent actionEvent) {
        try {

            URI uri = new URI("https://web.facebook.com/p/Lady-Rose-Hotel-Restaurant-100083435891152/?_rdc=1&_rdr#");

            java.awt.Desktop.getDesktop().browse(uri);

        } catch (Exception e) {}
    }

    public void showrcptnLoginFormOnAction(ActionEvent actionEvent) throws IOException {
        setUI("Receptionist Login Form");
    }

    public void showmngrLoginFormOnAction(ActionEvent actionEvent) throws IOException {
        setUI("Manager Login Form");
    }

    private void setUI(String s) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(anchorPane));
        stage.setTitle(s);
        stage.centerOnScreen();
    }
}

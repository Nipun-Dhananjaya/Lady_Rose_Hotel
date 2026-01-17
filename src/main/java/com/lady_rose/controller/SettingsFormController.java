package com.lady_rose.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SettingsFormController {
    public AnchorPane settingsRoot;
    public JFXButton userChangeBtn;
    public JFXButton changeRoomPackBtn;
    public AnchorPane settingInsideRoot;
    public JFXButton changeSpaPackBtn;

    public void showChangeUserOnAction(ActionEvent actionEvent) throws IOException {
        settingInsideRoot.getChildren().clear();
        settingInsideRoot.getChildren().add(FXMLLoader.load(getClass().getResource("/view/changeUserForm.fxml")));
    }

    public void showChangeRoomPackOnAction(ActionEvent actionEvent) throws IOException {
        settingInsideRoot.getChildren().clear();
        settingInsideRoot.getChildren().add(FXMLLoader.load(getClass().getResource("/view/changeRoomPackForm.fxml")));
    }

    public void showChangeSpaPackOnAction(ActionEvent actionEvent) throws IOException {
        settingInsideRoot.getChildren().clear();
        settingInsideRoot.getChildren().add(FXMLLoader.load(getClass().getResource("/view/changeSpaPackForm.fxml")));
    }
}

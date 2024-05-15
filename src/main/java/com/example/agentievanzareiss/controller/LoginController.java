package com.example.agentievanzareiss.controller;

import com.example.agentievanzareiss.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    Service service;

    @FXML
    TextField textFieldUsername;

    @FXML
    TextField textFieldPassword;

    public void setService(Service service) {
        this.service = service;
    }

    public void handleLogIn(ActionEvent actionEvent){
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();

    }
}

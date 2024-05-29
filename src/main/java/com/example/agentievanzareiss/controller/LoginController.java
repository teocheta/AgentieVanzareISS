package com.example.agentievanzareiss.controller;

import com.example.agentievanzareiss.model.Utilizator;
import com.example.agentievanzareiss.service.Service;
import com.example.agentievanzareiss.service.ServiceException;
import com.example.agentievanzareiss.utils.MessageAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    Service service;

    private Utilizator crtUser;


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
        crtUser = new Utilizator(username, password);
        boolean exista = service.exista(crtUser);
        if(!exista){
            MessageAlert.showErrorMessage(null,"Username sau parola incorecta!");
            return;
        }
        else {
            Utilizator u = service.getUserByUsername(username);
            String resource;
            if(password.equals(u.getPassword())) {
                if (Objects.equals(username, "admin")) {
                    resource = "/com/example/agentievanzareiss/admin-view.fxml";
                } else {
                    resource = "/com/example/agentievanzareiss/main-view.fxml";
                }
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource(resource));
                    AnchorPane layout = loader.load();

                    Stage stage = new Stage();
                    stage.setTitle("Agentie de vanzari");
                    stage.initModality(Modality.WINDOW_MODAL);

                    Scene scene = new Scene(layout);
                    stage.setScene(scene);
                    if (Objects.equals(username, "admin")) {
                        AdminController adminController = loader.getController();
                        adminController.setService(service);
                        stage.show();


                    } else {
                        MainController mainController = loader.getController();
                        mainController.setService(service, crtUser);
                        stage.show();

                    }


                } catch(IOException e){
                    e.printStackTrace();
                }
            }else{
                MessageAlert.showErrorMessage(null,"Username sau parola incorecta!");
                return;
            }
        }

    }
}

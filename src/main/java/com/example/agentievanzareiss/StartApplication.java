package com.example.agentievanzareiss;

import com.example.agentievanzareiss.controller.AdminController;
import com.example.agentievanzareiss.controller.LoginController;
import com.example.agentievanzareiss.model.Produs;
import com.example.agentievanzareiss.model.Utilizator;
import com.example.agentievanzareiss.model.validators.Validator;
import com.example.agentievanzareiss.model.validators.ValidatorProdus;
import com.example.agentievanzareiss.repository.*;
import com.example.agentievanzareiss.service.Service;
import com.example.agentievanzareiss.service.ServiceAgentie;
import com.example.agentievanzareiss.utils.MessageAlert;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartApplication extends Application {
    Validator<Produs> produsValidator;
    ProdusRepository produsRepository;

    Validator<Utilizator> utilizatorValidator;

    UtilizatorRepository utilizatorRepository;

    ComandaRepository comandaRepository;

    ProdusComandaRepository produsComandaRepository;

    Service service;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        }catch(IOException e) {
            MessageAlert.showErrorMessage(null,"Cannot find bd.config " + e);
        }
        produsValidator = new ValidatorProdus();

        produsRepository = new ProdusDBRepository(props);

        utilizatorRepository = new UtilizatorDBRepository(props);

        comandaRepository = new ComandaDBRepository(props);

        produsComandaRepository = new ProdusComandaDBRepository(props);

        service = new ServiceAgentie(produsValidator, produsRepository,utilizatorRepository, comandaRepository, produsComandaRepository);
        initView(primaryStage);
        primaryStage.setWidth(800);
        primaryStage.show();

    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login-view.fxml"));
        AnchorPane loginLayout = loader.load();
        primaryStage.setScene(new Scene(loginLayout));

        LoginController loginController = loader.getController();
        loginController.setService(service);
    }
}

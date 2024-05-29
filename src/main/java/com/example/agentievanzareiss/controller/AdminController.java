package com.example.agentievanzareiss.controller;
import com.example.agentievanzareiss.model.Produs;
import com.example.agentievanzareiss.model.Utilizator;
import com.example.agentievanzareiss.service.Service;
import com.example.agentievanzareiss.service.ServiceException;
import com.example.agentievanzareiss.utils.MessageAlert;
import com.example.agentievanzareiss.utils.events.ChangeEvent;
import com.example.agentievanzareiss.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

public class AdminController implements Observer<ChangeEvent> {
    @FXML
    TableView<Produs> tableView;

    @FXML
    TableColumn<Produs, String> tableColumnDenumire;

    @FXML
    TableColumn<Produs, Float> tableColumnPret;

    @FXML
    TableColumn<Produs, Integer> tableColumnStoc;

    @FXML
    TextField textFieldDenumire;

    @FXML
    TextField textFieldPret;

    @FXML
    TextField textFieldStoc;

    Service service;


    ObservableList<Produs> model = FXCollections.observableArrayList();

    public void initialize(){
        tableColumnDenumire.setCellValueFactory(new PropertyValueFactory<Produs, String>("denumire"));
        tableColumnPret.setCellValueFactory(new PropertyValueFactory<Produs, Float>("pret"));
        tableColumnStoc.setCellValueFactory(new PropertyValueFactory<Produs, Integer>("stoc"));
        tableView.setItems(model);

    }

    public void setService(Service service) {

        this.service = service;
        initModel();
        service.addObserver(this);
    }

    private void initModel() {
        Iterable<Produs> produse = service.findAllProduse();
        List<Produs> produseList = StreamSupport.stream(produse.spliterator(), false).toList();
        model.setAll(produseList);

    }

    public void handleAdaugaProdus(ActionEvent actionEvent) {
        if(textFieldDenumire.getText().isEmpty() || textFieldPret.getText().isEmpty() || textFieldStoc.getText().isEmpty()){
            MessageAlert.showErrorMessage(null, "Completati toate casetele!");
        }
        else {
            String denumire = textFieldDenumire.getText();
            float pret = Float.parseFloat(textFieldPret.getText());
            int stoc = Integer.parseInt(textFieldStoc.getText());
            try {
                service.adaugaProdus(denumire, pret, stoc);
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Adugare produs", "Produs adaugat!");
            } catch (ServiceException exception) {
                MessageAlert.showErrorMessage(null, exception.getMessage());
            }
        }

    }

    public void handleStergeProdus(ActionEvent actionEvent){
        Produs selected = tableView.getSelectionModel().getSelectedItem();
        if(selected == null){
            MessageAlert.showErrorMessage(null,"Nu a fost selectat niciun produs!");

        }
        else {

            try{
                service.stergeProdus(selected);
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Stergere produs", "Produsul a fost sters!");

            }catch(ServiceException exception){
                MessageAlert.showErrorMessage(null, exception.getMessage());
            }
        }
    }

    public void handleUpdateProdus(ActionEvent actionEvent){
        Produs selected = tableView.getSelectionModel().getSelectedItem();
        float pret = 0;
        int stoc = 0;
        if(!Objects.equals(textFieldPret.getText(), "")) {
            pret = Float.parseFloat(textFieldPret.getText());
        }
        if(!Objects.equals(textFieldStoc.getText(), "")) {
            stoc = Integer.parseInt(textFieldStoc.getText());
        }
        if(selected == null){
            MessageAlert.showErrorMessage(null,"Nu a fost selectat niciun produs!");

        }
        else {
            try{
                service.updateProdus(selected.getDenumire(), pret, stoc);
            }
            catch(ServiceException exception){
                MessageAlert.showErrorMessage(null, exception.getMessage());
            }
        }

    }

    @Override
    public void update(ChangeEvent eveniment) {
        initModel();
        tableView.setItems(model);
    }

    public void handleLogOut(ActionEvent event){
        Stage currentStage = (Stage) tableView.getScene().getWindow();
        currentStage.close();

    }


}

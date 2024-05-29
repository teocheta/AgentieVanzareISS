package com.example.agentievanzareiss.controller;
import com.example.agentievanzareiss.model.Comanda;
import com.example.agentievanzareiss.model.Produs;
import com.example.agentievanzareiss.model.ProdusComanda;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class MainController implements Observer<ChangeEvent> {
    @FXML
    TableView<Produs> tableView;

    @FXML
    TableColumn<Produs, String> tableColumnDenumire;

    @FXML
    TableColumn<Produs, Float> tableColumnPret;

    @FXML
    TableColumn<Produs, Integer> tableColumnStoc;

    @FXML
    TextField textFieldCantitate;

    @FXML
    TextField textFieldDetalii;

    @FXML
    TextField textFieldFiltru;

    Service service;

    Utilizator utilizator;

    ObservableList<Produs> model = FXCollections.observableArrayList();

    List<ProdusComanda> produse = new ArrayList<>();

    public void initialize(){
        tableColumnDenumire.setCellValueFactory(new PropertyValueFactory<Produs, String>("denumire"));
        tableColumnPret.setCellValueFactory(new PropertyValueFactory<Produs, Float>("pret"));
        tableColumnStoc.setCellValueFactory(new PropertyValueFactory<Produs, Integer>("stoc"));
        tableView.setItems(model);

    }

    public void setService(Service service, Utilizator crtUser) {
        this.service = service;
        initModel();
        service.addObserver(this);
        this.utilizator = crtUser;
    }

    private void initModel() {

        Iterable<Produs> produse = service.findAllProduse();
        List<Produs> produseList = StreamSupport.stream(produse.spliterator(), false).toList();
        model.setAll(produseList);

    }

    public void handleAdaugaComanda(ActionEvent event){

        Produs selected = tableView.getSelectionModel().getSelectedItem();
        String cantitateText = textFieldCantitate.getText();
        if(selected!= null && !cantitateText.isEmpty()) {
            int cantitate = Integer.parseInt(cantitateText);
            if(selected.getStoc() < cantitate){
                MessageAlert.showErrorMessage(null, "Nu exista suficiente produse pe stoc!");
            } else {
                ProdusComanda produsComanda = new ProdusComanda(selected, null, cantitate);
                produse.add(produsComanda);
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Adaugare produs", "Produs adaugat la comanda!");
            }
        }
        else {
            MessageAlert.showErrorMessage(null, "Selectati un produs si introduceti cantitatea dorita!");
        }

    }

    public void handlePlaseazaComanda(ActionEvent event){
        String detaliiLivrare = textFieldDetalii.getText();
        if(detaliiLivrare == ""){
            MessageAlert.showErrorMessage(null, "Nu au fost adaugate detaliile de livrare!");
        }
        else if(produse.isEmpty()){
            MessageAlert.showErrorMessage(null, "Nu au fost adaugate produse la comanda!");

        }
        else {
            Comanda comanda = new Comanda(this.utilizator, detaliiLivrare);
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/example/agentievanzareiss/comanda-view.fxml"));
                AnchorPane layout = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Vizualizare comanda");
                stage.initModality(Modality.WINDOW_MODAL);

                Scene scene = new Scene(layout);
                stage.setScene(scene);
                ComandaController comandaController = loader.getController();
                comandaController.setService(service, produse, comanda);
                stage.show();


            } catch(ServiceException exception){
                MessageAlert.showErrorMessage(null, exception.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void update(ChangeEvent eveniment) {
        initModel();
        tableView.setItems(model);
    }

    public void handleFiltreazaProduse(ActionEvent actionEvent) {
        String filtru = textFieldFiltru.getText();
        try {
           Produs produseFiltrate =  service.filtreazaProduse(filtru);
           model.setAll(produseFiltrate);
           tableView.setItems(model);


        } catch (ServiceException exception){
            MessageAlert.showErrorMessage(null, "Nu exista produse cu aceea denumire!");
        }
    }

    public void handleUndo(ActionEvent event){
        initModel();
    }

    public void handleLogOut(ActionEvent event){
        Stage currentStage = (Stage) tableView.getScene().getWindow();
        currentStage.close();

    }
}

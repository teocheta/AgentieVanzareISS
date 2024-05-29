package com.example.agentievanzareiss.controller;


import com.example.agentievanzareiss.model.Comanda;
import com.example.agentievanzareiss.model.Produs;
import com.example.agentievanzareiss.model.ProdusComanda;
import com.example.agentievanzareiss.service.Service;
import com.example.agentievanzareiss.service.ServiceException;
import com.example.agentievanzareiss.utils.MessageAlert;
import com.example.agentievanzareiss.utils.events.ChangeEvent;
import com.example.agentievanzareiss.utils.observer.Observer;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.StreamSupport;

public class ComandaController implements Observer<ChangeEvent> {

    @FXML
    TableView<ProdusComanda> tableViewProduse;

    @FXML
    TableColumn<ProdusComanda, String> tableColumnDenumire;

    @FXML
    TableColumn<ProdusComanda, Float> tableColumnPret;

    @FXML
    TableColumn<ProdusComanda, Integer> tableColumnCantitate;

    ObservableList<ProdusComanda> model = FXCollections.observableArrayList();

    private List<ProdusComanda> produse;

    private Service service;

    private Comanda comanda;

    public void initialize(){
        tableColumnDenumire.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProdus().getDenumire())
        );
        tableColumnPret.setCellValueFactory(cellData ->
                new SimpleFloatProperty(cellData.getValue().getProdus().getPret()).asObject()
        );
        tableColumnCantitate.setCellValueFactory(new PropertyValueFactory<ProdusComanda, Integer>("cantitate"));
        tableViewProduse.setItems(model);

    }

    public void setService(Service service, List<ProdusComanda> produse, Comanda comanda) {
        this.service = service;
        this.produse = produse;
        this.comanda = comanda;
        service.addObserver(this);
        initModel();

    }

    private void initModel() {

        model.setAll(produse);
    }

    @FXML
    private void handleConfirmaComanda(ActionEvent event){
        try {
            int idComanda = service.adaugaComanda(comanda);
            service.adaugaProdusComanda(idComanda, model);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Plasare comanda", "Comanda a fost plasata!");
            Stage stage = (Stage) tableViewProduse.getScene().getWindow();
            stage.close();
            produse.clear();

        }catch(ServiceException exception){
            MessageAlert.showErrorMessage(null,exception.getMessage());
        }


    }

    @Override
    public void update(ChangeEvent eveniment) {

    }
}

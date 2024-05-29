package com.example.agentievanzareiss.service;

import com.example.agentievanzareiss.model.Comanda;
import com.example.agentievanzareiss.model.Produs;
import com.example.agentievanzareiss.model.ProdusComanda;
import com.example.agentievanzareiss.model.Utilizator;
import com.example.agentievanzareiss.model.validators.ValidationException;
import com.example.agentievanzareiss.model.validators.Validator;
import com.example.agentievanzareiss.repository.*;
import com.example.agentievanzareiss.utils.events.ChangeEvent;
import com.example.agentievanzareiss.utils.events.ChangeEventType;
import com.example.agentievanzareiss.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ServiceAgentie implements Service {

    private Validator<Produs> produsValidator;

    private ProdusRepository produsRepository;

    private UtilizatorRepository utilizatorRepository;

    private ComandaRepository comandaRepository;

    private ProdusComandaRepository produsComandaRepository;

    private List<Observer<ChangeEvent>> observers = new ArrayList<>();

    public ServiceAgentie(Validator<Produs> produsValidator, ProdusRepository produsRepository, UtilizatorRepository utilizatorRepository, ComandaRepository comandaRepository, ProdusComandaRepository produsComandaRepository){
        this.produsValidator = produsValidator;
        this.produsRepository = produsRepository;
        this.utilizatorRepository = utilizatorRepository;
        this.comandaRepository = comandaRepository;
        this.produsComandaRepository = produsComandaRepository;
    }

    public boolean exista(Produs produs){
        return produsRepository.findByDenumire(produs.getDenumire()) != null;
    }
    @Override
    public void adaugaProdus(String denumire, float pret, int stoc) {
        Produs produs = new Produs(denumire, pret, stoc);
        try{
            produsValidator.validate(produs);
        } catch (ValidationException ex){
            throw new ServiceException(ex.getMessage());
        }
        if(exista(produs)){
            throw new ServiceException("Exista deja un produs cu aceeasi denumire!");
        }

        produsRepository.add(produs);
        notifyObservers(new ChangeEvent(ChangeEventType.ADD, produs));

    }

    @Override
    public void stergeProdus(Produs produs) {
        try {
            produsValidator.validate(produs);
        } catch (ValidationException ex){
            throw new ServiceException(ex.getMessage());
        }
        if(!exista(produs)){
            throw new ServiceException("Nu exista un produs cu acesta denumire!");
        }
        produsRepository.delete(produs);
        notifyObservers(new ChangeEvent(ChangeEventType.DELETE, produs));
    }

    @Override
    public void updateProdus(String denumire, float pretNou, int stocNou) {
        Produs produs = produsRepository.findByDenumire(denumire);
        if(pretNou != 0 ) {
            produs.setPret(pretNou);
        }
        if(stocNou != 0){
            produs.setStoc(stocNou);
        }
            try {
                produsValidator.validate(produs);
            } catch (ValidationException ex){
                throw new ServiceException(ex.getMessage());
            }

        if(!exista(produs)){
            throw new ServiceException("Nu exista un produs cu acesta denumire!");
        }
        produsRepository.update(produs);
        notifyObservers(new ChangeEvent(ChangeEventType.UPDATE, produs));


    }

    @Override
    public Iterable<Produs> findAllProduse() {
        return produsRepository.findAll();
    }


    @Override
    public boolean exista(Utilizator crtUser) {
        return utilizatorRepository.findByUsername(crtUser.getUsername()) != null;
    }

    @Override
    public Utilizator getUserByUsername(String username) {
        return utilizatorRepository.findByUsername(username);
    }

    @Override
    public int adaugaComanda(Comanda comanda) {
        Utilizator utilizatorFaraId = comanda.getAgent();
        Utilizator utilizatorCuId = utilizatorRepository.findByUsername(utilizatorFaraId.getUsername());
        comanda.setAgent(utilizatorCuId);
        return comandaRepository.add(comanda);

    }

    @Override
    public void adaugaProdusComanda(int idComanda, List<ProdusComanda> produse) {
        for(ProdusComanda produsComanda:produse){
            Comanda comanda = comandaRepository.findById(idComanda);
            produsComanda.setComanda(comanda);
            produsComandaRepository.add(produsComanda);
            Produs produs = produsComanda.getProdus();
            int stoc_curent = produs.getStoc();
            produs.setStoc(stoc_curent-produsComanda.getCantitate());
            produsRepository.update(produsComanda.getProdus());
            notifyObservers(new ChangeEvent(ChangeEventType.UPDATE, produsComanda.getProdus()));
        }

    }

    @Override
    public Produs filtreazaProduse(String filtru) {
        return produsRepository.findByDenumire(filtru);
    }

    @Override
    public void addObserver(Observer<ChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<ChangeEvent> e) {
        observers.remove(e);

    }

    @Override
    public void notifyObservers(ChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }
}

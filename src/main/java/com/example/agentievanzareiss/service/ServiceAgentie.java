package com.example.agentievanzareiss.service;

import com.example.agentievanzareiss.model.Produs;
import com.example.agentievanzareiss.model.validators.ValidationException;
import com.example.agentievanzareiss.model.validators.Validator;
import com.example.agentievanzareiss.repository.ProdusRepository;
import com.example.agentievanzareiss.utils.events.ChangeEvent;
import com.example.agentievanzareiss.utils.events.ChangeEventType;
import com.example.agentievanzareiss.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ServiceAgentie implements Service {

    private Validator<Produs> produsValidator;

    private ProdusRepository produsRepository;

    private List<Observer<ChangeEvent>> observers = new ArrayList<>();

    public ServiceAgentie(Validator<Produs> produsValidator, ProdusRepository produsRepository){
        this.produsValidator = produsValidator;
        this.produsRepository = produsRepository;

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
    public void login(String username, String password) {

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

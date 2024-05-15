package com.example.agentievanzareiss.service;
public class ServiceException extends RuntimeException {
    public ServiceException(){}

    public ServiceException(String mesaj){
        super(mesaj);
    }
}

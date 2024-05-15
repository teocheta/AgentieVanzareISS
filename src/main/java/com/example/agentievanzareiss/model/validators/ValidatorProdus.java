package com.example.agentievanzareiss.model.validators;

import com.example.agentievanzareiss.model.Produs;

import java.util.Objects;

public class ValidatorProdus implements Validator<Produs>{
    @Override
    public void validate(Produs entity) throws ValidationException {
        String errorMessage = "";
        if (Objects.equals(entity.getDenumire(), "")) {
            errorMessage += "Denumire invalida!\n";
        }
        if (entity.getPret() <= 0.00) {
            errorMessage += "Pret invalid!\n";
        }
        if (entity.getStoc() < 0) {
            errorMessage += "Stoc invalid!\n";
        }

        if (errorMessage.length() != 0) {
            throw new ValidationException(errorMessage);
        }
    }


}

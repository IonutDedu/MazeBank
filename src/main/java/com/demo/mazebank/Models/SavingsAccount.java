package com.demo.mazebank.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class SavingsAccount extends Account{
    // the withdrawal limit from the savings account
    private final DoubleProperty withdrawalLimit;

    public SavingsAccount(String owner, String accountNumber, double balance, double withdrawalLimit){
        super(owner, accountNumber, balance);
        this. withdrawalLimit = new SimpleDoubleProperty(this, "Withdrawal Limit", withdrawalLimit);
    }

    public DoubleProperty withdrawalLimitProp(){
        return this.withdrawalLimit;
    }

    @Override
    public String toString(){
        return accountNumberProperty().get();
    }
}

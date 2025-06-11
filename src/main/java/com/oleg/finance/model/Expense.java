package com.oleg.finance.model;

import java.time.LocalDate;

public class Expense extends Transaction {

    public Expense(double amount, Category category, LocalDate date, String description) {
        super(amount, category, date, description);
    }

    @Override
    public String getType() {
        return "Expense";
    }
}

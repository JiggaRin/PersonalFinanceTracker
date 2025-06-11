package com.oleg.finance.model;

import java.time.LocalDate;

public class Income extends Transaction {

    public Income(double amount, Category category, LocalDate date, String description) {
        super(amount, category, date, description);
    }

    @Override
    public String getType() {
        return "Income";
    }

}

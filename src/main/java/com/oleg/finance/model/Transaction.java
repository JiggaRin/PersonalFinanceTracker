package com.oleg.finance.model;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Transaction {

    private final UUID id;
    private double amount;
    private Category category;
    private LocalDate date;
    private String description;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Transaction(double amount, Category category, LocalDate date, String description) {
        this.id = UUID.randomUUID();
        this.setAmount(amount);
        this.setCategory(category);
        this.setDate(date);
        this.setDescription(description);
    }

    public UUID getId() {
        return this.id;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.amount = amount;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        this.category = category;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        if (date == null || date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be null or in the future");
        }
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description : "";
    }

    public abstract String getType();

    @Override
    public String toString() {
        return String.format("%s: amount = %.2f, category = %s, date = %s, description = %s",
                getType(), amount, category.getName(), date, description);
    }
}

package com.oleg.finance.model;

public class Category {

    private String name;
    private double budgetLimit;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Category(String name, double budgetLimit) {
        this.setName(name);
        this.setBudgetLimit(budgetLimit);
    }

    public Category (String name) {
        this.name = name;
        this.budgetLimit = 0;
    }

    public String getName() {
        return this.name;
    }

    public double getBudgetLimit() {
        return this.budgetLimit;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public void setBudgetLimit(double budgetLimit) {
        if(budgetLimit < 0) {
            throw new IllegalArgumentException("Budget limit cannot be negative");
        }
        this.budgetLimit = budgetLimit;
    }

    @Override
    public String toString() {
        return String.format("Category: name = %s, budgetLimit = %.2f", name, budgetLimit);
    }
}
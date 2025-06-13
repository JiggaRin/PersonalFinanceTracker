package com.oleg.finance.model;

import java.util.Objects;

public class Category {
    private String name;
    private final double budgetLimit;

    public Category(String name, double budgetLimit) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        if (budgetLimit < 0) {
            throw new IllegalArgumentException("Budget limit cannot be negative");
        }
        this.name = name.trim();
        this.budgetLimit = budgetLimit;
    }

    public Category(String name) {
        this(name, 0.0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Double.compare(category.budgetLimit, budgetLimit) == 0 &&
                name.equalsIgnoreCase(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase(), budgetLimit);
    }

    @Override
    public String toString() {
        return "Category{name='" + name + "', budgetLimit=" + budgetLimit + "}";
    }
}
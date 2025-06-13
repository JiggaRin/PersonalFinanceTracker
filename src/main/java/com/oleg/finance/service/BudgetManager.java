package com.oleg.finance.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.oleg.finance.model.Category;
import com.oleg.finance.model.Expense;
import com.oleg.finance.model.Transaction;

public class BudgetManager {
    private static final BudgetManager instance = new BudgetManager();
    private final Set<Transaction> transactions;
    private final Set<Category> categories;
    private final List<BudgetObserver> observers;

    private BudgetManager() {
        transactions = new HashSet<>();
        categories = new HashSet<>();
        observers = new ArrayList<>();
    }

    public static BudgetManager getInstance() {
        return instance;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        if (!categories.contains(transaction.getCategory())) {
            throw new IllegalArgumentException("Category must exist in the system");
        }
        transactions.add(transaction);
        if (transaction instanceof Expense) {
            notifyBudgetObservers(transaction.getCategory());
        }
    }

    public void addCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (categories.stream().anyMatch(c -> c.getName().equalsIgnoreCase(category.getName()))) {
            throw new IllegalArgumentException("Category name already exists");
        }
        categories.add(category);
    }

    public Category getCategoryByName(String name) {
        if (name == null) {
            return null;
        }
        return categories.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public Set<Category> getCategories() {
        return new HashSet<>(categories);
    }

    public void registerObserver(BudgetObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public List<BudgetObserver> getObservers() {
        return new ArrayList<>(observers);
    }

    private void notifyBudgetObservers(Category category) {
        if (category.getBudgetLimit() <= 0) {
            return;
        }
        double totalSpent = calculateTotalExpenses(category);
        if (totalSpent >= category.getBudgetLimit() * 0.9) {
            for (BudgetObserver observer : observers) {
                observer.onBudgetLimitApproached(category.getName(), totalSpent, category.getBudgetLimit());
            }
        }
    }

    private double calculateTotalExpenses(Category category) {
        double total = transactions.stream()
                .filter(t -> t instanceof Expense && t.getCategory().equals(category))
                .mapToDouble(Transaction::getAmount)
                .sum();
        return total;
    }
}
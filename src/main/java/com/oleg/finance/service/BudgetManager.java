package com.oleg.finance.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.oleg.finance.model.Category;
import com.oleg.finance.model.Expense;
import com.oleg.finance.model.Transaction;

public class BudgetManager {

    private static BudgetManager instance;
    private final List<Transaction> transactions;
    private final Set<Category> categories;
    private final List<BudgetObserver> observers;

    private BudgetManager() {
        this.transactions = new ArrayList<>();
        this.categories = new HashSet<>();
        this.observers = new ArrayList<>();
    }

    public static synchronized BudgetManager getInstance() {
        if (instance == null) {
            instance = new BudgetManager();
        }
        return instance;
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(this.transactions);
    }

    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(this.categories);
    }

    public void registerObserver(BudgetObserver observer) {
        if(observer != null) {
            observers.add(observer);
        }
    }

    public void unregisterObserver(BudgetObserver observer) {
        observers.remove(observer);
    }

    private double calculateTotalExpenses(Category category) {
        return transactions.stream().filter(t -> t instanceof Expense && t.getCategory().equals(category)).mapToDouble(Transaction::getAmount).sum();
    }

    private void notifyBudgetObservers(Category category) {
        if (category.getBudgetLimit() <= 0) {
            return;
        }
        double totalSpent = this.calculateTotalExpenses(category);
        if(totalSpent >= category.getBudgetLimit() * 0.9) {
            for (BudgetObserver observer : observers) {
                observer.onBudgetLimitApproached(category.getName(), totalSpent, totalSpent);
            }
        }
    }

    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        if (!this.categories.contains(transaction.getCategory())) {
            throw new IllegalArgumentException("Category must exist in the system");
        }
        transactions.add(transaction);
        if (transaction instanceof Expense) {
            notifyBudgetObservers(transaction.getCategory());
        }
    }

    public Transaction getTransactionById(UUID transactionId) {
        if (transactionId == null) {
            return null;
        }

        return transactions.stream().filter(transaction -> transaction.getId().equals(transactionId))
                .findFirst()
                .orElse(null);
    }

    public void updateTransaction(UUID transactionId, double amount, Category category, LocalDate date, String description) {
        Transaction transaction = this.getTransactionById(transactionId);
        if (transactionId == null) {
            throw new IllegalArgumentException("Transaction ID cannot be null");
        }

        if (!this.categories.contains(transaction.getCategory())) {
            throw new IllegalArgumentException("Category must exist in the system");
        }

        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setDate(date);
        transaction.setDescription(description);
        if (transaction instanceof Expense) {
            notifyBudgetObservers(transaction.getCategory());
        }
    }

    public void deleteTransaction(UUID transactionId) {
        Transaction transaction = getTransactionById(transactionId);

        if (transaction == null) {
            throw new IllegalArgumentException("Transaction not found");
        }

        transactions.remove(transaction);
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

    public Category getCategoryByName(String categoryName) {
        if (categoryName == null) {
            throw new IllegalArgumentException("Category name cannot be null");
        }

        return categories.stream().filter(c -> c.getName().equalsIgnoreCase(categoryName)).findFirst().orElse(null);
    }

    public void updateCategory(String oldName, String newName, double budgetLimit) {
        Category existedCategory = getCategoryByName(oldName);
        if (existedCategory == null) {
            throw new IllegalArgumentException("Category not found");
        }
        if (!oldName.equalsIgnoreCase(newName)
                && categories.stream().anyMatch(c -> c.getName().equalsIgnoreCase(newName))) {
            throw new IllegalArgumentException("Desired category name already exists");
        }

        existedCategory.setName(newName);
        existedCategory.setBudgetLimit(budgetLimit);
    }

    public void deleteCategory(String categoryName) {
        Category category = getCategoryByName(categoryName);
        if (category == null) {
            throw new IllegalArgumentException("Category not found");
        }
        if (transactions.stream().anyMatch(t -> t.getCategory().equals(category))) {
            throw new IllegalArgumentException("Cannot delete category referenced by transactions");
        }
        categories.remove(category);
    }
}

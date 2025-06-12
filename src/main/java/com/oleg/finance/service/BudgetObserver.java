package com.oleg.finance.service;

public interface BudgetObserver {

    void onBudgetLimitApproached(String categoryName, double totalSpent, double budgetLimit);
}

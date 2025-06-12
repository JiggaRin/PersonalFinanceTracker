package com.oleg.finance.service;

public class ConsoleBudgetObserver implements BudgetObserver {

    @Override
    public void onBudgetLimitApproached(String categoryName, double totalSpent, double budgetLimit) {
        if (totalSpent > budgetLimit) {
            System.out.printf("ERROR: Category '%s' has exceeded budget limit of %.2f. Total spent: %.2f%n",
                    categoryName, budgetLimit, totalSpent);
        } else if (totalSpent < budgetLimit * 0.9) {
            System.out.printf("WARNING: Category '%s' is approaching budget limit of %.2f. Total spent: %.2f%n",
                    categoryName, budgetLimit, totalSpent);
        }
    }
}

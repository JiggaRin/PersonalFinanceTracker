package com.oleg.finance.command;

import com.oleg.finance.model.Category;
import com.oleg.finance.service.BudgetManager;

public class ViewCategoriesCommand implements Command {
    private final BudgetManager budgetManager;

    public ViewCategoriesCommand(BudgetManager budgetManager) {
        this.budgetManager = budgetManager;
    }

    @Override
    public void execute() {
        System.out.println("\nCategories:");
        System.out.println("Name | Budget Limit");
        System.out.println("-----|-------------");
        for (Category c : budgetManager.getCategories()) {
            System.out.printf("%s | %.2f%n", c.getName(), c.getBudgetLimit());
        }
    }
}
package com.oleg.finance.command;

import java.util.Scanner;

import com.oleg.finance.model.Category;
import com.oleg.finance.service.BudgetManager;

public class AddCategoryCommand implements Command {
    private final BudgetManager budgetManager;
    private final Scanner scanner;

    public AddCategoryCommand(BudgetManager budgetManager, Scanner scanner) {
        this.budgetManager = budgetManager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter category name: ");
        String name = scanner.nextLine().trim();

        System.out.println("Enter budget limit (0 for none): ");
        double budgetLimit;
        try {
            budgetLimit = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid budget limit. Must be a number.");
            return;
        }

        try {
            Category category = new Category(name, budgetLimit);
            budgetManager.addCategory(category);
            System.out.println("Category added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
package com.oleg.finance.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.oleg.finance.model.Category;
import com.oleg.finance.model.Expense;
import com.oleg.finance.model.Income;
import com.oleg.finance.model.Transaction;
import com.oleg.finance.service.BudgetManager;

public class AddTransactionCommand implements Command {
    private final BudgetManager budgetManager;
    private final Scanner scanner;

    public AddTransactionCommand(BudgetManager budgetManager, Scanner scanner) {
        this.budgetManager = budgetManager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter transaction type (Income/Expense): ");
        String type = scanner.nextLine().trim();
        if (!type.equalsIgnoreCase("Income") && !type.equalsIgnoreCase("Expense")) {
            System.out.println("Invalid type. Use 'Income' or 'Expense'.");
            return;
        }

        System.out.println("Enter amount: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Must be a number.");
            return;
        }

        System.out.println("Enter category name: ");
        String categoryName = scanner.nextLine().trim();
        Category category = budgetManager.getCategoryByName(categoryName);
        if (category == null) {
            System.out.println("Category not found.");
            return;
        }

        System.out.println("Enter date (YYYY-MM-DD): ");
        LocalDate date;
        try {
            date = LocalDate.parse(scanner.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
            return;
        }

        System.out.println("Enter description: ");
        String description = scanner.nextLine().trim();

        Transaction transaction = type.equalsIgnoreCase("Income")
                ? new Income(amount, category, date, description)
                : new Expense(amount, category, date, description);
        try {
            budgetManager.addTransaction(transaction);
            System.out.println("Transaction added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
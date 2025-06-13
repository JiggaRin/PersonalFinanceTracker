package com.oleg.finance.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.oleg.finance.service.BudgetManager;

public class ConsoleInterface {
    @SuppressWarnings("unused")
    private final BudgetManager budgetManager;
    private final Scanner scanner;
    private final Map<Integer, Command> commands;

    public ConsoleInterface(BudgetManager budgetManager, Scanner scanner) {
        this.budgetManager = budgetManager;
        this.scanner = scanner;
        this.commands = new HashMap<>();
        commands.put(1, new AddTransactionCommand(budgetManager, scanner));
        commands.put(2, new AddCategoryCommand(budgetManager, scanner));
        commands.put(3, new ViewTransactionsCommand(budgetManager));
        commands.put(4, new ViewCategoriesCommand(budgetManager));
    }

    public void run() {
        while (true) {
            displayMenu();
            System.out.print("Select an option (1-5): ");
            try {
                int option = Integer.parseInt(scanner.nextLine().trim());
                if (option == 5) {
                    System.out.println("Exiting...");
                    break;
                }
                Command command = commands.get(option);
                if (command != null) {
                    command.execute();
                } else {
                    System.out.println("Invalid option. Please select 1-5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nPersonal Finance Tracker");
        System.out.println("1. Add Transaction");
        System.out.println("2. Add Category");
        System.out.println("3. View Transactions");
        System.out.println("4. View Categories");
        System.out.println("5. Exit");
    }
}
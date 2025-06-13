package com.oleg.finance;

import java.util.Scanner;

import com.oleg.finance.command.ConsoleInterface;
import com.oleg.finance.service.BudgetManager;
import com.oleg.finance.service.ConsoleBudgetObserver;

public class App {

    public static void main(String[] args) {
        BudgetManager budgetManager = BudgetManager.getInstance();
        budgetManager.registerObserver(new ConsoleBudgetObserver());
        try (Scanner scanner = new Scanner(System.in)) {
            ConsoleInterface consoleInterface = new ConsoleInterface(budgetManager, scanner);
            consoleInterface.run();
        }
    }
}

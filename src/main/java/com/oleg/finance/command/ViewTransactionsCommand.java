package com.oleg.finance.command;

import com.oleg.finance.model.Transaction;
import com.oleg.finance.service.BudgetManager;

public class ViewTransactionsCommand implements Command{
    private final BudgetManager budgetManager;


    public ViewTransactionsCommand(BudgetManager budgetManager) {
        this.budgetManager = budgetManager;
    }

    @Override
    public void execute() {
        System.out.println("\nTransactions:");
        System.out.println("Type    | Amount | Category | Date       | Description");
        System.out.println("--------|--------|----------|------------|------------");
        for (Transaction t : budgetManager.getTransactions()) {
            System.out.printf("%s | %.2f | %s | %s | %s%n",
                    t.getType(), t.getAmount(), t.getCategory().getName(),
                    t.getDate(), t.getDescription());
        }
    }
}
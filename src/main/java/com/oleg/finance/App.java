package com.oleg.finance;

import java.time.LocalDate;

import com.oleg.finance.model.Category;
import com.oleg.finance.model.Expense;
import com.oleg.finance.model.Income;
import com.oleg.finance.service.BudgetManager;
import com.oleg.finance.service.ConsoleBudgetObserver;

public class App 
{
     public static void main(String[] args) {
        BudgetManager manager1 = BudgetManager.getInstance();
        BudgetManager manager2 = BudgetManager.getInstance();
        System.out.println("Is Singleton: " + (manager1 == manager2));

        manager1.registerObserver(new ConsoleBudgetObserver());

        Category food = new Category("Food", 500.0);
        Category salary = new Category("Salary");
        manager1.addCategory(food);
        manager1.addCategory(salary);

        Income income = new Income(1000.0, salary, LocalDate.now(), "Monthly salary");
        Expense expense1 = new Expense(300.0, food, LocalDate.now(), "Grocery shopping");
        Expense expense2 = new Expense(250.0, food, LocalDate.now(), "Restaurant bill");
        manager1.addTransaction(income);
        manager1.addTransaction(expense1);
        manager1.addTransaction(expense2);

        manager1.updateTransaction(expense1.getId(), 200.0, food, LocalDate.now(), "Updated grocery shopping");

        manager1.deleteTransaction(expense2.getId());

        System.out.println("\nTransactions:");
        manager1.getTransactions().forEach(System.out::println);
        System.out.println("\nCategories:");
        manager1.getCategories().forEach(System.out::println);
    }
}

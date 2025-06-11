package com.oleg.finance;

import java.time.LocalDate;

import com.oleg.finance.model.Category;
import com.oleg.finance.model.Expense;
import com.oleg.finance.model.Income;

public class App 
{
    public static void main( String[] args )
    {
        // Test the model classes
        Category food = new Category("Food", 500.0);
        Category salary = new Category("Salary");

        Income income = new Income(1000.0, salary, LocalDate.now(), "Monthly salary");
        Expense expense = new Expense(150.0, food, LocalDate.now(), "Grocery shopping");

        System.out.println();
        System.out.println(income);
        System.out.println(expense);
        System.out.println(food);
    }
}

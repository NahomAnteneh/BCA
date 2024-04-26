package test;

import bank.Customer;
import bank.Transaction;

public class Test {
    public static void main(String[] args) {
        char[] pass = {'1', '2', '3'};
        Customer cust = new Customer("Nahom", "Nahom", 1000, "Current", "nn", pass);

        System.out.println(hasTransactions(cust.getTransactions()));
    }

    public static boolean hasTransactions(Transaction[] transactions) {
        for (Transaction transaction : transactions) {
            if (transaction != null) {
                return true; // At least one non-null element found
            }
        }
        return false; // No non-null elements found
    }
}
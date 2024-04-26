package test;

import java.util.Formatter;

import bank.Customer;
import bank.Database;

public class Test {
    public static void main(String[] args) {
        char[] pass = {'1', '2', '3'};
        Customer cust = new Customer("Nahom", "Anteneh", 2000, "Saving", "nahom", pass);
        Database.addToCustomers(cust);
        
        cust.transferFunds(0, 20160000, 100, "HI");

        Formatter format0 = new Formatter();
        Formatter format1 = new Formatter();

        format0.format("%s %30s %20s %20s %20s %20s", "TransactionID", "FromAccountNo", "ToAccountsNo", "Amount", "Timestamp", "Description");
        System.out.println(format0);
        format1.format("%s %30s %20s %20s %20s %20s", cust.getTransactions()[0].getTransactionId().toString(), "10001421", "103082", "100", "8932489", "SEX");
        System.out.println(format1);
    }
}

package bank.AccountType;

import bank.Account;

public class Womens extends Account {
    private static double interestRate = 0.116;

    public Womens(double balance) {
        super(balance += balance*interestRate);
    }

    public String getAccountType() {
        return "Womens";
    }
    
}
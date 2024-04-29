package bank.AccountType;

import bank.Account;

public class Current extends Account {
    private static double interestRate = 0.032;

    public Current(double balance) {
        super(balance += balance*interestRate);
    }

    public String getAccountType() {
        return "Current";
    }
    
}

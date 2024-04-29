package bank.AccountType;

import bank.Account;

public class Saving extends Account {
    private static double interestRate = 0.073;

    public Saving(double balance) {
        super(balance += balance*interestRate);
    }
    
    public String getAccountType() {
        return "Saving";
    }
}

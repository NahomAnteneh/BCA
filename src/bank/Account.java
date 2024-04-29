package bank;
import java.util.Date;

public abstract class Account {
    private int accountNo;
    private double balance;
    private String accountCreatedDate;
    private static int generalAccountNo = 20160000;

    public Account(double balance) {
        Date date = new Date();
        this.accountNo = generalAccountNo++;
        this.balance = balance;
        accountCreatedDate = date.toString();
    }

    public int getAccountNo() {
        return accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public void updataBalance(double amount) {
        balance += amount;
    }

    public abstract String getAccountType();

    public String getCreationDate() {
        return accountCreatedDate;
    }
}
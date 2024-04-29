package bank;
import java.util.Date;

public class Account {
    private int accountNo;
    private double balance;
    private String accountType;
    private String accountCreatedDate;
    private static int generalAccountNo = 20160000;

    public Account(double balance, String accountType) {
        Date date = new Date();
        this.accountNo = generalAccountNo++;
        this.balance = balance;
        this.accountType = accountType;
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

    public String getAccountType() {
        return accountType;
    }

    public String getCreationDate() {
        return accountCreatedDate;
    }
}
package bank;

import java.util.ArrayList;
import java.util.Formatter;

import bank.AccountType.Current;
import bank.AccountType.Saving;
import bank.AccountType.Womens;

public class Customer implements Bank {
    private String username;
    private char[] password;
    private String firstName;
    private String lastName;
    private ArrayList<Account> account = new ArrayList<>();
    private ArrayList<Transaction> transaction = new ArrayList<>();
    private int accountCounter = 0;
    private int txnCounter = 0;


    public Customer(String firstName, String lastName, double balance, int accountType, String username, char[] password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        
        createAccount(balance, accountType);

        Database.addToLogin(username, password);
    }

    @Override
    public void createAccount(double balance, int accountType) {
        // Account acc = new Account(balance, accountType);
        Account acc;
        if (accountType == 1) {
            acc = new Saving(balance);
            account.add(acc);
        } else if (accountType == 2) {
            acc = new Womens(balance);
            account.add(acc);
        } else {
            acc = new Current(balance);
            account.add(acc);
        }
        Database.addToAccounts(account.getLast());
        accountCounter++;
    }

    @Override
    public void transferFunds(int accountIndex, int toAccountNo, double amount, String description) {
        account.get(accountIndex).updataBalance(-amount);
        Database.getAccount(toAccountNo).updataBalance(amount);
        Transaction txn = new Transaction(account.get(accountIndex).getAccountNo(), toAccountNo, amount, description);
        // transaction[txnCounter] = txn;
        transaction.add(txn);
        Database.addToTransactions(txn.getTransactionId(), txn);
        txnCounter++;
        // if (txnCounter >= 10) {
        //     txnCounter = 0;
        // }
    }

    @Override
    public void changeUsername(String username) {
        Database.removeCustomer(this.username);
        Database.removeCredential(this.username);

        this.username = username;
        Database.addToCustomers(this);
        Database.addToLogin(username, this.password);
    }

    @Override
    public void changePassword(char[] password) {
        Database.removeCredential(username);
        this.password = password;
        Database.addToLogin(username, password);
    }

    @Override
    public void deleteAccount(int index) {
        Database.removeAccount(account.get(index).getAccountNo());
        account.remove(index);
        accountCounter--;
    }

    public int getNoOfAccounts() {
        return accountCounter;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ArrayList<Transaction> getTransactions() {
        return transaction;
    }

    public ArrayList<Account> getAccounts() {
        return account;
    }

    public Account getAccount(int accountCounter) {
        // return account[accountCounter];
        return account.get(accountCounter);
    }

    public void displayTransactions(Customer customer) {
        ArrayList<Transaction> transactions = customer.getTransactions();
        int colWidth = 25;
        
        Formatter formatter = new Formatter(System.out);
        formatter.format("%-" + colWidth + "s%-" + colWidth + "s%-" + colWidth + "s%-" + colWidth + "s%-" + colWidth + "s%-" + colWidth + "s%n", "TransactionID", "FromAccountNo", "ToAccountsNo", "Amount", "Timestamp", "Description");
    
        for (Transaction transaction : transactions) {
            formatter.format("%-" + colWidth + "s%-" + colWidth + "s%-" + colWidth + "s%-" + colWidth + ".2f%-" + colWidth + "d%-" + colWidth + "s%n", transaction.getTransactionId(), transaction.getFromAccountNo(), transaction.getToAccountNo(), transaction.getAmount(), transaction.getTimestamp(), transaction.getDescription());
        }
    }  

    public void displayAccounts(Customer customer) {
        ArrayList<Account> accounts = customer.getAccounts();
        int colWidth = 30;
        
        Formatter formatter = new Formatter(System.out);
        formatter.format("%-" + colWidth + "s%-" + colWidth + "s%-" + colWidth + "s%-" + colWidth + "s%n", "Account No", "Balance", "Creation Date", "Account Type");
    
        for (Account account : accounts) {      // this.accountType = accountType;
            formatter.format("%-" + colWidth + "s$%-" + colWidth + ".2f%-" + colWidth + "s%-" + colWidth + "s%n", account.getAccountNo(), account.getBalance(), account.getCreationDate(), account.getAccountType());
        }
    }    
}
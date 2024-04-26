package bank;
public class Customer {
    private String username;
    private String firstName;
    private String lastName;
    private Account[] account = new Account[5];
    private Transaction[] transaction = new Transaction[10];
    private int[] accounts = new int[5];
    private int accountCounter = 0;
    private int txnCounter = 0;


    public Customer(String firstName, String lastName, double balance, String accountType, String username, char[] password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        
        createAccount(balance, accountType);

        Database.addToLogin(username, password);
    }

    public void createAccount(double balance, String accountType) {
        Account acc = new Account(balance, accountType);
        account[accountCounter] = acc;
        accounts[accountCounter] = acc.getAccountNo();
        Database.addToAccounts(account[accountCounter]);
        accountCounter++;
    }

    public void transferFunds(int accountIndex, int toAccountNo, double amount, String description) {
        account[accountIndex].updataBalance(-amount);
        Database.getAccount(toAccountNo).updataBalance(amount);
        Transaction txn = new Transaction(account[accountIndex].getAccountNo(), toAccountNo, amount, description);
        transaction[txnCounter] = txn;
        Database.addToTransactions(txn.getTransactionId(), txn);
        txnCounter++;
        if (txnCounter >= 10) {
            txnCounter = 0;
        }
    }

    public void settings(int choice) {

    }

    public void closeAccount() {
        
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

    public Transaction[] getTransactions() {
        return transaction;
    }

    public Account getAccount(int accountCounter) {
        return account[accountCounter];
    }
}
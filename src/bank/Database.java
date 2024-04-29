package bank;
import java.util.HashMap;

public class Database {
    private static HashMap<String, Customer> customers = new HashMap<>();
    private static HashMap<Integer, Account> accounts = new HashMap<>();
    private static HashMap<String, char[]> credentials = new HashMap<>();
    private static HashMap<String, Transaction> transactions = new HashMap<>();

    public static void addToCustomers(Customer customer) {
        customers.put(customer.getUsername(), customer);
    }

    public static void addToLogin(String username, char[] password) {
        credentials.put(username, password);
    }

    public static void addToAccounts(Account account) {
        accounts.put(account.getAccountNo(), account);
    }

    public static void addToTransactions(String transactionID, Transaction transaction) {
        transactions.put(transactionID, transaction);
    }

    public static void removeCustomer(String username) {
        customers.remove(username);
    }

    public static void removeAccount(int accountNo) {
        accounts.remove(accountNo);
    }

    public static void removeCredential(String username) {
        credentials.remove(username);
    }

    public static Customer getCustomer(String username) {
        return customers.get(username);
    }

    public static Account getAccount(int accountNo) {
        return accounts.get(accountNo);
    }

    public static char[] getPassword(String username) {
        return credentials.get(username);
    }

    public static boolean usernameExists(String username) {
        return credentials.containsKey(username);
    }

    public static boolean accountExists(int accountNo) {
        return accounts.containsKey(accountNo);
    }
}

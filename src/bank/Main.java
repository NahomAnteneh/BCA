package bank;

import java.io.Console;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Main {
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        mainMenu();
    }

    private static void mainMenu() {
        int choice;
        clearScreen();
        System.out.println("======== Welcome to Bicbank. just a Bank! ========");
        System.out.println("Please choose the number you want to do.");
        System.out.println("1. Sign Up.");
        System.out.println("2. Login.");
        System.out.println("3. About Us.");
        System.out.println("4. Exit.");
        System.out.print("Enter your choice: ");
        choice = input.nextInt();

        switch (choice) {
            case 1:
                signUpMenu();
                break;
            case 2:
                loginMenu();
                break;
            case 3:
                aboutMenu();
                break;
            case 4:
                System.exit(0);
            default:
                System.out.println("Please Enter a Valid Choice (1 - 4)");
                break;
        }
    }

    private static void signUpMenu() {
        String firstName;
        String lastName;
        String username;
        char[] password;
        char[] confirmPasswd;
        // int accountNo;
        float balance;
        String accountType;

        clearScreen();
        System.out.print("First Name: ");
        firstName = input.next();
        System.out.print("Last Name: ");
        lastName = input.next();
        // System.out.print("account no: ");
        // accountNo = input.nextInt();
        System.out.print("Starting Balance: ");
        balance = input.nextFloat();
        
        accountType = chooseAccountType();

        do {
            System.out.print("Create Username: ");
            username = input.next();
            if (Database.usernameExists(username)) {
                JOptionPane.showMessageDialog(null, "Username Already Exists Try Another!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                break;
            }
        } while (!Database.usernameExists(username));

        do {
            System.out.print("Enter Password: ");
            password = readPasswordSecurely();
            System.out.print("Confirm Password: ");
            confirmPasswd = readPasswordSecurely();
            if (!Arrays.equals(password, confirmPasswd)) {
                System.out.println("Passwords don't match, try again!");
            } else {
                System.out.println("Passwords Match!");
                break;
            }
        } while (true);

        Customer customer = new Customer(firstName, lastName, balance, accountType, username, password);
        Database.addToCustomers(customer);
        System.out.println("Sign up Successful.");
        System.out.println("Your account number is '" + customer.getAccount(0).getAccountNo() + "'");

        loginMenu();
    }

    private static void homeMenu(Customer customer) {
        int choice;
        
        do {
            clearScreen();
            System.out.println("======== HOME MENU ========");
            System.out.println("Welcome " + customer.getFirstName());
            System.out.println("1. Check Balance");
            System.out.println("2. Transfer Funds");
            System.out.println("3. Apply for Loan");
            System.out.println("4. Create another account");
            System.out.println("5. My Accounts");
            System.out.println("6. Transaction History");
            System.out.println("7. Settings");
            System.out.println("7. Logout");
        
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
        
            switch (choice) {
                case 1:
                    System.out.println("Please choose which account you want to check balance.");
                    choice = chooseAccount(customer);
                    JOptionPane.showMessageDialog(null, "Yor  Account Balance is " + customer.getAccount(choice-1).getBalance());
                    break;
                case 2:
                    double amount;
                    int accNo;
                    String description;
                    System.out.println("Please choose account you want to transfer from.");
                    choice = chooseAccount(customer);
                    System.out.println("Please enter the account you want to transfer to.");
                    accNo = input.nextInt();
                    if (!Database.accountExists(accNo)) {
                        JOptionPane.showMessageDialog(null, "The account number you entered doesn't exist.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    System.out.println("Please enter the amount you want to transfer.");
                    amount = input.nextDouble();
                    if (customer.getAccount(choice-1).getBalance() < amount) {
                        JOptionPane.showMessageDialog(null, "the amount you entered is more than your balance!", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    System.out.println("Enter description for the transfer.");
                    description = input.next();
                    customer.transferFunds((choice - 1), accNo, amount, description);
                    break;
                case 3:
                    System.out.println("This Service is yet to be started!");
                    break;
                case 4:
                    if (customer.getNoOfAccounts() >= 5) {
                        JOptionPane.showMessageDialog(null, "You cannot create more than 5 accounts!", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    String accountType = chooseAccountType();
                    System.out.print("Enter Strting Balance: ");
                    double balance = input.nextDouble();
                    customer.createAccount(balance, accountType);
                    break;
                case 5:
                    // Display account no and balance
                    break;
                case 6:
                    Transaction[] txn = customer.getTransactions();
                    if (hasTransactions(txn)) {
                        displayTransactions(customer);
                    }
                    break;
                case 7:
                    settingsMenu(customer);
                    break;
                case 8:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }  while (true);
    }    

    private static void aboutMenu() {

    }

    private static void loginMenu() {
        String username;
        char[] password;
        System.out.println("======== Welcome Back to the Login Page =======");
        System.out.print("Username: ");
        username = input.next();
        System.out.print("Password: ");
        password = readPasswordSecurely();

        if (Authenticate.authenticate(username, password)) {
            homeMenu(Database.getCustomer(username));
        } else {
            System.out.println("Incorrect Username or password!");
        }
    }

    public static void settingsMenu(Customer customer) {
        System.out.println("======== Sttings ========");
        System.out.println("1. Change Username");
        System.out.println("2. Change Password");
        System.out.println("3. Delete Account");
        System.out.println("4. Exit");

        // Implement the Switch Case
    }

    private static char[] readPasswordSecurely() {
        Console console = System.console();
        return console.readPassword();
    }

    private static void logout() {
        clearScreen();
        mainMenu();
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static int chooseAccount(Customer customer) {
        int choice;
        for (int i = 0; i < customer.getNoOfAccounts(); i++) {
            System.out.println(i+1 + ". " + customer.getAccount(i).getAccountNo());
        }
        System.out.print("Choose: ");
        choice = input.nextInt();
        if (choice < 0 && choice > customer.getNoOfAccounts()) {
            System.out.println("You Entered Account that doesn't exist. using the default.");
            choice = 1;
        }

        return choice;
    }

    private static String chooseAccountType() {
        String accountType;
        int chooseType;

        System.out.println("1. Saving");
        System.out.println("2. Current");
        System.out.println("3. Cheking");
        System.out.println("4. Womens");
        System.out.print("Choose Type (default='Saving'): ");
        chooseType = input.nextInt();
        switch (chooseType) {
            case 1:
                accountType = "Saving";
                break;
            case 2:
                accountType = "Current";
                break;
            case 3: 
                accountType = "Cheking";
                break;
            case 4:
                accountType = "Womens";
            default:
                accountType = "Saving";
                break;
        }
        return accountType;
    }

    private static void displayTransactions(Customer customer) {
        Transaction[] transactions = new Transaction[10];
        transactions = customer.getTransactions();
        int colWidth = 25;
        Formatter formatter = new Formatter(System.out);
        formatter.format("%-" + colWidth + "s%-" + colWidth + "s%-" + colWidth + "s%-" + colWidth + "s%-" + colWidth + "s%-" + colWidth + "s%n", "TransactionID", "FromAccountNo", "ToAccountsNo", "Amount", "Timestamp", "Description");

        formatter.format("%-" + colWidth + "s%-" + colWidth + "d%-" + colWidth + "d%-" + colWidth + "d%-" + colWidth + "d%-" + colWidth + "s%n", transactions[0].getTransactionId(), transactions[0].getFromAccountNo(), transactions[0].getToAccountNo(), transactions[0].getAmount(), transactions[0].getTimestamp(), transactions[0].getDescription());
        formatter.close();
    }

    private static boolean hasTransactions(Transaction[] transactions) {
        for (Transaction transaction : transactions) {
            if (transaction != null) {
                return true; // At least one non-null element found
            }
        }
        return false; // No non-null elements found
    }
}

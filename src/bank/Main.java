package bank;

import java.io.Console; // for accepting hidden password
import java.io.IOException; // for error handling in IO
import java.util.ArrayList; // for dyanmic array
import java.util.Arrays; // for array comparision
import java.util.Scanner; // for input
import javax.swing.JOptionPane; // for message box

public class Main {
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        mainMenu();
    }

    private static void mainMenu() {
        int choice;
        do {
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
        } while (true);
    }

    private static void signUpMenu() {
        String firstName;
        String lastName;
        String username;
        char[] password;
        char[] confirmPasswd;
        float balance; // to be removed
        int accountType;

        clearScreen();
        System.out.println("======== Signup Menu ========");
        System.out.print("Enter First Name: ");
        firstName = input.next();
        System.out.print("Enter Last Name: ");
        lastName = input.next();
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
        enterToContinue();

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
            System.out.println("8. Logout");
        
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
        
            switch (choice) {
                case 1:
                    System.out.println("Please choose which account you want to check balance.");
                    choice = chooseAccount(customer);
                    double accBalance = customer.getAccount(choice-1).getBalance();
                    JOptionPane.showMessageDialog(null,"Your Account Balance is: " + accBalance);
                    break;
                case 2:
                    double amount;
                    int accNo;
                    String description;
                    System.out.println("Please choose the account you want to transfer from.");
                    choice = chooseAccount(customer);
                    System.out.println("Please enter the account you want to transfer to.");
                    accNo = input.nextInt();
                    if (accNo == customer.getAccount(choice-1).getAccountNo()) {
                        JOptionPane.showMessageDialog(null, "You cannot transfer to the same account!", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    } else if (Database.accountExists(accNo)) {
                        System.out.println("Please enter the amount you want to transfer.");
                        amount = input.nextDouble();
                        if (customer.getAccount(choice-1).getBalance() < amount) {
                            JOptionPane.showMessageDialog(null, "the amount you entered is more than your balance!", "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                        System.out.println("Enter description for the transfer.");
                        description = input.next();
                        customer.transferFunds((choice - 1), accNo, amount, description);
                        System.out.println("Complete.");
                        enterToContinue();
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "The account number you entered doesn't exist.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                case 3:
                    System.out.println("This Service is yet to be implemented!");
                    enterToContinue();
                    break;
                case 4:
                    if (customer.getNoOfAccounts() >= 5) {
                        JOptionPane.showMessageDialog(null, "You cannot create more than 5 accounts!", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    int accountType = chooseAccountType();
                    System.out.print("Enter Starting Balance: ");
                    double balance = input.nextDouble();
                    customer.createAccount(balance, accountType);
                    System.out.println("Account Created Successfully! Your new Account's Account number is " + customer.getAccount(customer.getNoOfAccounts()-1).getAccountNo());
                    enterToContinue();
                    break;
                case 5:
                    customer.displayAccounts(customer);
                    enterToContinue();
                    break;
                case 6:
                    ArrayList<Transaction> txn = customer.getTransactions();
                    if (hasTransactions(txn)) {
                        customer.displayTransactions(customer);
                        enterToContinue();
                        break;
                    } else {
                        System.out.println("You Have No Transactions!");
                        enterToContinue();
                        break;
                    }
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
        clearScreen();
        System.out.println("About Us: ");
        System.out.println("    we are third-year computer science students.");
        System.out.println("Our Team: ");
        System.out.println("[+] Nahom Anteneh           1404607");
        System.out.println("[+] Melaku Azene            1405424");
        System.out.println("[+] Mastewal Loha           1405072");
        System.out.println("[+] Saba Aregawi            1405440");
        System.out.println("[+] Samrawit Solomon        1404491");
        System.out.println("[+] Gerawork Zewdu          1404191");
        System.out.println("Project Objective: ");
        System.out.println("    Our project aims to create a user-friendly bank management system that simplifies banking operations");
        enterToContinue();
    }

    private static void loginMenu() {
        String username;
        char[] password;
        clearScreen();
        System.out.println("======== Welcome to the Login Page =======");
        System.out.print("Enter Username: ");
        username = input.next();
        System.out.print("Enter Password: ");
        password = readPasswordSecurely();

        if (Authenticate.authenticate(username, password)) {
            homeMenu(Database.getCustomer(username));
        } else {
            System.out.println("Incorrect Username or password!");
        }
    }

    public static void settingsMenu(Customer customer) {
        int choice;
        do {
            clearScreen();
            System.out.println("======== Settings ========");
            System.out.println("1. Change Username");
            System.out.println("2. Change Password");
            System.out.println("3. Delete Account");
            System.out.println("4. Main Menu");
            System.out.print("Enter your choice: ");

            choice = input.nextInt();
            switch (choice) {
                case 1:
                    String username;
                    System.out.print("Enter the new username: ");
                    username = input.next();
                    if (username.equals(customer.getUsername())) {
                        System.out.println("You entered the same username as your current username! username not affected.");
                        enterToContinue();
                        break;
                    } else if (Database.usernameExists(username)) {
                        System.out.println("This Username is taken. try other username.");
                        enterToContinue();
                        break;
                    } else {
                        customer.changeUsername(username);
                        System.out.println("Username changed Logging out. log in with the updated credentials.");
                        enterToContinue();
                        logout();
                        break;
                    }
                case 2:
                    char[] password;
                    System.out.print("Enter your current password: ");
                    password = readPasswordSecurely();
                    if (Arrays.equals(password, Database.getPassword(customer.getUsername()))) {
                        System.out.print("Enter your new password: ");
                        password = readPasswordSecurely();
                        customer.changePassword(password);
                        System.out.println("Password changed Logging out. log in with the updated credentials.");
                        enterToContinue();
                        logout();
                        break;
                    } else {
                        System.out.println("Your password is incorrect!");
                        enterToContinue();
                        break;
                    }
                case 3:
                    if (customer.getNoOfAccounts() > 1) {
                        System.out.println("Which account you want to delete?");
                        choice = chooseAccount(customer);
                        int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to proceed?", "Confirmation", JOptionPane.YES_NO_OPTION);

                        if (option == JOptionPane.YES_OPTION) {
                            customer.deleteAccount(choice-1);
                            System.out.println("Account Deleted Successfully.");
                        } else if (option == JOptionPane.NO_OPTION) {
                            System.out.println("Aborted...");
                        } else {
                            System.out.println("Canceled..");
                        }
                        enterToContinue();
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "You cannot delete your only account!", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                case 4:
                    return;
                default:
                    break;
            }
        } while (true);
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

    private static int chooseAccountType() {
        int accountType;
        int chooseType;

        System.out.println("1. Saving");
        System.out.println("2. Womens");
        System.out.println("3. Current");
        System.out.print("Choose Type (default='Saving'): ");
        chooseType = input.nextInt();
        switch (chooseType) {
            case 1:
                accountType = 1;
                break;
            case 2:
                accountType = 2;
                break;
            case 3: 
                accountType = 3;
                break;
            default:
                accountType = 1;
                break;
        }
        return accountType;
    }

    private static boolean hasTransactions(ArrayList<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            if (transaction != null) {
                return true;
            }
        }
        return false;
    }

    private static void enterToContinue() {
        System.out.print("Press Enter to continue ...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
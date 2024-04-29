package test;

import javax.swing.JOptionPane;

import bank.Customer;

public class Test {

    public static void main(String[] args) {
        char[] pass = {'p', 'a', 's', 's'};
        Customer customer = new Customer("FirstName", "LastName", 10000, 1, "uname", pass);

        customer.createAccount(20000, 2);
        int index = customer.getNoOfAccounts();

        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to proceed?", "Confirmation", JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            customer.displayAccounts(customer);
        } else if (choice == JOptionPane.NO_OPTION) {
            System.out.println("Nothing");
        } else {
            System.out.println("Canceled");
        }
    }

    public static void option() {
        // Display a confirmation dialog
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to proceed?", "Confirmation", JOptionPane.YES_NO_OPTION);

        // Check the user's choice
        if (choice == JOptionPane.YES_OPTION) {
            // User clicked Yes
            JOptionPane.showMessageDialog(null, "You clicked Yes. Proceeding...");
            // Perform the action you want to take if the user confirms
        } else if (choice == JOptionPane.NO_OPTION) {
            // User clicked No
            JOptionPane.showMessageDialog(null, "You clicked No. Canceling...");
            // Perform the action you want to take if the user cancels
        } else {
            // User closed the dialog or clicked Cancel
            JOptionPane.showMessageDialog(null, "You closed the dialog or clicked Cancel. Canceling...");
            // Perform the action you want to take if the user cancels
        }
    }
}
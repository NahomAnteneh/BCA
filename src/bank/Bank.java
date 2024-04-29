package bank;

public interface Bank {
    
    public void createAccount(double balance, int accountType);
    public void deleteAccount(int index);
    public void changeUsername(String username);
    public void changePassword(char[] password);
    public void transferFunds(int accountIndex, int toAccountNo, double amount, String description);
}

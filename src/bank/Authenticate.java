package bank;
import java.util.Arrays;

public class Authenticate {

    public static void checkUsername() {
        
    }

    public static void checkPassword() {

    }


    public static boolean authenticate(String username, char[] password) {
        char[] storedPassword = Database.getPassword(username);
        return Arrays.equals(storedPassword, password);
    }

}
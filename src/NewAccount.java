import java.util.Random;

public class NewAccount {
    static String generateAccountNumber() {
        Random random = new Random();
        String accountNumber = ""; // Initialize as an empty string
        String digits = "0123456789";

        for (int i = 0; i < 8; i++) { // Generate a 8-digit account number
            int index = random.nextInt(digits.length());
            char randomChar = digits.charAt(index);
            accountNumber += randomChar;
        }
        return accountNumber;
    }

    static String generatePassword() {
        Random random = new Random();
        String password = ""; // Initialize as an empty string
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890#_!%^&@";

        for (int i = 0; i < 8; i++) { // Generate a 8-character password
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            password += randomChar;
        }
        return password;
    }
}
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Account extends AccessAccount {

    String name;
    String dob;
    double amount;  
    String pass;
    String accountNumber;  

    // Define a list to store account information
    private static List<Account> accounts = new ArrayList<>();

    public Account() {
        this.name = "default";
        this.dob = "01/01/1000";
        this.amount = 0;
        this.pass = "abcdefgh";  
        this.accountNumber = generateAccountNumber(); 
        writeToAccountFile();
    }

    public Account(String name, String dob) {
        // Check if an account with the same name and dob already exists in the file
        if (accountExistsInFile(name, dob)) {
            System.out.println("An account with the same name and date of birth already exists.");
            return; // Do not create a new account
        }

        this.name = name;
        this.dob = dob;
        this.amount = 0;
        this.pass = generatePassword(); 
        this.accountNumber = generateAccountNumber();  
        writeToAccountFile();
    }
    public Account(String name, String dob, double amount) {
        // Check if an account with the same name and dob already exists
        if (accountExistsInFile(name, dob)) {
            System.out.println("An account with the same name and date of birth already exists.");
            return; // Do not create a new account
        }

        this.name = name;
        this.dob = dob;
        this.amount = amount;
        this.pass = generatePassword();  // Generate a random password
        this.accountNumber = generateAccountNumber();  // Initialize account number
        writeToAccountFile(amount);
    }

    public Account(String name, String pass, String action, double amount) {
        this.name = name;
        this.pass = pass;

        if (action.equals("deposit")) {
            if (accountExists(name, pass)) {
                double existingBalance = getExistingBalance(name);
                this.amount = existingBalance + amount;  // Add the deposit to the balance
                // Update the balance in the file
                updateBalanceInFile(name, this.amount);
                // Append the transaction to the balance sheet
                AccessAccount.appendTransactionToBalanceSheet(accountNumber, name, "Deposited", amount, this.amount);
                System.out.println("Deposit successful.");
            } else {
                System.err.println("Account not found or invalid password.");
            }
        } else if (action.equals("withdraw")) {
            if (accountExists(name, pass)) {
                double existingBalance = getExistingBalance(name);
                if (existingBalance >= amount) {
                    this.amount = existingBalance - amount;  // Deduct the withdrawal from the balance
                    // Update the balance in the file
                    updateBalanceInFile(name, this.amount);
                    // Append the transaction to the balance sheet
                    AccessAccount.appendTransactionToBalanceSheet(accountNumber, name, "Withdrawn", amount, this.amount);
                    System.out.println("Withdrawal successful.");
                } else {
                    System.err.println("Insufficient balance for withdrawal.");
                }
            } else {
                System.err.println("Account not found or invalid password.");
            }
        }
    }


    private double getExistingBalance(String name) {
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/anxhelametani/Desktop/accountInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String accountName = parts[0];
                    if (name.equals(accountName)) {
                        return Double.parseDouble(parts[4]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("An error occurred while reading the account file.");
        }
        return 0;  // Account not found or file error
    }

    private void updateBalanceInFile(String name, double amount) {
        File file = new File("/Users/anxhelametani/Desktop/accountInfo.txt");
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String accountName = parts[0];
                    if (name.equals(accountName)) {
                        parts[4] = Double.toString(amount);  // Update the balance
                    }
                    updatedLines.add(String.join(",", parts));
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("An error occurred while reading the account file.");
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("An error occurred while updating the account file.");
        }
    }

    private void writeToAccountFile() {
        this.accountNumber = NewAccount.generateAccountNumber(); // Generate and assign the account number
        writeToAccountFile(0); // Default amount is 0
    }

    private void writeToAccountFile(double amount) {
        this.accountNumber = NewAccount.generateAccountNumber(); // Generate and assign the account number
        try (FileWriter fileWriter = new FileWriter("/Users/anxhelametani/Desktop/accountInfo.txt", true)) {
            fileWriter.write(name + "," + dob + "," + accountNumber + "," + pass + "," + amount + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("An error occurred while writing to the account file.");
        }

        // Add the created account to the list
        accounts.add(this);
    }
}



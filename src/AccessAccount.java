import java.io.*;

public class AccessAccount extends NewAccount {

	public boolean accountExistsInFile(String name, String dob) {
	    try (BufferedReader reader = new BufferedReader(new FileReader("/Users/anxhelametani/Desktop/accountInfo.txt"))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(",");
	            if (parts.length >= 2) {
	                String accountName = parts[0];
	                String accountDob = parts[1];
	                if (name.equals(accountName) && dob.equals(accountDob)) {
	                    return true;
	                }
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.err.println("An error occurred while reading the account file.");
	    }
	    return false; // Account with the same name and dob not found
	}

	public boolean accountExists(String name, String pass) {
	    try (BufferedReader reader = new BufferedReader(new FileReader("/Users/anxhelametani/Desktop/accountInfo.txt"))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(",");
	            if (parts.length >= 2) {
	                String accountName = parts[0];
	                String accountPass = parts[3];
	                if (name.equals(accountName) && pass.equals(accountPass)) {
	                    return true;
	                }
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.err.println("An error occurred while reading the account file.");
	    }
	    return false; // Account with the same name and pass not found
	}


	public static void appendTransactionToBalanceSheet(String accountNumber, String name, String action, double amount, double total) {
	    String balanceSheetFilePath = "/Users/anxhelametani/Desktop/balanceSheet.txt";

	    try (FileWriter balanceSheetWriter = new FileWriter(balanceSheetFilePath, true)) {
	        String transaction = name + " " +accountNumber+" "+ action + " " + amount + " total remained: " + total;
	        balanceSheetWriter.write(transaction + "\n");
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.err.println("An error occurred while writing to the balance sheet file.");
	    }
	}
 }


	
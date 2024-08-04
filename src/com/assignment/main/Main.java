// -----------------------------------
// Name: Kamal Dhital
// University Student ID: 2407046
// -----------------------------------

// package declaration
package com.assignment.main;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

//Creating a customer class to kept the customer details
class Customer {

	// Private fields for storing the customer's first and last names
	private String firstName;
	private String lastName;

	/**
	 * Gets the first name of the customer.
	 * 
	 * @return The first name of the customer.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the last name of the customer.
	 * 
	 * @return The last name of the customer.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the first name of the customer.
	 * 
	 * @param firstName The first name to set.
	 */
	public void setFirstName(String FName) {
		this.firstName = FName;
	}

	/**
	 * Sets the last name of the customer.
	 * 
	 * @param lastName The last name to set.
	 */
	public void setLastName(String LName) {
		this.lastName = LName;
	}
}

/**
 * The Account class represents a bank account and extends the Customer class.
 * It includes additional fields for account number and balance, and provides
 * methods for depositing, withdrawing, and retrieving account details.
 */

class Account extends Customer {

	// Private fields for storing the account balance and account number
	private int balance;
	private int accountNumber;
    private boolean isTransfer;

	/**
	 * Constructs a new Account with the specified details.
	 * 
	 * @param FName  The first name of the account holder.
	 * @param LName  The last name of the account holder.
	 * @param accNum The account number.
	 * @param accBal The initial account balance.
	 */
	Account(String FName, String LName, int accNum, int accBal) {
		// set the first and last name using the parent class methods
		setFirstName(FName);
		setLastName(LName);

		// Initialize balance and account number
		this.balance = accBal;
		this.accountNumber = accNum;
	}

	/**
	 * Gets the current balance of the account.
	 * 
	 * @return The balance of the account.
	 */
	public int getBalance() {
		return balance;
	}

	public void setTransfer(boolean isTransfer) {
        this.isTransfer = isTransfer;
    }
	/**
	 * Gets the account number.
	 * 
	 * @return The account number.
	 */
	public int getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Deposits a specified amount into the account.
	 * 
	 * @param amount The amount to deposit.
	 */
	public void deposit(int amount) {
		balance += amount;
	}

	/**
	 * Withdraws a specified amount from the account.
	 * 
	 * @param amount The amount to withdraw.
	 */
	public void withdraw(int amount) throws InsufficientFundsException {
		if (amount < balance) {
			balance -= amount;
		} else {
			if (!isTransfer) {
			JOptionPane.showMessageDialog(null, "Withdraw failed! Insufficient balance for withdrawal");
			}
			throw new InsufficientFundsException("Insufficient balance for withdrawal");
		}
	}

}

//suppress warning is used to suppress the warning in the class
@SuppressWarnings("serial")

//Class InsufficientFundsException is a custom class for exception handling
class InsufficientFundsException extends Exception {

	/**
	 * Constructs a new InsufficientFundsException with the specified detail
	 * message.
	 * 
	 * @param message (The message explaining the about the exception)
	 */
	public InsufficientFundsException(String message) {

		// Call the constructor of the superclass(Exception) with the message
		super(message);
	}
}

//The Transaction class provides a method to transfer funds between two accounts
class Transaction {

	/**
	 * Transfers a specified amount from one account to another.
	 * 
	 * @param acc1   The account to withdraw from.
	 * @param acc2   The account to deposit into.
	 * @param amount The amount to transfer.
	 */
	public void transfer(Account acc1, Account acc2, int amount) {
		try {
            acc1.setTransfer(true); // Set transfer flag to true for acc1

			acc1.withdraw(amount);
			acc2.deposit(amount);
			JOptionPane.showMessageDialog(null, "Transfer Successful");
		} catch (InsufficientFundsException e) {
			JOptionPane.showMessageDialog(null, "Transfer Failed! Insufficient Amount For Transaction");
		}finally {
            acc1.setTransfer(false);

		}

	}

}

//This class reads account details from a CSV file
class ReadAccounts {

	// Declaring the class variable to store the file URL
	public String url;

	/**
	 * Constructs a new ReadAccounts object with the specified file URL.
	 * 
	 * @param URL The URL of the CSV file to read.
	 */
	ReadAccounts(String URL) {
		// Assigning the provided file URL to the class variable 'url'
		this.url = URL;
	}

	/**
	 * Reads the first names from the CSV file.
	 * 
	 * @return A LinkedList of first names.
	 */
	public LinkedList<String> getFirstNames() {

		// Creating a new LinkedList to store the first names
		LinkedList<String> firstNames = new LinkedList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(url))) {
			String line;

			// Looping through each line in the CSV file
			while ((line = reader.readLine()) != null) {

				// Splitting the line by comma to extract values
				String[] values = line.split(",");

				// Checking if there are values present in the line
				if (values.length > 0) {

					// Adding the first name (index 0) to the LinkedList
					firstNames.add(values[0]);
				}
			}
		} catch (IOException e) {

			// Catching any IOException and printing the error message
			System.out.println("Exception Caught: " + e.getMessage());
		}

		// Returning the LinkedList of first names
		return firstNames;
	}

	/**
	 * Reads the last names from the CSV file.
	 * 
	 * @return A LinkedList of last names.
	 */
	public LinkedList<String> getLastNames() {

		// Creating a new LinkedList to store the last names
		LinkedList<String> lastNames = new LinkedList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(url))) {
			String line;

			// Splitting the line by comma to extract values
			while ((line = reader.readLine()) != null) {

				// Splitting the line by comma to extract values
				String[] values = line.split(",");

				// Checking if there are values present in the line
				if (values.length > 0) {

					// Adding the last name (index 1) to the LinkedList
					lastNames.add(values[1]);
				}
			}
		} catch (IOException e) {

			// Catching any IOException and printing the error message

			System.out.println("Exception Caught: " + e.getMessage());
		}

		// Returning the LinkedList of last names
		return lastNames;
	}

	/**
	 * Reads the account numbers from the CSV file.
	 * 
	 * @return A LinkedList of account numbers.
	 */
	public LinkedList<Integer> getAccounts() {

		// Creating a new LinkedList to store the account numbers
		LinkedList<Integer> accountList = new LinkedList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(url))) {
			String line;

			// Looping through each line in the CSV file
			while ((line = reader.readLine()) != null) {

				// Splitting the line by comma to extract values
				String[] values = line.split(",");

				// Checking if there are values present in the line
				if (values.length > 0) {

					// Parsing the account number (index 2) to Integer and adding it to the
					// LinkedList
					accountList.add(Integer.parseInt(values[2]));
				}
			}
		} catch (IOException e) {

			// Catching any IOException and printing the error message
			System.out.println("Exception Caught: " + e.getMessage());
		}

		// Returning the LinkedList of account numbers
		return accountList;
	}

	/**
	 * Reads the account balances from the CSV file.
	 * 
	 * @return A LinkedList of account balances.
	 */
	public LinkedList<Integer> getBalances() {

		// Creating a new LinkedList to store the account balances
		LinkedList<Integer> balanceList = new LinkedList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(url))) {
			String line;

			// Looping through each line in the CSV file
			while ((line = reader.readLine()) != null) {

				// Splitting the line by comma to extract values
				String[] values = line.split(",");

				// Checking if there are values present in the line
				if (values.length > 0) {

					// Parsing the balance (index 3) to Integer and adding it to the LinkedList
					balanceList.add(Integer.parseInt(values[3]));
				}
			}
		} catch (IOException e) {

			// Catching any IOException and printing the error message
			System.out.println("Exception Caught: " + e.getMessage());
		}

		// Returning the LinkedList of account balances
		return balanceList;
	}
}

//The CSVUpdater class updates account details in the CSV file
class CSVUpdater {
	private String filePath;

	/**
	 * Constructs a new CSVUpdater object with the specified file path.
	 * 
	 * @param filePath The path of the CSV file to update.
	 */
	public CSVUpdater(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Updates the account details in the CSV file with the provided accounts.
	 * 
	 * @param accounts A LinkedList of updated Account objects.
	 */
	public void updateAccounts(LinkedList<Account> accounts) {

		// LinkedList to store the updated lines of the CSV file
		LinkedList<String> lines = new LinkedList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;

			// Loop through each line in the CSV file
			while ((line = reader.readLine()) != null) {

				// Split the line by comma to extract values
				String[] values = line.split(",");

				// Extract the account number from the line
				int accountNumber = Integer.parseInt(values[2]);

				// Find the corresponding updated account based on the account number
				Account updatedAccount = findAccountByNumber(accounts, accountNumber);

				// Check if the updated account exists
				if (updatedAccount != null) {

					// Update the line with the updated account information
					line = updatedAccount.getFirstName() + "," + updatedAccount.getLastName() + ","
							+ updatedAccount.getAccountNumber() + "," + updatedAccount.getBalance();
				}

				// Add the updated line to the LinkedList
				lines.add(line);
			}
		} catch (IOException e) {

			// Print an error message if there is an error reading the file
			System.out.println("Error reading the file: " + e.getMessage());
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

			// Write the updated lines to the CSV file
			for (String updatedLine : lines) {
				writer.write(updatedLine);
				writer.newLine();
			}
		} catch (IOException e) {

			// Print an error message if there is an error writing to the file
			System.out.println("Error writing to the file: " + e.getMessage());
		}
	}

	/**
	 * Finds and returns the account with the specified account number from the
	 * provided list of accounts.
	 * 
	 * @param accounts      The list of accounts to search.
	 * @param accountNumber The account number to find.
	 * @return The account with the specified account number, or null if not found.
	 */
	public Account findAccountByNumber(LinkedList<Account> accounts, int accountNumber) {
		// Iterate through the list of accounts
		for (Account account : accounts) {

			// Check if the account number matches the specified account number
			if (account.getAccountNumber() == accountNumber) {

				// Return the account if found
				return account;
			}
		}

		// Return null if the account is not found
		return null;
	}
	
}





//Main Class declaration
public class Main {

	/**
	 * Main method which runs at the starting of the application This method
	 * demonstrates the creation and modification of Account objects including
	 * checking balances, depositing, withdrawing, and transferring funds between
	 * accounts.
	 * 
	 * @param args (No arguments used)
	 */

	public static void main(String[] args) {
		

        SwingUtilities.invokeLater(() -> {
            LoginGUI gui = new LoginGUI();
            gui.setVisible(true);
        });
    }

}

package com.assignment.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
class LoginGUI extends JFrame {
	private JTextField usernameTextField;
	private JPasswordField passwordField;

	public LoginGUI() {
		setBounds(100, 100, 450, 300);
		setTitle("Lotus Bank");
		setSize(1920, 1080);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Lotus_Logo.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center the window
		setResizable(false);

		// Panel to hold components
		JPanel panel = new JPanel();
		panel.setLayout(null); // Use absolute positioning

		// Username Label
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(500, 350, 150, 30); // Adjust position and size as needed
		panel.add(usernameLabel);

		// Username Text Field
		usernameTextField = new JTextField();
		usernameTextField.setBounds(650, 350, 300, 30); // Adjust position and size as needed
		panel.add(usernameTextField);

		// Password Label
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(500, 400, 150, 30); // Adjust position and size as needed
		panel.add(passwordLabel);

		// Password Field
		passwordField = new JPasswordField();
		passwordField.setBounds(650, 400, 300, 30); // Adjust position and size as needed
		panel.add(passwordField);

		// Login Button
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(650, 450, 100, 40); // Adjust position and size as needed
		panel.add(loginButton);

		// Add panel to the frame
		getContentPane().add(panel);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("Lotus_Logo.png"));
		lblNewLabel.setBounds(650, 53, 100, 100);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Lotus Bank");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 72));
		lblNewLabel_1.setBounds(540, 173, 391, 70);
		panel.add(lblNewLabel_1);
		setVisible(true);

		// Action listener for the login button
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameTextField.getText();
				String password = new String(passwordField.getPassword());

				// Validate against CSV file
				if (username.equals("Admin") && password.equals("Admin")) {
					JOptionPane.showMessageDialog(null, "Login successful!");
					String file = "Accounts.csv";

					ReadAccounts readAccounts = new ReadAccounts(file);

					LinkedList<Account> accounts = new LinkedList<>();

					LinkedList<String> firstNames = readAccounts.getFirstNames();
					LinkedList<String> lastNames = readAccounts.getLastNames();
					LinkedList<Integer> accountList = readAccounts.getAccounts();
					LinkedList<Integer> balanceList = readAccounts.getBalances();

					for (int i = 0; i < firstNames.size(); i++) {
						accounts.add(new Account(firstNames.get(i), lastNames.get(i), accountList.get(i),
								balanceList.get(i)));
					}
					GUI gui = new GUI(accounts);
					gui.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Invalid username or password!");
				}
			}
		});

		setVisible(true);
	}

}

@SuppressWarnings("serial")
class GUI extends JFrame {

	private StringBuilder sbAllData;
	private LinkedList<Account> globalAccounts;
	private Transaction transferObject = new Transaction();

	private DefaultTableModel tableModel;
	private JLabel showAllData;
	private JButton showAllButton, depositButton, withdrawButton, transferButton;
	private JTextField accDeposit, depositInput;
	private JTextField accWithdraw, withdrawInput;
	private JTextField acc1Transfer, acc2Transfer, transferAmount;
	private JTable accountsTable;
	private JPanel panel;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	private JPanel panel_7;

	public GUI(LinkedList<Account> accounts) {
		super("Lotus Bank");
		this.globalAccounts = accounts;
		sbAllData = new StringBuilder();

		for (Account acc : globalAccounts) {
			sbAllData.append(acc.toString()).append("\n");
		}

		setTitle("Lotus Bank");
		setSize(1920, 1080);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Lotus_Logo.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center the window
		setResizable(false);

		// Panel to hold components
		panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(null); // Use absolute positioning
		panel.setBounds(0, 0, 1920, 1080);// Use absolute positioning

		getContentPane().add(panel);

		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setBounds(10, 10, 1520, 110);
		panel.add(panel_1);

		JLabel lblNewLabel = new JLabel("");
		panel_1.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon("Lotus_Logo.png"));

		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panel_2.setBounds(10, 120, 1520, 88);
		panel.add(panel_2);

		JLabel bankLabel = new JLabel("Lotus Bank");
		bankLabel.setForeground(new Color(128, 0, 128));
		bankLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 72));
		panel_2.add(bankLabel);

		panel_3 = new JPanel();
		panel_3.setOpaque(false);
		panel_3.setBounds(10, 218, 1520, 627);
		panel.add(panel_3);
		panel_3.setLayout(null);

		panel_4 = new JPanel();
		panel_4.setOpaque(false);
		panel_4.setBounds(10, 10, 1500, 150);
		panel_3.add(panel_4);
		panel_4.setLayout(null);

		// Deposit Section
		JLabel depositAccountLabel = new JLabel("Account Number:");
		depositAccountLabel.setBounds(250, 63, 150, 30);
		panel_4.add(depositAccountLabel);

		depositButton = new JButton("Deposit");
		depositButton.setBounds(980, 50, 200, 50);
		panel_4.add(depositButton);
		depositButton.setBackground(Color.GREEN);
		depositButton.setForeground(Color.WHITE);
		depositButton.setFont(new Font("Arial", Font.BOLD, 20));

		depositInput = new JTextField();
		depositInput.setBounds(750, 64, 150, 30);
		panel_4.add(depositInput);

		JLabel depositAmountLabel = new JLabel("Amount:");
		depositAmountLabel.setBounds(625, 63, 100, 30);
		panel_4.add(depositAmountLabel);

		accDeposit = new JTextField();
		accDeposit.setBounds(400, 64, 150, 30);
		panel_4.add(accDeposit);

		panel_5 = new JPanel();
		panel_5.setOpaque(false);
		panel_5.setBounds(10, 170, 1500, 150);
		panel_3.add(panel_5);
		panel_5.setLayout(null);

		withdrawButton = new JButton("Withdraw");
		withdrawButton.setBounds(981, 50, 200, 50);
		panel_5.add(withdrawButton);
		withdrawButton.setBackground(Color.RED);
		withdrawButton.setForeground(Color.WHITE);
		withdrawButton.setFont(new Font("Arial", Font.BOLD, 20));

		withdrawInput = new JTextField();
		withdrawInput.setBounds(755, 64, 150, 30);
		panel_5.add(withdrawInput);

		JLabel withdrawAmountLabel = new JLabel("Amount:");
		withdrawAmountLabel.setBounds(625, 63, 100, 30);
		panel_5.add(withdrawAmountLabel);

		accWithdraw = new JTextField();
		accWithdraw.setBounds(400, 64, 150, 30);
		panel_5.add(accWithdraw);

		// Withdraw Section
		JLabel withdrawAccountLabel = new JLabel("Account Number:");
		withdrawAccountLabel.setBounds(250, 63, 150, 30);
		panel_5.add(withdrawAccountLabel);

		panel_6 = new JPanel();
		panel_6.setOpaque(false);
		panel_6.setBounds(10, 330, 1500, 150);
		panel_3.add(panel_6);
		panel_6.setLayout(null);

		transferButton = new JButton("Transfer");
		transferButton.setBounds(980, 48, 200, 50);
		panel_6.add(transferButton);
		transferButton.setBackground(Color.ORANGE);
		transferButton.setForeground(Color.WHITE);
		transferButton.setFont(new Font("Arial", Font.BOLD, 20));

		acc2Transfer = new JTextField();
		acc2Transfer.setBounds(400, 97, 150, 30);
		panel_6.add(acc2Transfer);

		JLabel transferToAccountLabel = new JLabel("To Account:");
		transferToAccountLabel.setBounds(250, 96, 100, 30);
		panel_6.add(transferToAccountLabel);

		// Transfer Section
		JLabel transferFromAccountLabel = new JLabel("From Account:");
		transferFromAccountLabel.setBounds(250, 29, 150, 30);
		panel_6.add(transferFromAccountLabel);

		acc1Transfer = new JTextField();
		acc1Transfer.setBounds(400, 30, 150, 30);
		panel_6.add(acc1Transfer);

		JLabel transferAmountLabel = new JLabel("Amount:");
		transferAmountLabel.setBounds(625, 61, 100, 30);
		panel_6.add(transferAmountLabel);

		transferAmount = new JTextField();
		transferAmount.setBounds(726, 62, 150, 30);
		panel_6.add(transferAmount);

		panel_7 = new JPanel();
		panel_7.setOpaque(false);
		panel_7.setBounds(10, 488, 1500, 129);
		panel_3.add(panel_7);
		panel_7.setLayout(null);

		showAllButton = new JButton("Show All");
		showAllButton.setBounds(611, 50, 270, 51);
		panel_7.add(showAllButton);
		showAllButton.setBackground(Color.BLUE);
		showAllButton.setForeground(Color.WHITE);
		showAllButton.setFont(new Font("Arial", Font.BOLD, 36));

		// Show All Section
		showAllData = new JLabel("Show All Data");
		showAllData.setBounds(680, 10, 142, 30);
		panel_7.add(showAllData);
		showAllData.setFont(new Font("Arial", Font.BOLD, 20));
		showAllData.setForeground(Color.BLUE);
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create the table and add it to the panel when the button is clicked
				addTableToPanel();
			}
		});
		setVisible(true);

		HandlerClass handler = new HandlerClass();
		showAllButton.addActionListener(handler);
		depositButton.addActionListener(handler);
		withdrawButton.addActionListener(handler);
		transferButton.addActionListener(handler);
	}

	private JScrollPane addTableToPanel() {
		// Initialize the table model and add data to it
		tableModel = new DefaultTableModel();
		tableModel.addColumn("First Name");
		tableModel.addColumn("Last Name");
		tableModel.addColumn("Account Number");
		tableModel.addColumn("Balance");

		for (Account acc : globalAccounts) {
			tableModel.addRow(
					new Object[] { acc.getFirstName(), acc.getLastName(), acc.getAccountNumber(), acc.getBalance() });
		}

		// Create the table with the initialized model
		accountsTable = new JTable(tableModel);

		// Add the table to a scroll pane
		JScrollPane scrollPane = new JScrollPane(accountsTable);
		scrollPane.setBounds(100, 600, 800, 300);

		return scrollPane;
	}

	private class HandlerClass implements ActionListener {
		LinkedList<Account> updatedAccounts = new LinkedList<>();
		CSVUpdater csv = new CSVUpdater("Accounts.csv");

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == showAllButton) {

				JScrollPane tableScrollPane = addTableToPanel();
				JOptionPane.showMessageDialog(null, tableScrollPane, "Accounts Information",
						JOptionPane.INFORMATION_MESSAGE);

			} else if (e.getSource() == depositButton) {
				String acc1Text = accDeposit.getText();
				String amountText = depositInput.getText();

				// Check if any of the input fields are empty
				if (acc1Text.equals("") || amountText.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in all fields.");
					return; // Exit the method early
				}
				try {
					// Action for deposit
					int accNumber = Integer.parseInt(accDeposit.getText());
					int amount = Integer.parseInt(depositInput.getText());
					Account acc = csv.findAccountByNumber(globalAccounts, accNumber);

					if (acc == null) {
						JOptionPane.showMessageDialog(null, "Accounts not found.");
						return; // Exit the method early
					}

					acc.deposit(amount);
					updatedAccounts.add(acc);
					csv.updateAccounts(updatedAccounts);
					JOptionPane.showMessageDialog(null, "Deposit successful!");
					accDeposit.setText("");
					depositInput.setText("");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Deposit Unsuccessful");
					accDeposit.setText("");
					depositInput.setText("");
				}
				// Call deposit function with accNumber and amount
			} else if (e.getSource() == withdrawButton) {
				String acc1Text = accWithdraw.getText();
				String amountText = withdrawInput.getText();

				// Check if any of the input fields are empty
				if (acc1Text.equals("") || amountText.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in all fields.");
					return; // Exit the method early
				}
				try {
					// Action for withdraw
					int accNumber = Integer.parseInt(accWithdraw.getText());
					int amount = Integer.parseInt(withdrawInput.getText());
					Account acc = csv.findAccountByNumber(globalAccounts, accNumber);

					if (acc == null) {
						JOptionPane.showMessageDialog(null, "Accounts not found.");
						return; // Exit the method early
					}
					// Call withdraw function with accNumber and amount
					acc.withdraw(amount);
					updatedAccounts.add(acc);
					csv.updateAccounts(updatedAccounts);
					JOptionPane.showMessageDialog(null, "Withdraw successful!");
					accWithdraw.setText("");
					withdrawInput.setText("");
				} catch (InsufficientFundsException e1) {
					System.out.println("Exception Caught");
					accWithdraw.setText("");
					withdrawInput.setText("");
				}
			} else if (e.getSource() == transferButton) {

				String acc1Text = acc1Transfer.getText().trim();
				String acc2Text = acc2Transfer.getText().trim();
				String amountText = transferAmount.getText().trim();

				// Check if any of the input fields are empty
				if (acc1Text.equals("") || acc2Text.equals("") || amountText.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in all fields.");
					return; // Exit the method early
				}
				// Action for transfer

				try {
					int acc1 = Integer.parseInt(acc1Transfer.getText());
					int acc2 = Integer.parseInt(acc2Transfer.getText());
					int amount = Integer.parseInt(transferAmount.getText());

					Account account1 = csv.findAccountByNumber(globalAccounts, acc1);
					Account account2 = csv.findAccountByNumber(globalAccounts, acc2);

					if (account1 == null || account2 == null) {
						JOptionPane.showMessageDialog(null, "One or both accounts not found.");
						return; // Exit the method early
					}

					// Call transfer function with acc1, acc2, and amount
					transferObject.transfer(account1, account2, amount);
					updatedAccounts.add(account1);
					updatedAccounts.add(account2);
					csv.updateAccounts(updatedAccounts);
					acc1Transfer.setText("");
					acc2Transfer.setText("");
					transferAmount.setText("");
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Invalid input. Please enter numbers only.");
					acc1Transfer.setText("");
					acc2Transfer.setText("");
					transferAmount.setText("");
				}

			}
		}
	}

}
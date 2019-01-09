package com.revature.project_0.utils;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.models.Account;
import com.revature.project_0.models.Transaction;
import com.revature.project_0.models.User;
import com.revature.project_0.services.*;

public class BankApplication {
	public static Boolean loggedIn = false;
	public static Boolean isAdmin = false;
	public static Logger Log = LogManager.getLogger(BankApplication.class);
	public static User currentUser = null;
	public static UserService userService;
	public static AdminService adminService;
	public static AccountService accountService;
	public static enum States {Welcome, LogIn, Register, User, DeleteAccount, Deposit, Withdraw, Admin, UpdateUsername, DeleteUser, Terminated};
	public static States curState = States.Welcome;
	public static Boolean switchPrompt = true;
	
	public static void main (String[] args) {
		Log.info("APPLICATION STARTED");
		Scanner s = new Scanner(System.in);
		userService = UserService.getService();
		adminService = AdminService.getService();
		accountService = AccountService.getService();
		while(curState != States.Terminated){
			if(switchPrompt == true) {
				switchPrompts();
				switchPrompt = false;
			}
			String input = s.nextLine();
			if(input.equals("TERMINATE")) {
				curState = States.Terminated;
			}
			switch(curState) {
			default:
				Log.info("main found unknown state " + curState);
				break;
			case Welcome:
				Log.info("main entered Welcome state");
				welcomeActions(input);
				break;
			case LogIn:
				Log.info("main entered LogIn state");
				if(input.equals("BACK")) {
					changeStateTo(States.Welcome);
				}else {
					logInActions(input);
				}
				break;
			case Register:
				Log.info("main entered Register state");
				if(input.equals("BACK")) {
					if(isAdmin) {
						changeStateTo(States.Admin);
					}else {
						changeStateTo(States.Welcome);
					}
				}else {
					registerActions(input);	
				}
				break;
			case User:
				Log.info("main entered User state");
				if(input.equals("BACK")) {
					logOut();
				}else {
					userActions(input);
				}
				break;
			case DeleteAccount:
				Log.info("main entered DeleteAccount state");
				if(input.equals("BACK")) {
					changeStateTo(States.User);
				}else {
					deleteAccountActions(input);
				}
				break;
			case Deposit:
				if(input.equals("BACK")) {
					changeStateTo(States.User);
				}else {
					depositActions(input);
				}
				break;
			case Withdraw:
				if(input.equals("BACK")) {
					changeStateTo(States.User);
				}else {
					withdrawActions(input);
				}
				break;
			case Admin:
				Log.info("main entered Admin state");
				if(input.equals("BACK")) {
					logOut();
				}else {
					adminActions(input);
				}
				break;
			case UpdateUsername:
				if(input.equals("BACK")) {
					changeStateTo(States.Admin);
				}else {
					updateUsernameActions(input);
				}
				break;
			case DeleteUser:
				if(input.equals("BACK")) {
					changeStateTo(States.Admin);
				}else {
					deleteUserActions(input);
				}
				break;
			}
		}
		s.close();
		System.out.println("Application terminated");
		Log.info("APPLICATION TERMINATED");
	}
	
	public static void switchPrompts() {
		switch(curState) {
		default:
			System.out.println("Something happened, please refresh application");
			break;
		case Welcome:
			System.out.println("_WELCOME MENU_\n");
			System.out.println("Enter TERMINATE at any time to terminate the program\n");
			System.out.println("1. Log in");
			System.out.println("2. Register");
			promptInput();
			break;
		case LogIn:
			System.out.println("_LOGIN MENU_\n");
			System.out.println("Enter BACK to return to welcome menu");
			System.out.println("Enter your username and password with space between");
			promptInput();
			break;
		case Register:
			System.out.println("_REGISTER MENU_\n");
			System.out.println("Enter BACK to return to welcome menu");
			System.out.println("Enter new username and password in the same line");
			System.out.println("Separate each by using a space");
			promptInput();
			break;
		case User:
			System.out.println("_USER MENU_\n");
			System.out.println("1. View accounts");
			System.out.println("2. Create new account");
			System.out.println("3. Delete an account");
			System.out.println("4. Make a deposit");
			System.out.println("5. make a withdrawal");
//			System.out.println("6. View transactions");
			System.out.println("6. Log out");
			promptInput();
			break;
		case DeleteAccount:
			System.out.println("_DELETE ACCOUNT_\n");
			System.out.println("Enter BACK to return to user menu");
			System.out.println("Enter Account ID");
			promptInput();
			break;
		case Deposit:
			System.out.println("_DEPOSIT MENU_\n");
			System.out.println("Enter BACK to return to user menu");
			System.out.println("Enter account ID and amount in the same line");
			System.out.println("Separate each by using a space");
			promptInput();
			break;
		case Withdraw:
			System.out.println("_WITHDRAW MENU_\n");
			System.out.println("Enter BACK to return to user menu");
			System.out.println("Enter account ID and amount in the same line");
			System.out.println("Separate each by using a space");
			promptInput();
			break;
		case Admin:
			System.out.println("_ADMIN MENU_\n");
			System.out.println("1. View all users");
			System.out.println("2. Create a new user");
			System.out.println("3. Update a username");
			System.out.println("4. Delete a user");
			System.out.println("5. log out");
			promptInput();
			break;
		case UpdateUsername:
			System.out.println("_USERNAME UPDATE MENU_\n");
			System.out.println("Enter current username and new username in the same line");
			System.out.println("Separate each by using a space");
			promptInput();
			break;
		case DeleteUser:
			System.out.println("_USER DELETE MENU_\n");
			System.out.println("Enter username");
			promptInput();
			break;
		}
	}
	
	public static void changeStateTo(States newState) {
		curState = newState;
		System.out.println("\n===========================================================\n");
		switchPrompt = true;
	}
	
	public static void promptInput() {
		System.out.print("\nInput: ");
	}
	
	public static void welcomeActions(String input) {
		switch(input) {
		default:
			System.out.println("Unknown command. Enter one of the given options");
			promptInput();
			break;
		case "1":
			changeStateTo(States.LogIn);
			break;
		case "2":
			changeStateTo(States.Register);
			break;
		}
	}
	
	public static Boolean logInActions(String input) {
		String[] strings = input.split("\\s+");
		if(strings.length != 2) {
			Log.info("checkLogInInput returning false - input should be two words. Actual amount is " + strings.length);
			System.out.println("Incorrect format");
			promptInput();
			return false;
		}
		String username = strings[0];
		String password = strings[1];
		if(logIn(username, password) != true) {
			Log.info("checkLogInInput returning false - failed to log in");
			promptInput();
			return false;
		}
		Log.info("checkLogInInput returning true - successfully logged in");
		return true;
	}
	
	public static Boolean userActions(String input) {
		switch(input) {
		default:
			System.out.println("Unknown command");
			promptInput();
			break;
		case "1":	//View Accounts
			userViewAccounts();
			break;
		case "2":	//Create Account
			userCreateAccount();
			break;
		case "3":	//Delete Account
			changeStateTo(States.DeleteAccount);
			break;
		case "4":	//Deposit
			changeStateTo(States.Deposit);
			break;
		case "5":	//Withdraw
			changeStateTo(States.Withdraw);
			break;
//		case "6":	//Transactions
//			break;
		case "6":	//Log out
			logOut();
			break;
		}
		return true;
	}
	
	public static Boolean registerActions(String input) {
		String[] strings = input.split("\\s+");
		if(strings.length != 2) {
			System.out.println("Input not recognized. Should be username and password with space in between");
			promptInput();
			return false;
		}
		String username = strings[0];
		String password = strings[1];
		if(createNewUser(username, password) != true) {
			promptInput();
			return false;
		}
		promptInput();
		return true;
	}
	
	public static Boolean deleteAccountActions(String input) {
		Integer accountId = null;
		try {
			accountId = (Integer)Integer.parseInt(input);
		}catch(NumberFormatException e) {
			System.out.println(input + " is not a valid account ID");
			promptInput();
			return false;
		}
		if(userDeleteAccount(accountId) == false) {
			promptInput();
			return false;
		}
		promptInput();
		return true;
	}
	
	public static void depositActions(String input) {
		String[] strings = input.split("\\s+");
		if(strings.length != 2) {
			System.out.println("Input format is incorrect");
			promptInput();
			return;
		}
		Integer accountId = null;
		Double amount = null;
		try {
			accountId = ((Integer)Integer.parseInt(strings[0]));
			amount = ((Double)Double.parseDouble(strings[1]));
		}catch(NumberFormatException e) {
			System.out.println("Input format is incorrect");
			promptInput();
			return;
		}
		if(userDepositToAccount(accountId, amount) == false) {
			promptInput();
			return;
		}
		promptInput();
		return;
	}
	
	public static void withdrawActions(String input) {
		String[] strings = input.split("\\s+");
		if(strings.length != 2) {
			System.out.println("Input format is incorrect");
			promptInput();
			return;
		}
		Integer accountId = null;
		Double amount = null;
		try {
			accountId = ((Integer)Integer.parseInt(strings[0]));
			amount = ((Double)Double.parseDouble(strings[1]));
		}catch(NumberFormatException e) {
			System.out.println("Input format is incorrect");
			promptInput();
			return;
		}
		if(userWithdrawFromAccount(accountId, amount) == false) {
			promptInput();
			return;
		}
		promptInput();
		return;
	}
	
	public static void adminActions(String input) {
		switch(input) {
		default: 
			System.out.println("Unknown command");
			promptInput();
			break;
		case "1":	//view all users
			adminViewAllUsers();
			break;
		case "2":	//create a new user
			changeStateTo(States.Register);
			break;
		case "3":	//update a username
			changeStateTo(States.UpdateUsername);
			break;
		case "4":	//delete a user
			changeStateTo(States.DeleteUser);
			break;
		case "5":	//log out
			logOut();
			break;
		}
	}
	
	public static void updateUsernameActions(String input) {
		String[] strings = input.split("\\s+");
		if(strings.length != 2) {
			System.out.println("Input not recognized. Should be username and password with space in between");
			promptInput();
			return;
		}
		String username = strings[0];
		String password = strings[1];
		if(adminUpdateUsername(username, password) != true) {
			promptInput();
			return;
		}
		promptInput();
		return;
	}
	
	public static void deleteUserActions(String input) {
		adminDeleteUser(input);
		promptInput();
	}
	
	//A registered user can login with their username and password  
	public static Boolean logIn(String username, String password) {
		Log.info("logIn called and passed username " + username + " and password [Hidden]");
		if(loggedIn == true) {
			Log.info("logIn returned false - already logged in" + System.lineSeparator());
			System.out.println("Already logged in");
			promptInput();
			return false;
		}
		System.out.println("Logging in");
		Log.info("LogIn calling adminLogIn and passing username and password");
		loggedIn = adminService.adminLogIn(username, password);
		if(loggedIn == true) {
			isAdmin = true;
			currentUser = null;
			Log.info("logIn returned true - logged in as admin" + System.lineSeparator());
			System.out.println("Logged in as admin");
			changeStateTo(States.Admin);
			return true;
		}
		Log.info("LogIn calling userLogIn and passing username and password and retrieving user object");
		currentUser = userService.userLogIn(username, password);
		Log.info("LogIn checking if user object exists");
		loggedIn = currentUser != null ? true : false;
		if(loggedIn == true) {
			Log.info("logIn returned true - logged in as user" + System.lineSeparator());
			System.out.println("Logged in as user " + username);
			changeStateTo(States.User);
			return true;
		}
		Log.info("logIn returned false - parameters didn't match anything" + System.lineSeparator());
		System.out.println("user doesn't exist");
		return false;
	}
	
	public static Boolean logOut() {
		Log.info("logOut called");
		if(loggedIn == false) {
			Log.info("logOut returned false - already logged off" + System.lineSeparator());
			System.out.println("Already logged out");
			promptInput();
			return false;
		}
		System.out.println("Logging out");
		isAdmin = false;
		loggedIn = false;
		currentUser = null;
		Log.info("logOut returned true - successfully logged off" + System.lineSeparator());
		System.out.println("Logged out");
		changeStateTo(States.Welcome);
		return true;
	}
	
	//An unregistered user can register by creating a username and password.
	//A superuser can create users.
	public static Boolean createNewUser(String username, String password) {
		Log.info("register called and passed username " + username + " and password [Hidden]");
		Log.info("register checking if currently logged in as user or admin");
		if(loggedIn == true && isAdmin == false) {
			Log.info("register returned false - currently logged in as user" + System.lineSeparator());
			System.out.println("Must be logged out of user to register a new user");
			return false;
		}
		System.out.println("Registering new user");
		Log.info("register calling createUser, passing username and password, and checking result");
		if(userService.createUser(username, password) == null) {
			Log.info("register returned false - createUser failed" + System.lineSeparator());
			return false;
		}
		Log.info("register returned true - createUser succeeded" + System.lineSeparator());
		System.out.println("Successfully registered new user.");
		return true;
	}
	
	public static Boolean userCreateAccount() {
		Log.info("userCreateAccount called");
		Log.info("userCreateAccount checking if user is logged in");
		if(loggedIn == false || currentUser == null) {
			Log.info("userCreateAccount returned false - not currently logged in as user" + System.lineSeparator());
			System.out.println("Must be signed in to a user in order to perform this action");
			promptInput();
			return false;
		}
		System.out.println("Creating new bank account");
		Log.info("userCreateAccount calling createAccount and passing ID from current user object to try and get account object");
		if(accountService.createAccount(currentUser.getUserId()) == null) {
			Log.info("userCreateAccount returned false - createAccount failed" + System.lineSeparator());
			System.out.println("Something went wrong. Please try again");
			promptInput();
			return false;
		}
		Log.info("userCreateAccount returned true - createAccount succeeded" + System.lineSeparator());
		promptInput();
		return true;
	}
	
	public static Boolean userViewAccounts() {
		Log.info("userViewAccounts called");
		Log.info("userViewAccounts checking if user is logged in");
		if(loggedIn == false || currentUser == null) {
			Log.info("userViewAccount returned false - not currently logged in as user" + System.lineSeparator());
			System.out.println("Must be signed in to a user in order to perform this action");
			promptInput();
			return false;
		}
		System.out.println("Viewing all bank accounts");
		Log.info("userViewAccounts calling retrieveUserAccounts and passing ID from current user object to try and get list of account objects");
		List<Account> accountList = accountService.retrieveUserAccounts(currentUser.getUserId());
		if(accountList.isEmpty()) {
			Log.info("userViewAccount found no accounts for user");
			System.out.println("No active accounts");
			promptInput();
			return true;
		}
		Log.info("userViewAccount iterating through list");
		for (int i = 0; i < accountList.size(); i++) {
			Account account = accountList.get(i);
			Log.info("userViewAccount displayed " + account.toString());
			System.out.println(account.toString());
		}
		Log.info("userViewAccount completed retrieving accounts" + System.lineSeparator());
		System.out.println("Completed search for accounts");
		promptInput();
		return true;
	}
	
	public static Boolean userDeleteAccount(Integer accountId) {
		Log.info("userDeleteAccount called and passed account ID " + accountId);
		Log.info("userDeleteAccount checking if user is logged in");
		if(loggedIn == false || currentUser == null) {
			Log.info("userDeleteAccount returned false - not currently logged in as user" + System.lineSeparator());
			System.out.println("Must be signed in to a user in order to perform this action");
			return false;
		}
		System.out.println("Deleting account " + accountId);
		Log.info("userDeleteAccount calling deleteAccount and passing account ID and user ID");
		if(accountService.deleteAccount(accountId, currentUser.getUserId()) == false) {
			Log.info("userDeleteAccount returning false - deleteAccount failed");
			return false;
		}
		Log.info("userDeleteAccount returning true - successfully deleted account");
		return true;
	}
	
	public static Boolean userDepositToAccount(Integer accountId, Double amount) {
		Log.info("userDepositToAccount called and passed account ID " + accountId + " and amount" + amount);
		Log.info("userDepositToAccount checking if user is logged in");
		if(loggedIn == false || currentUser == null) {
			Log.info("userDepositToAccount returned false - not currently logged in as user" + System.lineSeparator());
			System.out.println("Must be signed in to a user in order to perform this action");
			return false;
		}
		Log.info("userDepositToAccount calling makeDeposit and passing account ID, amount, and user ID to retrieve transaction object");
		Transaction transaction = accountService.makeDeposit(accountId, amount, currentUser.getUserId());
		if(transaction == null) {
			Log.info("userDepositToAccount returning false - makeDeposit failed" + System.lineSeparator());
			System.out.println("Failed to make deposit. Please try again");
			return false;
		}
		Log.info("userDepositToAccount returning true - deposit successful" + System.lineSeparator());
		return true;
	}
	
	public static Boolean userWithdrawFromAccount(Integer accountId, Double amount) {
		Log.info("userWithDrawFromAccount called and passed account ID " + accountId + " and amount" + amount);
		Log.info("userWithDrawFromAccount checking if user is logged in");
		if(loggedIn == false || currentUser == null) {
			Log.info("userWithDrawFromAccount returned false - not currently logged in as user" + System.lineSeparator());
			System.out.println("Must be signed in to a user in order to perform this action");
			return false;
		}
		Log.info("userWithDrawFromAccount calling makeWithdrawal and passing account ID, amount, and user ID to retrieve transaction object");
		Transaction transaction = accountService.makeWithdrawal(accountId, amount, currentUser.getUserId());
		if(transaction == null) {
			Log.info("userWithDrawFromAccount returning false - makeWithdrawal failed" + System.lineSeparator());
			return false;
		}
		Log.info("userWithDrawFromAccount returning true - withdrawal successful" + System.lineSeparator());
		return true;
	}
	
	public static Boolean adminViewUser(String username) {
		Log.info("adminViewUser called and passed username ");
		Log.info("adminViewUsers checking if logged in as admin");
		if(isAdmin == false) {
			Log.info("adminViewUser returned false - not logged in as admin" + System.lineSeparator());
			System.out.println("Only admin users can perform this action");
			return false;
		}
		System.out.println("Viewing user");
		Log.info("adminViewUser calling viewUser and passing username");
		Boolean result = adminService.viewUser(username);
		Log.info("adminViewUser returning result of viewUser" + System.lineSeparator());
		return result;
	}
	
	public static Boolean adminViewAllUsers() {
		Log.info("adminViewUsers called");
		Log.info("adminViewUsers checking if logged in as admin");
		if(isAdmin == false) {
			Log.info("adminViewUsers returned false - not logged in as admin" + System.lineSeparator());
			System.out.println("Only admin users can perform this action");
			return false;
		}
		System.out.println("Viewing all users");
		Log.info("adminViewAllUsers calling retrieveAllUsers to try and get list of user objects");
		List<User> userList = adminService.retrieveAllUsers();
		if( userList == null || userList.isEmpty()) {
			Log.info("adminViewAllUsers completed search for users - no users exist" + System.lineSeparator());
			System.out.println("Completed search - no users exist");
			return true;
		}
		Log.info("adminViewAllUsers displaying all users");
		for(int i = 0; i < userList.size(); i++) {
			User user = userList.get(i);
			System.out.println(user.toString());
		}
		Log.info("adminViewAllUsers completed showing all users" + System.lineSeparator());
		System.out.println("Completed search for users");
		promptInput();
		return true;
	}
	
	public static Boolean adminUpdateUsername(String username, String newName) {
		Log.info("adminUpdateUsername called and passed " + username + " and " + newName);
		Log.info("adminUpdateUsername checking if logged in as admin");
		if(isAdmin == false) {
			Log.info("adminUpdateUsername returned false - not logged in as admin" + System.lineSeparator());
			System.out.println("Only admin users can perform this action");
			return false;
		}
		System.out.println("Updating username from " + username + " to " + newName);
		Log.info("adminUpdateUsername calling updateUsername and passing username and new name");
		if(adminService.updateUsername(username, newName) == false) {
			Log.info("adminUpdateUsername returned false - updateUsername failed" + System.lineSeparator());
			return false;
		}
		Log.info("adminUpdateUsername returned true - successfully changed username" + System.lineSeparator());
		return true;
	}
	
	public static Boolean adminDeleteUser(String username) {
		Log.info("adminDeleteUser called and passed username " + username);
		Log.info("adminViewUsers checking if logged in as admin");
		if(isAdmin == false) {
			Log.info("adminDeleteuser returned false - not logged in as admin" + System.lineSeparator());
			System.out.println("Only admin users can perform this action");
			return false;
		}
		System.out.println("Deleting user");
		Log.info("adminDeleteUser calling deleteUser and passing username");
		if(adminService.deleteUser(username) == false) {
			Log.info("adminDeleteUser returning false - deleteUser failed" + System.lineSeparator());
			return false;
		}
		Log.info("adminDeleteUser returning true - successfully deleted user" + System.lineSeparator());
		System.out.println("Successfully deleted user");
		return true;
	}
}
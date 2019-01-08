package com.revature.project_0.utils;

import java.lang.Thread.State;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_0.models.Account;
import com.revature.project_0.models.Transaction;
import com.revature.project_0.models.User;
import com.revature.project_0.services.*;

public class BankApplication {
	private static Boolean loggedIn = false;
	private static Boolean isAdmin = false;
	private static Logger Log = LogManager.getLogger(BankApplication.class);
	private static User currentUser = null;
	private static UserService userService;
	private static AdminService adminService;
	private static AccountService accountService;
	private static enum States {Welcome, LogIn, Register, User, Admin, Terminated};
	private static States curState = States.Welcome;
	private static Boolean switchPrompt = true;
	
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
				checkWelcomeInput(input);
				break;
			case LogIn:
				Log.info("main entered LogIn state");
				checkLogInInput(input);
				break;
			case Register:
				Log.info("main entered Register state");
				break;
			case User:
				Log.info("main entered User state");
				checkUserInput(input);
				break;
			case Admin:
				Log.info("main entered Admin state");
				break;
			}
		}
		s.close();
		System.out.println("Application terminated");
		Log.info("APPLICATION TERMINATED");
	}
	
	private static void switchPrompts() {
		switch(curState) {
		default:
			Log.info("stateMachine found unknown state " + curState);
			System.out.println("Something happened, please refresh application");
			break;
		case Welcome:
			Log.info("stateMachine entered Welcome state");
			System.out.println("======================================================\n_WELCOME MENU_");
			System.out.println("1. Log in");
			System.out.println("2. Register");
			System.out.println("Enter TERMINATE at any time to terminate the program");
			System.out.print("Input: ");
			break;
		case LogIn:
			Log.info("stateMachine entered LogIn state");
			System.out.println("======================================================\n_LOGIN MENU_");
			System.out.println("Enter your username and password with space between to log in, or enter 'return' to go back to main menu");
			System.out.println("Enter TERMINATE at any time to terminate the program");
			System.out.print("Input: ");
			break;
		case Register:
			Log.info("stateMachine entered Register state");
			System.out.println("======================================================\n_REGISTER MENU_");
			System.out.println("Enter TERMINATE at any time to terminate the program");
			System.out.print("Input: ");
			break;
		case User:
			Log.info("stateMachine entered User state");
			System.out.println("======================================================\n_USER MENU_");
			System.out.println("1. Log out");
			System.out.println("Enter TERMINATE at any time to terminate the program");
			System.out.print("Input: ");
			break;
		case Admin:
			Log.info("stateMachine entered Admin state");
			System.out.println("======================================================\n_ADMIN MENU_");
			System.out.println("Enter TERMINATE at any time to terminate the program");
			System.out.print("Input: ");
			break;
		}
	}
	
	private static void switchPrompt() {
		switchPrompt = true;
	}
	
	private static void checkWelcomeInput(String input) {
		switch(input) {
		default:
			System.out.println("Unknown command. Enter one of the given options");
			break;
		case "1":
			curState = States.LogIn;
			switchPrompt();
			break;
		case "2":
			curState = States.Register;
			switchPrompt();
			break;
		case "3":
			curState = States.Terminated;
		}
	}
	
	private static Boolean checkLogInInput(String input) {
		if(input.equals("return")) {
			System.out.println("Returning to Welcome page");
			curState = States.Welcome;
			switchPrompt();
			return true;
		}
		String[] strings = input.split("\\s+");
		if(strings.length != 2) {
			Log.info("checkLogInInput returning false - input should be two words. Actual amount is " + strings.length);
			System.out.println("Input not recognized. Please ensure correct username and password with space in between");
			System.out.print("Input: ");
			return false;
		}
		String username = strings[0];
		String password = strings[1];
		if(logIn(username, password) != true) {
			Log.info("checkLogInInput returning false - failed to log in");
			System.out.println("Username or password incorrect");
			return false;
		}
		Log.info("checkLogInInput returning true - successfully logged in");
		switchPrompt();
		return true;
	}
	
	private static Boolean checkUserInput(String input) {
		switch(input) {
		default:
			System.out.println("Unknown command. Enter one of the given options");
			break;
		case "1":
			curState = States.Welcome;
			switchPrompt();
			break;
		}
		return true;
	}
	
	//A registered user can login with their username and password  
	private static Boolean logIn(String username, String password) {
		Log.info("logIn called and passed username " + username + " and password [Hidden]");
		if(loggedIn == true) {
			Log.info("logIn returned false - already logged in" + System.lineSeparator());
			System.out.println("Already logged in");
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
			curState = States.Admin;
			return true;
		}
		Log.info("LogIn calling userLogIn and passing username and password and retrieving user object");
		currentUser = userService.userLogIn(username, password);
		Log.info("LogIn checking if user object exists");
		loggedIn = currentUser != null ? true : false;
		if(loggedIn == true) {
			Log.info("logIn returned true - logged in as user" + System.lineSeparator());
			System.out.println("Logged in as user " + username);
			curState = States.User;
			return true;
		}
		Log.info("logIn returned false - parameters didn't match anything" + System.lineSeparator());
		System.out.println("Couldn't verify the username or password");
		return false;
	}
	
	private static Boolean logOut() {
		Log.info("logOut called");
		if(loggedIn == false) {
			Log.info("logOut returned false - already logged off" + System.lineSeparator());
			System.out.println("Already logged out");
			return false;
		}
		System.out.println("Logging out");
		isAdmin = false;
		loggedIn = false;
		currentUser = null;
		Log.info("logOut returned true - successfully logged off" + System.lineSeparator());
		System.out.println("Logged out");
		return true;
	}
	
	//An unregistered user can register by creating a username and password.
	//A superuser can create users.
	private static Boolean createNewUser(String username, String password) {
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
	
	private static Boolean userCreateAccount() {
		Log.info("userCreateAccount called");
		Log.info("userCreateAccount checking if user is logged in");
		if(loggedIn == false || currentUser == null) {
			Log.info("userCreateAccount returned false - not currently logged in as user" + System.lineSeparator());
			System.out.println("Must be signed in to a user in order to perform this action");
			return false;
		}
		System.out.println("Creating new bank account");
		Log.info("userCreateAccount calling createAccount and passing ID from current user object to try and get account object");
		if(accountService.createAccount(currentUser.getUserId()) == null) {
			Log.info("userCreateAccount returned false - createAccount failed" + System.lineSeparator());
			return false;
		}
		Log.info("userCreateAccount returned true - createAccount succeeded" + System.lineSeparator());
		return true;
	}
	
	private static Boolean userViewAccounts() {
		Log.info("userViewAccounts called");
		Log.info("userViewAccounts checking if user is logged in");
		if(loggedIn == false || currentUser == null) {
			Log.info("userViewAccount returned false - not currently logged in as user" + System.lineSeparator());
			System.out.println("Must be signed in to a user in order to perform this action");
			return false;
		}
		System.out.println("Viewing all bank accounts");
		Log.info("userViewAccounts calling retrieveUserAccounts and passing ID from current user object to try and get list of account objects");
		List<Account> accountList = accountService.retrieveUserAccounts(currentUser.getUserId());
		if(accountList.isEmpty()) {
			Log.info("userViewAccount found no accounts for user");
		}
		Log.info("userViewAccount iterating through list");
		for (int i = 0; i < accountList.size(); i++) {
			Account account = accountList.get(i);
			Log.info("userViewAccount displayed " + account.toString());
			System.out.println(account.toString());
		}
		Log.info("userViewAccount completed retrieving accounts" + System.lineSeparator());
		System.out.println("Completed searching for accounts");
		return true;
	}
	
	private static Boolean userDeleteAccount(Integer accountId) {
		Log.info("userDeleteAccount called and passed account ID " + accountId);
		Log.info("userDeleteAccount checking if user is logged in");
		if(loggedIn == false || currentUser == null) {
			Log.info("userDeleteAccount returned false - not currently logged in as user" + System.lineSeparator());
			System.out.println("Must be signed in to a user in order to perform this action");
			return false;
		}
		Log.info("userDeleteAccount calling deleteAccount and passing account ID and user ID");
		if(accountService.deleteAccount(accountId, currentUser.getUserId()) == false) {
			Log.info("userDeleteAccount returning false - deleteAccount failed");
			return false;
		}
		Log.info("userDeleteAccount returning true - successfully deleted account");
		return true;
	}
	
	private static Boolean userDepositToAccount(Integer accountId, Double amount) {
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
			return false;
		}
		Log.info("userDepositToAccount returning true - deposit successful" + System.lineSeparator());
		return true;
	}
	
	private static Boolean userWithdrawFromAccount(Integer accountId, Double amount) {
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
	
	private static Boolean adminViewUser(String username) {
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
	
	private static Boolean adminViewAllUsers() {
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
		System.out.println("Completed searching for users");
		return true;
	}
	
	private static Boolean adminUpdateUsername(String username, String newName) {
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
	
	private static Boolean adminDeleteUser(String username) {
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
package com.vrshah.moviedb;

import java.sql.SQLException;

import com.vrshah.moviedb.query.QueryExecuter;
import com.vrshah.moviedb.tables.CustomerManager;
import com.vrshah.moviedb.tables.MovieManager;
import com.vrshah.moviedb.tables.SchemaManager;
import com.vrshah.moviedb.tables.StarManager;

public class Login {
	
	private static PromptManager promptManager;
	
	public static void main(String[] args) {
		
		promptManager = new PromptManager();
		
		loginPhase();
		
		String prompt = "\n(1) Find A Movie By Star\n"
				+ "(2) Register A New Star\n"
				+ "(3) Register A Customer\n"
				+ "(4) Unregister A Customer\n"
				+ "(5) Print Database Schema\n"
				+ "(6) Execute SQL Command\n"
				+ "(7) Exit To Login\n"
				+ "(8) Exit Program\n";
		
		boolean shouldExit = false;
		
		do 
		{
			switch (promptManager.requestInt(prompt)) {
			case 1:
				findMovieByStar();
				break;
			case 2:
				registerNewStar();
				break;
			case 3:
				registerNewCustomer();
				break;
			case 4:
				deleteCustomer();
				break;
			case 5:
				SchemaManager.printSchema();
				break;
			case 6:
				QueryExecuter.executeQuery(promptManager.requestString("Enter a Valid SELECT/UPDATE/INSERT/DELETE SQL Statement:"));
				break;
			case 7:
				ConnectionManager.getInstance().close();
				loginPhase();
				break;
			case 8:
				closeResources();
				shouldExit = true;
				System.out.println("Exited Successfully");
				break;
			default:
				System.out.println("Invalid Option Selected");
				break;
			}
		}
		while (!shouldExit);
	}
	
	public static void findMovieByStar()
	{
		int result;
		
		do 
		{
			result = promptManager.requestInt("Search by (1) ID, or (2) Name");
		}
		while (result < 1 || result > 2);
		
		if (result == 1)
		{
			try 
			{
				MovieManager.findMovieByStar(promptManager.requestInt("Enter Star ID: "));
			} 
			catch (SQLException e) 
			{
				System.out.println("Could not find movie by Star ID");
			}
		}
		else
		{
			try 
			{
				String firstName = "";
				String lastName = "";
				do
				{
					System.out.println("Please Provide Either First Name, Last Name, Or Both:");
					firstName = promptManager.requestString("Enter First Name (if any):");
					lastName = promptManager.requestString("Enter Last Name (if any):");
				}
				while (firstName.equals("") && lastName.equals(""));
				
				MovieManager.findMovieByStarNames(firstName, lastName);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void registerNewStar()
	{
		String firstName = "";
		String lastName = "";
		String url = "";
		String dob = "";
		
		do
		{
			System.out.println("Please Provide Either First Name, last name, Or Both:");
			firstName = promptManager.requestString("Enter First Name (if any):");
			lastName = promptManager.requestString("Enter Last Name (if any):");
		} 
		while (firstName.isEmpty() && lastName.isEmpty());
		
		if (lastName.isEmpty()) 
		{
			lastName = firstName; 
			firstName = "";
		}
		
		url = promptManager.requestString("Please Enter A Photo URL (Optional): ");
		dob = promptManager.requestString("Please Enter The Star's Date Of Birth In YYYY-MM-DD Format (Optional): ");
		
		StarManager.insertStar(firstName, lastName, dob, url);
	}
	
	public static void registerNewCustomer()
	{
		String firstName = "";
		String lastName = "";
		String cc_id = "";
		String address = "";
		String email = "";
		String password = "";
		
		do
		{
			System.out.println("Please provide either first name, last name, or both:");
			firstName = promptManager.requestString("Enter First Name:");
			lastName = promptManager.requestString("Enter Last Name:");		
		} 
		while (firstName.isEmpty() && lastName.isEmpty());
		
		if (lastName.isEmpty()) 
		{
			lastName = firstName; 
			firstName = "";
		}
		
		cc_id = promptManager.requestString("Please Enter A Credit Card ID: ");
		address = promptManager.requestString("Please Enter An Address: ");
		email = promptManager.requestString("Please Enter An Email: ");
		password = promptManager.requestString("Please Enter A Password: ");
		
		CustomerManager.insertCustomer(firstName, lastName, cc_id, address, email, password);
	}
	
	public static void deleteCustomer() {
		int idToDelete = promptManager.requestInt("Please Provide The ID Of The Customer You Wish To Delete:");
		CustomerManager.deleteCustomer(idToDelete);
		
		
		int result;
		
		do 
		{
			result = promptManager.requestInt("Delete by (1) ID, or (2) Name");
		}
		while (result < 1 || result > 2);
		
		if (result == 1)
		{

			CustomerManager.deleteCustomer(promptManager.requestInt("Enter Customer ID:"));
		}
		else
		{
			try 
			{
				String firstName = "";
				String lastName = "";
				do
				{
					firstName = promptManager.requestString("Enter First Name:");
					lastName = promptManager.requestString("Enter Last Name:");
				}
				while (firstName.equals("") || lastName.equals(""));
				
				MovieManager.findMovieByStarNames(firstName, lastName);
				CustomerManager.deleteCustomer(firstName, lastName);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
	}
	
	public static void loginPhase()
	{
		boolean run = true;
		do
		{	String userName = promptManager.requestWord("Please Enter Your Username:");
			String password = promptManager.requestWord("Please Enter Your Password:");
			ConnectionManager.getInstance().setUserName(userName);
			ConnectionManager.getInstance().setPassword(password);
			if (ConnectionManager.getInstance().getConnection() != null) {run = false;}
			else {
				if (userName != "dbuser"){System.out.println("INVALID Credential!");}
				else if (password!= "dbpassword"){System.out.println("INVALID Credential!");}
				else{System.out.println("Database does not exist");}
				if (promptManager.requestInt("Enter 1 to exit: ") == 1) {
					System.out.println("Exiting!");
					System.exit(0);
				}
				
			}
		}
		while (run);
		System.out.println("Access Granted!");
	}
	
	public static void closeResources() 
	{
		promptManager.closeScanner();
		ConnectionManager.getInstance().close();
	}

}

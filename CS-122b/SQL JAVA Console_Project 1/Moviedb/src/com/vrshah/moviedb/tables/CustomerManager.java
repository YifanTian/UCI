package com.vrshah.moviedb.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vrshah.moviedb.ConnectionManager;

public class CustomerManager {
	
	private static Connection conn = ConnectionManager.getInstance().getConnection();

	public static void insertCustomer(String first_name, String last_name, String cc_id, String address, String email, String password) 
	{
		String ccSQL = "SELECT COUNT(*) FROM creditcards WHERE id = ?";
		
		try (PreparedStatement pStatement = conn.prepareStatement(ccSQL);)
		{
			
			pStatement.setString(1, cc_id);
			
			ResultSet results = pStatement.executeQuery();
			results.next();
			
			if (results.getInt(1) < 1)
			{
				System.out.println("An error occurred: The credit card Id provided does not match an existing credit card in the database");
				return;
			}
		}
		catch (SQLException e)
		{
			System.out.println("An error occurred: The credit card Id provided does not match an existing credit card in the database");
			return;
		}
		
		String sql = "INSERT INTO customers (first_name, last_name, cc_id, address, email, password) VALUES(?,?,?,?,?,?)";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql);)
		{
			
			stmt.setString(1, first_name);
			stmt.setString(2, last_name);
			stmt.setString(3, cc_id);
			stmt.setString(4, address);
			stmt.setString(5, email);
			stmt.setString(6, password);
			
			System.out.println("Number of customers added: " + stmt.executeUpdate());
		}
		catch (SQLException e)
		{
			System.out.println("An error occurred: the customer details provided were not added to the database");
		}
	}
	
	public static void deleteCustomer(int customerId) {
		
		String sql = "DELETE FROM customers WHERE id = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);)
		{
			stmt.setInt(1, customerId);
			System.out.println("Number of customer details deleted: " + stmt.executeUpdate());
		}
		catch (SQLException e)
		{
			System.out.println("An error occurred: customer details have not ben modified");
		}
		
	}
	
public static void deleteCustomer(String firstName, String lastName) {
		
		String sql = "DELETE FROM customers WHERE first_name = ? AND last_name = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) 
		{
			stmt.setString(1,firstName);
			stmt.setString(2, lastName);
			System.out.println("Number of customer details deleted: " + stmt.executeUpdate());
		}
		catch (SQLException e)
		{
			System.out.println("An error occurred: customer details have not ben modified");
		}
		
	}
	
	
}

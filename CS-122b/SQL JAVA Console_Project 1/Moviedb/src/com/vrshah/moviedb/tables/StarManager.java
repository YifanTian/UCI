package com.vrshah.moviedb.tables;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.vrshah.moviedb.ConnectionManager;

public class StarManager {
	
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	public static void insertStar(String first_name, String last_name, String sdob, String photo_url)
	{
		String sql = "INSERT INTO stars (first_name, last_name, dob, photo_url) VALUES (?,?,?,?)";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql);)
		{
			stmt.setString(1, first_name);
			stmt.setString(2, last_name);
			stmt.setDate(3, (!sdob.isEmpty()) ? Date.valueOf(sdob) : null);
			stmt.setString(4, photo_url);
			
			System.out.println("Number of star details added: " + stmt.executeUpdate());
		}
		catch (SQLException e)
		{
			System.out.println("An error occurred: the star details provided were not added to the database");
		}
	}
}

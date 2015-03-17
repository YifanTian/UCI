package com.vrshah.moviedb.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vrshah.moviedb.ConnectionManager;

public class MovieManager {
	
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	public static void findMovieByStar(int id) throws SQLException 
	{	
		String sql = "SELECT movies.id, movies.title FROM movies JOIN stars_in_movies ON movies.id = stars_in_movies.movie_id JOIN stars ON stars.id = stars_in_movies.star_id WHERE stars.id = ?";
		
		try(PreparedStatement pStatement = conn.prepareStatement(sql);)
		{
			
			pStatement.setInt(1, id);
			
			ResultSet rs = pStatement.executeQuery();
			printResults(rs);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void findMovieByStarNames(String first_name, String last_name) throws SQLException {
		
		String sql = "SELECT movies.id, movies.title FROM movies JOIN stars_in_movies ON movies.id = stars_in_movies.movie_id JOIN stars ON stars.id = stars_in_movies.star_id ";
		
		PreparedStatement pStatement;
		
		if (first_name != "" && last_name != "")
		{
			sql += "WHERE stars.first_name LIKE ? AND stars.last_name LIKE ?";
			pStatement = conn.prepareStatement(sql);
			pStatement.setString(1, first_name + "%");
			pStatement.setString(2, last_name + "%");
		}
		else if (first_name == "")
		{
			sql += "WHERE stars.first_name LIKE ?";
			pStatement = conn.prepareStatement(sql);
			pStatement.setString(1, first_name + "%");
		}
		else if (last_name == "")
		{
			sql += "WHERE stars.last_name LIKE ?";
			pStatement = conn.prepareStatement(sql);
			pStatement.setString(1, last_name + "%");
		}
		else
		{
			System.out.println("Insufficient search parameters provided: star names");
			return;
		}
		
		ResultSet rs = pStatement.executeQuery();
		printResults(rs);
	}
	
	public static void printResults(ResultSet rs) throws SQLException
	{
		while (rs.next())
		{
			StringBuffer bf = new StringBuffer();
			bf.append(rs.getInt("id") + ": ");
			bf.append(rs.getString("title"));
			System.out.println(bf.toString());
		}
	}
}

package com.vrshah.moviedb.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.vrshah.moviedb.ConnectionManager;

public class QueryExecuter {

	private static final String SELECT_COMMAND = "SELECT";
	private static final String DELETE_COMMAND = "DELETE";
	private static final String INSERT_COMMAND = "INSERT";
	private static final String UPDATE_COMMAND = "UPDATE";
	
	private static Connection conn = ConnectionManager.getInstance().getConnection();

	public static void executeQuery(String sqlStatement) {

		String statement = sqlStatement.toUpperCase();

		if (statement.startsWith(SELECT_COMMAND))
		{
			executeSelectQuery(sqlStatement);
		}
		else if (statement.startsWith(UPDATE_COMMAND))
		{
			executeUpdateInsertDeleteQuery(sqlStatement, UPDATE_COMMAND);
		}
		else if (statement.startsWith(INSERT_COMMAND))
		{
			executeUpdateInsertDeleteQuery(sqlStatement, INSERT_COMMAND);
		}
		else if (statement.startsWith(DELETE_COMMAND))
		{
			executeUpdateInsertDeleteQuery(sqlStatement, DELETE_COMMAND);
		}
		else
		{
			System.out.println("Invalid SELECT/UPDATE/INSERT/DELETE SQL Command");
		}
	}

	public static void executeSelectQuery(String sqlStatement)
	{
		try 
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlStatement);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			while (rs.next()) 
			{
				for (int i = 1; i <= columnCount; i++)
				{
					if (i > 1 && i != columnCount + 1)
					{
						System.out.print(", ");
					}
					System.out.print(rsmd.getColumnName(i).toUpperCase() + ": " + rs.getString(i));
				}
				System.out.println("");
			}
		}
		catch (SQLException e)
		{
			System.out.println("SQL " + SELECT_COMMAND + " Error!");
		}
	}

	public static void executeUpdateInsertDeleteQuery(String sqlStatement, String commandType)
	{
		try 
		{
			Statement stmt = conn.createStatement();
			int result = stmt.executeUpdate(sqlStatement);
			System.out.println(commandType + " command successful: " + result + " rows affected.");
		}
		catch (SQLException e)
		{
			System.out.println("SQL " + commandType + " Error!");
		}
	}

}

package com.vrshah.moviedb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager
{
	private static ConnectionManager instance = null;

	private  String userName = "dbuser";
	private  String password = "dbpassword";
	private String dbName = "moviedb";
	private String host = "localhost";
	private final String M_CONN_STRING ="jdbc:mysql://";
	private Connection conn = null;

	private ConnectionManager() {}

	public static ConnectionManager getInstance() 
	{
		if (instance == null) 
		{
			instance = new ConnectionManager();
		}
		return instance;
	}

	private boolean openConnection()
	{
		try 
		{
			conn = DriverManager.getConnection(M_CONN_STRING + host + "/" + dbName, userName, password);
			return true;
		}
		catch (SQLException e) 
		{
			return false;
		}
	}

	public Connection getConnection()
	{
		if (conn == null) 
		{
			if (openConnection()) 
			{
				return conn;
			}
			else 
			{
				return null;
			}
		}
		return conn;
	}

	public void close() 
	{
		try 
		{
			conn.close();
			conn = null;
		} 
		catch (Exception e) { }
	}
	
	public static void processException(SQLException e) 
	{
		System.err.println("Error message: " + e.getMessage());
		System.err.println("Error code: " + e.getErrorCode());
		System.err.println("SQL state: " + e.getSQLState());
	}
	
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}
	
	public void setDbName(String dbName) 
	{
		this.dbName = dbName;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	public void setHost(String host) 
	{
		this.host = host;
	}

}
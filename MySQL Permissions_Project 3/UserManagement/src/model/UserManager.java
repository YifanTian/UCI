package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import beans.User;
import connection.ConnectionManager;

public class UserManager {
	
	private static final ObservableList<User> users = FXCollections.observableArrayList();
	
	public static void fetchUsers()
	{
		users.clear();
		
		try 
		{
			Connection connection = ConnectionManager.getSessionConnection();
			
			String sql = "SELECT Host, User, Select_priv, Insert_priv, Update_priv, Delete_priv, Create_priv, Drop_priv, "
					+ "Reload_priv, Shutdown_priv, Process_priv, File_priv, Grant_priv, References_priv, Index_priv, Alter_priv, "
					+ "Show_db_priv, Super_priv, Create_tmp_table_priv, Lock_tables_priv, Execute_priv, Repl_slave_priv, "
					+ "Repl_client_priv, Create_view_priv, Show_view_priv, Create_routine_priv, Alter_routine_priv, "
					+ "Create_user_priv, Event_priv, Trigger_priv, Create_tablespace_priv FROM mysql.user";
			
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			
			while (resultSet.next())
			{
				String host = resultSet.getString(1);
				String username = resultSet.getString(2);
				
				ArrayList<String> privileges = new ArrayList<String>();
				
				for (int i = 3; i <= resultSet.getMetaData().getColumnCount(); i++)
				{
					privileges.add(resultSet.getString(i));
				}
				
				if (username != null && !username.isEmpty()) 
				{
					users.add(new User(username, host, privileges));
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static ObservableList<User> getUsers() 
	{	
		return users;
	}
	
	public static boolean createUser(String username, String password, String host) throws Exception
	{
		if (host == null || host.isEmpty())
		{
			host = "localhost";
		}
		for (User user : users) {
			if (user.getUsername().equals(username) && user.getHost().equals(host)) {
				return true;
			}
		}
		
		Connection connection = ConnectionManager.getSessionConnection();
		
		PreparedStatement preparedStatement = connection.prepareStatement("CREATE USER ?@? IDENTIFIED BY ?");
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, host);
		preparedStatement.setString(3, password);
		
		return (preparedStatement.executeUpdate() == 1) ? true : false;
	}
	
	public static boolean deleteUser(User user) throws Exception
	{
		Connection connection = ConnectionManager.getSessionConnection();
		
		PreparedStatement preparedStatement = connection.prepareStatement("DROP USER ?@?");
		preparedStatement.setString(1, user.getUsername());
		preparedStatement.setString(2, user.getHost());
		
		return (preparedStatement.executeUpdate() == 1) ? true : false;
	}
}

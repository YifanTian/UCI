package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import beans.ColumnResource;
import beans.DatabaseLevelResource;
import beans.DatabaseResource;
import beans.TableResource;
import beans.User;
import connection.ConnectionManager;

public class DatabaseLevelManager {
	
	private static final String NOT_ALLOWED = "N";
	
	private static final ObservableList<DatabaseLevelResource> resources = FXCollections.observableArrayList();
	
	public static void fetchDatabaseData(User user)
	{
		resources.clear();
		
		try 
		{
			Connection connection = ConnectionManager.getSessionConnection();
			
			Statement databaseStatement = connection.createStatement();
			ResultSet databaseResultSet = databaseStatement.executeQuery("SHOW DATABASES");
			
			while (databaseResultSet.next())
			{
				String databaseName = databaseResultSet.getString(1);
				
				String sqlPrivilegesQuery = "SELECT Db, Select_priv, Insert_priv, Update_priv, Delete_priv, Create_priv, Drop_priv, "
						+ "Grant_priv, References_priv, Index_priv, Alter_priv, Create_tmp_table_priv, Lock_tables_priv, Create_view_priv, "
						+ "Show_view_priv, Create_routine_priv, Alter_routine_priv, Execute_priv, Event_priv, Trigger_priv FROM mysql.db "
						+ "WHERE Host = ? AND User = ? AND Db = ?";
		
				PreparedStatement privilegesStatement = connection.prepareStatement(sqlPrivilegesQuery);
				privilegesStatement.setString(1, user.getHost());
				privilegesStatement.setString(2, user.getUsername());
				privilegesStatement.setString(3, databaseName);
				
				ResultSet privilegesResultSet = privilegesStatement.executeQuery();
				
				ArrayList<String> privileges = new ArrayList<String>();
				
				if (privilegesResultSet.next())
				{
					for (int i = 2; i <= privilegesResultSet.getMetaData().getColumnCount(); i++)
					{
						privileges.add(privilegesResultSet.getString(i));
					}
				}
				else
				{
					for (int i = 0; i < DatabaseResource.getPrivilegeTitles().size(); i++)
					{
						privileges.add(NOT_ALLOWED);
					}
				}
				
				DatabaseResource db = new DatabaseResource(databaseName, privileges);
				
				if (!databaseName.equals("information_schema"))
				{
					fetchTableData(db, user);
					
					resources.add(db);
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private static void fetchTableData(DatabaseResource database, User user)
	{
		database.getTables().clear();
		
		try 
		{
			Connection connection = ConnectionManager.getSessionConnection();
			connection.setCatalog(database.getResourceName());
			
			Statement tableStatement = connection.createStatement();
			ResultSet tableResultSet = tableStatement.executeQuery("SHOW TABLES");
			
			while (tableResultSet.next())
			{
				String tableName = tableResultSet.getString(1);
				
				String sqlPrivilegesQuery = "SELECT Table_name, Table_priv FROM mysql.tables_priv "
						+ "WHERE Host = ? AND User = ? AND Table_name = ?";
				PreparedStatement privilegesStatement = connection.prepareStatement(sqlPrivilegesQuery);
				privilegesStatement.setString(1, user.getHost());
				privilegesStatement.setString(2, user.getUsername());
				privilegesStatement.setString(3, tableName);
				
				ResultSet privilegesResultSet = privilegesStatement.executeQuery();
				
				ArrayList<String> tablePrivileges = new ArrayList<String>();
				
				if (privilegesResultSet.next())
				{
					tablePrivileges = new ArrayList<String>(Arrays.asList(privilegesResultSet.getString(2).split(",")));
				}
				
				TableResource table = new TableResource(tableName, database.getResourceName(), tablePrivileges);
				
				fetchColumnData(database, table, user);
				
				database.getTables().add(table);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private static void fetchColumnData(DatabaseResource database, TableResource table, User user)
	{
		table.getColumns().clear();
		
		try 
		{
			Connection connection = ConnectionManager.getSessionConnection();
			connection.setCatalog(database.getResourceName());

			Statement columnStatement = connection.createStatement();
			ResultSet columnResultSet = columnStatement.executeQuery("DESCRIBE " + database.getResourceName() + "." + table.getResourceName());
			
			connection.setCatalog("mysql");
			
			while (columnResultSet.next())
			{
				String columnName = columnResultSet.getString(1);
				
				String sqlPrivilegesQuery = "SELECT Column_name, Column_priv FROM mysql.columns_priv "
						+ "WHERE Host = ? AND User = ? AND Column_name = ?";
				PreparedStatement privilegesStatement = connection.prepareStatement(sqlPrivilegesQuery);
				privilegesStatement.setString(1, user.getHost());
				privilegesStatement.setString(2, user.getUsername());
				privilegesStatement.setString(3, columnName);
				
				ResultSet privilegesResultSet = privilegesStatement.executeQuery();
				
				ArrayList<String> privileges = new ArrayList<String>();
				
				if (privilegesResultSet.next())
				{
					privileges = new ArrayList<String>(Arrays.asList(privilegesResultSet.getString(2).split(",")));
				}
				
				ColumnResource column = new ColumnResource(columnName, table.getResourceName(), database.getResourceName(), privileges);
				
				table.getColumns().add(column);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static ObservableList<DatabaseLevelResource> getDatabaseLevelResources()
	{
		return resources;
	}
}

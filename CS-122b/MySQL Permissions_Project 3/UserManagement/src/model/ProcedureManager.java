package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import beans.ProcedureResource;
import beans.User;
import connection.ConnectionManager;

public class ProcedureManager {
	
	private static final ObservableList<ProcedureResource> resources = FXCollections.observableArrayList();

	public static void fetchProcedures(User user)
	{
		resources.clear();
		
		try 
		{
			Connection connection = ConnectionManager.getSessionConnection();
			
			String sqlProcedures = "SHOW PROCEDURE STATUS";
			Statement proceduresStatement = connection.createStatement();
			ResultSet proceduresResultSet = proceduresStatement.executeQuery(sqlProcedures);
			
			while (proceduresResultSet.next())
			{
				String procedureName = proceduresResultSet.getString(2);
				String databaseName = proceduresResultSet.getString(1);
				
				String sql = "SELECT Routine_name, Proc_priv FROM mysql.procs_priv "
						+ "WHERE Routine_name = ? AND Host = ? AND User = ?";
				PreparedStatement procedurePrivilegesStatement = connection.prepareStatement(sql);
				procedurePrivilegesStatement.setString(1, procedureName);
				procedurePrivilegesStatement.setString(2, user.getHost());
				procedurePrivilegesStatement.setString(3, user.getUsername());
				
				ResultSet privilegesResultSet = procedurePrivilegesStatement.executeQuery();
				
				ArrayList<String> privileges = null;
				
				if (privilegesResultSet.next())
				{
					privileges = new ArrayList<String>(Arrays.asList(privilegesResultSet.getString(2).split(",")));
				}
				else
				{
					privileges = new ArrayList<String>();
				}
				
				ProcedureResource resource = new ProcedureResource(procedureName, databaseName, privileges);
				
				resources.add(resource);
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static ObservableList<ProcedureResource> getProcedureResources()
	{
		return resources;
	}
}

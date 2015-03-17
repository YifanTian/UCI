package com.vrshah.moviedb.tables;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.vrshah.moviedb.ConnectionManager;

public class SchemaManager {
	
	private static Connection conn = ConnectionManager.getInstance().getConnection();

	public static void printSchema()
	{
		ResultSet resultTables = null;
		ResultSet resultColumns = null;
		ArrayList<String> tables = new ArrayList<>();
		
		try  
		{
			DatabaseMetaData metadata = conn.getMetaData();
			String[] tableTypes = {"TABLE"};
			resultTables = metadata.getTables(null, "%", "%", tableTypes);
			
			while (resultTables.next()) 
			{
				tables.add(resultTables.getString("TABLE_NAME"));
			}

			for (String tableName : tables) 
			{
				System.out.println("Table: " + tableName);
				System.out.println("----------------");
				
				resultColumns = metadata.getColumns(null, "%", tableName, "%");
				
				while (resultColumns.next()) 
				{
					StringBuffer buffer = new StringBuffer();
					buffer.append(resultColumns.getString("COLUMN_NAME"));
					buffer.append(": ");
					buffer.append(resultColumns.getString("TYPE_NAME"));
					System.out.println(buffer.toString());
				}
				
				System.out.println("");
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Database error: Schema Unavailable");
		}
		finally 
		{
			try 
			{
				resultTables.close();
				resultColumns.close();
			} 
			catch (SQLException e) { }
		}
		
	}
}

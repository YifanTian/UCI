package beans;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class DatabaseResource implements DatabaseLevelResource {
	
	private static final String ALLOWED = "Y";
	
	public static final String SELECT_TITLE = "SELECT";
	public static final String INSERT_TITLE = "INSERT";
	public static final String UPDATE_TITLE = "UPDATE";
	public static final String DELETE_TITLE = "DELETE";
	public static final String CREATE_TITLE = "CREATE";
	public static final String DROP_TITLE = "DROP";
	public static final String GRANT_OPTION_TITLE = "GRANT OPTION";
	public static final String REFERENCES_TITLE = "REFERENCES";
	public static final String INDEX_TITLE = "INDEX";
	public static final String ALTER_TITLE = "ALTER";
	public static final String CREATE_TEMP_TABLE_TITLE = "CREATE TEMPORARY TABLES";
	public static final String LOCK_TABLES_TITLE = "LOCK TABLES";
	public static final String CREATE_VIEW_TITLE = "CREATE VIEW";
	public static final String SHOW_VIEW_TITLE = "SHOW VIEW";
	public static final String CREATE_ROUTINE_TITLE = "CREATE ROUTINE";
	public static final String ALTER_ROUTINE_TITLE = "ALTER ROUTINE";
	public static final String EXECUTE_TITLE = "EXECUTE";
	public static final String EVENT_TITLE = "EVENT";
	public static final String TRIGGER_TITLE = "TRIGGER";

	private ArrayList<TableResource> tables = new ArrayList<TableResource>();
	
	private ArrayList<BooleanProperty> privileges = new ArrayList<BooleanProperty>();
	
	@SuppressWarnings("serial")
	private static ArrayList<String> privilegeTitles = new ArrayList<String>() {{
		add(SELECT_TITLE);
		add(INSERT_TITLE);
		add(UPDATE_TITLE);
		add(DELETE_TITLE);
		add(CREATE_TITLE);
		add(DROP_TITLE);
		add(GRANT_OPTION_TITLE);
		add(REFERENCES_TITLE);
		add(INDEX_TITLE);
		add(ALTER_TITLE);
		add(CREATE_TEMP_TABLE_TITLE);
		add(LOCK_TABLES_TITLE);
		add(CREATE_VIEW_TITLE);
		add(SHOW_VIEW_TITLE);
		add(CREATE_ROUTINE_TITLE);
		add(ALTER_ROUTINE_TITLE);
		add(EXECUTE_TITLE);
		add(EVENT_TITLE);
		add(TRIGGER_TITLE);
	}};

	private String databaseName;
	
	public DatabaseResource(String databaseName, ArrayList<String> privileges) 
	{
		this.databaseName = databaseName;
		
		setPrivileges(privileges);
	}
	
	public void checkAllPrivileges(boolean check) 
	{
		boolean boolValue = checkPermission("N");
		
		if (check) 
		{
			boolValue = checkPermission(ALLOWED);
		}
		
		for (int i=0; i < privileges.size(); i++) 
		{
			privileges.get(i).set(boolValue);
		}
	}
	
	@Override
	public String getResourceName() 
	{
		return databaseName;
	}

	@Override
	public String getResourceType() 
	{
		return "Database";
	}

	public ArrayList<TableResource> getTables() 
	{
		return tables;
	}

	public void setTables(ArrayList<TableResource> tables) 
	{
		this.tables = tables;
	}
	
	public ArrayList<BooleanProperty> getPrivileges() 
	{
		return privileges;
	}

	public void setPrivileges(ArrayList<String> privileges) 
	{
		for (int i = 0; i < privileges.size(); i++)
		{
			boolean boolValue = checkPermission(privileges.get(i));
			this.privileges.add(new SimpleBooleanProperty(boolValue));
		}
	}
	
	public boolean checkPermission(String permission)
	{
		return (permission.equals(ALLOWED));
	}
	
	public static ArrayList<String> getPrivilegeTitles()
	{
		return privilegeTitles;
	}

	@Override
	public ArrayList<String> getColumnTitles() 
	{
		return privilegeTitles;
	}
}

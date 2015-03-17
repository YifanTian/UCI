package beans;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class User {
	
	private static final String ALLOWED = "Y";
	
	public static final String SELECT_TITLE = "SELECT";
	public static final String INSERT_TITLE = "INSERT";
	public static final String UPDATE_TITLE = "UPDATE";
	public static final String DELETE_TITLE = "DELETE";
	public static final String CREATE_TITLE = "CREATE";
	public static final String DROP_TITLE = "DROP";
	public static final String RELOAD_TITLE = "RELOAD";
	public static final String SHUTDOWN_TITLE = "SHUTDOWN";
	public static final String PROCESS_TITLE = "PROCESS";
	public static final String FILE_TITLE = "FILE";
	public static final String REFERENCES_TITLE = "REFERENCES";
	public static final String INDEX_TITLE = "INDEX";
	public static final String ALTER_TITLE = "ALTER";
	public static final String SHOW_DATABASES_TITLE = "SHOW DATABASES";
	public static final String SUPER_TITLE = "SUPER";
	public static final String CREATE_TEMPORARY_TABLES_TITLE = "CREATE TEMPORARY TABLES";
	public static final String LOCK_TABLES_TITLE = "LOCK TABLES";
	public static final String EXECUTE_TITLE = "EXECUTE";
	public static final String REPLICATION_SLAVE_TITLE = "REPLICATION SLAVE";
	public static final String REPLICATION_CLIENT_TITLE = "REPLICATION CLIENT";
	public static final String CREATE_VIEW_TITLE = "CREATE VIEW";
	public static final String SHOW_VIEW_TITLE = "SHOW VIEW";
	public static final String CREATE_ROUTINE_TITLE = "CREATE ROUTINE";
	public static final String ALTER_ROUTINE_TITLE = "ALTER ROUTINE";
	public static final String CREATE_USER_TITLE = "CREATE USER";
	public static final String EVENT_TITLE = "EVENT";
	public static final String TRIGGER_TITLE = "TRIGGER";
	public static final String CREATE_TABLESPACE_TITLE = "CREATE TABLESPACE";
	
	@SuppressWarnings("serial")
	public static final ArrayList<String> privilegeTitles = new ArrayList<String>(){{
		add(SELECT_TITLE);
		add(INSERT_TITLE);
		add(UPDATE_TITLE);
		add(DELETE_TITLE);
		add(CREATE_TITLE);
		add(DROP_TITLE);
		add(RELOAD_TITLE);
		add(SHUTDOWN_TITLE);
		add(PROCESS_TITLE);
		add(FILE_TITLE);
		add(REFERENCES_TITLE);
		add(INDEX_TITLE);
		add(ALTER_TITLE);
		add(SHOW_DATABASES_TITLE);
		add(SUPER_TITLE);
		add(CREATE_TEMPORARY_TABLES_TITLE);
		add(LOCK_TABLES_TITLE);
		add(EXECUTE_TITLE);
		add(REPLICATION_SLAVE_TITLE);
		add(REPLICATION_CLIENT_TITLE);
		add(CREATE_VIEW_TITLE);
		add(SHOW_VIEW_TITLE);
		add(CREATE_ROUTINE_TITLE);
		add(ALTER_ROUTINE_TITLE);
		add(CREATE_USER_TITLE);
		add(EVENT_TITLE);
		add(TRIGGER_TITLE);
		add(CREATE_TABLESPACE_TITLE);
	}};
	
	private String username;
	private String host;
	
	private ArrayList<BooleanProperty> privileges = new ArrayList<BooleanProperty>();
	
	public User(String username, String host)
	{
		this.username = username;
		this.host = host;
	}
	
	public User(String username, String host, ArrayList<String> privileges)
	{
		this.username = username;
		this.host = host;
		
		this.setPrivileges(privileges);
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
	
	public boolean checkPermission(String permission)
	{
		return (permission.equals(ALLOWED));
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getHost()
	{
		return host;
	}
	
	public String getDisplayName()
	{
		return username + "@" + host;
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

	public ArrayList<String> getPrivilegeTitles() 
	{
		return privilegeTitles;
	}
}

package beans;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ColumnResource implements DatabaseLevelResource {
	
	private static final String ALLOWED = "Y";
	
	private String columnName;
	
	private String tableName;
	private String databaseName;
	
	private ArrayList<BooleanProperty> privileges = new ArrayList<BooleanProperty>();
	
	@SuppressWarnings("serial")
	private static ArrayList<String> privilegeTitles = new ArrayList<String>() {{
		add("SELECT");
		add("INSERT");
		add("UPDATE");
		add("REFERENCES");
	}};
	
	@SuppressWarnings("serial")
	private static ArrayList<String> privilegeNames = new ArrayList<String>() {{
		add("Select");
		add("Insert");
		add("Update");
		add("References");
	}};
	
	public ColumnResource(String columnName, String tableName, String databaseName, ArrayList<String> privileges)
	{
		this.columnName = columnName;
		
		this.tableName = tableName;
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
	
	public String getTableName()
	{
		return tableName;
	}

	public String getDatabaseName()
	{
		return databaseName;
	}
	
	@Override
	public String getResourceName() 
	{
		return columnName;
	}

	@Override
	public String getResourceType() 
	{
		return "Column";
	}
	
	public ArrayList<BooleanProperty> getPrivileges() 
	{
		return privileges;
	}
	
	public void setPrivileges(ArrayList<String> privileges) 
	{
		for (int i = 0; i < privilegeNames.size(); i++)
		{
			boolean boolValue = checkPermission(privilegeNames.get(i), privileges);
			
			this.privileges.add(new SimpleBooleanProperty(boolValue));
		}
	}
	
	public boolean checkPermission(String permission, ArrayList<String> privileges)
	{
		boolean found = false;
		
		for (String element: privileges)
		{
			if (permission.toLowerCase().equals(element.toLowerCase()))
			{
				found = true;
			}
		}
		
		return found;
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
		return getPrivilegeTitles();
	}
}

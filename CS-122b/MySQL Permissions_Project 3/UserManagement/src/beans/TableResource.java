package beans;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TableResource implements DatabaseLevelResource {
	
	private static final String ALLOWED = "Y";
	
	private String tableName;
	
	private String databaseName;
	
	private ArrayList<ColumnResource> columns = new ArrayList<ColumnResource>();
	
	private ArrayList<BooleanProperty> privileges = new ArrayList<BooleanProperty>();

	@SuppressWarnings("serial")
	private static ArrayList<String> privilegeTitles = new ArrayList<String>() {{
		add("SELECT");
		add("INSERT");
		add("UPDATE");
		add("DELETE");
		add("CREATE");
		add("DROP");
		add("GRANT OPTION");
		add("REFERENCES");
		add("INDEX");
		add("ALTER");
		add("CREATE VIEW");
		add("SHOW VIEW");
		add("TRIGGER");
	}};
	
	@SuppressWarnings("serial")
	private static ArrayList<String> privilegeNames = new ArrayList<String>() {{
		add("Select");
		add("Insert");
		add("Update");
		add("Delete");
		add("Create");
		add("Drop");
		add("Grant");
		add("References");
		add("Index");
		add("Alter");
		add("Create View");
		add("Show view");
		add("Trigger");
	}};
	
	public TableResource(String tableName, String databaseName, ArrayList<String> privileges)
	{
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
	
	public String getDatabaseName()
	{
		return databaseName;
	}
	
	@Override
	public String getResourceName() 
	{
		return tableName;
	}

	@Override
	public String getResourceType() 
	{
		return "Table";
	}
	
	public ArrayList<ColumnResource> getColumns() 
	{
		return columns;
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
	
	public ArrayList<String> getColumnTitles() 
	{
		return privilegeTitles;
	}

	@Override
	public ArrayList<BooleanProperty> getPrivileges() 
	{
		return privileges;
	}
}

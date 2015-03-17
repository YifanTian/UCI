package beans;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ProcedureResource {

	private String procedureName;
	
	private String databaseName;
	
	private ArrayList<BooleanProperty> privileges = new ArrayList<BooleanProperty>();
	
	@SuppressWarnings("serial")
	private static ArrayList<String> columnTitles = new ArrayList<String>() {{
		add("Procedure Name");
		add("ALTER ROUTINE");
		add("EXECUTE");
		add("GRANT OPTION");
	}};
	
	@SuppressWarnings("serial")
	private static ArrayList<String> privilegeNames = new ArrayList<String>() {{
		add("Alter Routine");
		add("Execute");
		add("Grant");
	}};
	
	public ProcedureResource(String procedureName, String databaseName, ArrayList<String> privileges) 
	{
		this.procedureName = procedureName;
		
		this.databaseName = databaseName;
		
		setPrivileges(privileges);
	}
	
	public String getDatabaseName()
	{
		return databaseName;
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

	public String getProcedureName() 
	{
		return procedureName;
	}

	public ArrayList<BooleanProperty> getPrivileges() 
	{
		return privileges;
	}

	public static ArrayList<String> getColumnTitles() 
	{
		return columnTitles;
	}
}

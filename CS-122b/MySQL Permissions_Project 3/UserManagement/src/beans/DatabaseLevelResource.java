package beans;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;

public interface DatabaseLevelResource {
	
	public String getResourceName();
	public String getResourceType();
	
	public ArrayList<String> getColumnTitles();
	public ArrayList<BooleanProperty> getPrivileges();
	
	public void checkAllPrivileges(boolean check);
}

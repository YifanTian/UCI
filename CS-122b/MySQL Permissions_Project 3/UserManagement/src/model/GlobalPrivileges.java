package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import beans.ColumnResource;
import beans.DatabaseLevelResource;
import beans.DatabaseResource;
import beans.ProcedureResource;
import beans.TableResource;
import beans.User;
import connection.ConnectionManager;

public class GlobalPrivileges {

	public static void updatePrivileges(User user) throws Exception {
		Connection connection = ConnectionManager.getSessionConnection();
		String sql;

		Statement initial = connection.createStatement();
		initial.executeQuery("REVOKE ALL PRIVILEGES, GRANT OPTION FROM "
				+ user.getUsername() + "@" + user.getHost());

		ArrayList<BooleanProperty> privileges = user.getPrivileges();
		ArrayList<String> titles = user.getPrivilegeTitles();
		for (int i = 0; i < privileges.size() - 1; i++) {
			sql = "";
			if (privileges.get(i).getValue() == true) {
				sql += "GRANT ";
				sql += titles.get(i);
				sql += " ON *.* TO '" + user.getUsername() + "'@'"
						+ user.getHost() + "'";
				Statement statement = connection.createStatement();
				System.out.println(sql);
				statement.executeQuery(sql);
			}
			// else {
			// sql += "REVOKE ";
			// sql += titles.get(i);
			// sql += " ON *.* FROM '" + user.getUsername() + "'@'"
			// + user.getHost() + "'";
			// }
		}
	}

	public static void updateDatabasePrivileges(User user,
			ObservableList<DatabaseLevelResource> resources,
			ObservableList<ProcedureResource> procedures) throws Exception {
		Connection connection = ConnectionManager.getSessionConnection();
		String sql;
		for (DatabaseLevelResource resource : resources) {
			sql = "";
			if (resource.getResourceType() == "Database") {
				if (resource.getResourceName() != "information_schema"
						&& resource.getResourceName() != "mysql"
						&& resource.getResourceName() != "performance_schema") {

					// GRANT SELECT, INSERT ON mydb.* TO 'someuser'@'somehost';
					for (int i = 0; i < resource.getColumnTitles().size(); i++) {
						if (resource.getPrivileges().get(i).getValue()
								.booleanValue()) {
							sql = "GRANT " + resource.getColumnTitles().get(i)
									+ " ON ";
							sql += resource.getResourceName() + ".* TO '"
									+ user.getUsername() + "'@'"
									+ user.getHost() + "'";
							Statement statement = connection.createStatement();
							System.out.println(sql);
							statement.executeQuery(sql);
						}
					}
					
					DatabaseResource db = (DatabaseResource) resource;
					updateTablesPrivileges(db.getTables(), user,
							connection);
				}
			}
			updateProcedurePrivileges(procedures, user, connection);
		}
	}

	public static void updateTablesPrivileges(
			ArrayList<TableResource> resources, User user, Connection connection)
			throws SQLException {
		for (TableResource resource : resources) {
			String sql = "";
			for (int i = 0; i < resource.getColumnTitles().size(); i++) {
				if (resource.getPrivileges().get(i).getValue().booleanValue()) {
					sql = "GRANT " + resource.getColumnTitles().get(i) + " ON "
							+ resource.getDatabaseName() + ".";
					sql += resource.getResourceName() + " TO '"
							+ user.getUsername() + "'@'" + user.getHost() + "'";
					Statement statement = connection.createStatement();
					System.out.println(sql);
					statement.executeQuery(sql);
				}
			}
			updateColumn(resource.getColumns(), user, connection);
		}
	}
	
	public static void updateColumn(ArrayList<ColumnResource> resources, User user, Connection connection) throws SQLException
	{
		for (ColumnResource resource: resources)
		{
			String sql = "";
			for (int i = 0; i < resource.getColumnTitles().size(); i++) {
				if (resource.getPrivileges().get(i).getValue()
						.booleanValue()) {
					sql = "GRANT " + resource.getColumnTitles().get(i)
							+ " (" + resource.getResourceName()
							+ ") ON ";
					sql += resource.getDatabaseName() + "."
							+ resource.getTableName();
					sql += " TO '" + user.getUsername() + "'@'"
							+ user.getHost() + "'";
					
					Statement statement = connection.createStatement();
					System.out.println(sql);
					statement.executeQuery(sql);
				}
			}
		}
	}
	
	public static void updateProcedurePrivileges(ObservableList<ProcedureResource> procedures, User user, Connection connection) throws SQLException
	{
		for (ProcedureResource procedure: procedures)
		{
			String sql = "";
			for (int i = 1; i < ProcedureResource.getColumnTitles().size(); i++) {
				if (procedure.getPrivileges().get(i - 1).getValue()
						.booleanValue()) {
					sql = "GRANT " + ProcedureResource.getColumnTitles().get(i);
					sql += " ON PROCEDURE " + procedure.getDatabaseName() + "."
							+ procedure.getProcedureName();
					sql += " TO '" + user.getUsername() + "'@'"
							+ user.getHost() + "'";
					
					Statement statement = connection.createStatement();
					System.out.println(sql);
					statement.executeQuery(sql);
				}
			}
		}
	}
}


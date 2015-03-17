package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Reporter {

	private static Connection conn;
	private static ResultSet rs;
	private static Statement stmt;
	private static ArrayList<Integer> result;
	private static final String SQL = "SELECT \"tbl_author_document_mapping\" AS table_name, COUNT(*) AS erc FROM dblp.tbl_author_document_mapping UNION SELECT \"tbl_booktitle\" AS table_name, COUNT(*) AS exact_row_count FROM dblp.tbl_booktitle UNION SELECT \"tbl_dblp_document\" AS table_name, COUNT(*) AS exact_row_count FROM dblp.tbl_dblp_document UNION SELECT \"tbl_genres\" AS table_name, COUNT(*) AS exact_row_count FROM dblp.tbl_genres UNION  SELECT \"tbl_people\" AS table_name, COUNT(*) AS exact_row_count FROM dblp.tbl_people UNION SELECT \"tbl_publisher\" AS table_name, COUNT(*) AS exact_row_count FROM dblp.tbl_publisher";


	public static ArrayList<Integer> report() {
		result = new ArrayList<Integer>();
		try
		{
			conn = ConnectionManager.getSessionConnection();
			conn.setCatalog("dblp");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			rs.beforeFirst();
			while (rs.next()) 
			{
				result.add(rs.getInt("erc"));
			}
			return result;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		result.add(-1);
		result.add(-1);
		result.add(-1);
		result.add(-1);
		result.add(-1);
		result.add(-1);
		return result;
	}
}

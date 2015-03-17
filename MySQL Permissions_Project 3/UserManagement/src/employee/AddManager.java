package employee;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

import connection.ConnectionManager;

public class AddManager {

	public static Connection conn;

	private static String employee_email;
	private static String employee_password;
	private static String employee_fullname;
	
	private static Statement stmt;
	
	private static Scanner in = new Scanner(System.in);
	
	private static String login = "SELECT count(*), email, password, fullname FROM employees as e WHERE e.email = ? AND e.password = ?";

	private static boolean valid = false;
	private static boolean finished = false;

	public static String send(String title, int year, String director,
			String banner, String trailer, String genre, String first,
			String last, Date DOB, String photo) 
	{
		String result = "Invalid Fields";
		
		try
		{
			conn = ConnectionManager.getSessionConnection();
			conn.setCatalog("moviedb");
			
			CallableStatement cs = conn
					.prepareCall("{CALL add_movie(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			cs.setString(1, title);
			cs.setInt(2, year);
			cs.setString(3, director);
			cs.setString(4, banner);
			cs.setString(5, trailer);
			cs.setString(6, genre);
			cs.setString(7, first);
			cs.setString(8, last);
			cs.setDate(9, DOB);
			cs.setString(10, photo);
			cs.registerOutParameter(11, Types.INTEGER); // movie_count
			cs.registerOutParameter(12, Types.INTEGER); // genre_count
			cs.registerOutParameter(13, Types.INTEGER); // star_count
			cs.registerOutParameter(14, Types.INTEGER); // ref_movie_id
			cs.registerOutParameter(15, Types.INTEGER); // ref_genre_id
			cs.registerOutParameter(16, Types.INTEGER); // ref_star_id
			cs.registerOutParameter(17, Types.INTEGER); // genres_in_movies_count
			cs.registerOutParameter(18, Types.INTEGER); // stars_in_movies_count
			
			cs.executeUpdate();
			
			String film_success = (cs.getInt(11) > 0) ? "Movie Already Present"
					: "Movie Added: " + cs.getInt(14);
			String genre_success = (cs.getInt(12) > 0) ? "Genre Already Present"
					: "Genre Added: " + cs.getInt(15);
			String star_success = (cs.getInt(13) > 0) ? "Star Already Present"
					: "Star Added: " + cs.getInt(16);
			String genre_linked = (cs.getInt(17) == 0) ? "Movie Linked To Genre"
					: "Movie Already Linked To Genre";
			String star_linked = (cs.getInt(18) == 0) ? "Movie Linked To Star"
					: "Movie Already Linked To Star";
			
			result = String.format("%s\n%s\n%s\n%s\n%s\n ", film_success,
					genre_success, star_success, genre_linked, star_linked);
			
			return result;

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return result;
	}

	public static boolean login(String username, String password) 
	{
		try 
		{
			conn = ConnectionManager.getSessionConnection();
			conn.setCatalog("moviedb");
			
			PreparedStatement ps = conn.prepareStatement(login);
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			rs.first();
			
			if (rs.getInt(1) == 1) 
			{
				employee_email = rs.getString(2);
				employee_password = rs.getString(3);
				employee_fullname = rs.getString(4);
				return true;
			} 
			else 
			{
				System.out.println("Employee Not Found");
				return false;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}

	public static Connection getConn() {
		return conn;
	}

	public static void setConn(Connection conn) {
		AddManager.conn = conn;
	}

	public static String getEmployee_email() {
		return employee_email;
	}

	public static void setEmployee_email(String employee_email) {
		AddManager.employee_email = employee_email;
	}

	public static String getEmployee_password() {
		return employee_password;
	}

	public static void setEmployee_password(String employee_password) {
		AddManager.employee_password = employee_password;
	}

	public static String getEmployee_fullname() {
		return employee_fullname;
	}

	public static void setEmployee_fullname(String employee_fullname) {
		AddManager.employee_fullname = employee_fullname;
	}

	public static Statement getStmt() {
		return stmt;
	}

	public static void setStmt(Statement stmt) {
		AddManager.stmt = stmt;
	}

	public static Scanner getIn() {
		return in;
	}

	public static void setIn(Scanner in) {
		AddManager.in = in;
	}

	public static String getLogin() {
		return login;
	}

	public static void setLogin(String login) {
		AddManager.login = login;
	}

	public static boolean isValid() {
		return valid;
	}

	public static void setValid(boolean valid) {
		AddManager.valid = valid;
	}

	public static boolean isFinished() {
		return finished;
	}

	public static void setFinished(boolean finished) {
		AddManager.finished = finished;
	}
	
	
}

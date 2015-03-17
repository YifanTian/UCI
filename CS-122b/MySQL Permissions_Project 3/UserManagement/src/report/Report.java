package report;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.ConnectionManager;

public class Report {
	public static Connection conn;
	public static PrintWriter writer;
	public static Statement stmt;
	public static File file;
	public static ResultSet rs;
	
	public enum Query {
		MOVIESNOSTARS ("SELECT id AS 'Movie ID', title AS 'Movie Title', year AS 'Movie Year' FROM movies WHERE id NOT IN(SELECT movie_id AS id FROM stars_in_movies)"),
		STARSNOMOVIES("SELECT id AS 'Star ID', first_name AS 'Star First Name', last_name AS 'Star Last Name' FROM stars WHERE id NOT IN (SELECT star_id AS id FROM stars_in_movies)"),
		MOVIESNOGENRES("SELECT id AS 'Movie ID', title AS 'Movie Title', year AS 'Movie Year' FROM movies WHERE id NOT IN (SELECT movie_id AS id FROM genres_in_movies)"),
		GENRESNOMOVIES("SELECT id AS 'Genre ID', name AS 'Genre Name' FROM genres WHERE id NOT IN (SELECT genre_id AS id FROM genres_in_movies)"),
		STARSNOFIRSTLASTNAME("SELECT id AS 'Star ID', first_name AS 'Star First Name', last_name AS 'Star Last Name' FROM stars WHERE first_name = '' OR last_name = ''"),
		EXPIREDCREDITCARDS("SELECT c.id AS 'Customer ID', c.first_name AS 'Customer First Name', c.last_name AS 'Customer Last Name', c.cc_id AS 'Credit Card Number', cc.expiration AS 'Credit Card Expiration Date 'FROM customers AS c, creditcards AS cc WHERE c.cc_id = cc.id AND cc.expiration < CURDATE()"),
		DUPLICATEMOVIES("SELECT m.* FROM movies AS m WHERE m.id IN (SELECT id FROM movies GROUP BY title, year HAVING COUNT(*) > 1)"),
		DUPLICATESTARS("SELECT s.* FROM stars AS s WHERE s.id IN (SELECT id FROM stars GROUP BY first_name, last_name HAVING COUNT(*) > 1)"),
		DUPLICATEGENRES("SELECT g.* FROM genres AS g WHERE g.id IN (SELECT id FROM genres GROUP BY name HAVING COUNT(*) > 1)"),
		BIRTHDAYFILTER("SELECT * FROM stars WHERE YEAR(dob) < 1900 OR dob > CURDATE()"),
		EMAILFILTER("SELECT c.* from customers AS c WHERE c.id NOT IN (SELECT id FROM customers WHERE email LIKE '%@%')");
		private String sql;
		Query (String s) {
			this.sql = s;
		}
		public final String toString () {
			return this.sql;
		}
	}

	public static void writeDocumentBegin () {
		writer.println("<!DOCTYPE html>\n<html>\n<head>\n<meta charset = \"utf-8\">\n<meta name=\"viewport\" content = \"width-device-width, initial-scale=1.0\">\n<meta name=\"description\" content=\"Database Report\">\n<title> FabFlix Report</title>\n<meta name=\"author\" content=\"Viral Shah\">\n<link href=\"https://maxcdn.bootstrapcdn.com/bootswatch/3.3.0/cyborg/bootstrap.min.css\" rel=\"stylesheet\">\n</head>\n<body>\n<div class = \"navbar navbar-inverse navbar-static-top\">\n<div class = \"container\">\n<a href = \"#\" class = \"navbar-brand\">FabFlix Report Sheet</a>\n</div>\n</div>\n");
	}
	
	public static void writeMoviesNoStars () throws SQLException {
		rs = response(Query.MOVIESNOSTARS);
		rs.last();
		int rows = rs.getRow();
		writer.println("<div class=\"panel panel-default\">\n<div class=\"panel-heading\">Movies With No Stars</div>\n<div class=\"panel-body\">\n<p></p>\n</div>\n<table class=\"table table-striped\">\n<tr>\n<th>Movie ID</th>\n<th>Movie Title</th>\n<th>Movie Year</th>\n</tr>\n");
		if (rows > 0) {
			rs.beforeFirst();
			while (rs.next()) {
				String s = String.format("<tr>\n<td>%d</td>\n<td>%s</td>\n<td>%d</td>\n</tr>\n", rs.getInt(1), rs.getString(2), rs.getInt(3));
				writer.println(s);
			}
			writer.println("<tr>"+rows+": Rows Fetched</tr>");
		}
		else {
			writer.println("<tr>No Results</tr>\n");
		}
		writer.println("</table>\n</div>\n");
		rs.close();
	}
	
	public static void writeStarsNoMovies () throws SQLException {
		rs = response(Query.STARSNOMOVIES);
		rs.last();
		int rows = rs.getRow();
		writer.println("<div class=\"panel panel-default\">\n <div class=\"panel-heading\">Stars With No Movies</div>\n<div class=\"panel-body\">\n <p></p>\n </div>\n <table class=\"table table-striped\">\n <tr>\n <th>Star ID</th>\n <th>Star First Name</th>\n <th>Star Last Name</th>\n </tr>\n");
		if (rows > 0) {
			rs.beforeFirst();
			while (rs.next()) {
				String s = String.format("<tr>\n <td>%d</td>\n <td>%s</td>\n <td>%s</td>\n </tr>\n", rs.getInt(1), rs.getString(2), rs.getString(3));
				writer.println(s);
			}			
			writer.println("<tr>"+rows+": Rows Fetched</tr>");
		}
		else {
			writer.println("<tr>No Results</tr>\n");
		}
		writer.println("</table>\n</div>\n");
		rs.close();
	}
	
	public static void writeGenresNoMovies () throws SQLException {
		rs = response(Query.GENRESNOMOVIES);
		rs.last();
		int rows = rs.getRow();
		writer.println("<div class=\"panel panel-default\">\n <div class=\"panel-heading\">Genres With No Movies</div>\n <div class=\"panel-body\">\n <p></p>\n </div>\n <table class=\"table table-striped\">\n <tr>\n <th>Genre ID</th>\n <th>Genre Name</th>\n </tr>\n");
		if (rows > 0) {
			rs.beforeFirst();
			while (rs.next()) {
				String s = String.format("<tr>\n <td>%d</td>\n <td>%s</td>\n </tr>\n", rs.getInt(1), rs.getString(2));
				writer.println(s);
			}			
			writer.println("<tr>"+rows+": Rows Fetched</tr>");
		}
		else {
			writer.println("<tr>No Results</tr>\n");
		}
		writer.println("</table>\n</div>\n");
		rs.close();
		}
	
	public static void writeMoviesNoGenres () throws SQLException {
		rs = response(Query.MOVIESNOGENRES);
		rs.last();
		int rows = rs.getRow();
		writer.println("<div class=\"panel panel-default\">\n<div class=\"panel-heading\">Movies With No Genres</div>\n<div class=\"panel-body\">\n<p></p>\n</div>\n<table class=\"table table-striped\">\n<tr>\n<th>Movie ID</th>\n<th>Movie Title</th>\n<th>Movie Year</th>\n</tr>\n<tr>\n");
		if (rows > 0) {
			rs.beforeFirst();
			while (rs.next()) {
				String s = String.format("<td>%d</td>\n<td>%s</td>\n<td>%d</td>\n</tr>\n", rs.getInt(1), rs.getString(2), rs.getInt(3));
				writer.println(s);
			}			
			writer.println("<tr>"+rows+": Rows Fetched</tr>");
		}
		else {
			writer.println("<tr>No Results</tr>\n");
		}
		writer.println("</table>\n</div>\n");
		rs.close();
	}
	
	public static void writeStarsNoFirstLastName () throws SQLException {
		rs = response(Query.STARSNOFIRSTLASTNAME);
		rs.last();
		int rows = rs.getRow();
		writer.println("<div class=\"panel panel-default\">\n <div class=\"panel-heading\">Stars With No First Or Last Names</div>\n <div class=\"panel-body\">\n <p></p>\n </div>\n <table class=\"table table-striped\">\n <tr>\n <th>Star ID</th>\n <th>Star First Name</th>\n <th>Star Last Name</th>\n </tr>\n");
		if (rows > 0) {
			rs.beforeFirst();
			while (rs.next()) {
				String s = String.format("<tr>\n <td>%d</td>\n <td>%s</td>\n <td>%s</td>\n </tr>\n", rs.getInt(1), rs.getString(2), rs.getString(3));
				writer.println(s);
			}			
			writer.println("<tr>"+rows+": Rows Fetched</tr>");
		}
		else {
			writer.println("<tr>No Results</tr>\n");
		}
		writer.println("</table>\n</div>\n");
		rs.close();
	}
	
	public static void writeExpiredCreditCards () throws SQLException {
		rs = response(Query.EXPIREDCREDITCARDS);
		rs.last();
		int rows = rs.getRow();
		writer.println("<div class=\"panel panel-default\">\n <div class=\"panel-heading\">Expired Customer Credit Cards</div>\n <div class=\"panel-body\">\n <p></p>\n </div>\n <table class=\"table table-striped\">\n <tr>\n <th>Customer ID</th>\n <th>Customer First Name</th>\n <th>Customer Last Name</th>\n <th>Credit Card Number</th>\n <th>Credit Card Expiration Date</th>\n </tr>\n");
		if (rows > 0) {
			rs.beforeFirst();
			while (rs.next()) {
				String s = String.format("<tr> <td>%d</td> <td>%s</td> <td>%s</td> <td>%s</td> <td>%s</td> </tr>", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
				writer.println(s);
			}			
			writer.println("<tr>"+rows+": Rows Fetched</tr>");
		}
		else {
			writer.println("<tr>No Results</tr>\n");
		}
		writer.println("</table>\n</div>\n");
		rs.close();
	}
	
	public static void writeDuplicateMovies () throws SQLException {
		rs = response(Query.DUPLICATEMOVIES);
		rs.last();
		int rows = rs.getRow();
		writer.println("<div class=\"panel panel-default\">\n <div class=\"panel-heading\">Duplicate Movies</div>\n <div class=\"panel-body\">\n <p></p>\n </div>\n <table class=\"table table-striped\">\n <tr>\n <th>Movie ID</th>\n <th>Movie Title</th>\n <th>Movie Year</th>\n <th>Movie Director</th>\n <th>Movie Banner URL</th>\n <th>Movie Trailer URL</th>\n </tr>\n");
		if (rows > 0) {
			rs.beforeFirst();
			while (rs.next()) {
				String s = String.format("<tr>\n <td>%d</td>\n <td>%s</td>\n <td>%d</td>\n <td>%s</td>\n <td>%s</td>\n <td>%s</td>\n </tr>\n", rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6));
				writer.println(s);
			}
			writer.println("<tr>"+rows+": Rows Fetched</tr>");
		}
		else {
			writer.println("<tr>No Results</tr>\n");
		}
		writer.println("</table>\n</div>\n");
		rs.close();
	}
	
	public static void writeDuplicateStars () throws SQLException {
		rs = response(Query.DUPLICATESTARS);
		rs.last();
		int rows = rs.getRow();
		writer.println("<div class=\"panel panel-default\">\n <div class=\"panel-heading\">Duplicate Stars</div>\n <div class=\"panel-body\">\n <p></p>\n </div>\n <table class=\"table table-striped\">\n <tr>\n <th>Star ID</th>\n <th>Star First Name</th>\n <th>Star Last Name</th>\n <th>Star Date Of Birth</th>\n <th>Star Photo URL</th>\n </tr>\n");
		if (rows > 0) {
			rs.beforeFirst();
			while (rs.next()) {
				String s = String.format("<tr>\n <td>%d</td>\n <td>%s</td>\n <td>%s</td>\n <td>%s</td>\n <td>%s</td>\n </tr>\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5));
				writer.println(s);
			}			
			writer.println("<tr>"+rows+": Rows Fetched</tr>");
		}
		else {
			writer.println("<tr>No Results</tr>\n");
		}
		writer.println("</table>\n</div>\n");
		rs.close();
	}

	public static void writeDuplicateGenres () throws SQLException {
		rs = response(Query.DUPLICATEGENRES);
		rs.last();
		int rows = rs.getRow();
		writer.println("<div class=\"panel panel-default\">\n<div class=\"panel-heading\">Duplicate Genres</div>\n<div class=\"panel-body\">\n <p></p>\n</div>\n<table class=\"table table-striped\">\n<tr>\n<th>Genre ID</th>\n<th>Genre Name</th>\n</tr>\n");
		if (rows > 0) {
			rs.beforeFirst();
			while (rs.next()) {
				String s = String.format("<tr>\n <td>%d</td>\n <td>%s</td>\n </tr>\n", rs.getInt(1), rs.getString(2));
				writer.println(s);
			}			
			writer.println("<tr>"+rows+": Rows Fetched</tr>");
		}
		else {
			writer.println("<tr>No Results</tr>\n");
		}
		writer.println("</table>\n</div>\n");
		rs.close();
	}
	
	public static void writeBirthdayFilter () throws SQLException {
		rs = response(Query.BIRTHDAYFILTER);
		rs.last();
		int rows = rs.getRow();
		writer.println("<div class=\"panel panel-default\">\n<div class=\"panel-heading\">Birthday Filter</div>\n<div class=\"panel-body\">\n<p></p>\n</div>\n<table class=\"table table-striped\">\n<tr>\n<th>Star ID</th>\n<th>Star First Name</th>\n<th>Star Last Name</th>\n<th>Star Date Of Birth</th>\n<th>Star Photo URL</th>\n</tr>\n");
		if (rows > 0) {
			rs.beforeFirst();
			while (rs.next()) {
				String s = String.format("<tr>\n <td>%d</td>\n <td>%s</td>\n <td>%s</td>\n <td>%s</td>\n <td>%s</td>\n </tr>\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5));
				writer.println(s);
			}			
			writer.println("<tr>"+rows+": Rows Fetched</tr>");
		}
		else {
			writer.println("<tr>No Results</tr>\n");
		}
		writer.println("</table>\n</div>\n");
		rs.close();
	}
	
	public static void writeEmailFilter () throws SQLException {
		rs = response(Query.EMAILFILTER);
		rs.last();
		int rows = rs.getRow();
		writer.println("<div class=\"panel panel-default\">\n <div class=\"panel-heading\">Email Filter</div>\n <div class=\"panel-body\">\n <p></p>\n </div>\n <table class=\"table table-striped\">\n <tr>\n <th>Customer ID</th>\n <th>Customer First Name</th>\n <th>Customer Last Name</th>\n <th>Credit Card ID</th>\n <th>Customer Address</th>\n <th>Customer Email</th>\n <th>Customer Password</th>\n </tr>\n");
		if (rows > 0) {
			rs.beforeFirst();
			while (rs.next()) {
				String s = String.format("<tr>\n <td>%d</td>\n <td>%s</td>\n <td>%s</td>\n <td>%s</td>\n <td>%s</td>\n <td>%s</td>\n <td>%s</td>\n </tr>\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				writer.println(s);
			}			
			writer.println("<tr>"+rows+": Rows Fetched</tr>");
		}
		else {
			writer.println("<tr>No Results</tr>\n");
		}
		writer.println("</table>\n</div>\n");
		rs.close();
	}
	
	public static void writeDocumentEnd ( ) {
		writer.println("</body>");
		writer.println("</html>");
	}
	
	public static void generateReport() {
		file = new File("index.html");
		try {
			conn = ConnectionManager.getSessionConnection();
			stmt = conn.createStatement();
			stmt.execute("USE moviedb");
			
			writer = new PrintWriter(file, "UTF-8");
			writeDocumentBegin();
			writeMoviesNoStars();
			writeStarsNoMovies();
			writeGenresNoMovies();
			writeMoviesNoGenres();
			writeStarsNoFirstLastName();
			writeExpiredCreditCards();
			writeDuplicateMovies();
			writeDuplicateStars();
			writeDuplicateGenres();
			writeBirthdayFilter();
			writeEmailFilter();
			writeDocumentEnd();
			writer.println("");
			writer.close();
			
		} catch (Exception e) {
			e.getMessage();
		}
		
	}
	
	public static ResultSet response(Query q) throws SQLException {
		return conn.prepareStatement(q.toString()).executeQuery();	
	}
	
	
}

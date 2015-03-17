package servlets.auto_popups;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import custom_http.CustomHttpServletResponseWrapper;
import queries.SearchQuery;
import data_beans.Movie;

@WebServlet("/FabFlixSearchBar")
public class FabFlixSearchBar extends HttpServlet {
private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/MovieDB", type=javax.sql.DataSource.class)
	private DataSource dataSource;
	
	private Connection connection;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			if (connection == null || connection.isClosed())
			{
				connection = dataSource.getConnection();
			}
			
			String movieSearch = request.getParameter("searchText");
			
			if (movieSearch == null) 
			{
				return;
			}
			
			ArrayList<String> keywords = new ArrayList<String>(Arrays.asList(movieSearch.split(" ")));
			
			ArrayList<Movie> movies = SearchQuery.fastSearchMovies(keywords, connection);
			
			request.setAttribute("movies", movies);
			
			CustomHttpServletResponseWrapper customResponse = new CustomHttpServletResponseWrapper(response);
		    request.getRequestDispatcher("FabFlixAutocompleteDropdown.jsp").forward(request, customResponse);
			
		    response.setContentType("text/plain");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(customResponse.toString());
		    
		    if (connection != null && !connection.isClosed())
			{
				connection.close();
			}
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
}

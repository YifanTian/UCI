package servlets.login_logout;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import queries.SearchQuery;
import session.SessionCart;
import data_beans.Customer;


@WebServlet("/Login")
public class Login extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/MovieDB", type=javax.sql.DataSource.class)
	private DataSource dataSource;
	
	private Connection connection;
	private HttpSession session;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try 
		{
			login(email,password, request, response);
		} 
		catch (Exception e) 
		{
			e.getMessage();
		}
	}
	
	private synchronized void login(String email, String password, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException 
	{	
		if (connection == null || connection.isClosed())
		{
			connection = dataSource.getConnection();
		}
		
		Customer validCustomer = null;

		try 
		{	
			validCustomer = SearchQuery.verifyLoginAccount(email, password, connection);
		} 
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
		}

		if (validCustomer != null)
		{    
			session = request.getSession(true);
			session.setAttribute("customer_loggedin", validCustomer);
			session.setAttribute("connection", connection);
			session.setAttribute("session_cart", new SessionCart());
			request.setAttribute("login_invalid", "");
			response.sendRedirect("FabFlixMain");
		}
		else if (email != null && password != null)
		{
			request.setAttribute("login_invalid", "Invalid login details");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		else
		{
			request.setAttribute("login_invalid", "Not all login details were provided");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
		if (connection != null && !connection.isClosed())
		{
			connection.close();
		}
	}
}

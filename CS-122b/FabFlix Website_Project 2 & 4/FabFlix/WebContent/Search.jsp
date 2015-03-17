<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search</title>

<link href="Style/search.css" rel="stylesheet" />
<link href='http://fonts.googleapis.com/css?family=Raleway:300,400,500' rel='stylesheet' type='text/css'>

</head>

<body>

<%
	if ((Customer) request.getSession().getAttribute("customer_loggedin") == null)
	{
		response.sendRedirect("login.jsp");
	}
%>

<%@ include file="NavBar.jsp"%>


<div id="main_content">

<div style="text-align:center;font-size:40px;">Search Options</div>

<div style="font-size:20px;margin-left: auto;margin-right: auto; width: 20em;">
<form name="Search" action="FabFlixMovieList" method="GET">
Title<br>
<input type="text" name="title" size="50" value=""/><br>
Year<br>
<input type="text" name="year" size="50" value=""/><br>
Director<br>
<input type="text" name="director" size="50" value=""/><br>
Star's Name<br>
<input type="text" name="first_name" size="22" value=""/>
<input type="text" name="last_name" size="22" value=""/><br>
<input type="checkbox" name="substring_match" value="true">Substring Matching<br>
<br>
<input type="hidden" name="from_search" value="true"/>
<input type="submit" name="Search Submit" value="Go" class="button_submit"/>
</form></div>

</div><!-- end div#main_content -->

</body>
</html>
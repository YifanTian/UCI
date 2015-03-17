<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="data_beans.*" %>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FabFlix Checkout</title>

<link href="Style/checkout.css" rel="stylesheet" /> 
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
<div style="text-align:center;font-size:40px;">FabFlix Checkout</div><br>

<div style="font-size:20px; margin-left: auto; margin-right: auto; width: 20em;">
	<form name="Checkout" action="Checkout" method="POST">
		<span id="form1">Credit Card Number<br></span>
	<input type="text" name="cc_id" size="50" id="cc_id" maxlength="20" value="" required/><br>
		<span id="form2">Name on Card<br></span>
	<input type="text" name="first_name" size="22" id="first_name" value="" required/>
	<input type="text" name="last_name" size="22" id="last_name" value="" required/>
		<span id="form3">Expiration Date (YYYY-MM-DD)<br></span>
	<input type="text" name="expiration" size="50" id="expiration" maxlength="10" value="" required/><br><br>
	<input type="submit" name="Search Submit" value="Go" class="button_submit"/>
	</form>
</div>

</div>

</body>
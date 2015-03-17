<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  
  <title>FabFlix Login</title>
  <link href='http://fonts.googleapis.com/css?family=Raleway:300,400,500' rel='stylesheet' type='text/css'>
  <link href="Style/login.css" rel="stylesheet" /> 

</head>
<body>
<!--  The action = is the path ending the name of the servlet -->
<%
	if (request.getSession(false) != null && request.getSession().getAttribute("customer_loggedin") != null)
	{
		response.sendRedirect("FabFlixMain");
	}
%>

<div class="fadein">
  <img src="Images/fibflax_bg1.jpg" />
</div>

<div id="header">
  <!-- so that the opacity does not effect the header content -->
  
  <img src="Images/fabflix_logo.png" id="logo"/>

  <div id="header_content">
  
  <h3>Login: </h3> 
  <form name="loginform" action="Login" method="POST">
  <input name="email" placeholder="Email" required type="email"/>
  <input name="password" type="password" placeholder="Password" required="required"/>
  <input type="submit" value="Login" id="login_button"/>
  </form>
  <h4>${login_invalid}</h4>
    
  </div>
</div>



<div id="headerbg"></div>


<div id="main_content">
  <h2>
  Here at FabFlix we <br/> offer you the most <br/>
  up-to-dated, latest <br/> movies the industry <br/> has to offer. <br/>
   
  </h2>

</div>

</body>
</html>
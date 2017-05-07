<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Riepilogo Credenziali</title>

<link rel="stylesheet" type="text/css" href="stylesheet/style.css">

</head>
<body>
<form>
<p class="check">
Hai inserito le seguenti credenziali:</p>
<br>
<%
    String usr = String.format("Username: %s", request.getAttribute("Username"));
	out.println(usr);
%>

<br>
<%
	String pwd = String.format("Password: %s", request.getAttribute("Password")); 
	out.println(pwd);
%>
<br> 
<%   
	String ml = String.format("Email: %s", request.getAttribute("Email"));
	out.println(ml); 
%>
<br> 
<p class="upload">
Ora sei pronto per entrare nel fantastico mondo di SPSS!</p> 
<a href='upload.html'>Clicca qui senza esitare!</a>


</form>
</body>
</html>
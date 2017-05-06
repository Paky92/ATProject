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
Hai inserito le seguenti credenziali:
<br> 
<%
     out.println((String) request.getAttribute("Username"));
%>
<br> 
<%
	 out.println((String) request.getAttribute("Password"));
%>
<br> 
<%   
	 out.println((String) request.getAttribute("Email")); 
%>
<br> 
<p class="upload">
Ora sei pronto per entrare nel fantastico mondo di SPSS!
</p> <a href='upload.html'>Clicca qui senza esitare!</a>


</form>
</body>
</html>
/* PreparedStatement: protezione contro attacchi SQL Injection.  
 * A tal scopo, si creano oggetti di tipo Statement per l'esecuzione delle query. */

package servlets;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/SigninCookieServlet")
public class SigninCookieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 Cookie cookie = null;
	 Cookie[] cookies = null;	

	public void dispatch(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response, String nextPage)
					throws ServletException, IOException {
		
		String redirect = response.encodeRedirectURL(request.getContextPath() + "/" + nextPage);
		response.sendRedirect(redirect);
			
			}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/plan");
		out.println("WARNING: Questa SigninCookieServlet non supporta il metodo doGet()!"
				+ "\nPrego, provare col metodo doPost() (eseguire la FirstForm).");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("entrato in SigninCookieServlet");
		
        //URL del database locale che memorizza le credenziali inserite nella FirstForm
        String url = "jdbc:mysql://bgianfranco.ddns.net:3132/at";
		//String url = "jdbc:mysql://localhost:3306/at";
          
	try
    {
		// Get an array of Cookies associated with this domain
	    cookies = request.getCookies();
	 
	    if(cookies.length > 1)
	    { 	    
			//Istanza e nuova connessione al database (user="root", password not used)
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//Connection con = DriverManager.getConnection(url, "root", "000000");
			Connection con = DriverManager.getConnection(url, "root_at", "at");
			
			// Controllo che il nickname inserito sia diverso dal username di un account già esistente
			String queryCheck = "SELECT * FROM account WHERE username = ? AND password = ?";
			PreparedStatement st = con.prepareStatement(queryCheck); 
			st.setString(1, cookies[1].getValue());
			st.setString(2, cookies[2].getValue());
	        ResultSet rs = st.executeQuery();
	        
			if (rs.next() == true)
			{
				st.close();	
			
				dispatch(request, response, "upload.html");
					
			}
			else{
				st.close();
				
				dispatch(request, response, "login.html");
				}	
	    }
	    else
	    {
	    	dispatch(request, response, "login.html");
	    }
	
    }
		
	catch (InstantiationException e)
    	{
	    // TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
	catch (IllegalAccessException e)
	    {
	    // TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
	catch (ClassNotFoundException e) 
	    {
	    // TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
    catch (SQLException e) 
	    {
	    // TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
    }
	
}
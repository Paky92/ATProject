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


@WebServlet("/SigninServlet")
public class SigninServlet extends HttpServlet {
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
		out.println("WARNING: Questa SigninServlet non supporta il metodo doGet()!"
				+ "\nPrego, provare col metodo doPost() (eseguire la FirstForm).");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
        //URL del database locale che memorizza le credenziali inserite nella FirstForm
        //String url = "jdbc:mysql://bgianfranco.ddns.net:3132/at";
		String url = "jdbc:mysql://localhost:3306/at";
          
	try
    {
		// Get an array of Cookies associated with this domain
	    cookies = request.getCookies();
	    
		//Istanza e nuova connessione al database (user="root", password not used)
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection con = DriverManager.getConnection(url, "root", "");
		//Connection con = DriverManager.getConnection(url, "root_at", "at");
		
		//Tipo del contenuto della risposta da parte del Server, da inoltrare e far visualizzare sul Browser Client
		response.setContentType("text/plan");
		PrintWriter out = response.getWriter();

		// Controllo che il nickname inserito sia diverso dal username di un account già esistente
		String queryCheck = "SELECT * FROM account WHERE username = ? AND password = ?";
		PreparedStatement st = con.prepareStatement(queryCheck); 
        st.setString(1, request.getParameter("username"));
        st.setString(2, request.getParameter("password"));
        ResultSet rs = st.executeQuery();
        
		if (rs.next() == true)
		{
			if (cookie != null){
				
				if (!request.getParameter("username").equals(cookies[0].getValue())){
					
					cookies[0].setValue(request.getParameter("username"));
					cookies[0].setMaxAge(1000);
				
				}
			}
			else{
				
				Cookie ck=new Cookie("name", rs.getString("username")); 
				ck.setMaxAge(1000);  	//Viene settata a -1 così ogni volta che si riavvia il browser, questo cookie viene eliminato
				response.addCookie(ck);
				
			}		
	
			dispatch(request, response, "upload.html");	
			st.close();
			
		}
	
		else
		{
			out.println("\nERROR: Username o password sbagliati, ricontrollare");
			response.setContentType("text/html");
			out.println("<!DOCTYPE html>");
			out.println("<html>");
				out.println("<head>");
				out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
					out.println("<title>SignupServlet</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<form>"
						+ "<a href='login.html'>Clicca per tornare indietro a Sign In</a> </form>");
				out.println("</body>");
			out.println("</html>");
			
			st.close();
			
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
/* PreparedStatement: protezione contro attacchi SQL Injection.  
 * A tal scopo, si creano oggetti di tipo Statement per l'esecuzione delle query. */

package FirstServlet;

import firstClasses.*;

import java.io.*;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet2")
public class servlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void dispatch(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response, String nextPage)
			throws ServletException, IOException {


			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);

			}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/plan");
		out.println("WARNING: Questa servlet2 non supporta il metodo doGet()!"
				+ "\nPrego, provare col metodo doPost() (eseguire la FirstForm).");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
        //URL del database locale che memorizza le credenziali inserite nella FirstForm
        //String url = "jdbc:mysql://bgianfranco.ddns.net:3132/at";
		String url = "jdbc:mysql://localhost:3306/at";
	try
    {
		//Istanza e nuova connessione al database (user="root", password not used)
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection con = DriverManager.getConnection(url, "root", "");
		
		
		//Tipo del contenuto della risposta da parte del Server, da inoltrare e far visualizzare sul Browser Client
		response.setContentType("text/plan");
		PrintWriter out = response.getWriter();
		out.println("\nServlet2 - Sign In (Login) and dispatching to upload.html");

		// Controllo che il nickname inserito sia diverso dal username di un account già esistente
		String queryCheck = "SELECT * FROM account WHERE username = ? AND password = ?";
		PreparedStatement st = con.prepareStatement(queryCheck); 
        st.setString(1, request.getParameter("username"));
        st.setString(2, request.getParameter("password"));
        ResultSet rs = st.executeQuery();
        
		if (rs.next() == true)
		{
			dispatch(request, response, "upload.html");
			/*inserire LISTA file dell'utente loggato*/
			/*se non sono presenti file, inserire un messaggio di ASSENZA directory e/o file*/
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
					out.println("<title>servlet1</title>");
				out.println("</head>");
				out.println("<body>");
					out.println("<form <button onclick=\"window.history.back()\">Click here to GoBack</button> </form>");
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
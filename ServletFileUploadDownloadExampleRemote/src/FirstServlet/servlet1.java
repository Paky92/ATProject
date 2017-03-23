/* PreparedStatement: protezione contro attacchi SQL Injection.  
 * A tal scopo, si creano oggetti di tipo Statement per l'esecuzione delle query. */

package FirstServlet;

import firstClasses.*;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet1")
public class servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/plan");
		out.println("WARNING: Questa servlet1 non supporta il metodo doGet()!"
				+ "\nPrego, provare col metodo doPost() (eseguire la FirstForm).");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
        //URL del database locale che memorizza le credenziali inserite nella FirstForm
        String url = "jdbc:mysql://bgianfranco.ddns.net:3132/at";
	try
    {
		//Istanza e nuova connessione al database (user="root", password not used)
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection con = DriverManager.getConnection(url, "root_at", "at");
		
		//Tipo del contenuto della risposta da parte del Server, da inoltrare e far visualizzare sul Browser Client
		response.setContentType("text/plan");
		PrintWriter out = response.getWriter();
		out.println("\nAT - Prima Servlet, Prima Form!");

		// Controllo che il nickname inserito sia diverso dal username di un account gi� esistente
		String queryCheck = "SELECT * FROM account WHERE username = ?";
		PreparedStatement st = con.prepareStatement(queryCheck); 
        st.setString(1, request.getParameter("username"));
        ResultSet rs = st.executeQuery();
        
		if (rs.next() == true)
		{
			out.println("\nERROR: ("+ request.getParameter("username")+") coincide con un Account gi� presente nel database!"
					+ "\nPrego, inserirne uno diverso!");
			
			//inoltrare e visualizzare sul Browser Client un pulsante Go Back di ritorno alla form
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
		else
		{
			st.close();
			out.println("Account");
			out.println("UserName: " + request.getParameter("username") + "\n");
			out.println("Password: " + request.getParameter("password") + "\n");
			out.println("Email: " + request.getParameter("email") + "\n");
			
		// Creazione degli oggetti Account e Utente
		account account = new account(request.getParameter("username"), request.getParameter("password"), request.getParameter("email"));
		 
		String queryInsertAccount = "INSERT INTO account (username, password, email) VALUES (? ,? ,?)";
		PreparedStatement IA = con.prepareStatement(queryInsertAccount);
		IA.setString(1, account.leggiUsername());
		IA.setString(2, account.leggiPassword());
		IA.setString(3, account.leggiEmail());
		
		IA.executeUpdate();
		
		IA.close();
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
	
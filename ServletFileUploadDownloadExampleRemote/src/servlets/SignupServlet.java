/* PreparedStatement: protezione contro attacchi SQL Injection.  
 * A tal scopo, si creano oggetti di tipo Statement per l'esecuzione delle query. */

package servlets;

import java.io.*;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.*;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		out.println("WARNING: Questa SignupServlet non supporta il metodo doGet()!"
				+ "\nPrego, provare col metodo doPost() (eseguire la FirstForm).");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
        //URL del database locale che memorizza le credenziali inserite nella form
        String url = "jdbc:mysql://bgianfranco.ddns.net:3132/at";
//		String url = "jdbc:mysql://localhost:3306/at";
		
	try
    {
		//Istanza e nuova connessione al database (user="root", password not used)
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		//Connection con = DriverManager.getConnection(url, "root", "000000");
		Connection con = DriverManager.getConnection(url, "root_at", "at");
		
		//Tipo del contenuto della risposta da parte del Server, da inoltrare e far visualizzare sul Browser Client
		response.setContentType("text/plan");
		PrintWriter out = response.getWriter();
	
		// Controllo che il nickname inserito sia diverso dal username di un account già esistente
		String queryCheck = "SELECT * FROM account WHERE username = ?";
		PreparedStatement st = con.prepareStatement(queryCheck); 
        st.setString(1, request.getParameter("username"));
        ResultSet rs = st.executeQuery();
        
		if (rs.next() == true)
		{
			out.println("\nERROR: ("+ request.getParameter("username")+")"
					+ "coincide con un Account già presente nel database!"
					+ "\nPrego, inserirne uno diverso!");
			
			//inoltrare e visualizzare sul Browser Client un pulsante GoBack di ritorno alla form
			response.setContentType("text/html");
			out.println("<!DOCTYPE html>");
			out.println("<html>");
				out.println("<head>");
				out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
					out.println("<title>Error Page!</title>");
				out.println("</head>");
				out.println("<body>");
					out.println("<form>"
							+ "<a href='signup.html'>Clicca per tornare indietro a Sign Up</a> </form>");
				out.println("</body>");
			out.println("</html>");
			
			st.close();
		}
		else
		{
		// Creazione degli oggetti Account e Utente
		account account = new account(request.getParameter("username"), 
				request.getParameter("password"), request.getParameter("email"));
		 
		String queryInsertAccount = "INSERT INTO account (username, password, email) VALUES (? ,? ,?)";
		PreparedStatement IA = con.prepareStatement(queryInsertAccount);
		IA.setString(1, account.leggiUsername());
		IA.setString(2, account.leggiPassword());
		IA.setString(3, account.leggiEmail());
		
		IA.executeUpdate();
		IA.close();
		st.close();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("riepilogo.jsp");
		request.setAttribute("Username", account.leggiUsername()); // set your String value in the attribute
		request.setAttribute("Password", account.leggiPassword());
		request.setAttribute("Email", account.leggiEmail());

		Cookie ck=new Cookie("name", request.getParameter("username"));
		Cookie ck_2=new Cookie("password", request.getParameter("password"));
		
		ck.setMaxAge(1000);  	// (default) viene settata a -1 così ogni volta che si riavvia il browser, questo cookie viene eliminato
		ck_2.setMaxAge(1000);
		response.addCookie(ck);
		response.addCookie(ck_2);
        dispatcher.forward(request, response);

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
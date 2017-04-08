package FirstServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/validate")
public class validate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
    public validate() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/plan");
		out.println("WARNING: Questa validate_servlet non supporta il metodo doGet()!"
				+ "\nPrego, provare col metodo doPost() (eseguire la FirstForm).");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("POST!");
		
		//URL del database locale che memorizza gli input name inseriti nella Form
		String url = "jdbc:mysql://localhost:3306/atlogindb";
		try
		{
			//Istanza e nuova connessione al database (user="root", password not used)
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection(url, "root", "");
				        
	        //Valore di input inserito dal Client
	        String targetId = request.getParameter("id");
	        if(targetId == null || targetId.equals("")) {
	        	System.out.println("In attesa di un input ...");
	        	response.sendError(HttpServletResponse.SC_NO_CONTENT);
	        	//return;
	        }
	        else
	        	System.out.println("Il client ha inviato: " + targetId);
	 
	        // Controllo se il valore di input inserito esiste già nel database
			String queryCheck = "SELECT * FROM login WHERE username = ?"; 
			PreparedStatement st = con.prepareStatement(queryCheck); 
			st.setString(1, targetId);
			ResultSet rs = st.executeQuery();
			
			//response.setContentType("text/xml");
			//response.setHeader("Cache-Control", "no-cache");
		
		    if (rs.next() == true){
		    	// risposta in XML da parte del Server: valore "valido"
		    	PrintWriter out = response.getWriter();
				out.append("<risposta>valido</risposta>");
		    	st.close();
			} else {
				// risposta XML da parte del Server: valore "invalido"
		    	PrintWriter out = response.getWriter();
				out.append("<risposta>invalido</risposta>");
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
			
		response.setContentType("text/xml"); 
		response.setHeader("Cache-Control", "no-cache");
		 
	}
	
}

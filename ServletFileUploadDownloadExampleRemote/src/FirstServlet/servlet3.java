/* PreparedStatement: protezione contro attacchi SQL Injection.  
 * A tal scopo, si creano oggetti di tipo Statement per l'esecuzione delle query. */

package FirstServlet;

import firstClasses.*;

import java.io.*;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet3")
public class servlet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Cookie cookie = null;
	Cookie[] cookies = null;
	
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
		out.println("WARNING: Questa servlet3 non supporta il metodo doGet()!"
				+ "\nPrego, provare col metodo doPost() (eseguire la FirstForm).");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
		File dir = new File (request.getServletContext().getAttribute("FILES_DIR")+File.separator+ cookies[0].getValue());
		PrintWriter out = response.getWriter();
		File[] directoryListing = dir.listFiles();
		response.setContentType("text/html");
		out.write("<html>");
		  if (directoryListing != null) 
		  {
		    for (File child : directoryListing)
		    {
		    	out.write("<br>");
				out.write("<a href=\"UploadDownloadFileServlet?fileName="+child.getName()+"\">Download "+getFileName(child.getName())+"</a>");
		    }
		  } 
		  else
		  {
		    
		  }
		  out.write("</html>");
}
	
	public String getFileName(String fname)
	{
		  
	      String string_originale = fname.substring(fname.indexOf("=") + 1, fname.length());
	      String stringa_invertita = "";
	      String fileN = "";
	      int i = 0;
	   
	      i = string_originale.length()-1;
	      
	      do{
	    	  stringa_invertita = stringa_invertita + string_originale.charAt(i);
	          i--;
	        }
	      while(!(string_originale.charAt(i) == '\\' || i == 0));
	      	  stringa_invertita = stringa_invertita + string_originale.charAt(i);
	 	  
	      for (int j = stringa_invertita.length() - 1; j >= 0; j--){
	    	  fileN = fileN + stringa_invertita.charAt(j);
	        }
	      
	      return fileN;
	}
	
}


	

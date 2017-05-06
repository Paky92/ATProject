package servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;


@WebServlet("/ListDownloadFileServlet")
public class ListDownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    Cookie cookie = null;
	Cookie[] cookies = null;	
    
	@Override
	public void init() throws ServletException{
		
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

		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache"); 
		PrintWriter out = response.getWriter();
		try {
			
		    cookies = request.getCookies();
		    if (cookies != null) //Se non sono presenti cookie all'interno della request, cookies avrà valore 'null'
		    {			
				File dir =  new File(request.getServletContext().getAttribute("FILES_DIR")+
						File.separator+ cookies[0].getValue());
				out.write("<root>");
				if (dir.exists()){
					File[] directoryListing = dir.listFiles();
							  						
						if (directoryListing != null){

							for (File child :directoryListing){
								System.out.println("<list>"+getFileName(child.getName())+"</list>");
								out.write("<list>"+getFileName(child.getName())+"</list>");
							}
						} 
						else{
						  System.out.println("Non esiste una lista!");
						}			
				}
				else 
				{
					System.out.println("Non esiste una directory utente!");	
				}
				out.write("</root>");
		    }
		    else
		    {
		    	System.out.println("<root><assente>ciao</assente></root>");
		    	out.write("<root><assente>ciao</assente></root>");	//Se non sono presenti cookie, viene creato 
		    												//un oggetto XML vuoto, in modo che il Ajax Engine   									
		    }									//in 'upload.html' possa gestire correttamente la risposta e rimandare 
		    									//alla pagina di login
										
		
		} catch (Exception e) {
			out.write("Exception in uploading file.");
		}

	}
	
	public void dispatch(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response, String nextPage)
					throws ServletException, IOException {
				
		String redirect = response.encodeRedirectURL(request.getContextPath() + "/" + nextPage);
		response.sendRedirect(redirect);
			
			}
	
	public String getFileName(String fname) {
		  
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
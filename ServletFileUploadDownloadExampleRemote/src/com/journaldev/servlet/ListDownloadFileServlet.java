package com.journaldev.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/ListDownloadFileServlet")
public class ListDownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    Cookie cookie = null;
	Cookie[] cookies = null;	
    
	@Override
	public void init() throws ServletException{
		
	}
	//si può anche eliminare la GET per il Download
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/plan");
		out.println("WARNING: Questa servlet1 non supporta il metodo doGet()!"
				+ "\nPrego, provare col metodo doPost() (eseguire la FirstForm).");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache"); 
		PrintWriter out = response.getWriter();
		try {
			
		    // Get an array of Cookies associated with this domain
		    cookies = request.getCookies();
		    if (cookies != null) //Se non sono presenti cookie all'interno della request, cookies avrà valore null
		    {			
				File dir =  new File(request.getServletContext().getAttribute("FILES_DIR")+
						File.separator+ cookies[0].getValue());
				out.write("<root>");
				if (dir.exists()){
					File[] directoryListing = dir.listFiles();
							  						
						if (directoryListing != null){
							System.out.println(cookies[0].getValue());
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
		    												//un oggetto XML vuoto, in modo che il motore AJAX   									
		    }									//in upload.html possa interpretare la risposta e rimandare 
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
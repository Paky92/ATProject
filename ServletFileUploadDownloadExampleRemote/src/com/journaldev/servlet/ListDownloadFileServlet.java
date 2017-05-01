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
    private ServletFileUpload uploader = null;
    
    Cookie cookie = null;
	Cookie[] cookies = null;	
    
	@Override
	public void init() throws ServletException{
		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
		
		fileFactory.setRepository(filesDir);
		this.uploader = new ServletFileUpload(fileFactory);
	}
	//si pu� anche eliminare la GET per il Download
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		String fileName = request.getParameter("fileName");		
		String fileN = getFileName(fileName); 
		cookies = request.getCookies();
		
		if(fileName == null || fileName.equals("")){
			throw new ServletException("File Name can't be null or empty");
		}
		File file = new File(request.getServletContext().getAttribute("FILES_DIR")+
				File.separator +cookies[0].getValue() +File.separator+fileN);
				
		if(!file.exists()){
			throw new ServletException("File doesn't exists on server.");
		}
		
		System.out.println("File location on server: " +file.getAbsolutePath());
		
		ServletContext ctx = getServletContext();
		InputStream fis = new FileInputStream(file);
		String mimeType = ctx.getMimeType(file.getAbsolutePath());
		response.setContentType(mimeType != null? mimeType:"application/octet-stream");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		ServletOutputStream os = response.getOutputStream();
		byte[] bufferData = new byte[1024];
		int read=0;
		while((read = fis.read(bufferData))!= -1){
			os.write(bufferData, 0, read);
		}
		os.flush();
		os.close();
		fis.close();
			
		System.out.println("File downloaded at client successfully.");
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache"); 
		PrintWriter out = response.getWriter();
		try {
			
		    // Get an array of Cookies associated with this domain
		    cookies = request.getCookies();
		    if (cookies != null) //Se non sono presenti cookie all'interno della request, cookies avr� valore null, ecco perch� viene fatto questo controllo qui
		    {			
				File dir =  new File(request.getServletContext().getAttribute("FILES_DIR")+
						File.separator+ cookies[0].getValue());
				
				if (dir.exists()){
					File[] directoryListing = dir.listFiles();
					
					out.write("<root>");				  						
						if (directoryListing != null){
							System.out.println(cookies[0].getValue());
					    for (File child :directoryListing){
					    	System.out.println("<list>"+getFileName(child.getName())+"</list>");
					    	out.write("<list>"+getFileName(child.getName())+"</list>");
							}
					    out.write("</root>");
						} 
						else{
						  System.out.println("Non esiste una lista!");
						}			
				}
				else {
					System.out.println("Non esiste una directory utente!");	
				}
		    }
		    else
		    {
		    	out.write("<root> </root>");	//Se non sono presenti cookie, viene creato un oggetto XML vuoto, in modo che il motore AJAX   									
		    }									//in upload.html possa interpretare la risposta e rimandare alla pagina di login
										
		
		} catch (Exception e) {
			out.write("Exception in uploading file.");
		}

	}
	
	public void dispatch(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response, String nextPage)
			throws ServletException, IOException {

			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
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
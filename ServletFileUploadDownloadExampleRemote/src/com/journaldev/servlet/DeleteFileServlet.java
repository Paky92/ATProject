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

@WebServlet("/DeleteFileServlet")
public class DeleteFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ServletFileUpload uploader = null;
    
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

		System.out.println("Entrato nella servlet");
		//response.setContentType("text/xml");
		//response.setHeader("Cache-Control", "no-cache"); 
		PrintWriter out = response.getWriter();
		try {
			
		    // Get an array of Cookies associated with this domain
		    cookies = request.getCookies();
		    if (cookies != null) //Se non sono presenti cookie all'interno della request, cookies avrà valore null, ecco perchè viene fatto questo controllo qui
		    {			
				File dir =  new File(request.getServletContext().getAttribute("FILES_DIR")+
						File.separator+ cookies[0].getValue());
				//out.write("<root>");
				System.out.println("Entrato nell'if");
					File[] directoryListing = dir.listFiles();
					for (File child :directoryListing)
					{
						System.out.println(child.getName() + " " + request.getParameter("nomefile"));
						if (child.getName().equals(request.getParameter("nomefile")))
						{
							System.out.println("Entrato nel secondo if");
							try
							{
								System.out.println("Stai per cancellare un file");
								child.delete();
								System.out.println("Hai cancellato un file");
							}
							catch (Exception e)
							{
								out.write("Exception in deleting file.");
							}
						}
					}
					//response.sendRedirect("upload.html");
					//request.getRequestDispatcher("/upload.html").forward(request,response);
					dispatch (request, response, "upload.html");
			} 		
			else 
			{
				System.out.println("Non esiste una directory utente!");	
			}
		} catch (Exception e) {
			out.write("Exception in uploading file.");
		}

	}
	
	public void dispatch(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response, String nextPage)
			throws ServletException, IOException {
			System.out.println("Entrato nel dispatcher");
			String redirect = 
				    response.encodeRedirectURL(request.getContextPath() + "/" + nextPage);
				response.sendRedirect(redirect);
//			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
//			dispatch.forward(request, response);
			}
	
}
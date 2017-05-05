package servlets;

import java.io.File;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;


@WebServlet("/DeleteFileServlet")
public class DeleteFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
        
    Cookie cookie = null;
	Cookie[] cookies = null;	
    
	@Override
	public void init() throws ServletException{}
	
	//si può anche eliminare la GET per il Download
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/plan");
		out.println("WARNING: Questa SignupServlet non supporta il metodo doGet()!"
				+ "\nPrego, provare col metodo doPost() (eseguire la FirstForm).");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		System.out.println("Entrato nella servlet");
		response.setContentType("text/plan");

		PrintWriter out = response.getWriter();
		try {
			
		    // Get an array of Cookies associated with this domain
		    cookies = request.getCookies();
		    if (cookies != null) //Se non sono presenti cookie all'interno della request, cookies avrà valore null
		    {			
				File dir =  new File(request.getServletContext().getAttribute("FILES_DIR")+
						File.separator+ cookies[0].getValue());
				
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
			
		String redirect = response.encodeRedirectURL(request.getContextPath() + "/" + nextPage);
		response.sendRedirect(redirect);
			
			}
}
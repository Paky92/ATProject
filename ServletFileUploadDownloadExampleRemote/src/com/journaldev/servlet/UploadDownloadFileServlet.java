package com.journaldev.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

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

@WebServlet("/UploadDownloadFileServlet")
public class UploadDownloadFileServlet extends HttpServlet {
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String fileName = request.getParameter("fileName");		
		String fileN = getFileName(fileName); 
		cookies = request.getCookies();
		
		if(fileName == null || fileName.equals("")){
			throw new ServletException("File Name can't be null or empty");
		}
		File file = new File(request.getServletContext().getAttribute("FILES_DIR")+File.separator+cookies[0].getName()+File.separator+fileN);
		if(!file.exists()){
			throw new ServletException("File doesn't exists on server.");
		}
		
		System.out.println("File location on server: "+file.getAbsolutePath());
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!ServletFileUpload.isMultipartContent(request)){
			throw new ServletException("Content type is not multipart/form-data");
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write("<html><head></head><body>");
		try {
			
		      // Get an array of Cookies associated with this domain
		    cookies = request.getCookies();
			
			List<FileItem> fileItemsList = uploader.parseRequest(request);
			Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
			while(fileItemsIterator.hasNext()){
				String fileName1="";
				FileItem fileItem = fileItemsIterator.next();
				System.out.println("FieldName="+fileItem.getFieldName());
				System.out.println("FileName="+fileItem.getName());
				
				String fileN = fileItem.getName();
				fileName1=getFileName(fileN);
				
				
				System.out.println("ContentType="+fileItem.getContentType());
				System.out.println("Size in bytes="+fileItem.getSize());
				
				File dir = new File (request.getServletContext().getAttribute("FILES_DIR")+File.separator+ cookies[0].getValue());
				File file = new File(request.getServletContext().getAttribute("FILES_DIR")+File.separator+ cookies[0].getValue() + File.separator + fileName1);
				
				if (!dir.exists())
				{
					dir.mkdirs();
				}
				
				System.out.println("Absolute Path at server="+file.getAbsolutePath());
				fileItem.write(file);
				out.write("File "+fileItem.getName()+ " Uploaded successfully.");
				File[] directoryListing = dir.listFiles();
				  if (directoryListing != null) {
				    for (File child : directoryListing) {
				    	out.write("<br>");
						out.write("<a href=\"UploadDownloadFileServlet?fileName="+child.getName()+"\">Download "+getFileName(child.getName())+"</a>");
				    }
				  } else {
				    }
				
				
			
			}
			
		} catch (FileUploadException e) {
			out.write("Exception in uploading file.");
		} catch (Exception e) {
			out.write("Exception in uploading file.");
		}
		out.write("</body></html>");
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
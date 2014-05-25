/*
 * HTTPPOSTServer.java
 * Author: S.Prasanna
 * @version 1.00 
*/

import java.io.*;
import java.net.*;
import java.util.*;

public class HTTPPOSTServer extends Thread {
	
	static final String HTML_START = 
			"<html>" +
			"<title>HTTP POST Server in java</title>" +
			"<body>";
			
    static final String HTML_END = 
			"</body>" +
			"</html>";
			
	Socket connectedClient = null;	
	BufferedReader inFromClient = null;
	DataOutputStream outToClient = null;
	
			
	public HTTPPOSTServer(Socket client) {
		connectedClient = client;
	}			
			
	public void run() {
		
	  String currentLine = null, postBoundary = null, contentength = null, filename = null, contentLength = null;
	  PrintWriter fout = null;
		
	  try {
		
		System.out.println( "The Client "+
        connectedClient.getInetAddress() + ":" + connectedClient.getPort() + " is connected");
            
        inFromClient = new BufferedReader(new InputStreamReader (connectedClient.getInputStream()));                  
        outToClient = new DataOutputStream(connectedClient.getOutputStream());
        
		currentLine = inFromClient.readLine();
        String headerLine = currentLine;            	
        StringTokenizer tokenizer = new StringTokenizer(headerLine);
		String httpMethod = tokenizer.nextToken();
		String httpQueryString = tokenizer.nextToken();
		
		System.out.println(currentLine);
				
        if (httpMethod.equals("GET")) {    
        	System.out.println("GET request");    	
			if (httpQueryString.equals("/")) {
   				  // The default home page
   				  String responseString = HTTPPOSTServer.HTML_START + 
   				  "<form action=\"http://127.0.0.1:5000\" enctype=\"multipart/form-data\"" +
   				  "method=\"post\">" +
   				  "Enter the name of the File <input name=\"file\" type=\"file\"><br>" +
  				  "<input value=\"Upload\" type=\"submit\"></form>" +
  				  "Upload only text files." +
  				  HTTPPOSTServer.HTML_END;
				  sendResponse(200, responseString , false);				  			  
				} else {
                  sendResponse(404, "<b>The Requested resource not found ...." +
				  "Usage: http://127.0.0.1:5000</b>", false);				  
				}
		}
		else { //POST request
		    System.out.println("POST request"); 
			do {
				currentLine = inFromClient.readLine();
											
			    if (currentLine.indexOf("Content-Type: multipart/form-data") != -1) {
				  String boundary = currentLine.split("boundary=")[1];
				  // The POST boundary			  			 
				  
				  while (true) {
				  	currentLine = inFromClient.readLine();
				  	if (currentLine.indexOf("Content-Length:") != -1) {
				  		contentLength = currentLine.split(" ")[1];
				  		System.out.println("Content Length = " + contentLength);
				  		break;
				  	}				  	
				  }
				  
				  //Content length should be < 2MB
				  if (Long.valueOf(contentLength) > 2000000L) {
				  	sendResponse(200, "File size should be < 2MB", false);
				  }
				  
				  while (true) {
				  	currentLine = inFromClient.readLine();
				  	if (currentLine.indexOf("--" + boundary) != -1) {
				  		filename = inFromClient.readLine().split("filename=")[1].replaceAll("\"", ""); 				  			 		
				  		String [] filelist = filename.split("\\" + System.getProperty("file.separator"));
				  		filename = filelist[filelist.length - 1];				  		
				  		System.out.println("File to be uploaded = " + filename);
				  		break;
				  	}				  	
				  }
				  
				  String fileContentType = inFromClient.readLine().split(" ")[1];
				  System.out.println("File content type = " + fileContentType);
				  
				  inFromClient.readLine(); //assert(inFromClient.readLine().equals("")) : "Expected line in POST request is "" ";
				  
				  fout = new PrintWriter(filename);
				  String prevLine = inFromClient.readLine();
				  currentLine = inFromClient.readLine();			  
				  
				  //Here we upload the actual file contents
				  while (true) {
				  	if (currentLine.equals("--" + boundary + "--")) {
				  		fout.print(prevLine);
				  		break;
				  	}
				  	else {
				  		fout.println(prevLine);
				  	}	
				  	prevLine = currentLine;			  		
				  	currentLine = inFromClient.readLine();
			      }
			      
			      sendResponse(200, "File " + filename + " Uploaded..", false);
			      fout.close();				   
				} //if							  				
			}while (inFromClient.ready()); //End of do-while
	  	}//else
	  } catch (Exception e) {
			e.printStackTrace();
	  }	
	}
	
	public void sendResponse (int statusCode, String responseString, boolean isFile) throws Exception {
		
		String statusLine = null;
		String serverdetails = "Server: Java HTTPServer";
		String contentLengthLine = null;
		String fileName = null;		
		String contentTypeLine = "Content-Type: text/html" + "\r\n";
		FileInputStream fin = null;
		
		if (statusCode == 200)
			statusLine = "HTTP/1.1 200 OK" + "\r\n";
		else
			statusLine = "HTTP/1.1 404 Not Found" + "\r\n";	
			
		if (isFile) {
			fileName = responseString;			
			fin = new FileInputStream(fileName);
			contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
			if (!fileName.endsWith(".htm") && !fileName.endsWith(".html"))
				contentTypeLine = "Content-Type: \r\n";	
		}						
		else {
			responseString = HTTPPOSTServer.HTML_START + responseString + HTTPPOSTServer.HTML_END;
			contentLengthLine = "Content-Length: " + responseString.length() + "\r\n";	
		}			
		 
		outToClient.writeBytes(statusLine);
		outToClient.writeBytes(serverdetails);
		outToClient.writeBytes(contentTypeLine);
		outToClient.writeBytes(contentLengthLine);
		outToClient.writeBytes("Connection: close\r\n");
		outToClient.writeBytes("\r\n");		
		
		if (isFile) sendFile(fin, outToClient);
		else outToClient.writeBytes(responseString);
		
		outToClient.close();
	}
	
	public void sendFile (FileInputStream fin, DataOutputStream out) throws Exception {
		byte[] buffer = new byte[1024] ;
		int bytesRead;
	
		while ((bytesRead = fin.read(buffer)) != -1 ) {
		out.write(buffer, 0, bytesRead);
	    }
	    fin.close();
	}
			
	public static void main (String args[]) throws Exception {
		
		ServerSocket Server = new ServerSocket (5000, 10, InetAddress.getByName("10.0.1.15"));         
		System.out.println ("HTTP Server Waiting for client on port 5000");
								
		while(true) {	                	   	      	
				Socket connected = Server.accept();
	            (new HTTPPOSTServer(connected)).start();
        }      
	}
}

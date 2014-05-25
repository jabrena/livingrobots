import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class WebServer {

  protected void start2() {
    ServerSocket s;
    String gets = "";
    System.out.println("Start on port 80");
    try {
      // create the main server socket
      s = new ServerSocket(80);
    } catch (Exception e) {
      System.out.println("Error: " + e);
      return;
    }

    System.out.println("Waiting for connection");
    for (;;) {
      try {
        // wait for a connection
        Socket remote = s.accept();
        // remote is now the connected socket
        System.out.println("Connection, sending data.");
        BufferedReader in = new BufferedReader(new InputStreamReader(
            remote.getInputStream()));
        PrintWriter out = new PrintWriter(remote.getOutputStream());

        String str = ".";

        while (!str.equals("")) {
          str = in.readLine();
          if (str.contains("GET")){
            gets = str;
            break;
          }
        }

        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        out.println("");
        // Send the HTML page
        String method = "get";
        out.print("<html><form method="+method+">");
        out.print("<textarea name=we></textarea></br>");
        out.print("<input type=text name=a><input type=submit></form></html>");
        out.println(gets);
        out.flush();

        remote.close();
      } catch (Exception e) {
        System.out.println("Error: " + e);
      }
    }
  }

  protected void start(){
	  try {
		    //Socket socket = new Socket("127.0.0.1",80);//params[0];
		  
		    Socket socket = null;
		           socket = new Socket("127.0.0.1", 80);

		  
		    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		    
		    // read request
		    String line;
		    line = in.readLine();
		    StringBuilder raw = new StringBuilder();
		    raw.append("" + line);
		    boolean isPost = line.startsWith("POST");
		    int contentLength = 0;
		    while (!(line = in.readLine()).equals("")) {
		        raw.append('\n' + line);
		        if (isPost) {
		            final String contentHeader = "Content-Length: ";
		            if (line.startsWith(contentHeader)) {
		                contentLength = Integer.parseInt(line.substring(contentHeader.length()));
		            }
		        }
		    }
		    StringBuilder body = new StringBuilder();
		    if (isPost) {
		        int c = 0;
		        for (int i = 0; i < contentLength; i++) {
		            c = in.read();
		            body.append((char) c);
		            //Log.d("JCD", "POST: " + ((char) c) + " " + c);
		        }
		    }
		    raw.append(body.toString());
		    //publishProgress(raw.toString());
		    System.out.println(raw.toString());
		    // send response
		    out.write("HTTP/1.1 200 OK\r\n");
		    out.write("Content-Type: text/html\r\n");
		    out.write("\r\n");
		    out.write(new Date().toString());
		    if (isPost) {
		        out.write("<br><u>" + body.toString() + "</u>");
		    } else {
		        out.write("<form method='POST'>");
		        out.write("<input name='name' type='text'/>");
		        out.write("<input type='submit'/>");
		    }
		    out.write("</form>");
		    //
		    // do not in.close();
		    out.flush();
		    out.close();
		    socket.close();
		    //
	    }
	    catch (IOException e) {
	        System.out.println(e);
		} catch (Exception e) {
		    e.printStackTrace();
		    StringWriter sw = new StringWriter();
		    e.printStackTrace(new PrintWriter(sw));
		    //publishProgress('\n' + sw.toString());
		    System.out.println(sw.toString());
		}
  }
  
  public static void main(String args[]) {
    WebServer ws = new WebServer();
    ws.start();
  }
}
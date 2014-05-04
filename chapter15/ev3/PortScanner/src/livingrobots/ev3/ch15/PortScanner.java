package livingrobots.ev3.ch15;

import java.net.*;
import java.io.*;

public class PortScanner{

	private boolean availablePort(String ip, int port){
		
		boolean flag = false;
		
		Socket s = null;
	    try {
	        s = new Socket(ip, port);
	        flag = true;
	    } catch (IOException e) {
	        flag = false;
	    } finally {
	        if( s != null){
	            try {
	                s.close();
	            } catch (IOException e) {
	                throw new RuntimeException("You should handle this error." , e);
	            }
	        }
	    }
	    
	    return flag;
	}
	
	public void scan(String ip, int maxPort){

		System.out.println("Scanning IP: " + ip);
		System.out.println("Ports to scan: 0-" + maxPort);
		
		for (int port = 0; port < maxPort; port++) {
			if(availablePort(ip, port)){
				System.out.println("Port Open: " + port);
			}
		}
		
		System.out.println("");
		System.out.println ("All non Displayed ports are CLOSED");
	}
}


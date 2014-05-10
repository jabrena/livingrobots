package livingrobots.ev3.ch15.syslog.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import livingrobots.ev3.ch15.syslog.Message;
import livingrobots.ev3.ch15.syslog.SyslogParser;

import org.joda.time.Hours;
import org.joda.time.LocalDate;

public class SyslogReader {

	private static final String file = "./lib/messages.txt";
	
	private static InputStream in;
	
	final private static int threshold = -2;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		FileInputStream fis;
		fis = new FileInputStream(file);
		in = fis;
	
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		String line = "";
		
		SyslogParser sp = new SyslogParser();
		
		List<Message> messages = new ArrayList();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
		Calendar calNow = Calendar.getInstance();
    	System.out.println(sdf.format(calNow.getTime()));
		
		
		//Read the file
		try {
			while (br.ready()) {
			    line = br.readLine();
			    
			    //System.out.println(line);
			    Message msg = sp.parse(line);
			    Calendar calMsg = msg.getCal();
			    
			    LocalDate d1 = new LocalDate(calNow.getTimeInMillis());
			    LocalDate d2 = new LocalDate(calMsg.getTimeInMillis());
			    int hours = Hours.hoursBetween(d1,d2).getHours();
			    
			    if(hours >= threshold){   
			    	messages.add(msg);
			    }
			}
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Process messages
		if(messages.size() > 0){
			for (Message msg : messages) {
				if(msg.getProcess().indexOf("authpriv") > -1){
					System.out.println(sdf.format(msg.getCal().getTime()) + " " + msg.getProcess() + " " + msg.getMessage());
				}
			}
		}else{
			System.out.println("No risk");
		}

		try {Thread.sleep(5000);} catch (InterruptedException e) {}
		System.exit(0);
	}
	

}

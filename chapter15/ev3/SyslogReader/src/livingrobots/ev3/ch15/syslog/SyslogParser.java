package livingrobots.ev3.ch15.syslog;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


public class SyslogParser {

	String syslogMessage = "";
	
	public SyslogParser(){
		
	}
	
	public Message parse(String message){
		this.syslogMessage = message;
		
		char[] ca;
		
		int part = 0;
		
		//PRIORITY
		/*
		if (c == '<') {
			priority = readInt();
		
			expect('>');
		}
		*/
		
		//Part 1
		Calendar cal;
		cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.getDefault());
		
		String date;
		StringBuffer part1;
		char separatorHour = ':';
		int separatorCounter = 0;
		boolean separatorFlag = false;
		int dateStringLength = 0;

		//Part 2
		char separator2 = ' ';
		StringBuffer part2;
		
		//Part 3
		StringBuffer part3;
		
		//Part 4
		StringBuffer part4;
		
		ca = message.toCharArray();
	    
	    part = 0;
	    part1 = new StringBuffer();
	    part2 = new StringBuffer();
	    part3 = new StringBuffer();
	    part4 = new StringBuffer();
	    
	    separatorCounter = 0;
	    separatorFlag = false;
		
	    for(int i=0;i<ca.length;i++){
	    	
	    	//System.out.println("Data at ["+i+"]="+ca[i]);
	    	
	        //Part 0: Detect date;
	        if(part == 0){
	        	part1.append(ca[i]);
	        	if(ca[i] == separatorHour){
	        		separatorCounter++;
	        	}
	        	if((separatorCounter == 2) && (!separatorFlag)){
	        		dateStringLength = i+2;
	        		separatorFlag = true;
	        	}
	        	
	        	if((i == dateStringLength) && (separatorFlag)){
	        		
		        	cal = getDate(part1.toString());
		        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
		        	//System.out.println(sdf.format(cal.getTime()));
	        		
		        	separatorCounter = 0;
	        		part = 1;
	        	}
	        
	        //Part 1:
	        }else if(part == 1){

	        	if(ca[i] == separator2){
	        		separatorCounter++;
	        	}
	        	
	        	if(separatorCounter == 2){
	        		//System.out.println(part2.toString().trim());
	        		
	        		separatorCounter = 0;
	        		part = 2;
	        	}else{
	        		part2.append(ca[i]);
	        	}
	        	
	        //Part 2
	        }else if(part == 2){

	        	if(ca[i] == separator2){
	        		separatorCounter++;
	        	}
	        	
	        	if(separatorCounter == 1){
	        		//System.out.println(part3.toString().trim());
	        		
	        		separatorCounter = 0;
	        		part = 3;
	        	}else{
	        		part3.append(ca[i]);
	        	}
		    	 	
	        }else if(part == 3){
		    	part4.append(ca[i]);
	        }
	    }
	    
	    //System.out.println(part4.toString().trim());
	    
	    return new Message(null,cal,part2.toString(),part3.toString(),part4.toString());

	}
	
	private static Calendar getDate(String dateSring){

		String[] splitedDate = dateSring.split("\\s+");
		
		Calendar cal = null;
		cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.getDefault());
		
		int m = readMonthAbbreviation(splitedDate[0]);
		int d = Integer.valueOf(splitedDate[1]);
		String[] hour = splitedDate[2].split(":");
		int hh = Integer.valueOf(hour[0]);
		int mm = Integer.valueOf(hour[1]);
		int ss = Integer.valueOf(hour[2]);
		
		cal.set(Calendar.MONTH, m);
		cal.set(Calendar.DAY_OF_MONTH, d);
		cal.set(Calendar.HOUR_OF_DAY, hh);
		cal.set(Calendar.MINUTE, mm);
		cal.set(Calendar.SECOND, ss);
		
		return cal;
	}
	
	/**
	 * Read a month value as an English abbreviation.
	 *
	 * @see RFC 3164, Sec. 4.1.2.
	 */
	private static int readMonthAbbreviation(String month) {

		String monthAnalysis = month.toUpperCase();
		//System.out.println(monthAnalysis);
		
		if(monthAnalysis.length() == 3){
			switch (monthAnalysis) {
			case "JAN":
				return Calendar.JANUARY;
			case "FEB":
				return Calendar.FEBRUARY;
			case "MAR":
				return Calendar.MARCH;
			case "APR":
				return Calendar.APRIL;
			case "MAY":
				return Calendar.MAY;
			case "JUN":
				return Calendar.JUNE;
			case "JUL":
				return Calendar.JULY;
			case "AUG":
				return Calendar.AUGUST;
			case "SEP":
				return Calendar.SEPTEMBER;
			case "OCT":
				return Calendar.OCTOBER;
			case "NOV":
				return Calendar.NOVEMBER;
			case "DEC":
				return Calendar.DECEMBER;
			default:
				return -1;
			}
		}else{
			return -1;
		}
		
	}
}

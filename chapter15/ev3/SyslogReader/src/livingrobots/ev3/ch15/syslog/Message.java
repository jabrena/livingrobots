package livingrobots.ev3.ch15.syslog;
import java.util.Calendar;


public class Message {

	private Priority pri;
	private Calendar cal;
	private String hostName;
	private String process;
	private String message;
	
	public Message(Priority pri, Calendar cal,String hostName, String process, String message){
		this.pri = pri;
		this.cal = cal;
		this.hostName = hostName;
		this.process = process;
		this.message = message;
	}
	
	public Calendar getCal(){
		return cal;
	}
	
	public String getProcess(){
		return process;
	}
	
	public String getMessage(){
		return message;
	}
}

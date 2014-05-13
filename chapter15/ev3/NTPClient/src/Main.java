import java.io.IOException;


public class Main {

	public static void main(String[] args) throws IOException {

		String Server = "1.uk.pool.ntp.org"; //"ptbtime1.ptb.de";
		
		String dt = SntpClient.getDate(Server);
		System.out.println("Date and time is " + dt);
		Runtime.getRuntime().exec("date -s " + dt);

	}

}

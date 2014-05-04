package livingrobots.ev3.ch15;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class USBPortScannerTest {

	public static void main (String args[]) throws UnknownHostException{
		
		byte[] ipAddr = new byte[]{10, 0, 1, 1};
		InetAddress ip = InetAddress.getByAddress(ipAddr);
		final int maxPort = 65536;

		PortScanner ps = new PortScanner();	
		ps.scan(ip.getCanonicalHostName(), maxPort);
		
	}

}

package livingrobots.ev3.ch15;

import java.io.IOException;

import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;

public class EV3PortScanner {

	public static void main(String[] args) throws IOException {

		//Get EV3 bricks connected in the same network
		BrickInfo[] bi;
		BrickFinder bf = new BrickFinder();
		bi = bf.discover();
		
		final int maxPort = 65536;
		
		PortScanner ps = new PortScanner();
		
		for (BrickInfo brick : bi) {
		    System.out.println("Scanning EV3:" + brick.getName());
		    
			ps.scan(brick.getIPAddress(), maxPort);
		}
	}

}

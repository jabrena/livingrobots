
import lejos.hardware.port.I2CPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.I2CSensor;
import lejos.hardware.sensor.SensorConstants;

public class Main {

	static int I2CSlaveAddress = 4; // 7bit addressing.  It would be 0xA4 in 8bit
	static final byte REG_ARDUINO = (byte) 0x20;
	static byte[] buffReadResponse = new byte[8];
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Arduino Connection Test");

		for(int i = 0; i<= 127; i++){
			
		}
		
		I2CSensor Arduino = new I2CSensor(SensorPort.S1,
				I2CSlaveAddress, 
				SensorConstants.TYPE_LOWSPEED);
				//SensorConstants.TYPE_LOWSPEED_9V);
				//SensorConstants.TYPE_HISPEED);

		
		Thread.sleep(500);

		//Writing
		//Arduino.sendData(REG_ARDUINO, buffReadResponse,1);
		
		byte value = 1;
		Arduino.sendData(REG_ARDUINO, value);
		
		System.out.println(buffReadResponse[0]);
		System.out.println(buffReadResponse[1]);
		System.out.println(buffReadResponse[2]);
		System.out.println(buffReadResponse[3]);
		System.out.println(buffReadResponse[4]);
		System.out.println(buffReadResponse[5]);
		System.out.println(buffReadResponse[6]);
		System.out.println(buffReadResponse[7]);
		
		//Reading
		Arduino.getData(REG_ARDUINO, buffReadResponse,1);

		System.out.println(buffReadResponse[0]);
		System.out.println(buffReadResponse[1]);
		System.out.println(buffReadResponse[2]);
		System.out.println(buffReadResponse[3]);
		System.out.println(buffReadResponse[4]);
		System.out.println(buffReadResponse[5]);
		System.out.println(buffReadResponse[6]);
		System.out.println(buffReadResponse[7]);
		
		System.exit(1);
	}
	


}

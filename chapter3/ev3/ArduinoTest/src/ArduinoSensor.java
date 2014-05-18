import lejos.hardware.port.I2CPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.I2CSensor;


public class ArduinoSensor extends I2CSensor{

	static final byte REG_ARDUINO = (byte) 0x20;
	static byte[] buffReadResponse = new byte[8];
	
	public ArduinoSensor(Port arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Arduino Connection Test 2");
		
		// TODO Auto-generated method stub
		ArduinoSensor as = new ArduinoSensor(SensorPort.S1, 127);
		
		Thread.sleep(500);
		
		byte value = 1;
		as.sendData(REG_ARDUINO, value);
		
		//Reading
		as.getData(REG_ARDUINO, buffReadResponse,1);
	}

}

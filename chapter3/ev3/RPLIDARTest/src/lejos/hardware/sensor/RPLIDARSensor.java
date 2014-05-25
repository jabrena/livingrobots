package lejos.hardware.sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.I2CSensor;


public class RPLIDARSensor extends I2CSensor implements SensorMode {

	private final String sensorName = "RPLidar";
	
	//I2C
	private final static int I2CAddress = 8;
	private byte[] buffReadResponse = new byte[30];
	private byte[] buffReadResponse2 = new byte[30];
	
	//Registers
	private final int[ ] RPILidarRegisterByte1 = { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x10, 0x11, 0x12 };
	private final int[ ] RPILidarRegisterByte2 = { 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x30, 0x31, 0x32 };
	private final int[ ] RPILidarAngleBlocks = { 0, 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330 };

	private final int angles = 360;
	float[] distances = new float[angles];
	
	public RPLIDARSensor(Port arg0) {
		super(arg0, I2CAddress);
        init();
    }
    
    protected void init() {
    	setModes(new SensorMode[]{ this });
    }
    
	public SensorMode getLIDARMode(){
	    return this;
	}


	public void fetchSample(float[] sample, int offset) {
		for(int j= 0; j < RPILidarRegisterByte1.length; j++){
			this.getData(RPILidarRegisterByte1[j], buffReadResponse, buffReadResponse.length);
			this.getData(RPILidarRegisterByte2[j], buffReadResponse2, buffReadResponse2.length);
          
			updateDistances(RPILidarAngleBlocks[j]);
			
			sample = distances;
		}   
	}
	
	private void updateDistances(int from){
		for(int i = 0; i < buffReadResponse.length; i++){
			int value = (buffReadResponse[i] & 0xff);
			int value2 = (buffReadResponse2[i] & 0xff);
			distances[from + i] = value + value2;
		}
	}

	public int sampleSize() {
		return angles;
	}

	public String getName() {
		return sensorName;
	}

}

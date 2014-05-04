package livingrobots.ev3.ch3;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import livingrobots.ev3.utils.SensorPortSelector;

public class EV3GyroSensorTest {

	private final static int SWITCH_DELAY = 100;
	private static EV3GyroSensor gyro;
	private final static int threshold = 300;
   	 	

	public static void main(String[] args) throws InterruptedException {
		
        Port p = SensorPortSelector.getPort();
		
   	 	gyro = new EV3GyroSensor(p);
   	 	
		SampleProvider rateMode = gyro.getRateMode();
        float[] samples = new float[rateMode.sampleSize()];
		
		LCD.drawString("" + rateMode.sampleSize(), 0,0);
		while (!Button.ESCAPE.isDown()) {
			rateMode.fetchSample(samples, 0);
			
			for(int i = 0; i < rateMode.sampleSize(); i++){
                LCD.drawString("Val[" + i + "]: " + samples[i], 3, i);
                if(samples[i] > threshold){
                	Sound.buzz();
                }
            }
			Thread.sleep(SWITCH_DELAY);
		}
	}

}

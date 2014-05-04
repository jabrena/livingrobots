package livingrobots.ev3.ch3;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;
import livingrobots.ev3.utils.SensorPortSelector;

public class EV3IRSensorTest {

	private final static int SWITCH_DELAY = 100;
	private static EV3IRSensor ir;

	public static void main(String[] args) throws InterruptedException {
		
        Port p = SensorPortSelector.getPort();
		
		ir = new EV3IRSensor(p);
		SensorMode seekMode = ir.getSeekMode();
        float[] samples = new float[seekMode.sampleSize()];
		
		LCD.drawString("" + seekMode.sampleSize(), 0,0);
		while (!Button.ESCAPE.isDown()) {
			seekMode.fetchSample(samples, 0);
			
			for(int i = 0; i < seekMode.sampleSize(); i++){
                LCD.drawString("Val[" + i + "]: " + samples[i], 3, i);
            }
			Thread.sleep(SWITCH_DELAY);
		}
	}

}

import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.RPLIDARSensor;
import lejos.robotics.SampleProvider;


public class RPLIDARTest {

	public static void main(String[] args) {
		System.out.println("RPLIDAR Sensor Test");
		
		RPLIDARSensor lidar = new RPLIDARSensor(SensorPort.S4);
		
		SampleProvider lidarMode = lidar.getLIDARMode();
        float[] samples = new float[lidarMode.sampleSize()];
        
		boolean flag = true;
		while (flag) {
			
			long startTime = System.currentTimeMillis();
			
			lidarMode.fetchSample(samples, 0);
			
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			System.out.println("Time: " + elapsedTime + " Msecs.");
		}
        
	}

}

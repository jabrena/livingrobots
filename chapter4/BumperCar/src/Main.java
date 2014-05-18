
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;

public class Main {

    private static int steps = 1000;
	
	private final static int SWITCH_DELAY = 200;
	
	private static EV3IRSensor leftIR;
	private static EV3IRSensor rightIR;
    private static float leftDistance = 0;
    private static float rightDistance = 0;
    private static float THRESHOLD = 40;
    
	private static RegulatedMotor leftMotor = Motor.A;
    private static RegulatedMotor rightMotor = Motor.B;
    private static int FORWARD_TIME = 500;
	
	public static void main(String[] args) throws InterruptedException {
		
		Compositions.play(1);
		
		leftMotor.resetTachoCount();
	    rightMotor.resetTachoCount();
	    leftMotor.rotateTo(0);
	    rightMotor.rotateTo(0);
	    leftMotor.setSpeed(400);
	    rightMotor.setSpeed(400);
	    leftMotor.setAcceleration(800);
	    rightMotor.setAcceleration(800);
		
	    leftIR = new EV3IRSensor(SensorPort.S1);
		SensorMode distanceModeLeft = leftIR.getDistanceMode();
	    rightIR = new EV3IRSensor(SensorPort.S1);
		SensorMode distanceModeRight = rightIR.getDistanceMode();
        float[] samples = new float[distanceModeLeft.sampleSize()];

        
        for(int j=0; j<= steps; j++){
			distanceModeLeft.fetchSample(samples, 0);
	        leftDistance = samples[0];
			distanceModeRight.fetchSample(samples, 0);
	        leftDistance = samples[0];
	        System.out.println("S: " + j + "L: " + leftDistance + "R:" + rightDistance);

	        if(leftDistance <= THRESHOLD){
	        	leftMotor.setSpeed(200);
	            rightMotor.setSpeed(200);
	            leftMotor.forward();
	            rightMotor.backward();
	            Thread.sleep(FORWARD_TIME);
	            leftMotor.setSpeed(200);
	            rightMotor.setSpeed(200);
	            leftMotor.stop(true);
	            rightMotor.stop(true);
	        }else{
	        	leftMotor.setSpeed(200);
	            rightMotor.setSpeed(200);
	            leftMotor.forward();
	            rightMotor.forward();
	            Thread.sleep(FORWARD_TIME);
	        }

			Thread.sleep(SWITCH_DELAY);
		}
        
        System.exit(1);
	}

}

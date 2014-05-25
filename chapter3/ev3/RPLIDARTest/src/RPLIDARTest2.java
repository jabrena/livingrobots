import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.RPLIDARSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.DifferentialPilot;


public class RPLIDARTest2 {

	static double wheelDiameter=7.8;
	static double robotTrack=11.3;
	
	public static void main(String[] args) throws IOException {

		System.out.println("RPLIDAR Sensor Test");
		
		Brick brick=BrickFinder.getDefault();
		
		RegulatedMotor leftMotor=new EV3LargeRegulatedMotor(brick.getPort("B"));
		RegulatedMotor rightMotor=new EV3LargeRegulatedMotor(brick.getPort("A"));
		
		leftMotor.setAcceleration(200);
		rightMotor.setAcceleration(200);

		leftMotor.setSpeed(400);
		rightMotor.setSpeed(400);
		
		DifferentialPilot pilot = new DifferentialPilot(wheelDiameter, robotTrack, leftMotor, rightMotor);
		
		RPLIDARSensor lidar = new RPLIDARSensor(SensorPort.S4);
		
		SampleProvider lidarMode = lidar.getLIDARMode();
        float[] distances = new float[lidarMode.sampleSize()];
        
        int angles[] = {0,30,60,90,120,150,180,210,240,270,300,330};
        float maxDistance = 0;
        float currentDistance = 0;
        int maxAngle = 0;
        int maxAngleIndex = 0;
        float angleToTurn = 0;
        
		boolean flag = true;
		while (flag) {
			
	        maxDistance = 0;
	        maxAngle = 0;
	        maxAngleIndex = 0;

			//TODO: Discover why samples object doesn't update
			//lidar.fetchSample(samples, 0);
			distances = lidar.fetchSample2(distances, 0);

			//Determinate maxAngle
			for(int i = 0; i< angles.length; i++){
				currentDistance = distances[angles[i]];
				System.out.println(i + " " + angles[i] + ": " + currentDistance);
				
				if( currentDistance > maxDistance){
					maxDistance = currentDistance;
					maxAngleIndex = i;
					maxAngle = angles[i];
				}
			}

			System.out.println("Max Distance: " + maxDistance);
			System.out.println("Max i: " + maxAngleIndex);
			System.out.println("Max Angle: " + maxAngle);
			
			//TODO: Improve the movements in case of angles > 180 degrees
			if(maxAngle > 180){
				angleToTurn = maxAngle - 360;
			}else{
				angleToTurn = maxAngle;
			}
			
			System.out.println("Angle to turn: " + angleToTurn);
			
			//Move the robot
			pilot.rotate(angleToTurn);
			
			//TODO: If the distance is large, increase the distance to move.
			pilot.travel(20);
			pilot.stop();
			
			//flag = false;
			
			generateJSON(distances);
			generateJSArray(distances);
			
			//Debugging purpose
			//try {Thread.sleep(5000);} catch (InterruptedException e) {}

		}
		
		System.out.println("END");
		lidar.close();
		
		System.exit(0);
        
	}
	
	private static float median(float param1, float param2){
		float median = 0;
		median = (param1 + param2) / 2;
		
		return median;
	}
	
    private static void generateJSArray(float[] samples) throws IOException{
    	
    	StringBuffer sb = new StringBuffer();

    	String path = "/home/lejos/www/ogm/js/distances.js";
    	
    	sb.append("var distances = Array( \r\n");
    	
    	for(int i=0;i<samples.length;i++){
    		if(i == samples.length -1){
    	   		sb.append("[" + (i+1) + "," + samples[i] + "] \r\n");
    		}else{
    			sb.append("[" + (i+1) + "," + samples[i] + "], \r\n");
    		}
    	}
    	
    	sb.append(" ); \r\n");
    	
    	BufferedWriter writer = null;
    	try{
    	    writer = new BufferedWriter( new FileWriter(path));
    	    writer.write( sb.toString());

    	}catch ( IOException e){
    	}finally{
    	    try{
    	        if ( writer != null)
    	        writer.close( );
    	    }catch ( IOException e){
    	    }
    	}
    	
    	System.out.println("OK");
    	
    }
	
    private static void generateJSON(float[] samples) throws IOException{
    	
    	StringBuffer sb = new StringBuffer();

    	String path = "/home/lejos/www/Radar/amcharts/polar.json";
    	
    	sb.append("var chart = AmCharts.makeChart(\"chartdiv\", { \r\n");
    	sb.append("    \"type\": \"radar\", \r\n");
    	sb.append("    \"theme\": \"none\", \r\n");
    	sb.append("    \"dataProvider\": [ \r\n");
    	
    	for(int i=0;i<samples.length;i++){
    		if(i == samples.length -1){
    	   		sb.append("{\"direction\": \"" + (i+1) + "\",\"value\": " + samples[i] + "} \r\n");
    		}else{
    	   		sb.append("{\"direction\": \"" + (i+1) + "\",\"value\": " + samples[i] + "}, \r\n");
    		}
    	}
    	
    	sb.append(" ], \r\n");
    	
    	StringBuffer sb2 = includeOptions();
    	sb.append(sb2);
    	
    	BufferedWriter writer = null;
    	try{
    	    writer = new BufferedWriter( new FileWriter(path));
    	    writer.write( sb.toString());

    	}catch ( IOException e){
    	}finally{
    	    try{
    	        if ( writer != null)
    	        writer.close( );
    	    }catch ( IOException e){
    	    }
    	}
    	
    	System.out.println("OK");
    	
    }
    
    private static StringBuffer includeOptions() throws IOException{
    	
    	StringBuffer sb = new StringBuffer();
    	
    	BufferedReader br = new BufferedReader(new FileReader("./lib/amcharts.config.txt"));
        try {

            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            //String everything = sb.toString();
        } finally {
            br.close();
        }
    	
    	return sb;
    }

}

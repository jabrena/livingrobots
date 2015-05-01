import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Power;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.HiTechnicCompass;
import lejos.hardware.sensor.RPLIDARSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import mapping.OccupancyGridMap2;
import mapping.OccupancyMap;


public class RPLIDARTest3 {

	static double wheelDiameter=7.8;
	static double robotTrack=11.3;
	static int granularity = 20;
	static Pose currentPose;
	
	public static void main(String[] args) throws IOException {

		System.out.println("RPLIDAR Sensor Test");
		
		Brick brick=BrickFinder.getDefault();
		Power power = brick.getPower();
		
		RegulatedMotor leftMotor=new EV3LargeRegulatedMotor(brick.getPort("B"));
		RegulatedMotor rightMotor=new EV3LargeRegulatedMotor(brick.getPort("A"));
		
		leftMotor.setAcceleration(200);
		rightMotor.setAcceleration(200);

		leftMotor.setSpeed(400);
		rightMotor.setSpeed(400);
		
		DifferentialPilot pilot = new DifferentialPilot(wheelDiameter, robotTrack, leftMotor, rightMotor);
		OdometryPoseProvider opp = new OdometryPoseProvider(pilot);
		
		//Compass
		HiTechnicCompass compass = new HiTechnicCompass(brick.getPort("S1"));
		SensorMode compassMode = compass.getCompassMode();
        float[] samples = new float[compassMode.sampleSize()];

        compassMode.fetchSample(samples, 0);
        System.out.println("Compass: " + samples[0]);
        
        float angleTo = samples[0] +90;
        
    	compass.startCalibration();
    	pilot.rotate(angleTo);
    	pilot.rotate(-angleTo);
    	compass.stopCalibration();
    	Sound.beep();
    	
        compassMode.fetchSample(samples, 0);
        System.out.println("Compass: " + samples[0]);
		
		OccupancyGridMap2 ogm = new OccupancyGridMap2(3000,3000,granularity);
		
        
		Pose originalPose = new Pose(1500,1500,samples[0]);
		opp.setPose(originalPose);
		Navigator nav = new Navigator(pilot,opp);
		
		RPLIDARSensor lidar = new RPLIDARSensor(SensorPort.S4);
		
		SampleProvider lidarMode = lidar.getLIDARMode();
        float[] distances = new float[lidarMode.sampleSize()];
        
        int angles[] = {0,30,60,90,120,150,180,210,240,270,300,330};
        float maxDistance = 0;
        float currentDistance = 0;
        int maxAngle = 0;
        int maxAngleIndex = 0;
        float angleToTurn = 0;
        
        int counter = 0;
		boolean flag = true;
		

		while (flag) {
			
	        maxDistance = 0;
	        maxAngle = 0;
	        maxAngleIndex = 0;

			lidarMode.fetchSample(distances, 0);


			
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
			
			System.out.println("Battery: " + power.getVoltageMilliVolt());
			System.out.println("Compass: " + nav.getPoseProvider().getPose());

			Pose p = opp.getPose();
			currentPose = p;
			
			System.out.println("Pose: " + p.toString());
			
			int[][] map;

	    	for(int i=0;i<distances.length;i++){
	    		
	    		ogm.updateValue(p, i, (int) distances[i]);

	    	}
	    	
	    	map = ogm.getMap();

			generateJSON_POLAR(distances);
	    	generateJSON_OGM(map);
	    	
	    	/*
	    	Pose newPose = new Pose();
	    	newPose.getX();
	    	
	    	Waypoint w = new Waypoint(newPose);
	    	nav.addWaypoint(w);
	    	nav.goTo(w);
	    	*/
	    	//pilot.travel(20);
	    	

	    	
	    	counter++;
	    	if(counter > 50){
	    		flag = false;
	    	}
		}
		
		nav.stop();
		
		//System.out.println("Final Pose: " + nav.getPoseProvider().getPose().toString());
		
		System.out.println("END");
		lidar.close();
		
		Sound.buzz();
		
		System.exit(0);
        
	}
	
	private static void generateJSON_OGM(int[][] mapM){
		JSONArray jsonArray = new JSONArray();
		
		for(int i=0;i< mapM.length; i++){
			JSONObject col = new JSONObject();

			try {
				
				String row = "";
				for(int j = 0; j< mapM.length; j++){
					row += mapM[i][j];
					if(j < mapM.length - 1){
						row+= ",";
					}
				}
				
				col.put("row", row);

			} catch (JSONException e) {
			    e.printStackTrace();
			}
			
			jsonArray.put(col);
			
		}

		JSONObject json = new JSONObject();
		
		JSONArray pose = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("x", currentPose.getX());
		obj.put("y", currentPose.getY());
		obj.put("theta", currentPose.getHeading());
		pose.put(obj);
		json.put("pose", pose);
		json.put("granularity", granularity);
		json.put("mapM", jsonArray);
		
		//System.out.println(json.toString());
		
		store(json.toString(),"/home/lejos/www/ogm/js/map.json");
		
		System.out.println("OK");
	}
	
	private static void store(final String content, String path){
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream(path));
		    out.print(content);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		finally {
		    if (out != null) out.close();
		}
	}
	
    private static void generateJSON_POLAR(float[] samples) throws IOException{
    	
    	StringBuffer sb = new StringBuffer();

    	String path = "/home/lejos/www/Radar/amcharts/polar.json";
    	
    	sb.append("var chart = AmCharts.makeChart(\"chartdiv\", { \r\n");
    	sb.append("    \"type\": \"radar\", \r\n");
    	sb.append("    \"theme\": \"none\", \r\n");
    	sb.append("    \"dataProvider\": [ \r\n");
    	
    	/*
    	for(int i=0;i<samples.length;i++){
    		if(i == samples.length -1){
    	   		sb.append("{\"direction\": \"" + (i+1) + "\",\"value\": " + samples[i] + "} \r\n");
    		}else{
    	   		sb.append("{\"direction\": \"" + (i+1) + "\",\"value\": " + samples[i] + "}, \r\n");
    		}
    	}
    	*/
    	
    	for(int i=0;i<samples.length;i++){
    		
			int heading = (int) currentPose.getHeading() + i;
			
			if(heading > 360){
				heading = (int) currentPose.getHeading() + i - 360;
			}
    		
    		if(i == samples.length -1){
    			
    	   		sb.append("{\"direction\": \"" + heading + "\",\"value\": " + samples[i] + "} \r\n");
    			
    		}else{
    	   		sb.append("{\"direction\": \"" + heading + "\",\"value\": " + samples[i] + "}, \r\n");
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
    
	private static float median(float param1, float param2){
		float median = 0;
		median = (param1 + param2) / 2;
		
		return median;
	}

}

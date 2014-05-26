import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.RPLIDARSensor;
import lejos.robotics.SampleProvider;


public class RPLIDARTest {

	public static void main(String[] args) throws IOException {
		System.out.println("RPLIDAR Sensor Test");
		
		RPLIDARSensor lidar = new RPLIDARSensor(SensorPort.S4);
		
		SampleProvider lidarMode = lidar.getLIDARMode();
        float[] samples = new float[lidarMode.sampleSize()];
        
		boolean flag = true;
		while (flag) {
			
			long startTime = System.currentTimeMillis();

			lidarMode.fetchSample(samples, 0);

			generateJSON(samples);
			
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			System.out.println("Time: " + elapsedTime + " Msecs.");
		}
        
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


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.I2CSensor;

public class Main7 {
   static int I2CSlaveAddress = 8;
   static byte[] buffReadResponse = new byte[30];
   static byte[] buffReadResponse2 = new byte[30];
   static int[ ] RPILidarRegisterByte1 = { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x10, 0x11, 0x12 };
   static int[ ] RPILidarRegisterByte2 = { 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x30, 0x31, 0x32 };
   static int[ ] RPILidarAngleBlocks = { 0, 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330 };

   static final int DELAY = 50;
   
   static int[] distances = new int[360];

   
   public static void main(String[] args) throws IOException {    
      System.out.println("Arduino Connection Test 7");
      I2CSensor arduino = new I2CSensor(SensorPort.S4, I2CSlaveAddress);
      
      int i = 1;
      while(i < 2){
    	  
    	  long startTime = System.currentTimeMillis();
    	  
    	  //Update
    	  for(int j= 0; j < RPILidarRegisterByte1.length; j++){
              arduino.getData(RPILidarRegisterByte1[j], buffReadResponse, buffReadResponse.length);
              arduino.getData(RPILidarRegisterByte2[j], buffReadResponse2, buffReadResponse2.length);
              
              updateDistances(RPILidarAngleBlocks[j]);
              //try {Thread.sleep(DELAY);} catch (InterruptedException e) {}
    	  }         


    	  saveMap();
    	  
          long stopTime = System.currentTimeMillis();
          long elapsedTime = stopTime - startTime;
          System.out.println("Time: " + elapsedTime + " Msecs.");
    	  

      }
      
      System.out.println("END");

      arduino.close();
   }
   
   private static void updateDistances(int from){
       for(int i = 0; i < buffReadResponse.length; i++){
     	  
     	  int value = (buffReadResponse[i] & 0xff);
     	  int value2 = (buffReadResponse2[i] & 0xff);
     	  distances[from + i] = value + value2;
     	  
     	  //System.out.print(from + i + " " + new String(String.valueOf(value2)) + " ");
       }
       //System.out.println(" ");
   }
  
    static void saveMap(){
    	StringBuffer sb = new StringBuffer();
    		
    	sb.append("{\"success\": true,\"scan\": [ \r\n");
    	
    	for(int i=1;i<360;i++){
    		sb.append("{\"ardtime\":\"134\",\"pan\":\"" + i + "\",\"radius\":\"" + distances[i] + "\",\"heading\":\"19.5\"}, \r\n");
    	}
    	
		sb.append("{\"ardtime\":\"134\",\"pan\":\"" + 360 + "\",\"radius\":\"" + 0 + "\",\"heading\":\"19.5\"} \r\n ] }");

    	String path = "/home/lejos/www/json2.htm";
    	
    	BufferedWriter writer = null;
    	try{
    	    writer = new BufferedWriter( new FileWriter( path));
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

}
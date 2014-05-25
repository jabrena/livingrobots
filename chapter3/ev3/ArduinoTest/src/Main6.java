
import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.I2CSensor;

public class Main6 {
   static int I2CSlaveAddress = 8;
   static byte[] buffReadResponse = new byte[30];
   static int[ ] RPILidarRegister = { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x10, 0x11, 0x12 };
   static int[ ] RPILidarAngleBlocks = { 0, 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330 };

   static final int DELAY = 50;
   
   static int[] distances = new int[360];
   
   public static void main(String[] args) {    
      System.out.println("Arduino Connection Test5");
      I2CSensor arduino = new I2CSensor(SensorPort.S4, I2CSlaveAddress);
      
      int i = 1;
      while(i < 2){
    	  
    	  //Update
    	  for(int j= 0; j < RPILidarRegister.length; j++){
              arduino.getData(RPILidarRegister[j], buffReadResponse, buffReadResponse.length);
              updateDistances(RPILidarAngleBlocks[j]);
              try {Thread.sleep(DELAY);} catch (InterruptedException e) {}
    	  }         

    	  //Determinate
          for(int k = 0; k < 30; k++){
         	  
         	  if(distances[k] <= 50){
         		  Sound.beep();
         	  }
          }

          
          
          /*
          arduino.getData(0x02, buffReadResponse, buffReadResponse.length);
          showValues(30);
          Thread.sleep(DELAY);
          arduino.getData(0x03, buffReadResponse, buffReadResponse.length);
          showValues(60);
          Thread.sleep(DELAY);
          arduino.getData(0x04, buffReadResponse, buffReadResponse.length);
          showValues(90);
          Thread.sleep(DELAY);
          arduino.getData(0x05, buffReadResponse, buffReadResponse.length);
          showValues(120);
          Thread.sleep(DELAY);
          arduino.getData(0x06, buffReadResponse, buffReadResponse.length);
          showValues(150);
          Thread.sleep(DELAY);
          arduino.getData(0x07, buffReadResponse, buffReadResponse.length);
          showValues(180);
          Thread.sleep(DELAY);
          arduino.getData(0x08, buffReadResponse, buffReadResponse.length);
          showValues(210);
          Thread.sleep(DELAY);
          arduino.getData(0x09, buffReadResponse, buffReadResponse.length);
          showValues(240);
          Thread.sleep(DELAY);
          arduino.getData(0x10, buffReadResponse, buffReadResponse.length);
          showValues(270);
          Thread.sleep(DELAY);
          arduino.getData(0x11, buffReadResponse, buffReadResponse.length);
          showValues(300);
          Thread.sleep(DELAY);
          arduino.getData(0x12, buffReadResponse, buffReadResponse.length);
          showValues(330);
          Thread.sleep(DELAY);
          */
      }
      
      System.out.println("END");

      arduino.close();
   }
   
   private static void showValues(int from){
       for(int i = 0; i < buffReadResponse.length; i++){
     	  
     	  int value = (buffReadResponse[i] & 0xff);
     	  System.out.print(from + i + " " + new String(String.valueOf(value)) + " ");
       }
       System.out.println(" ");
   }
   
   private static void updateDistances(int from){
       for(int i = 0; i < buffReadResponse.length; i++){
     	  
     	  int value = (buffReadResponse[i] & 0xff);
     	  distances[from + i] = value;
     	  //System.out.print(from + i + " " + new String(String.valueOf(value)) + " ");
       }
       //System.out.println(" ");
   }
}
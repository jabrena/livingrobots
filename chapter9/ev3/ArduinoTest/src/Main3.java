
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.I2CSensor;

public class Main3 {
   static int I2CSlaveAddress = 8;
   static byte[] buffReadResponse = new byte[30];
   static byte[] buffReadResponse2 = new byte[360];
   
   
   public static void main(String[] args) throws InterruptedException {    
      System.out.println("Arduino Connection Test2");
      I2CSensor arduino = new I2CSensor(SensorPort.S4, I2CSlaveAddress);
      
      int j = 1;
      
      while(j < 10){
          arduino.getData(0x01, buffReadResponse, buffReadResponse.length);
          for(int i = 0; i < 30; i++){
        	  
        	  int value = (buffReadResponse[i] & 0xff);
        	  System.out.print(i + " " + new String(String.valueOf(value)) + " ");
          }
          System.out.println(" ");
          Thread.sleep(300);
      }
      


      arduino.close();
   }
}
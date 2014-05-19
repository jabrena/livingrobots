
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.I2CSensor;

public class Main2 {
   static int I2CSlaveAddress = 8;
   static byte[] buffReadResponse = new byte[8];
   static byte[] buffReadResponse2 = new byte[2];
   
   
   public static void main(String[] args) throws InterruptedException {    
      System.out.println("Arduino Connection Test2");
      I2CSensor arduino = new I2CSensor(SensorPort.S4, I2CSlaveAddress);
      
      for(int i = 1; i <=255; i++){
    	  arduino.getData(i, buffReadResponse2, buffReadResponse2.length);
    	  int value = (buffReadResponse2[0] & 0xff);
    	  System.out.println(i + " " + new String(String.valueOf(value)));
      }

      arduino.close();
   }
}
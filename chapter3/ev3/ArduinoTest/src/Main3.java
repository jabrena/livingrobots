
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.I2CSensor;

public class Main3 {
   static int I2CSlaveAddress = 8;
   static byte[] buffReadResponse = new byte[8];
   static byte[] buffReadResponse2 = new byte[360];
   
   
   public static void main(String[] args) throws InterruptedException {    
      System.out.println("Arduino Connection Test2");
      I2CSensor arduino = new I2CSensor(SensorPort.S4, I2CSlaveAddress);
      
      int j = 1;
      
      while(j < 10){
          arduino.getData(0x01, buffReadResponse2, buffReadResponse2.length);
          for(int i = 1; i <360; i++){
        	  
        	  int value = (buffReadResponse2[i] & 0xff);
        	  System.out.print(i + " " + new String(String.valueOf(value)));
          }
          System.out.println("");
      }
      


      arduino.close();
   }
}
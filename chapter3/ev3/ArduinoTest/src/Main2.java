import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.I2CSensor;

public class Main2 {
   static int I2CSlaveAddress = 8;
   static byte[] buffReadResponse = new byte[8];
   
   public static void main(String[] args) throws InterruptedException {    
      System.out.println("Arduino Connection Test2");
      I2CSensor arduino = new I2CSensor(SensorPort.S1, I2CSlaveAddress);
      
      arduino.getData('A', buffReadResponse, buffReadResponse.length);
      System.out.println(new String(buffReadResponse));
      
      /*
      while (Button.ESCAPE.isUp()) {
         int id = Button.waitForAnyPress();  
         if (id == Button.ID_ENTER) {  
            arduino.getData('A', buffReadResponse, buffReadResponse.length);
            System.out.println(new String(buffReadResponse));
         }
      }
      */
      arduino.close();
   }
}
package livingrobots.ev3.ch3.Magic8Ball;

import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;

public class EV3Magic8Ball {

	private static EV3GyroSensor gyro;
	private final static int threshold = 300;
	private final static int SWITCH_DELAY = 5000;
	
	private static void voidDetectGyro(){
		
   	 	gyro = new EV3GyroSensor(SensorPort.S1);
   	 	
		SampleProvider rateMode = gyro.getRateMode();
        float[] samples = new float[rateMode.sampleSize()];

		boolean flag = true;
		while (flag) {
			rateMode.fetchSample(samples, 0);
			
			for(int i = 0; i < rateMode.sampleSize(); i++){
                if(samples[i] > threshold){
                	flag = false;
                	Sound.buzz();
                	break;
                }
            }
		}
	}
	
	private static void IFeelLucky(){
        // create 20 sided die and roll it
        Die icosahedron = new Die(20);
        icosahedron.roll();
        
        // display message corresponding to die's top value
        switch (icosahedron.getTop()) {
            case  1: System.out.println("Outlook Good");                break;
            case  2: System.out.println("Outlook Not So Good");         break;
            case  3: System.out.println("My Reply Is No");              break;
            case  4: System.out.println("Don't Count On It");           break;
            case  5: System.out.println("You May Rely On It");          break;
            case  6: System.out.println("Ask Again Later");             break;
            case  7: System.out.println("Most Likely");                 break;
            case  8: System.out.println("Cannot Predict Now");          break;
            case  9: System.out.println("Yes");                         break;
            case 10: System.out.println("Yes Definately");              break;
            case 11: System.out.println("Better Not Tell You Now");     break;
            case 12: System.out.println("It Is Certain");               break;
            case 13: System.out.println("Very Doubtful");               break;
            case 14: System.out.println("It Is Decidedly So");          break;
            case 15: System.out.println("Concentrate and Ask Again");   break;
            case 16: System.out.println("Signs Point to Yes");          break;
            case 17: System.out.println("My Sources Say No");           break;
            case 18: System.out.println("Without a Doubt");             break;
            case 19: System.out.println("Reply Hazy, Try Again");       break;
            case 20: System.out.println("As I See It, Yes");            break;
            default: System.out.println("Psychic temporarily out of service.");
        }
	}
	
    public static void main (String[] args) throws InterruptedException {
        
        System.out.println("Ask your question now.");
        
        voidDetectGyro();
        IFeelLucky();
        
        Thread.sleep(SWITCH_DELAY);
        
        System.exit(0);
    }
    
}
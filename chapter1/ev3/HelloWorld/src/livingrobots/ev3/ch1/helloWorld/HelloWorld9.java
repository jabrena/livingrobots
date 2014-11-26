package livingrobots.ev3.ch1.helloWorld;

import lejos.hardware.Battery;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class HelloWorld9 {

	private static RegulatedMotor[] ref = new RegulatedMotor[3];
	private static int MID = 50;
	private static int FRONT = 1;
	private static int BACK = 2;
	private static int LEFT = 3;
	private static int RIGHT = 4;
	private static int REAR_LEG = 0;
	private static int LEFT_LEG = 1;
	private static int RIGHT_LEG = 2;
	private static int CW = 1;
	private static int CCW = 0;
	
	private static void Pulse(int motor, int times, boolean long_pulse){
		
		for(int i=0; i<times; i++){
			//ref[motor] = MID+10;
			ref[motor].rotate(MID + 10);
			Wait(80);
			//Wait(70+long_pulse*30);
			//ref[motor] = MID-10;
			ref[motor].rotate(MID - 10);
			Wait(80);
		}
	}
	
	private static void Wait(int time){
		try {Thread.sleep(time);} catch (InterruptedException e) {}
	}
	
	void PulseDouble(byte motor_one, byte motor_two, int times, boolean long_pulse)
	{
		for(int i=0; i<times; i++){
			//ref[motor_one] = 80;
			//ref[motor_two] = 80;
			ref[1].rotate(80);
			ref[2].rotate(80);
			Wait(50+Integer.parseInt(long_pulse +"")*40);
			//ref[motor_one] = 40;
			//ref[motor_two] = 40;
			ref[1].rotate(40);
			ref[2].rotate(40);
		}
	}
	
	private static void Set(int angle1,int angle2, int angle3){
		ref[0].rotate(angle1);
		ref[1].rotate(angle2);
		ref[2].rotate(angle3);
	}
	
	private static void Walk (int dir, int times){
		for(int i=0; i<times; i++){
	      if (dir == FRONT){
	         /*
	    	 Set(MID-20,MID,MID);
	         Wait(300);
	         Set(MID+10,MID,MID);
	         Wait(200);
	         */
	  		rearLeg.rotate(-20);
			leftLeg.rotate(0);
			rightLeg.rotate(0);
			Delay.msDelay(300);
				//System.out.println(rearLeg.getTachoCount());
			rearLeg.rotate(+20);
			leftLeg.rotate(0);
			rightLeg.rotate(0);
			Delay.msDelay(200);
	      }else if (dir == BACK){
	         Set(MID-10,MID+20,MID+20);
	         Wait(150);
	         Set(MID-10,MID-10,MID-10);
	         Wait(300);
	      }else if (dir == RIGHT){
	         Set(MID-10,MID-20,MID-10);
	         Wait(80);
	         Set(MID+10,MID-20,MID+10);
	         Wait(150);
	         Set(MID-10,MID-20,MID-10);
	         Wait(300);
	      }else if (dir == LEFT){
	         Set(MID-10,MID-10,MID-20);
	         Wait(80);
	         Set(MID+10,MID+10,MID-20);
	         Wait(150);
	         Set(MID-10,MID-10,MID-20);
	         Wait(300);
	      }
	   }
	}
	
	private static void Turn(int wise, int times)
	{
		for(int i=0; i<times; i++){
	      ref[REAR_LEG].rotate(MID-10);
	      ref[wise==CCW? RIGHT_LEG:LEFT_LEG].rotate(MID+10);
	      Wait(100);
	      ref[wise==CCW? LEFT_LEG:RIGHT_LEG].rotate(MID+10);
	      ref[wise==CCW? RIGHT_LEG:LEFT_LEG].rotate(MID-10);
	      Wait(100);
	      ref[wise==CCW? LEFT_LEG:RIGHT_LEG].rotate(MID-10);
	      ref[REAR_LEG].rotate(MID+10);
	      Wait(100);
	      
//	      Pulse( wise==CCW? REAR_LEG:LEFT_LEG, 1, false);
//	      Pulse(RIGHT_LEG,1,false);
//	      Pulse( wise==CCW? LEFT_LEG:REAR_LEG,1,false);
	   }
	}
	
	private static RegulatedMotor rearLeg;
	private static RegulatedMotor leftLeg;
	private static RegulatedMotor rightLeg;
	
	private static void init(){
		//Init
		
		rearLeg.rotate(MID);
		leftLeg.rotate(MID);
		rightLeg.rotate(MID);
		
		Sound.beep();
		Delay.msDelay(1000);
		
		/*
		for(int i=0; i<3; i++){
			int j = 20;
			
			rearLeg.rotate(j,false);
			leftLeg.rotate(j,false);
			rightLeg.rotate(j,false);
		}
		*/
		
		/*
		rearLeg.rotate(20);
		leftLeg.rotate(20);
		rightLeg.rotate(20);
		
		rearLeg.rotate(20);
		leftLeg.rotate(20);
		rightLeg.rotate(20);
		
		rearLeg.rotate(20);
		leftLeg.rotate(20);
		rightLeg.rotate(20);
		*/
	}
	

   public static void waitWhileMoving() {
	      while (true) {
	         if (rearLeg.isStalled()) {
	            //Logger.error("Stalled !");
        	 rearLeg.stop();
            break;
         }
         if (!rearLeg.isMoving()) {
            //Logger.info("Not moving");
            break;
         }
      
         Thread.yield();
      }
   }

	public static void main(String[] args) {

		rearLeg = new EV3LargeRegulatedMotor(MotorPort.A);
		leftLeg = new EV3LargeRegulatedMotor(MotorPort.B);
		rightLeg = new EV3LargeRegulatedMotor(MotorPort.C);
		rearLeg.resetTachoCount();
		leftLeg.resetTachoCount();
		rightLeg.resetTachoCount();
		/*
		rearLeg.setStallThreshold(10, 100);
		leftLeg.setStallThreshold(10, 100);
		rightLeg.setStallThreshold(10, 100);
		*/
		int speed = 900;
		int acceleration = 90000;
		
		rearLeg.setSpeed(speed);
		leftLeg.setSpeed(speed);
		rightLeg.setSpeed(speed);

		rearLeg.setAcceleration(acceleration);
		leftLeg.setAcceleration(acceleration);
		rightLeg.setAcceleration(acceleration);

		
		 
		//MID = 60; 
		
		ref[0] = rearLeg;
		ref[1] = leftLeg;
		ref[2] = rightLeg;
		
		init();
		
		//Delay.msDelay(2000);
		
		//rearLeg.rotate(MID);
		leftLeg.rotate(0,true);
		rightLeg.rotate(0,true);
		
		for(int i=0; i<=40; i++){
			//System.out.println(rearLeg.getTachoCount());
			rearLeg.rotate(-20,true);
			leftLeg.rotate(-10,true);
			rightLeg.rotate(-10,true);
			Delay.msDelay(200);
			//System.out.println(rearLeg.getTachoCount());
			rearLeg.rotate(+20,true);
			leftLeg.rotate(+10,true);
			rightLeg.rotate(+10,true);
			//System.out.println(rearLeg.getTachoCount());
			Delay.msDelay(100);
			
			if (i>1)  Delay.msDelay(80);
		}
		

		
		/*
		for(int i=0; i<=10; i++){
			rearLeg.rotate(-(MID-20),false);
	        //Set(MID-20,MID,MID);
	        Wait(50);
	        rearLeg.rotate(MID+10,false);
	        //Set(MID+10,MID,MID);
	        Wait(100);
		}
		*/

		//Walk(FRONT,10);
		
		/*
		Walk(FRONT,10);
		Turn(CCW,10);
		Walk(FRONT,10);
		Turn(CW,10);
		*/
		/*
		Walk(FRONT,10);
		Walk(LEFT,10);
		Walk(RIGHT,10);
		*/
		
		/*
		for(int i=0; i<6; i++){
			int j = -10;
			
			rearLeg.rotate(j);
			leftLeg.rotate(j);
			rightLeg.rotate(j);
		}
		*/
		
		//rearLeg.rotate(-MID-30,true);
		//waitWhileMoving();
		Sound.beep();
		System.out.println(Battery.getVoltage() );
	}

}

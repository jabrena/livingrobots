package livingrobots.ev3.utils;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.utility.TextMenu;

public class SensorPortSelector {

    static private final String ports[] = {"Port 1", "Port 2", "Port 3", "Port 4"};
    
	static public Port getPort(){
		
		TextMenu portMenu = new TextMenu(ports, 1, "Sensor port");

        int portNo = portMenu.select();
        //if (portNo < 0) return;
        LCD.clear();
        Port p = LocalEV3.get().getPort("S"+(portNo+1));
        
        return p;
	}
	
}

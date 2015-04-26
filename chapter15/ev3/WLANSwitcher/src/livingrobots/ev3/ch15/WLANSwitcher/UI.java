package livingrobots.ev3.ch15.WLANSwitcher;

import java.io.IOException;
import java.util.ArrayList;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.TextLCD;
import lejos.utility.TextMenu;

public class UI {

	private TextLCD screen;
	
	private final String path = "/home/root/lejos/bin/utils/";
	private final String pattern = "OPT";
	private final String MESSAGE = "WLAN Switcher is network utility " + 
									"to switch a WLAN network using a EV3 GUI " +  
									"if you have some wpa_supplicant.conf files in " +
									path;
	
	public UI(){
		screen = LocalEV3.get().getTextLCD();
	}
	
	public void showWelcome(){
		//GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
		//g.setContrast(0);
        //g.setFont(Font.getSmallFont());
		
		//System.out.println(MESSAGE);
		screen.drawString(MESSAGE,0,0);
	}
	
	public String showStep1(ArrayList<String> output) throws IOException, InterruptedException{

    	//Options to show
    	String[] options;
    	int optionInMenuSelected = 0;
    	String selectedOption = "";
    	
    	int i = 0;
    	options = new String[output.size()];
    	for(String line: output){
    		if(line.contains(pattern)){
        		options[i] = line.replaceAll(path, "");
        		i++;        			
    		}
		}
		
    	TextMenu textMenu = new TextMenu(options);
    	textMenu.setTitle("WLAN Switcher");
    	optionInMenuSelected = textMenu.select();
    	textMenu.quit();
    	
    	if(optionInMenuSelected == -1){
    		selectedOption = "99";
    	}else{
        	selectedOption = options[optionInMenuSelected];
    	}
    	
    	return selectedOption;
	}
	
	public void clear(){
    	
		for(int row=0; row<=8; row++){
	    	screen.clear(row);
		}
	}
	

}

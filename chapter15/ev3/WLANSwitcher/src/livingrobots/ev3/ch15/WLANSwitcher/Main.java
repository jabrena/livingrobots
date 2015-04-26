package livingrobots.ev3.ch15.WLANSwitcher;

import java.util.ArrayList;

import lejos.hardware.Button;
import lejos.utility.Delay;

/**
 * A Java method to execute and wait for a shell command to complete
 */
public class Main{
	
	private final static String ERROR_CODE = "99";
	
    public static void main(String[] args) throws Exception {
    	
    	UI ui = new UI();
    	Shell shell = new Shell();
    	ArrayList<String> rawShellOutput;

    	ui.showWelcome();
    	Button.waitForAnyPress();
    	ui.clear();
    	
    	rawShellOutput = shell.execute(ShellCommands.CMD1, false);
    	if(rawShellOutput.size() > 0){
    		
    		//STEP1
    		//Select a wpa_supplicant.conf file
        	String file = ui.showStep1(rawShellOutput);
        	
        	if(file.equals(ERROR_CODE)){
        		System.exit(0);
        	}else{
            	ui.clear();
            	
            	//STEP2 Create a Backup
            	shell.execute(ShellCommands.CMD2, false);
            	//STEP3 Switch wpa_supplicant.conf
            	shell.execute(ShellCommands.CMD3.replace("{PARAM}", file),false);
            	//STE4 Reboot network module
            	shell.execute(ShellCommands.CMD4, true);
            	
    			ArrayList<String> ips = NetworkUtils.getIPAddresses();
                String lastIp = null;
                for (String ip: ips) {
                	lastIp = ip;
                }
                System.out.println(lastIp);
        	}
        	

    	}

    	
    	Delay.msDelay(10000);
    	

    }



}

package livingrobots.ev3.ch15.WLANSwitcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Shell {
	
	public ArrayList<String> execute(final String shellCommand, final boolean printShellOutput) throws IOException, InterruptedException {
		ArrayList<String> output = new ArrayList();
		
		Runtime runtime = Runtime.getRuntime() ;
		Process shellProcess = runtime.exec(shellCommand) ;

		//Only wait for if you need the external app to complete
		shellProcess.waitFor() ;
        
		//You can read the contents of the application's information it is writing to the console
		BufferedReader shellCommandReader = new BufferedReader( new InputStreamReader(shellProcess.getInputStream() ) ) ;

		String currentLine = null;
		while ( (currentLine = shellCommandReader.readLine() ) != null ){
			output.add(currentLine);
			if (printShellOutput){
                System.out.println(currentLine);
			}
		};
		
		return output;
	}
	
}

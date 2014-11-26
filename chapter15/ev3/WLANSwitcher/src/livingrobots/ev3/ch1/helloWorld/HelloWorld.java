package livingrobots.ev3.ch1.helloWorld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A Java method to execute and wait for a shell command to complete
 */
public class HelloWorld
{
    public static void main(String[] args) throws Exception {
    	runShellCommand("date .", true);//'/home/root/lejos/bin/utils'
        runShellCommand("ls -la", true);
    }

    /**
     * The OPEN command on MacOSX causes the registered file handler to open a file.
     * The START command on Windows causes the registered file handler to open a file.
     *
     * Example param can include a path and file such as:
     * "open yourpath/somefile.txt"
     */
	public static void runShellCommand(String shellCommand, boolean printShellOutput) throws IOException, InterruptedException {
		Runtime runtime = Runtime.getRuntime() ;
		Process shellProcess = runtime.exec(shellCommand) ;

		//Only wait for if you need the external app to complete
		shellProcess.waitFor() ;
        
		//You can read the contents of the application's information it is writing to the console
		BufferedReader shellCommandReader = new BufferedReader( new InputStreamReader(shellProcess.getInputStream() ) ) ;

		String currentLine = null;
		while ( (currentLine = shellCommandReader.readLine() ) != null ){
			if (printShellOutput){
                System.out.println(currentLine);
			}
		}
	}
}

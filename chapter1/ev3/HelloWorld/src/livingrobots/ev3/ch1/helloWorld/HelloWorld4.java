package livingrobots.ev3.ch1.helloWorld;

/**
 * HelloWorld is an example designed to understand the environment.
 * This class show in console the string: "Hello World"
 * 
 * @author jabrena
 *
 */
public class HelloWorld4 {

	/**
	 * Internal variable with the message
	 */
	private static final String MESSAGE = "Hello World";
	
	/**
	 * Constructor
	 */
	public HelloWorld4(){
		
	}
	
	/**
	 * Internal method designed to show in console a String.
	 * 
	 */
	public void showMessage(){
		System.out.println(MESSAGE);
	}
	
	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		HelloWorld4 hw = new HelloWorld4();
		hw.showMessage();
	}

}

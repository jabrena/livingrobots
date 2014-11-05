package livingrobots.ev3.ch8.helloWorld;

/**
 * HelloWorld is an example designed to understand the environment.
 * This class show in console the string: "Hello World"
 * 
 * @author jabrena
 *
 */
public class HelloWorld7 implements IHelloWorld{

	/**
	 * Internal variable with the message
	 */
	private static final String MESSAGE = "Hello World";
	
	/**
	 * Constructor
	 */
	public HelloWorld7(){
		
	}
	
	/**
	 * Internal method designed to show in console a String.
	 * 
	 */
	public void showMessage(){
		System.out.println(MESSAGE);
	}
	
	/**
	 * 
	 * @param message
	 */
	public void showMessage(String message){
		System.out.println(message);
	}
	
	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		HelloWorld7 hw = new HelloWorld7();
		
		
		if(args.length > 0) {
			hw.showMessage(args[0]);
		} else {
			hw.showMessage("HelloWorld"); 
		}
		
	}

}

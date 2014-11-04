package livingrobots.ev3.ch8.helloWorld;

/**
 * HelloWorld is an example designed to understand the environment.
 * This class show in console the string: "Hello World"
 * 
 * @author jabrena
 *
 */
public class HelloWorld5 implements IHelloWorld{

	/**
	 * Internal variable with the message
	 */
	private static final String MESSAGE = "Hello World";
	
	/**
	 * Constructor
	 */
	public HelloWorld5(){
		
	}
	
	/**
	 * Internal method designed to show in console a String.
	 * 
	 */
	public void showMessage(){
		System.out.println(MESSAGE);
	}
	
	@Override
	public void showMessage(String message) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		HelloWorld5 hw = new HelloWorld5();
		hw.showMessage();
	}

}

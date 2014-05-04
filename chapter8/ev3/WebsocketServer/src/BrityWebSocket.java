
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;

import org.java_websocket.WebSocket;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

/**
 * A simple WebSocketServer Test
 */
public class BrityWebSocket extends WebSocketServer {

	//private BumpercarRobot robot;
	private enum CMD{
		UP, DOWN, LEFT, RIGHT
	}
	
	public BrityWebSocket( int port ) throws UnknownHostException {
		super( new InetSocketAddress( port ) );
	}

	/*
	public BrityWebSocket( int port , BumpercarRobot robot) throws UnknownHostException {
		super( new InetSocketAddress( port ) );
		this.robot = robot;
	}
	*/
	
	public BrityWebSocket( InetSocketAddress address ) {
		super( address );
	}

	@Override
	public void onOpen( WebSocket conn, ClientHandshake handshake ) {
		this.sendToAll( "new connection: " + handshake.getResourceDescriptor() );
		System.out.println( conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected with EV3!" );
	}

	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
		this.sendToAll( conn + " EV3 Connection closed" );
		System.out.println( conn + " EV3 Connection closed" );
	}

	@Override
	public void onMessage( WebSocket conn, String message ) {

		if(message.equals(CMD.UP.toString())){
			//robot.forward(1);
		}else if(message.equals(CMD.DOWN.toString())){
			//robot.backward(1);
		}else if(message.equals(CMD.LEFT.toString())){
			//robot.turnLeft(1);
		}else if(message.equals(CMD.RIGHT.toString())){
			//robot.turnRight(1);
		}
		
		this.sendToAll( message );
		System.out.println( conn + ": " + message );
	}

	@Override
	public void onFragment( WebSocket conn, Framedata fragment ) {
		System.out.println( "received fragment: " + fragment );
	}

	@Override
	public void onError( WebSocket conn, Exception ex ) {
		ex.printStackTrace();
		if( conn != null ) {
			// some errors like port binding failed may not be assignable to a specific websocket
		}
	}

	/**
	 * Sends <var>text</var> to all currently connected WebSocket clients.
	 * 
	 * @param text
	 *            The String to send across the network.
	 * @throws InterruptedException
	 *             When socket related I/O errors occur.
	 */
	public void sendToAll( String text ) {
		Collection<WebSocket> con = connections();
		synchronized ( con ) {
			for( WebSocket c : con ) {
				c.send( text );
			}
		}
	}
}

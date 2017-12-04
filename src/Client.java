import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Rudimentary Client class (See Absolute Java p1140)
 */
public class Client {

	private String hostName;
	private int port;

	Client(String hostName, int port){
		this.hostName = hostName;
		this.port = port;

		initialise();
	}



	/**
	 * Initialise the client instance.
	 */
	private void initialise(){
		try{
			System.out.println("Connecting to server on port: "+port);

			//Creates a stream socket and connects it to the specified port number on the named host.
			Socket connectionSock = new Socket(hostName, port);

			BufferedReader serverInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());

			System.out.println("Connection established, sending test string.");

			serverOutput.writeBytes("Test");
			System.out.println("Waiting on reply.");

			String serverResponse = serverInput.readLine();
			System.out.println("Received from server: "+serverResponse);

			serverInput.close();
			serverOutput.close();
			connectionSock.close();

		}catch(IOException e){
			System.err.println(e.getMessage());
		}

	}

	public static void main(String[] args) {
		System.out.println("Running standalone Client on port 8976 at localhost");

		try {
			System.out.println("Connecting to server on port: " + 8976);
			//Creates a stream socket and connects it to the specified port number on the named host.
			Socket connectionSock = new Socket("localhost", 8976);

			BufferedReader serverInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());

			System.out.println("Connection established, sending test string.");
			//Must include eof character to terminate message.
			serverOutput.writeBytes("Test\n");

			System.out.println("Waiting on reply.");
			String serverReponse = serverInput.readLine();
			System.out.println("Received from server: " + serverReponse);

			serverInput.close();
			serverOutput.close();
			connectionSock.close();

		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}
}
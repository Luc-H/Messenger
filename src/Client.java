import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * Rudimentary Client class (See Absolute Java p1140)
 */
public class Client {

	private String hostName;
	private int port;

	Client(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;

		initialise();
	}


	/**
	 * Initialise the client instance.
	 */
	private void initialise() {
		try {
			System.out.println("Connecting to server on port: " + port);

			//Creates a stream socket and connects it to the specified port number on the named host.
			Socket connectionSock = new Socket(hostName, port);

			BufferedReader serverInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());

			System.out.println("Connection established, sending test string.");

			serverOutput.writeBytes("Test");
			System.out.println("Waiting on reply.");

			String serverResponse = serverInput.readLine();
			System.out.println("Received from server: " + serverResponse);

			serverInput.close();
			serverOutput.close();
			connectionSock.close();

		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

	public static void main(String[] args) {
		int port = 8976;
		String hostName;

		Scanner inKb = new Scanner(new InputStreamReader(System.in));
		String line = inKb.nextLine();

		BufferedReader serverInput;
		DataOutputStream serverOutput = null;
		Socket connectionSock;

		try {
			System.out.println("Connecting to server on port: " + port + ", enter host address:");
			hostName = inKb.nextLine();

			//Creates a stream socket and connects it to the specified port number on the named host.
			connectionSock = new Socket(hostName, port);

			serverInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			serverOutput = new DataOutputStream(connectionSock.getOutputStream());

			while (!line.toUpperCase().equals("EXIT")) {
				System.out.println("Enter message to send (type '!EXIT' to quit).");
				String message = inKb.nextLine();
				serverOutput.writeBytes(message);
				String response = serverInput.readLine();
				System.out.println(response);
			}

			connectionSock.close();
			serverInput.close();
			serverOutput.close();
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

}
}
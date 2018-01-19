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


	public static void main(String[] args) {
		int port = 8976;
		String hostName;

		Scanner inKb = new Scanner(new InputStreamReader(System.in));
		String line = "";

		BufferedReader serverInput;
		DataOutputStream serverOutput;
		Socket connectionSock;

		try {
			System.out.println("Connecting to server on port: " + port + ", enter host address:");
			hostName = inKb.nextLine();

			//Creates a stream socket and connects it to the specified port number on the named host.
			connectionSock = new Socket(hostName, port);

			serverInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			serverOutput = new DataOutputStream(connectionSock.getOutputStream());

			while (true) {
				System.out.println("Enter message to send (type '!EXIT' to quit).");
				String message = inKb.nextLine();
				if (message.equals("!EXIT")) {
					break;
				}
				serverOutput.writeBytes(message);
				String response = serverInput.readLine();
				System.out.println(response);
			}

			serverOutput.writeBytes("!EXIT");
			connectionSock.close();
			serverInput.close();
			serverOutput.close();
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}


}

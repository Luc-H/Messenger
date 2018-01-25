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

	private static String hostName;
	private static int port = 8976;

	public static void main(String[] args) {
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

			while (!line.toUpperCase().equals("EXIT")) {
				System.out.println("Enter message to send or type '!EXIT' to quit.");
				String message = inKb.nextLine();
				serverOutput.writeBytes(message + "\n");
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
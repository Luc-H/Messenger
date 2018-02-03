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
	private static String name;

	public static void main(String[] args) {
		Scanner inKb = new Scanner(new InputStreamReader(System.in));
		String message = "";
		System.out.println("Please enter your name:");
		name = inKb.nextLine();

		BufferedReader serverOutput;
		DataOutputStream serverInput;
		Socket connectionSock;

		try {
			System.out.println("Connecting to server on port: " + port + ", enter host address:");
			hostName = inKb.nextLine();

			//Creates a stream socket and connects it to the specified port number on the named host.
			connectionSock = new Socket(hostName, port);

			serverOutput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			serverInput = new DataOutputStream(connectionSock.getOutputStream());

			//First input to server is client name.
			serverInput.writeBytes(name + "\n");

			Thread readLoop = new Thread((new Runnable() {
				@Override
				public void run() {
					while(!Thread.interrupted()){
						try {
							System.out.println(serverOutput.readLine());
						}
						catch (IOException e) {
							System.err.println(e.getMessage());
						}
					}
				}
			}));

			readLoop.start();

			while (true) {
				System.out.println("Enter message to send or type '!EXIT' to quit.");
				message = inKb.nextLine();
				if (message.equals("!EXIT")) {
					break;
				}
				serverInput.writeBytes(message + "\n");
			}

			inKb.close();
			readLoop.interrupt();
			connectionSock.close();
			serverOutput.close();
			serverInput.close();
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}



}
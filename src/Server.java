import java.util.Date;
import java.util.Scanner;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


/**
 * Implements general server duties.
 */
public class Server {

	private static int portNum;

	Server(int portNum) {
		this.portNum = portNum;
		initialise();

	}

	/**
	 * Initialises the server
	 */
	private void initialise() {
		try {
			System.out.println("Initialising server on port " + portNum + ".");

			ServerSocket serverSock = new ServerSocket(portNum);

			//ServerSocket.accept() returns the socket connecting to this server, allows the client to connect proactively.
			Socket connectionSock = serverSock.accept();

			// Use a BufferedReader as it reads the data stream rather than blocking until
			// receiving an end of line character.
			BufferedReader clientInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			DataOutputStream clientOutput = new DataOutputStream(connectionSock.getOutputStream());

			System.out.println("Waiting for client to respond.");

			//TODO Implement Client data.
			//TODO Receive and process data from clients.

		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}


	public static void main(String[] args){
		System.out.println("Running standalone Client.");
		Scanner in = new Scanner(System.in);
		
		portNum = in.nextInt();
	}
}


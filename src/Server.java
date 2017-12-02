import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Implements general server duties.
 */
public class Server {

	private int portNum;

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
			Socket connectionSock = serverSock.accept();

			// Use a BufferedReader as it reads the data stream rather than blocking until
			// receiving an end of line character.
			BufferedReader clientInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));

		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}

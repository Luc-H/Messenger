import java.io.BufferedReader;
import java.io.DataOutputStream;
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

			//ServerSocket.accept() returns the socket connecting to this server, allows the client to connect proactively.
			Socket connectionSock = serverSock.accept();

			// Use a BufferedReader as it reads the data stream rather than blocking until
			// receiving an end of line character.
			BufferedReader clientInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			DataOutputStream clientOutput = new DataOutputStream(connectionSock.getOutputStream());

			System.out.println("Waiting for client to respond.");

			String clientMessage = clientInput.readLine();
			System.out.println("Received from client: "+clientMessage);
			clientOutput.writeBytes("Received: "+clientMessage);

			clientOutput.close();
			clientInput.close();
			connectionSock.close();
			serverSock.close();

		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}


	public static void main(String[] args){
		System.out.println("Running standalone Client on port 8976.");
      
      try {
			System.out.println("Initialising server on port " + 8976 + ".");
			ServerSocket serverSock = new ServerSocket(8976);

		  while (true) {
			  //ServerSocket.accept() returns the socket connecting to this server, allows the client to connect proactively.
			  Socket connectionSock = serverSock.accept();
			  System.out.println("Connection received");
			  ClientHandler handler = new ClientHandler(connectionSock);
			  Thread newThread = new Thread(handler);
			  newThread.start();

		  }


		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}


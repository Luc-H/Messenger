import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Implements general server duties.
 */
public class Server {


	private static int portNum = 8976;
	private static ClientHandler handler;

	public static void main(String[] args){
		System.out.println("Running standalone Client on port 8976.");
		System.out.println("Running standalone Client on port 8976.");
		handler = new ClientHandler();

		try {
			System.out.println("Initialising server on port " + portNum + ".");
			ServerSocket serverSock = new ServerSocket(portNum);

		  while (true) {
			  //ServerSocket.accept() returns the socket connecting to this server, allows the client to connect proactively.
			  Socket connectionSock = serverSock.accept();
			  System.out.println("Connection received");

			  handler.addClientConnection(connectionSock);
		  }


		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}


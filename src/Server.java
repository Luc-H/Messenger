import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Implements general server duties.
 */
public class Server {


	private static final int portNum = 8976;


	public static void main(String[] args) {
		try {
			System.out.println("Initialising server on port " + portNum + ".");

			ServerSocket serverSock = new ServerSocket(portNum);

			while (true) {
				//ServerSocket.accept() returns the socket connecting to this server, allows the client to connect proactively.
				Socket connectionSock = serverSock.accept();

				ClientHandler newClient = new ClientHandler(connectionSock);
				Thread newThread = new Thread(newClient);
				newThread.start();
			}

			//System.out.println("Server closing.");

			//serverSock.close();

		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}


}



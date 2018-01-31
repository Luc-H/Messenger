import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Implements general server duties.
 */
public class Server {


	private static int portNum = 8976;
	//Should be a threadpool
	private static ArrayList<ClientHandler> clientPool;

	public static void main(String[] args) {
		clientPool = new ArrayList<>(5);

		try {
			System.out.println("Initialising server on port " + portNum + ".");
			ServerSocket serverSock = new ServerSocket(portNum);

			while (true) {
				//ServerSocket.accept() returns the socket connecting to this server, allows the client to connect proactively.
				Socket connectionSock = serverSock.accept();
				System.out.println("Connection received");
				ClientHandler handler = new ClientHandler(connectionSock);
				Thread newThread = new Thread(handler);
				newThread.start();
				clientPool.add(handler);
				showClients();
			}


		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void broadcastMessage(String message) {
		for (ClientHandler c : clientPool) {
			System.out.println("Sending message to " + c.getName());
			c.sendMessage(message);
		}
	}

	public static void showClients() {
		for (ClientHandler c : clientPool) {
			System.out.println(c.getName());
		}
	}


}


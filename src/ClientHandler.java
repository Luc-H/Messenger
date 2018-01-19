import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler {

	private ArrayList<ClientConnection> connectedClients;

	ClientHandler() {
		connectedClients = new ArrayList<>();
	}


	public void addClientConnection(Socket connectionSock) {
		connectedClients.add(new ClientConnection(connectionSock));
	}
}

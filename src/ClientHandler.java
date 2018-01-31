import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {

	private Socket connectionSock;
	private String name;
	private BufferedReader clientOutput;
	private DataOutputStream clientInput;

	ClientHandler(Socket connectionSock) {
		this.connectionSock = connectionSock;
	}

	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run() {
		try {
			// Use a BufferedReader as it reads the data stream rather than blocking until
			// receiving an end of line character.
			clientOutput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			clientInput = new DataOutputStream(connectionSock.getOutputStream());

			//First output from client is client name.
			setName(clientOutput.readLine());


			while (true) {
				System.out.println("Waiting for client(" + name + ") to send data.");
				String clientMessage = clientOutput.readLine();
				if (clientMessage.equals("!EXIT")) {
					break;
				}
				System.out.println("Received from client:(" + name + ") " + clientMessage);
				Server.broadcastMessage(clientMessage);

			}

			clientInput.close();
			clientOutput.close();
			connectionSock.close();


		}
		catch (IOException ex) {
			System.err.println("Issue communicating with client(" + name + ")\n\n" + ex.getMessage());
		}
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void sendMessage(String message) {
		try {
			System.out.println("passing msg over socket to " + name);
			clientInput.writeBytes(message + "\n");
		}
		catch (IOException e) {
			System.out.println("Unable to send message: " + message + "\n");
			e.printStackTrace();
		}
	}
}

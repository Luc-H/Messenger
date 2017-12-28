import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {

	private Socket connectionSock;

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
			BufferedReader clientInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			DataOutputStream clientOutput = new DataOutputStream(connectionSock.getOutputStream());

			while (true) {
				System.out.println("Waiting for client to send data.");
				String clientMessage = clientInput.readLine();
				if (clientMessage.equals("!EXIT")) {
					break;
				}
				System.out.println("Received from client: " + clientMessage);
				clientOutput.writeBytes("Received: " + clientMessage + "\n");
			}

			clientOutput.close();
			clientInput.close();
			connectionSock.close();


		}
		catch (IOException ex) {
			System.err.println("Issue communicating with client\n\n" + ex.getMessage());
		}
	}

}

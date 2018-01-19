import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created: Dec 2017
 * luc
 */
public class ClientHandler implements Runnable {

	private Socket connectionSock;

	public ClientHandler() {
		this(null);
	}

	public ClientHandler(Socket connectionSock) {
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
		BufferedReader clientInput = null;
		DataOutputStream clientOutput = null;
		String clientMessage = "";

		while (!clientMessage.equals("!EXIT")) {
			try{
				// Use a BufferedReader as it reads the data stream rather than blocking until
				// receiving an end of line character.
				clientInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
				clientOutput = new DataOutputStream(connectionSock.getOutputStream());


				System.out.println("Waiting for client to send data.");
				clientMessage = clientInput.readLine();
				System.out.println("Received from client: " + clientMessage);

				clientOutput.writeBytes("Received: " + clientMessage + "\n");
			}
			catch (IOException e) {
				System.err.println("Unable to read/write client data:");
				e.printStackTrace();
			}
		}

		try {
			clientOutput.close();
			clientInput.close();
			connectionSock.close();
		}
		catch (IOException e) {
			System.err.println("Error closing connections:");
			e.printStackTrace();
		}
	}

}

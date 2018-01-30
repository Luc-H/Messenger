import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {

	private Socket connectionSock;
	private String name;

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
			BufferedReader clientOutput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			DataOutputStream clientInput = new DataOutputStream(connectionSock.getOutputStream());
			name = clientOutput.readLine();
			//First output from client is client name.
			Thread.currentThread().setName(name);

			while (true) {
				System.out.println("Waiting for client(" + name + ") to send data.");
				String clientMessage = clientOutput.readLine();
				if (clientMessage.equals("!EXIT")) {
					break;
				}
				System.out.println("Received from client:(" + name + ") " + clientMessage);
				clientInput.writeBytes("Received: " + clientMessage + "\n");
			}

			clientInput.close();
			clientOutput.close();
			connectionSock.close();


		}
		catch (IOException ex) {
			System.err.println("Issue communicating with client(" + name + ")\n\n" + ex.getMessage());
		}
	}

}

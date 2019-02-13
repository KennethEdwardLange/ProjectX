package projectx.game.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import projectx.engine.io.IO;

public class OFileServer {
	int port = 4040;
	// String address = "10.24.226.239";
	String address = "localhost";
	ServerSocket server;
	File worldFile;
	ArrayList<ConnectedClients> clientList;
	byte[] b;
	int numusers;

	private void init() {
		clientList = new ArrayList<ConnectedClients>();
		try {
			server = new ServerSocket(port);
			IO.println(server);
			String filename = filenameGen();
			worldFile = new File(filename);
			worldFile.createNewFile();
			run();
		} catch (IOException e) {

			IO.printStackTrace(e);
			IO.println("Could not listen on port: " + port + " or could not create worldFile.txt");
			System.exit(-1);
		}
	}

	public static void main(String[] args) {
		new OFileServer();
	}

	public OFileServer() {
		init();
	}

	private class ClientChecker implements Runnable {

		OFileServer server;
		Socket socket;
		int id;

		public ClientChecker(OFileServer server, Socket socket, int id) {
			this.server = server;
			this.socket = socket;
			this.id = id;
		}

		public void run() {

			while (true) {
				try {

					FileInputStream in = (FileInputStream) socket.getInputStream();
					

					// InputStreams need to use byte arrays to read them.
					if (in.available() > 0) {
						File temp = new File("temp.txt");
						receiveFile(socket, temp);
						IO.println(b.length);
						String message = new String(b);
						IO.println(message);
						IO.println(
								"-------------------------------------------------------------------------------------------------");
						if (message.contains("server.shutdown")) {
							IO.println("shutting down");
							close();
							return; // Stops the Server.
						} else if (message.contains("getWorldFile")) {
							IO.println("Attempting to send file");
							server.fileMessenger(id);
						} else {
							BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(worldFile));
							BufferedInputStream bin = new BufferedInputStream(new FileInputStream(temp));
							reader(bin, bout);
							IO.println("World file updated");
							
						}
						IO.println(message);

					}

				} catch (IOException ioe) {
					IO.printStackTrace(ioe);
				}
			}
		}
	}

	public void close() {
		System.exit(-1);
	}

	/**
	 * Helper class to receive initial world file from host user
	 * 
	 * @param b
	 */
	private void receiveFile(Socket s, File temp) {

		try {
			BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(temp));
			BufferedInputStream bin = new BufferedInputStream(s.getInputStream());
			IO.println("File received...");
			reader(bin, bout);
		} catch (FileNotFoundException e) {
			IO.printStackTrace(e);
		} catch (IOException e) {
			IO.printStackTrace(e);
		}
	}

	/**
	 * Helper method to generate a unique world file based on current time
	 * 
	 * @return
	 */
	private String filenameGen() {
		String filename = "save" + java.time.LocalDateTime.now();
		filename = filename.replace(":", "");
		filename = filename.replace(".", "");
		filename = filename.replace("-", "");
		filename += ".txt";
		IO.println(filename);
		return filename;
	}

	/**
	 * 
	 * @author Kenenth Lange
	 *
	 *
	 *         This helper class is used by the server to keep track of which client
	 *         is which based on an id system.
	 * 
	 */
	private class ConnectedClients {
		Socket socket;
		int id;

		public ConnectedClients(Socket socket, int id) {
			this.socket = socket;
			this.id = id;
		}

		/**
		 * This helper method is used when checking who sent the message and used to see
		 * who hasn't yet seen the message sent by that user.
		 * 
		 * @param id
		 * @return
		 */
		public boolean checkId(int id) {
			return this.id == id;
		}

		/**
		 * This method sends a string back to a client
		 * 
		 * @param s
		 */

		private byte[] sendFile() {
			try {
				BufferedInputStream bin = new BufferedInputStream(new FileInputStream(worldFile));
				BufferedOutputStream bout = new BufferedOutputStream(socket.getOutputStream());
				IO.println("File sent");
				return reader(bin, bout);
			} catch (IOException e) {
				IO.printStackTrace(e);
			}
			return null;

		}
	}

	/**
	 * This method is responsible for taking a users input and sending it to all the
	 * other users. It also uses an id so it doesn't receive the message that it
	 * sends.
	 * 
	 * @param string
	 * @param id
	 */
	public void fileMessenger(int id) {
		IO.println("Still sending file...");
		for (int i = 0; i < clientList.size(); i++) {
			if (clientList.get(i).checkId(id)) {
				clientList.get(i).sendFile();
			}
		}
	}

	/**
	 * This method runs the server on the given socket and starts a new thread to be
	 * used in that socket.
	 */
	private void run() {
		while (true) {

			Socket socket = null;
			try {
				socket = server.accept();

				clientList.add(new ConnectedClients(socket, ++numusers));
				// This thread keeps track of the socket it is using and the number of users
				// that is currently connected to that socket.
				Thread t = new Thread(new ClientChecker(this, socket, numusers));
				t.start();

			} catch (IOException ioe) {
				IO.printStackTrace(ioe);
				System.exit(-1);
			}

		}
	}

	private byte[] reader(BufferedInputStream bin, BufferedOutputStream bout) {
		try {
			b = new byte[50000];
			int count = 1;
			while ((count = bin.read(b)) > 0) {
				bout.write(b, 0, count);
				IO.println(count);
//				break;
			}
			IO.println("stream read complete");
		} catch (IOException e) {
			IO.printStackTrace(e);
		}
		return null;
	}

}

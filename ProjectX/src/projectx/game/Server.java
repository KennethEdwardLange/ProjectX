package projectx.game;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import projectx.engine.io.IO;

/**
 * 
 * @author Max Medberry, Kenneth Lange
 * 
 *         Creates a server over port 4444 that facilitates multi-player
 *         functionality in games and database communication.
 * 
 */
public class Server {

	// Global variables for easy access and convenience for editing
	int port = 4444;
	String address = "10.24.226.239";
	ServerSocket server;
	int numusers;

	ArrayList<ConnectedClients> clientList;
	// ArrayList<ServerEntity> entityList; // arrayList holding the data of entities
	// in the game

	public Server() {
		init();
	}

	/**
	 * This method initializes the sever class for a proper setup
	 */
	private void init() {
		clientList = new ArrayList<ConnectedClients>();
		server = null;
		numusers = 0;

		try {
			server = new ServerSocket(port);
			IO.println(server.toString());
			run();
		} catch (IOException e) {
			IO.println("Could not listen on port: " + port);
			System.exit(-1);
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
				ioe.printStackTrace();
				System.exit(-1);
			}

		}
	}

	public void close() {
		System.exit(-1);
	}

	/**
	 * 
	 * @author Kenneth Lange
	 *
	 *         This helper class is used by the Server class to check if any other
	 *         clients are trying to connect Then it tries to read data by sent by
	 *         that client and return the data it receives back to other clients.
	 * 
	 */
	private class ClientChecker implements Runnable {

		Server server;
		Socket socket;
		int id;

		public ClientChecker(Server server, Socket socket, int id) {
			this.server = server;
			this.socket = socket;
			this.id = id;
		}

		public void run() {

			while (true) {
				try {

					BufferedInputStream bin = new BufferedInputStream(socket.getInputStream());
					byte[] b = new byte[1200];// 1200

					// InputStreams need to use byte arrays to read them.
					if (bin.available() > 0) {
						bin.read(b);
						server.messenger(b, id);
						String message = new String(b);
						if (message.contains("server.shutdown")) {
							close();
							return; // Stops the Server.
						}
//						if(b[0] > 0)
//							IO.println(Arrays.toString(b));
					}

				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
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
	public void messenger(byte[] b, int id) {
		byte[] b2 = new byte[10000];
		System.arraycopy(b, 0, b2, 0, b.length);

		for (int i = 0; i < clientList.size(); i++) {
			if (!clientList.get(i).checkId(id))// If the id matches the client, it skips sending the message back to
												// that client.
				clientList.get(i).sendString(b2);
		}
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
		public void sendString(byte[] b) {
			try {
				BufferedOutputStream bout = new BufferedOutputStream(socket.getOutputStream());
				bout.write(b);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Server();

	}

	/*
	 * private class ServerEntity { private String name; private int x; private int
	 * y; private byte health; private boolean pc; private byte dir;
	 * 
	 * public ServerEntity(String name, int x, int y, byte health, boolean pc, byte
	 * dir) { this.name = name; this.x = x; this.y = y; this.health = health;
	 * this.pc = pc; }
	 * 
	 * public boolean equals(String s) { if (name.equals(s)) { return true; } else
	 * return false; }
	 * 
	 * public void updatePos(int newx, int newy) { x = newx; y = newy; }
	 * 
	 * public void updateHealth(byte h) { health = h; }
	 * 
	 * public void updatePC(boolean newpc) { pc = newpc; }
	 * 
	 * public void updateDir(byte newdir) { dir = newdir; }
	 * 
	 * }
	 */
}
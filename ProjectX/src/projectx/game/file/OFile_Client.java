package projectx.game.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

import projectx.engine.io.IO;


public class OFile_Client {

	int port = 4040;

	String address = "10.24.226.239";
//	String address = "localhost";
	Socket socket;
	Scanner scanner;

	public OFile_Client() {
		init();
	}

	private void init() {
		try {
			socket = new Socket();
			SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(getAddress()), getPort());
			socket.connect(socketAddress);
			if (socket != null) {
				IO.println(socket.toString());
				run();
			}

		} catch (IOException ioe) {
			IO.printlnErr(" " + ioe + "\nUnable to  connect to '" + getAddress() + "' server on port '" + getPort()
					+ "' \nPlease make sure that the server is running.");
		}
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public void run() {
		// Starts a new thread for the client to stay active to communicate with the
		// server and it's clients
		Thread t = new Thread(new ServerScanner(socket));
		t.start();
	}

	/**
	 * Ends the client
	 */
	public void end() {
		System.exit(0);
	}

	/**
	 * 
	 * @author Kenneth Lange
	 *
	 *         This class is directly used by the Client class for receiving the
	 *         input stream back from the server that it connects to.
	 */
	private class ServerScanner implements Runnable {
		Socket socket;

		public ServerScanner(Socket socket) {
			this.socket = socket; // Used to make the socket transferable from one class to the next.
		}

		@Override
		public void run() {
			try {

				while (socket != null && !socket.isClosed()) {

					FileInputStream stream = (FileInputStream) socket.getInputStream();
					byte[] data = new byte[16000];

					if (stream.available() > 0) {
						stream.read(data);
						File worldFile = new File(filenameGen());
						FileOutputStream outstream = new FileOutputStream(worldFile);
						outstream.write(data);
						outstream.close();
					}

				}
			} catch (IOException e) {
				IO.printStackTrace(e);
			}
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
		return filename;
	}

	/**
	 * sends a key to the server asking to receive the world file
	 */
	public void getFile() {
		try {
			String s = "getWorldFile";
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(s.getBytes());
			IO.println("file received");
		} catch (IOException e) {
			IO.printStackTrace(e);
		}
	}

	public void sendFile(String s) {
		try {
			File file = new File(s);
			FileInputStream fin = new FileInputStream(file);
			byte[] b = new byte[16000];
			fin.read(b);
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(b);
			fin.close();
			IO.println("file " + s + " sent");
		} catch (IOException e) {
			System.out.println("could not send file");
		}
	}

	/**
	 * method to shutdown server from client side
	 */
	public void shutdown() {
		try {
			String s = "server.shutdown";
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(s.getBytes());
			IO.println("Server terminated remotely");
		} catch (IOException e) {
			IO.printStackTrace(e);
		}
	}

	public static void main(String[] args) {
		OFile_Client f = new OFile_Client();
		Scanner s = new Scanner(System.in);
		IO.println("Enter a command: ");
		String next = s.next();
		while (!next.equals("end")) {
			if (next.equals("send")) {
				IO.println("Enter filename: ");
				next = s.next();
				f.sendFile(next);
			} else if (next.equals("get")) {
				f.getFile();
			} else if (next.equals("end"))
				break;
			else if (next.equals("shutdown")) {
				f.shutdown();
			} else
				IO.println("try again");
			next = s.next();
		}
		s.close();

	}
	
	private static void reader(BufferedInputStream bin, BufferedOutputStream bout) {
		try {
			byte[] b = new byte[50000];
			int count;
			while (!((count = bin.read(b)) <= 0)) {
				bout.write(b, 0, count);
			}
		} catch (IOException e) {
			IO.printStackTrace(e);
		}
	}
}

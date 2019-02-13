package projectx.game.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.file.Files;

public class Client {

	String LOCAL_HOST = "localhost";

	int port;
	String host;

	Thread t;
	ServerScanner scanner;

	Socket socket;

	public Client() {
	}

	public boolean connect(String host, int port) {
		try {
			socket = new Socket();

			SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(host), port);

			socket.connect(socketAddress);
			if (socket != null) {
				return true;
			} else
				return false;

		} catch (IOException e) {
			System.out.println("Connection Unsuccessful");
			return false;
		}
	}

	public String socketToString() {
		return socket.toString();
	}

	public void run() {
		scanner = new ServerScanner(socket);
		t = new Thread(scanner);
		t.start();
	}

	public Socket getSocket() {
		return socket;
	}

	public boolean isDataAvailable() {
		if(scanner == null) return false;
		return scanner.isDataAvailable();
	}

	public byte[] getData() {
		return scanner.getData();
	}

	public void end() {
		if (socket != null && !socket.isClosed()) {
			stop();
		}
	}

	public void stop() {
		System.out.println("Closing socket");
		t.interrupt();
		t = null;
		try {
			socket.close();
			socket = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getConnected() {
		if (socket == null)
			return "null";
		return "" + socket.isConnected();
	}

	public boolean isConnected() {
		if (socket == null)
			return false;
		return socket.isConnected();
	}

	public String getClosed() {
		if (socket == null)
			return "null";
		return "" + socket.isClosed();
	}

	public String printSocket() {
		if (socket == null)
			return "null";
		return socket.toString();
	}

	public boolean sendBytes(byte[] packet) {
		if(socket.isClosed()) return false;
		try {
			BufferedOutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
			outputStream.write(packet);

		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public boolean sendFile(String filename) {
		byte[] file;
		try {
			file = Files.readAllBytes(new File(filename).toPath());
			sendBytes(file);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private class ServerScanner implements Runnable {

		private Socket socket;

		private boolean dataAvailable;

		private byte[] dataReady;

		public ServerScanner(Socket socket) {
			this.socket = socket;

			dataAvailable = false;

			dataReady = null;
		}

		@Override
		public void run() {
			try {
				while (socket != null && !socket.isClosed()) {

					BufferedInputStream stream = new BufferedInputStream(socket.getInputStream());
					byte[] data = new byte[1200];//62

					if (stream.available() > 0) {
						stream.read(data);
						if(data[0] > 0) {
							dataAvailable = true;
							dataReady = data;
						}
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public boolean isDataAvailable() {
			return dataAvailable;
		}

		public byte[] getData() {
			dataAvailable = false;
			return dataReady;
		}
	}
}

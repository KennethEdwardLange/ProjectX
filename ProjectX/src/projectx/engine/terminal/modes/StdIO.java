package projectx.engine.terminal.modes;

import java.util.Scanner;

import projectx.engine.Handler;
import projectx.engine.glgfx.Graphics;
import projectx.engine.io.IO;
import projectx.engine.terminal.Terminal;

public class StdIO extends Mode {
	
	StdInput stdinput;
	Thread t;

	public StdIO(Terminal terminal, Handler handler) {
		super(terminal, handler);
	}
	
	@Override
	public void init() {
		stdinput = new StdInput(this);
		t = new Thread(stdinput);
	}
	
	public void run() {
		t.start();
	}

	
	@Override
	public void stop() {
		stdinput.stop();
		t.interrupt();
	}

	/**
	 * Not used
	 */
	@Override public void update() {}
	
	@Override public void returnCommand() {}

	@Override public void render(Graphics g) {}

	@Override
	public void print(String s) {
		IO.println(s);
	}

	@Override
	public boolean validate() {
		return System.console() != null;
	}

	@Override
	public String toString() {
		return "Standard Input/Output";
	}

	private class StdInput implements Runnable {

		StdIO stdio;

		boolean running;

		Scanner scanner;

		public StdInput(StdIO stdio) {
			this.stdio = stdio;
			running = true;

			scanner = new Scanner(System.in);
			IO.println("Terminal Started...Type Commands");
		}

		@Override
		public void run() {
			while (running) {
				System.out.print(">");

				String command = scanner.nextLine();

				stdio.terminal.runCommand(command);
			}
		}

		public void stop() {
			running = false;
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
}

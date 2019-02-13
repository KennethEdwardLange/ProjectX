package projectx.engine.terminal;

import projectx.engine.Handler;
import projectx.engine.debug.Debug;
import projectx.engine.managers.entities.creatures.Creature;
import projectx.engine.managers.entities.nonmoving.Nonmoving;
import projectx.engine.terminal.TerminalExceptions.InvalidCommandException;
import projectx.game.enums.CreatureType;
import projectx.game.enums.NonmovingType;

public class ManualParser {
	
private Handler handler;
	
	Terminal terminal;
	
	
	public ManualParser(Handler handler, Terminal terminal) {
		this.handler = handler;
		this.terminal = terminal;
	}
	
	private void sendError(String message) {
		terminal.print(message);
	}
	
	public void run(String command) throws InvalidCommandException{
		String[] args = command.split(" ");
		if(args.length == 0) throw new InvalidCommandException("No Command Given", terminal);
		switch(args[0]) {
			case "":
				throw new InvalidCommandException("No Command Given", terminal);
			case "echo":
				echo(command);
			break;
			case "exit":
				handler.getEngine().exit();
			break;
			case "join":
				join(args);
			break;
			case "host":
				host(args);
			break;
			case "disconnect":
				disconnect();
			break;
			case "spawn":
				spawn(args[1], args[2], args[3]);
			break;
			case "kill": 
				kill(args[1]);
			break;
			case "list":
				list(args[1]);
			break;
			case "client":
				client(args, command);
			break;
			case "toggle":
				toggle(args[1]);
			return;
			
			default:
				sendError("Invalid Command");
			break;
		}
	}

	private void kill(String string) {
		switch(string) {
		case "rock":
		break;
		case"shell":
			
		break;
		default:
			sendError("Invalid Command");
		}
	}

	private void toggle(String string) {
		switch(string) {
		case "debug":
			Debug.setDEBUGMODE();
		break;
		default:
			sendError("Invalid Command");
		}
	}

	private void client(String[] args, String command) throws InvalidCommandException {
		if(args.length == 0) throw new InvalidCommandException("No Command Given", terminal);
		switch(args[1]) {
			case "printsocket":
				terminal.print(handler.getEngine().getDataProtocol().getClient().printSocket());
			break;
			case "send":
				handler.getEngine().getDataProtocol().getClient().sendBytes(command.substring(12).getBytes());
			break;
			default:
				sendError("Invalid Command");
			break;
		}
	}
	
	private void list(String s) {
		switch(s) {
		case "entities":
			terminal.print(handler.getWorld().getEntityManager().toString());
		break;
		default:
			sendError("Invalid Command");
		}
	}
	
	private void spawn(String type, String x, String y) {
		if(handler.getWorld() != null) {
			switch(type) {
			case "shell":
				handler.getWorld().getEntityManager().addEntity(new Creature(handler, Integer.parseInt(x), Integer.parseInt(y), CreatureType.PShell));
			break;
			case "rock":
				handler.getWorld().getEntityManager().addEntity(new Nonmoving(handler, Integer.parseInt(x), Integer.parseInt(y), NonmovingType.Rock));
			break;
			default:
				sendError("Invalid Command");
			}
		}
	}

	private void join(String[] args) throws InvalidCommandException {
		if(args.length == 1) {
			if(!handler.getEngine().getDataProtocol().join()) {
				 throw new InvalidCommandException("Connection unsuccessful", terminal);
			}
			else {
				terminal.print("Connection Successful");
				terminal.print(handler.getEngine().getDataProtocol().getClient().socketToString());
			}
		} else if(args.length == 3) {
			if(!handler.getEngine().getDataProtocol().join(args[1], Integer.parseInt(args[2]))) {
				 throw new InvalidCommandException("Connection unsuccessful", terminal);
			}
			else {
				terminal.print("Connection Successful");
				terminal.print(handler.getEngine().getDataProtocol().getClient().socketToString());
			}
		}
		else 
			throw new InvalidCommandException("Invalid command", terminal);
		
	}
	
	private void host(String[] args) throws InvalidCommandException {
		if(args.length == 1) {
			if(!handler.getEngine().getDataProtocol().host()) {
				 throw new InvalidCommandException("Connection unsuccessful", terminal);
			}
			else {
				terminal.print("Connection Successful");
				terminal.print(handler.getEngine().getDataProtocol().getClient().socketToString());
			}
		} else if(args.length == 3) {
			if(!handler.getEngine().getDataProtocol().host(args[1], Integer.parseInt(args[2]))) {
				 throw new InvalidCommandException("Connection unsuccessful", terminal);
			}
			else {
				terminal.print("Connection Successful");
				terminal.print(handler.getEngine().getDataProtocol().getClient().socketToString());
			}
		}
		else 
			throw new InvalidCommandException("Invalid command", terminal);
		
	}
	
	private void disconnect() throws InvalidCommandException {
		handler.getEngine().getDataProtocol().end();
		terminal.print("Disconnected from server");
	}

	private void echo(String command) {
		terminal.print(command.substring(5));
	}
}

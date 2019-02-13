package projectx.engine.terminal;

import java.util.HashMap;

import projectx.engine.Handler;
import projectx.engine.terminal.TerminalExceptions.InvalidCommandException;
import projectx.engine.terminal.TerminalExceptions.ProcConflictException;
import projectx.engine.terminal.TerminalExceptions.ProcsNotFoundException;
import projectx.engine.utils.Utils;

public class CommandParser {
	
	public final String PROCREGFILE 	= "res/epilogos/procreg.txt";
	public final String PROCWD 			= "projectx/engine/terminal/procs/";
	public final String PROCPACKAGEWD	= "projectx.engine.terminal.procs.";	
	public final String MANWD 			= "res/epilogos/man/";
	boolean VERBOSE_INIT 				= true;
	
	private Handler handler;
	
	Terminal terminal;
	
	HashMap<String, String> package_map;
	
	public CommandParser(Handler handler, Terminal terminal) {
		this.handler = handler;
		this.terminal = terminal;
		
		init();
	}
	
	/**
	 * Initalizes all the varaibles in the class
	 */
	private void init() {		
		terminal.print("Loading classes from " + PROCREGFILE);
		
		updateMap();
		
		if(package_map.size() != 0)
			terminal.print("Classes loaded");
		else
			terminal.print("No classes loaded");
	}
	
	public void updateMap() {
		package_map = new HashMap<String, String>();
		
		try {
			loadClasses();
		} catch (Exception e) {}
	}
	
	/**
	 * Loads all the procs from the procreg file into a hashmap to be referenced when the user types a command. 
	 * If the command is not in the procreg file or is not where is says, it is not loaded.
	 */
	private void loadClasses() throws ProcConflictException, ProcsNotFoundException {
		String[] lines = Utils.loadFileAsArray(PROCREGFILE);
		if(lines.length == 0)
			throw new ProcsNotFoundException("Could not load any procs, reg may not be found or empty", terminal);
		for (int i = 0; i < lines.length; i++) {
			processLine(lines[i]);
		}
	}
	
	/**
	 * Checks to see if the line references a real proc file in the package
	 * @param line
	 * @throws ProcConflictException
	 */
	private void processLine(String line) throws ProcConflictException {
		line = line.substring(0, (line.indexOf('#') == -1 ? line.length() : line.indexOf('#'))).replaceAll("\t", "").replaceAll(" ", "");
		if(line.length() != 0 && verifyClass(PROCPACKAGEWD + line)) {
			String classname;
			String path = line;
			if(path.indexOf('.') != -1) {
				classname = path.substring(path.lastIndexOf('.') + 1, path.length());
			} else {
				classname = path;
			}
			if(package_map.get(classname) == null) {
				if(VERBOSE_INIT)
					terminal.print("Loaded " + path);
				package_map.put(classname, path);
			} else {
				throw new ProcConflictException(package_map.get(classname), path, terminal);
			}
		}
	}
	
	/**
	 * Verifies if the given file path references a real class that can be run
	 * @param path
	 * @return
	 */
	public boolean verifyClass(String path) {
		Class clazz;
		try {
			clazz = Class.forName(path);
		} catch (ClassNotFoundException e) {return false;}
		
		Proc process;
        try {
        	process = (Proc) clazz.newInstance();
		} catch (InstantiationException e) {return false;} 
        catch (IllegalAccessException e) {return false;}
        
		return process.verify();
	}
	
	/**
	 * Returs the Proc object from a file path as a string
	 * @param path
	 * @return
	 */
	private Proc getClassFromPath(String path) {
		if(path == null) return null;
		Class clazz;
		try {
			clazz = Class.forName(path);
		} catch (ClassNotFoundException e) {return null;}
		
		Proc process;
        try {
        	process = (Proc) clazz.newInstance();
		} catch (InstantiationException e) {return null;} 
        catch (IllegalAccessException e) {return null;}
        
		return process;
	}
	
	/**
	 * Runs the given command
	 * @param command
	 * @throws InvalidCommandException
	 */
	public void run(String command) throws InvalidCommandException{
		String[] temp = Utils.stringToStringArray(command, " ");
		String[] string_args = new String[temp.length - 1];
		System.arraycopy(temp, 1, string_args, 0, string_args.length);
		String proc = temp[0];
		byte[] byte_args = Utils.stringArrayToByteArray(string_args, " ");
		Proc process  = getClassFromPath(PROCPACKAGEWD + getPath(proc));
		if(process == null) {terminal.print("invalid command [" + command + "]");return;}
		if(process.verify()) {
			String s = process.run(byte_args, handler);
			if(s != null)
				terminal.print(s);
		}
	}
	
	/**
	 * Runs the given command with the given byte[] args
	 * @param name
	 * @param byte_args
	 * @throws InvalidCommandException
	 */
	public void run(String name, byte[] byte_args) throws InvalidCommandException{
		Proc process  = getClassFromPath(PROCPACKAGEWD + getPath(name));
		if(process == null) {
			terminal.print("invalid command [" + name + "]");
			return;
		}
		if(process.verify()) {
			String s = process.run(byte_args, handler);
			if(s != null)
				terminal.print(s);
		}
	}
	
	/**
	 * Returns the name of the class given the path of the file
	 * @param packagePath
	 * @return
	 */
	public String getClassName(String packagePath) {
		packagePath = packagePath.replaceAll("/", ".");
		String classname;
		if(packagePath.indexOf('.') != -1) {
			classname = packagePath.substring(packagePath.lastIndexOf('.') + 1, packagePath.length());
		} else 
			classname = packagePath;
		return classname;
	}
	
	/**
	 * Returns the path of the java file given the name of the class
	 * @param name
	 * @return
	 */
	public String getPath(String name) {
		return (package_map.get(name) == null ? "" : package_map.get(name) );
	}
	
	/**
	 * Returns the current directory of the package path given
	 * @param name
	 * @return
	 */
	public String getDirectory(String name) {
		return getPath(name).replaceAll("\\.", "/");
	}
	
	public String mapToString() {
		return package_map.keySet().toString()	;
	}
}

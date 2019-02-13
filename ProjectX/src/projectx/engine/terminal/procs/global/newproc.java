package projectx.engine.terminal.procs.global;

import java.io.*;
import java.nio.channels.FileChannel;

import projectx.engine.Handler;
import projectx.engine.io.IO;
import projectx.engine.terminal.CommandParser;
import projectx.engine.terminal.Proc;
import projectx.engine.utils.Utils;

public class newproc extends Proc{
	
	String sampleProc = "sampleproc.txt";
	String procwd;
	String procpackagewd;
	
	
	Handler handler;
	
	@Override
	public String run(byte[] byte_args, Handler handler) {
		String[] args = Utils.byteArrayToStringArray(byte_args, " ");
		
		if(args.length != 1) return "invalid arguments, see \'man newproc\' for list options";
		
		this.handler = handler;
		this.procwd = handler.getEngine().getTerminal().getCommandParser().PROCWD;
		this.procpackagewd = handler.getEngine().getTerminal().getCommandParser().PROCPACKAGEWD;
		
		String packagePath = args[0];
		String path = args[0].replaceAll("\\.", "/");
		String absolutePath = "src/" + procwd + path + ".java";
		String name = packagePath.substring(packagePath.lastIndexOf('.') + 1, packagePath.length());
		
		if(procExists(name))
			return "error: proc already exists";
		if(fileExists(absolutePath))
			return "error: file already exists";
		
		createClass(absolutePath, name, packagePath);
		
		registerProc(args[0]);
		
		handler.getEngine().getTerminal().getCommandParser().updateMap();
		
		return "[" + name + "] created";
	}

	public boolean fileExists(String path) {
		File f = new File(path);
		return f.exists() && !f.isDirectory();
	}
	
	public boolean procExists(String name) {
		CommandParser cp = handler.getEngine().getTerminal().getCommandParser();
		IO.println(cp.getPath(name));
		IO.println(cp.PROCPACKAGEWD + cp.getPath(name));
		return cp.verifyClass(cp.PROCPACKAGEWD + cp.getPath(name));
	}
	
	public void createClass(String path, String name, String packagePath) {
		try {
			File newprocFile = new File(path);
			newprocFile.createNewFile();
			
			String samplefile = Utils.loadFileAsString("src/" + procwd + this.sampleProc);
			samplefile = samplefile.replace("$name", name);
			samplefile = samplefile.replace("$packdir", 
					(procpackagewd + packagePath).substring(0, (procpackagewd + packagePath).length() - name.length() - 1));
			FileOutputStream os = new FileOutputStream(newprocFile);
			os.write(samplefile.getBytes());

			os.close();

			IO.println("Created \'" + path + "\'");
		} catch (IOException e) {IO.printStackTrace(e);}
	}
	
	public void registerProc(String localPackagePath) {
		String procregString = handler.getEngine().getTerminal().getCommandParser().PROCREGFILE;
		
		String s = "\n" + localPackagePath;
		int tabs = 6 - (int)(localPackagePath.length() / 4);
		for(int i = 0; i < tabs;i++)s += "\t";
		s += "# Auto generated reg from procreg\n";
		
		Writer output;
		try {
			output = new BufferedWriter(new FileWriter(procregString, true));
			output.append(s);
			output.close();
			
			IO.println("Appended \'" + s + ".man" + "\'");
		} catch (IOException e) {IO.printStackTrace(e);}
	}
}

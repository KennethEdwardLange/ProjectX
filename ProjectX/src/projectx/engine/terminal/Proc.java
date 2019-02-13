package projectx.engine.terminal;

import projectx.engine.Handler;

/**
 * Super class for every process that is run in the terminal, any process MUST inherite the run() method
 * @author m_hop
 *
 */
public class Proc {
	public String run(byte[] args, Handler handler) {
		return "";
	}
	
	public boolean verify() {
		return true;
	}
}

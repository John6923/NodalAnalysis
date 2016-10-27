package nodal.framework;

@SuppressWarnings("serial")
public class InvalidNodeException extends RuntimeException {
	
	public InvalidNodeException(String s) {
		super("Invalid Node: "+s);
	}

}
